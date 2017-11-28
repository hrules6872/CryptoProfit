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

import com.hrules.cryptoprofit.commons.BasePreferences
import com.hrules.cryptoprofit.presentation.base.mvp.BasePresenter
import com.hrules.cryptoprofit.presentation.base.mvp.BaseView
import com.hrules.cryptoprofit.presentation.extensions.toBigDecimalOrOne
import com.hrules.cryptoprofit.presentation.extensions.toBigDecimalOrZero
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel
import java.math.BigDecimal

class MainActivityPresenter(private val preferences: BasePreferences) : BasePresenter<MainActivityModel, MainActivityPresenter.Contract>() {
  override fun viewReady(first: Boolean) {
    view?.let {
      it.setCurrencyConverterState(preferences.currencyConverter)
      calculate()
    }
  }

  fun calculate(coinPriceString: String, buyPriceString: String, buyAmountString: String, sellPriceString: String) {
    model.apply {
      coinPrice = coinPriceString.toBigDecimalOrOne()
      buyPrice = buyPriceString.toBigDecimalOrZero()
      buyAmount = buyAmountString.toBigDecimalOrZero()
      sellPrice = sellPriceString.toBigDecimalOrZero()
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

    view?.setResults(buyTotal, buyTotalFiat, buySingleFiat, sellTotal, sellTotalFiat, sellSingleFiat)
  }

  fun currencyConverter(state: Boolean) {
    preferences.currencyConverter = state
    view?.let {
      it.setCurrencyConverterState(state)
      calculate()
    }
  }

  fun makeExample() {
    model.apply {
      coinPrice = BigDecimal("8223.14")
      buyPrice = BigDecimal("0.001586")
      buyAmount = BigDecimal("1")
      sellPrice = BigDecimal("0.002012")
    }
    refresh()
  }

  fun memoryStore() {


  }

  fun memoryRecall() {}

  fun clear() {
    model.apply {
      coinPrice = BigDecimal.ONE
      buyPrice = BigDecimal.ZERO
      buyAmount = BigDecimal.ZERO
      sellPrice = BigDecimal.ZERO
    }
    refresh()
  }

  private fun refresh() {
    view?.setSources(model.coinPrice, model.buyPrice, model.buyAmount, model.sellPrice)
    calculate()
  }

  interface Contract : BaseView {
    fun setCurrencyConverterState(state: Boolean)
    fun setResults(buyTotal: BigDecimal, buyTotalFiat: BigDecimal, buySingleFiat: BigDecimal, sellTotal: BigDecimal,
        sellTotalFiat: BigDecimal, sellSingleFiat: BigDecimal)

    fun setSources(coinPrice: BigDecimal, buyPrice: BigDecimal, buyAmount: BigDecimal, sellPrice: BigDecimal)
  }
}

