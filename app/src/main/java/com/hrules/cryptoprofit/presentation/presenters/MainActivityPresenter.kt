/*
 * Copyright (c) 2017. Héctor de Isidro - hrules6872
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hrules.cryptoprofit.presentation.presenters

import com.hrules.cryptoprofit.data.cache.BaseCache
import com.hrules.cryptoprofit.data.preferences.BasePreferences
import com.hrules.cryptoprofit.presentation.base.mvp.BasePresenter
import com.hrules.cryptoprofit.presentation.base.mvp.BaseView
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import com.hrules.cryptoprofit.presentation.entitites.serializers.CryptoSerializer
import com.hrules.cryptoprofit.presentation.entitites.serializers.MainActivityModelSerializer
import com.hrules.cryptoprofit.presentation.extensions.elementFromJSONArray
import com.hrules.cryptoprofit.presentation.extensions.toOneIfZero
import com.hrules.cryptoprofit.presentation.extensions.withParams
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel
import com.hrules.cryptoprofit.presentation.resources.ResString
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.math.BigDecimal

private const val API_ENDPOINT = "https://api.coinmarketcap.com/v1/ticker/{{cryptoCurrency}}/?convert={{currencyToConvert}}"

class MainActivityPresenter(private val preferences: BasePreferences,
    private val cache: BaseCache<CryptoCurrency, Crypto>) : BasePresenter<MainActivityModel, MainActivityPresenter.Contract>() {
  override fun viewReady(first: Boolean) {
    view?.let {
      it.setCurrencyConverterState(preferences.currencyConverter)
      it.setCurrencyToConvert(preferences.currencyToConvert)
    }

    if (first) {
      getCryptoPrice(CryptoCurrency.valueOf(preferences.cryptoCurrency), preferences.currencyToConvert)
    } else {
      calculate()
    }
  }

  fun calculate(coinPriceInput: BigDecimal, buyPriceInput: BigDecimal, buyAmountInput: BigDecimal, sellPriceInput: BigDecimal) {
    with(model) {
      coinPrice = coinPriceInput.toOneIfZero()
      buyPrice = buyPriceInput
      buyAmount = buyAmountInput.toOneIfZero()
      sellPrice = sellPriceInput
    }
    calculate()
  }

  private fun calculate() {
    val buyTotal = model.buyAmount * model.buyPrice
    val buyTotalFiat = buyTotal * model.coinPrice
    val buySingleFiat = model.buyPrice * model.coinPrice

    val sellTotal = model.buyAmount * model.sellPrice
    val sellTotalFiat = sellTotal * model.coinPrice
    val sellSingleFiat = model.sellPrice * model.coinPrice

    val profit = sellTotal - buyTotal
    val profitFiat = sellTotalFiat - buyTotalFiat
    val profitSingleFiat = sellSingleFiat - buySingleFiat

    view?.setResults(buyTotal, buyTotalFiat, buySingleFiat, sellTotal, sellTotalFiat, sellSingleFiat, profit, profitFiat, profitSingleFiat)
  }

  fun currencyConverter(state: Boolean) {
    preferences.currencyConverter = state
    view?.let {
      it.setCurrencyConverterState(state)
      calculate()
    }
  }

  fun makeExample() {
    with(model) {
      coinPrice = BigDecimal(8201.36)
      buyPrice = BigDecimal(0.001516)
      buyAmount = BigDecimal(5)
      sellPrice = BigDecimal(0.002024)
    }
    setSourcesAndCalculate()
  }

  fun memoryStore() {
    preferences.memory = MainActivityModelSerializer.stringify(model)
    view?.showToast(ResString.memoryStore)
  }

  fun memoryRecall() {
    try {
      val modelRecall: MainActivityModel = MainActivityModelSerializer.parse(preferences.memory)
      with(model) {
        coinPrice = modelRecall.coinPrice
        buyPrice = modelRecall.buyPrice
        buyAmount = modelRecall.buyAmount
        sellPrice = modelRecall.sellPrice
      }
      view?.showToast(ResString.memoryRecall)
    } catch (e: Exception) {
    }
    setSourcesAndCalculate()
  }

  fun clear() {
    with(model) {
      coinPrice = BigDecimal.ONE
      buyPrice = BigDecimal.ZERO
      buyAmount = BigDecimal.ZERO
      sellPrice = BigDecimal.ZERO
    }
    setSourcesAndCalculate()
  }

  fun priceBitcoin() {
    preferences.cryptoCurrency = CryptoCurrency.BITCOIN.name
    getCryptoPrice(CryptoCurrency.BITCOIN, preferences.currencyToConvert)
  }

  fun priceEthereum() {
    preferences.cryptoCurrency = CryptoCurrency.ETHEREUM.name
    getCryptoPrice(CryptoCurrency.ETHEREUM, preferences.currencyToConvert)
  }

  fun currencyToConvert(currencyToConvert: String) {
    preferences.currencyToConvert = currencyToConvert
    view?.setCurrencyToConvert(currencyToConvert)
  }

  fun operation1() {
    model.sellPrice = model.buyPrice
    setSourcesAndCalculate()
  }

  fun operation2() {
    model.sellPrice = BigDecimal.ZERO
    setSourcesAndCalculate()
  }

  fun operation3() {
    model.sellPrice = model.sellPrice.multiply(BigDecimal(1.10))
    setSourcesAndCalculate()
  }

  fun operation4() {
    model.sellPrice = model.sellPrice.multiply(BigDecimal(0.90))
    setSourcesAndCalculate()
  }

  private fun setSourcesAndCalculate() {
    view?.setSources(model.coinPrice, model.buyPrice, model.buyAmount, model.sellPrice)
    calculate()
  }

  private fun getCryptoPrice(cryptoCurrency: CryptoCurrency, currencyToConvert: String) {
    launch(UI) {
      try {
        val crypto = async { getCryptoPriceAsync(cryptoCurrency, currencyToConvert) }.await()
        model.coinPrice = crypto.price(currencyToConvert)
        view?.let {
          it.setCurrencyPrice(model.coinPrice)
          calculate()
        }
      } catch (e: Exception) {
        view?.showToast(
            when (e) {
              is IOException -> ResString.errorNoConnection
              else -> ResString.errorUnknown
            })
      }
    }
  }

  private suspend fun getCryptoPriceAsync(cryptoCurrency: CryptoCurrency, currencyToConvert: String): Crypto {
    var crypto = cache.get(cryptoCurrency)
    if (!crypto.validate() or crypto.cacheDirty or (crypto.price(currencyToConvert) == BigDecimal.ZERO)) {
      val request = Request.Builder().url(API_ENDPOINT.withParams(cryptoCurrency.name, currencyToConvert)).build()
      val response = OkHttpClient().newCall(request).execute()
      response.body()?.let {
        val responseString = it.string()
        crypto = CryptoSerializer.parse(responseString.elementFromJSONArray())
        cache.put(cryptoCurrency, crypto)
      }
    }
    return crypto
  }

  interface Contract : BaseView {
    fun setCurrencyPrice(price: BigDecimal)
    fun setCurrencyConverterState(state: Boolean)
    fun setCurrencyToConvert(currencyToConvert: String)

    fun setSources(coinPrice: BigDecimal, buyPrice: BigDecimal, buyAmount: BigDecimal, sellPrice: BigDecimal)
    fun setResults(buyTotal: BigDecimal, buyTotalFiat: BigDecimal, buySingleFiat: BigDecimal, sellTotal: BigDecimal,
        sellTotalFiat: BigDecimal, sellSingleFiat: BigDecimal, profit: BigDecimal, profitFiat: BigDecimal, profitSingleFiat: BigDecimal)

    fun showToast(message: String)
  }
}

