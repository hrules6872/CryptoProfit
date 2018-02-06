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

import com.hrules.cryptoprofit.commons.formatDate
import com.hrules.cryptoprofit.commons.sameDay
import com.hrules.cryptoprofit.data.cache.base.Cache
import com.hrules.cryptoprofit.data.cache.params.CryptoCacheParams
import com.hrules.cryptoprofit.data.network.base.Network
import com.hrules.cryptoprofit.data.preferences.base.Preferences
import com.hrules.cryptoprofit.presentation.base.mvp.BasePresenter
import com.hrules.cryptoprofit.presentation.base.mvp.BaseView
import com.hrules.cryptoprofit.presentation.commons.ToolTipHelper
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import com.hrules.cryptoprofit.presentation.entitites.Currency
import com.hrules.cryptoprofit.presentation.entitites.serializers.CryptoSerializer
import com.hrules.cryptoprofit.presentation.entitites.serializers.MainActivityModelSerializer
import com.hrules.cryptoprofit.presentation.extensions.toOneIfZero
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel
import com.hrules.cryptoprofit.presentation.resources.base.ResId
import com.hrules.cryptoprofit.presentation.resources.base.ResString
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import java.io.IOException
import java.math.BigDecimal

private const val PERCENTAGE_PLUS = 1.05
private const val PERCENTAGE_MINUS = 0.95

private const val PERCENTAGE_15 = 0.15
private const val PERCENTAGE_20 = 0.20
private const val PERCENTAGE_25 = 0.25
private const val PERCENTAGE_33 = 0.33

