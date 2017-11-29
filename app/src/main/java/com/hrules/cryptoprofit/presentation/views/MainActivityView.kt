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

package com.hrules.cryptoprofit.presentation.views

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.hrules.cryptoprofit.App
import com.hrules.cryptoprofit.R
import com.hrules.cryptoprofit.R.id.*
import com.hrules.cryptoprofit.commons.Preferences
import com.hrules.cryptoprofit.presentation.base.BaseActivity
import com.hrules.cryptoprofit.presentation.extensions.*
import com.hrules.cryptoprofit.presentation.presenters.MainActivityPresenter
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal

class MainActivityView : BaseActivity<MainActivityModel, MainActivityPresenter.Contract, MainActivityPresenter>(), MainActivityPresenter.Contract {
  override var presenter: MainActivityPresenter = MainActivityPresenter(
      Preferences(PreferenceManager.getDefaultSharedPreferences(App.instance)))

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setSupportActionBar(toolbar)

    action_currencyConverter.setOnCheckedChangeListener { _, state -> presenter.currencyConverter(state) }

    edit_coinPrice.textWatcher { onTextChangedShout { notifyChange() } }
    edit_buyPrice.textWatcher { onTextChangedShout { notifyChange() } }
    edit_buyAmount.textWatcher { onTextChangedShout { notifyChange() } }
    edit_sellPrice.textWatcher { onTextChangedShout { notifyChange() } }

    action_memoryStore.setOnClickListener { view -> notifyClick(view) }
    action_memoryRecall.setOnClickListener { view -> notifyClick(view) }
    action_clear.setOnClickListener { view -> notifyClick(view) }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      action_donate -> donateClick()
      action_example -> presenter.makeExample()
      action_visitWebSite -> visitWebsiteClick()
    }
    return true
  }

  private fun notifyChange() {
    presenter.calculate(
        coinPriceString = edit_coinPrice.text.toString(),
        buyPriceString = edit_buyPrice.text.toString(),
        buyAmountString = edit_buyAmount.text.toString(),
        sellPriceString = edit_sellPrice.text.toString())
  }

  private fun notifyClick(view: View?) {
    view?.let {
      when (view) {
        action_memoryStore -> presenter.memoryStore()
        action_memoryRecall -> presenter.memoryRecall()
        action_clear -> presenter.clear()
      }
    }
  }

  private fun donateClick() {
  }

  private fun visitWebsiteClick() {
  }

  override fun setCurrencyConverterState(state: Boolean) {
    action_currencyConverter.isChecked = state

    edit_coinPrice.isEnabled = state
    text_buyTotalFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_buySingleFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_sellTotalFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_sellSingleFiat.visibility = state.toVisibility(invisibleIsGone = true)
  }

  override fun setResults(buyTotal: BigDecimal, buyTotalFiat: BigDecimal, buySingleFiat: BigDecimal, sellTotal: BigDecimal,
      sellTotalFiat: BigDecimal, sellSingleFiat: BigDecimal) {
    text_buyTotal.money = buyTotal
    text_buyTotalFiat.money = buyTotalFiat
    text_buySingleFiat.money = buySingleFiat
    text_sellTotal.money = sellTotal
    text_sellTotalFiat.money = sellTotalFiat
    text_sellSingleFiat.money = sellSingleFiat
  }

  override fun setSources(coinPrice: BigDecimal, buyPrice: BigDecimal, buyAmount: BigDecimal, sellPrice: BigDecimal) {
    edit_coinPrice.text = coinPrice.toEditable(DECIMAL_PLACES_FIAT)
    edit_buyPrice.text = buyPrice.toEditable()
    edit_buyAmount.text = buyAmount.toEditable()
    edit_sellPrice.text = sellPrice.toEditable()
  }

  override fun showToast(message: String) {
    toast(message)
  }
}
