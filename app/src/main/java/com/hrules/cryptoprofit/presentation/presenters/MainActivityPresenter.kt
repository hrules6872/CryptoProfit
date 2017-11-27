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
import com.hrules.cryptoprofit.presentation.extensions.isNumeric
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel

class MainActivityPresenter(private val preferences: BasePreferences) : BasePresenter<MainActivityModel, MainActivityPresenter.Contract>() {
  override fun viewReady(first: Boolean) {
    view?.let { view?.setCurrencyConverterState(preferences.currencyConverter) }
  }

  fun currencyConverter(state: Boolean) {
    preferences.currencyConverter = state
    view?.let { view?.setCurrencyConverterState(state) }
  }

  fun calculate(coinPrice: String, buyPrice: String, buyAmount: String, sellAmount: String) {
    if (coinPrice.isNumeric() && buyPrice.isNumeric() && buyAmount.isNumeric() && sellAmount.isNumeric()) {
    }
  }

  fun makeExample() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  interface Contract : BaseView {
    fun setCurrencyConverterState(state: Boolean)
  }
}