class MainActivityPresenter(
    private val resId: ResId,
    private val resString: ResString,
    private val preferences: Preferences,
    private val cache: Cache<CryptoCacheParams, Crypto>,
    private val network: Network
) : BasePresenter<MainActivityModel, MainActivityPresenter.Contract>() {

  private var buyTotalSkip = false

  override fun viewReady(first: Boolean) {
    val cryptoPriceDateMillis = if (preferences.cryptoPriceDateUseToday) System.currentTimeMillis() else preferences.cryptoPriceDate
    view?.run {
      setCryptoPriceDate(formatDate(cryptoPriceDateMillis), cryptoPriceDateMillis)
      setCurrencyConverterState(preferences.currencyConverter)
      setCurrencyToConvert(preferences.currencyToConvert)
    }

    if (first && preferences.currencyConverter) {
      getCryptoPrice(CryptoCurrency.valueOf(preferences.cryptoCurrency), preferences.currencyToConvert, System.currentTimeMillis(),
          preferences.cryptoPriceDate)
    } else {
      view?.setFocus(resId.editBuyAmount)
      calculate()
    }
  }

  fun notifyChangeCoinPriceAtBuyTime(coinPriceInput: BigDecimal, coinPriceAtBuyTimeInput: BigDecimal, buyPriceInput: BigDecimal,
      buyAmountInput: BigDecimal, sellPriceInput: BigDecimal) {
    preferences.cryptoPriceDateUseToday = false
    calculate(
        coinPriceInput = coinPriceInput,
        coinPriceAtBuyTimeInput = coinPriceAtBuyTimeInput,
        buyPriceInput = buyPriceInput,
        buyAmountInput = buyAmountInput,
        sellPriceInput = sellPriceInput)
  }

  fun notifyChangeCoinPrice(coinPriceInput: BigDecimal, coinPriceAtBuyTimeInput: BigDecimal, buyPriceInput: BigDecimal,
      buyAmountInput: BigDecimal, sellPriceInput: BigDecimal) {
    calculate(
        coinPriceInput = coinPriceInput,
        coinPriceAtBuyTimeInput = if (preferences.cryptoPriceDateUseToday) coinPriceInput else coinPriceAtBuyTimeInput,
        buyPriceInput = buyPriceInput,
        buyAmountInput = buyAmountInput,
        sellPriceInput = sellPriceInput)
  }

  fun notifyChangeBuyTotal(coinPriceInput: BigDecimal, coinPriceAtBuyTimeInput: BigDecimal, buyPriceInput: BigDecimal,
      buyTotalInput: BigDecimal, sellPriceInput: BigDecimal) {
    val buyAmount = if ((buyPriceInput > BigDecimal.ZERO) and (buyTotalInput > BigDecimal.ZERO)) buyTotalInput / buyPriceInput else BigDecimal.ZERO
    view?.setSources(coinPriceInput, coinPriceAtBuyTimeInput, buyPriceInput, buyAmount, sellPriceInput)
    skipBuyTotal {
      calculate(
          coinPriceInput = coinPriceInput,
          coinPriceAtBuyTimeInput = coinPriceAtBuyTimeInput,
          buyPriceInput = buyPriceInput,
          buyAmountInput = buyAmount,
          sellPriceInput = sellPriceInput)
    }
  }

  fun calculate(coinPriceInput: BigDecimal, coinPriceAtBuyTimeInput: BigDecimal, buyPriceInput: BigDecimal, buyAmountInput: BigDecimal,
      sellPriceInput: BigDecimal) {
    with(model) {
      coinPrice = coinPriceInput.toOneIfZero()
      coinPriceAtBuyTime = coinPriceAtBuyTimeInput.toOneIfZero()
      buyPrice = buyPriceInput
      buyAmount = buyAmountInput
      sellPrice = sellPriceInput
    }
    calculate()
  }

  private fun calculate() {
    val buyTotal = model.buyAmount * model.buyPrice
    val buyTotalFiat = buyTotal * model.coinPriceAtBuyTime
    val buySingleFiat = model.buyPrice * model.coinPriceAtBuyTime

    val sellTotal = model.buyAmount * model.sellPrice
    val sellTotalFiat = sellTotal * model.coinPrice
    val sellSingleFiat = model.sellPrice * model.coinPrice
    val sellMultiplier = if ((model.buyPrice > BigDecimal.ZERO) and (model.sellPrice > BigDecimal.ZERO)) model.sellPrice / model.buyPrice else BigDecimal.ZERO

    val profit = sellTotal - buyTotal
    val profitFiat = sellTotalFiat - buyTotalFiat
    val profitSingleFiat = sellSingleFiat - buySingleFiat

    view?.run {
      setResults(buyTotal, buyTotalFiat, buySingleFiat, sellTotal, sellTotalFiat, sellSingleFiat, profit, profitFiat,
          profitSingleFiat, sellMultiplier, buyTotalSkip)
      setExclamationVisibility(model.coinPriceAtBuyTime.compareTo(model.coinPrice) != 0)
    }

    val coinPriceAtBuyTimeInfo15 = BigDecimal(PERCENTAGE_15).multiply(model.coinPriceAtBuyTime)
    val coinPriceAtBuyTimeInfo20 = BigDecimal(PERCENTAGE_20).multiply(model.coinPriceAtBuyTime)
    val coinPriceAtBuyTimeInfo25 = BigDecimal(PERCENTAGE_25).multiply(model.coinPriceAtBuyTime)
    val coinPriceAtBuyTimeInfo33 = BigDecimal(PERCENTAGE_33).multiply(model.coinPriceAtBuyTime)
    val coinPriceAtBuyTimeInfo = if (model.coinPriceAtBuyTime != BigDecimal.ONE)
      resString.coinPriceAtBuyTimeInfo(coinPriceAtBuyTimeInfo15, coinPriceAtBuyTimeInfo20, coinPriceAtBuyTimeInfo25,
          coinPriceAtBuyTimeInfo33) else resString.errorEmptyPrice

    val coinPriceInfo15 = BigDecimal(PERCENTAGE_15).multiply(model.coinPrice)
    val coinPriceInfo20 = BigDecimal(PERCENTAGE_20).multiply(model.coinPrice)
    val coinPriceInfo25 = BigDecimal(PERCENTAGE_25).multiply(model.coinPrice)
    val coinPriceInfo33 = BigDecimal(PERCENTAGE_33).multiply(model.coinPrice)
    val coinPriceInfo = if (model.coinPrice != BigDecimal.ONE)
      resString.coinPriceInfo(coinPriceInfo15, coinPriceInfo20,
          coinPriceInfo25, coinPriceInfo33) else resString.errorEmptyPrice

    view?.setCryptoPriceInfo(coinPriceAtBuyTimeInfo, coinPriceInfo)
  }

  fun currencyConverter(state: Boolean) {
    preferences.currencyConverter = state
    view?.setCurrencyConverterState(state)
    calculate()
  }

  fun makeExample() {
    with(model) {
      coinPrice = BigDecimal(8201.36)
      coinPriceAtBuyTime = BigDecimal(5001.36)
      buyPrice = BigDecimal(0.001516)
      buyAmount = BigDecimal(5)
      sellPrice = BigDecimal(0.002024)
    }
    setSourcesAndCalculate()
  }

  fun memoryStore() {
    preferences.memory = MainActivityModelSerializer.stringify(model)
    view?.showToolTip(resId.actionMemoryStore, resString.memoryStore)
  }

  fun memoryRecall() {
    try {
      val modelRecall: MainActivityModel = MainActivityModelSerializer.parse(preferences.memory)
      with(model) {
        coinPrice = modelRecall.coinPrice
        coinPriceAtBuyTime = modelRecall.coinPriceAtBuyTime
        buyPrice = modelRecall.buyPrice
        buyAmount = modelRecall.buyAmount
        sellPrice = modelRecall.sellPrice
      }
    } catch (e: Exception) {
    }
    view?.showToolTip(resId.actionMemoryRecall, resString.memoryRecall)
    setSourcesAndCalculate()
  }

  fun clear() {
    with(model) {
      coinPrice = BigDecimal.ONE
      coinPriceAtBuyTime = BigDecimal.ONE
      buyPrice = BigDecimal.ZERO
      buyAmount = BigDecimal.ZERO
      sellPrice = BigDecimal.ZERO
    }
    setSourcesAndCalculate()
  }

  fun selectDate(dateInMillis: Long) {
    preferences.cryptoPriceDate = dateInMillis
    preferences.cryptoPriceDateUseToday = sameDay(dateInMillis, System.currentTimeMillis())
    view?.run {
      setCryptoPriceDate(formatDate(dateInMillis), dateInMillis)
      setFocus(resId.editCoinPriceAtBuyTime)
    }
    getCryptoPrice(CryptoCurrency.valueOf(preferences.cryptoCurrency), preferences.currencyToConvert, System.currentTimeMillis(),
        dateInMillis)
  }

  fun priceBitcoin() {
    preferences.cryptoCurrency = CryptoCurrency.BTC.name
    getCryptoPrice(CryptoCurrency.BTC, preferences.currencyToConvert, System.currentTimeMillis(), preferences.cryptoPriceDate)
  }

  fun priceEthereum() {
    preferences.cryptoCurrency = CryptoCurrency.ETH.name
    getCryptoPrice(CryptoCurrency.ETH, preferences.currencyToConvert, System.currentTimeMillis(), preferences.cryptoPriceDate)
  }

  fun currencyToConvert(currencyToConvert: String) {
    preferences.currencyToConvert = currencyToConvert
    view?.setCurrencyToConvert(currencyToConvert)
    getCryptoPrice(CryptoCurrency.valueOf(preferences.cryptoCurrency), currencyToConvert, System.currentTimeMillis(),
        if (preferences.cryptoPriceDateUseToday) System.currentTimeMillis() else preferences.cryptoPriceDate)
  }

  fun operationSellEquals() {
    model.sellPrice = model.buyPrice
    view?.setFocus(resId.editSellPrice)
    setSourcesAndCalculate()
  }

  fun operationSellCLear() {
    model.sellPrice = BigDecimal.ZERO
    view?.setFocus(resId.editSellPrice)
    setSourcesAndCalculate()
  }

  fun operationSellPercentagePlus() {
    model.sellPrice = BigDecimal(PERCENTAGE_PLUS).multiply(if (model.sellPrice > BigDecimal.ZERO) model.sellPrice else model.buyPrice)
    view?.setFocus(resId.editSellPrice)
    setSourcesAndCalculate()
  }

  fun operationSellPercentageMinus() {
    model.sellPrice = BigDecimal(PERCENTAGE_MINUS).multiply(if (model.sellPrice > BigDecimal.ZERO) model.sellPrice else model.buyPrice)
    view?.setFocus(resId.editSellPrice)
    setSourcesAndCalculate()
  }

  private fun setSourcesAndCalculate() {
    view?.setSources(model.coinPrice, model.coinPriceAtBuyTime, model.buyPrice, model.buyAmount, model.sellPrice)
    calculate()
  }

  private fun getCryptoPrice(cryptoCurrency: CryptoCurrency,
      currencyToConvert: String, timeStampToday: Long, timeStampDate: Long) {
    launch(UI) {
      view?.setProgressVisibility(true)
      try {
        val crypto = async { getCryptoPriceAsync(cryptoCurrency, currencyToConvert, timeStampToday) }.await()
        val cryptoAtBuyTime = async { getCryptoPriceAsync(cryptoCurrency, currencyToConvert, timeStampDate) }.await()
        model.coinPrice = crypto.price(currencyToConvert)
        model.coinPriceAtBuyTime = cryptoAtBuyTime.price(currencyToConvert)
        view?.setCryptoPrice(model.coinPrice, model.coinPriceAtBuyTime)
        calculate()
      } catch (e: Exception) {
        view?.showToolTip(resId.toolbar,
            when (e) {
              is IOException -> resString.errorNoConnection
              else -> resString.errorUnknown
            })
      }
      view?.setProgressVisibility(false)
    }
  }

  private fun getCryptoPriceAsync(cryptoCurrency: CryptoCurrency, currencyToConvert: String, timeStamp: Long): Crypto {
    var crypto = cache.get(CryptoCacheParams(cryptoCurrency, Currency.valueOf(currencyToConvert), timeStamp))
    if (!crypto.validate() or crypto.cacheDirty) {
      val response = network.getCryptoPrice(cryptoCurrency, currencyToConvert, timeStamp)
      response?.let {
        crypto = CryptoSerializer.parse(response)
        if (!crypto.validate()) throw IllegalStateException()
        crypto.cacheCreated = timeStamp
        cache.put(CryptoCacheParams(cryptoCurrency), crypto)
      }
    }
    return crypto
  }

  private inline fun skipBuyTotal(code: () -> Unit) {
    buyTotalSkip = true
    code()
    buyTotalSkip = false
  }

  interface Contract : BaseView {
    fun setCryptoPriceDate(cryptoPriceDate: String, cryptoPriceDateMillis: Long)
    fun setCryptoPrice(coinPrice: BigDecimal, coinPriceAtBuyTime: BigDecimal)
    fun setCurrencyConverterState(state: Boolean)
    fun setCurrencyToConvert(currencyToConvert: String)
    fun setSources(coinPrice: BigDecimal, coinPriceAtBuyTime: BigDecimal, buyPrice: BigDecimal, buyAmount: BigDecimal,
        sellPrice: BigDecimal)

    fun setResults(buyTotal: BigDecimal, buyTotalFiat: BigDecimal, buySingleFiat: BigDecimal,
        sellTotal: BigDecimal,
        sellTotalFiat: BigDecimal, sellSingleFiat: BigDecimal, profit: BigDecimal,
        profitFiat: BigDecimal, profitSingleFiat: BigDecimal, sellMultiplier: BigDecimal,
        skipBuyTotal: Boolean = false)

    fun setFocus(id: Int)
    fun setCryptoPriceInfo(coinPriceAtBuyTimeInfo: String, coinPriceInfo: String)
    fun setExclamationVisibility(state: Boolean)
    fun setProgressVisibility(state: Boolean)
    fun showToolTip(id: Int, text: String, @ToolTipHelper.Duration duration: Int = 0)
  }
}