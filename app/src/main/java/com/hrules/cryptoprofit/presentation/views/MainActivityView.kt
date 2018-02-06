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

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v7.app.AlertDialog
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import com.hrules.cryptoprofit.App
import com.hrules.cryptoprofit.R
import com.hrules.cryptoprofit.R.id.action_example
import com.hrules.cryptoprofit.R.id.action_visitWebSite
import com.hrules.cryptoprofit.data.cache.AndroidCryptoCache
import com.hrules.cryptoprofit.data.network.CryptoCompareNetwork
import com.hrules.cryptoprofit.data.preferences.AndroidPreferences
import com.hrules.cryptoprofit.presentation.base.BaseActivity
import com.hrules.cryptoprofit.presentation.commons.LENGTH_LONG
import com.hrules.cryptoprofit.presentation.commons.ToolTipHelper
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.extensions.textWatcher
import com.hrules.cryptoprofit.presentation.extensions.toVisibility
import com.hrules.cryptoprofit.presentation.presenters.MainActivityPresenter
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel
import com.hrules.cryptoprofit.presentation.resources.AndroidResId
import com.hrules.cryptoprofit.presentation.resources.AndroidResString
import kotlinx.android.synthetic.main.activity_main.*
import java.math.BigDecimal
import java.util.*


class MainActivityView : BaseActivity<MainActivityModel, MainActivityPresenter.Contract, MainActivityPresenter>(), MainActivityPresenter.Contract {
  override var presenter: MainActivityPresenter = MainActivityPresenter(
      AndroidResId,
      AndroidResString,
      AndroidPreferences(PreferenceManager.getDefaultSharedPreferences(App.instance)),
      AndroidCryptoCache(AndroidPreferences(PreferenceManager.getDefaultSharedPreferences(App.instance))),
      CryptoCompareNetwork())

  private var textWatcherSkip = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setSupportActionBar(toolbar)

    action_currencyConverter.setOnCheckedChangeListener { _, state -> presenter.currencyConverter(state) }

    info_coinPriceAtBuyTime.setOnClickListener { view -> showToolTipInfo(view) }
    info_coinPrice.setOnClickListener { view -> showToolTipInfo(view) }

    edit_coinPriceAtBuyTime.textWatcher {
      onTextChangedShout {
        doIfSkipTextWatcherFalse {
          presenter.notifyChangeCoinPriceAtBuyTime(coinPriceInput = edit_coinPrice.money,
              coinPriceAtBuyTimeInput = edit_coinPriceAtBuyTime.money,
              buyPriceInput = edit_buyPrice.money,
              buyAmountInput = edit_buyAmount.money,
              sellPriceInput = edit_sellPrice.money)
        }
      }
    }
    edit_coinPrice.textWatcher {
      onTextChangedShout {
        doIfSkipTextWatcherFalse {
          presenter.notifyChangeCoinPrice(coinPriceInput = edit_coinPrice.money,
              coinPriceAtBuyTimeInput = edit_coinPriceAtBuyTime.money,
              buyPriceInput = edit_buyPrice.money,
              buyAmountInput = edit_buyAmount.money,
              sellPriceInput = edit_sellPrice.money)
        }
      }
    }
    edit_buyPrice.textWatcher { onTextChangedShout { notifyChange() } }
    edit_buyAmount.textWatcher { onTextChangedShout { notifyChange() } }
    edit_buyTotal.textWatcher {
      onTextChangedShout {
        doIfSkipTextWatcherFalse {
          presenter.notifyChangeBuyTotal(coinPriceInput = edit_coinPrice.money,
              coinPriceAtBuyTimeInput = edit_coinPriceAtBuyTime.money,
              buyPriceInput = edit_buyPrice.money,
              buyTotalInput = edit_buyTotal.money,
              sellPriceInput = edit_sellPrice.money)
        }
      }
    }
    edit_sellPrice.textWatcher { onTextChangedShout { notifyChange() } }

    action_memoryStore.setOnClickListener { view -> notifyClick(view) }
    action_memoryRecall.setOnClickListener { view -> notifyClick(view) }
    action_clear.setOnClickListener { view -> notifyClick(view) }

    text_exclamation.setOnClickListener { view -> ToolTipHelper.show(view, getString(R.string.error_notEqualsCryptoPrices)) }

    action_priceToday.setOnClickListener { view -> notifyClick(view) }
    action_priceDate.setOnClickListener { view -> notifyClick(view) }
    action_priceBitcoin.setOnClickListener { view -> notifyClick(view) }
    action_priceEthereum.setOnClickListener { view -> notifyClick(view) }
    action_currencyToConvert.setOnClickListener { view -> notifyClick(view) }

    action_operationSellEquals.setOnClickListener { view -> notifyClick(view) }
    action_operationSellClear.setOnClickListener { view -> notifyClick(view) }
    action_operationSellPercentagePlus.setOnClickListener { view -> notifyClick(view) }
    action_operationSellPercentageMinus.setOnClickListener { view -> notifyClick(view) }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.menu_main, menu)
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      action_example -> presenter.makeExample()
      action_visitWebSite -> visitWebsiteClick()
    }
    return true
  }

  private fun showToolTipInfo(view: View) {
    view.tag?.let { ToolTipHelper.show(view, view.tag.toString(), LENGTH_LONG) }
  }

  private fun notifyChange() {
    doIfSkipTextWatcherFalse {
      presenter.calculate(
          coinPriceInput = edit_coinPrice.money,
          coinPriceAtBuyTimeInput = edit_coinPriceAtBuyTime.money,
          buyPriceInput = edit_buyPrice.money,
          buyAmountInput = edit_buyAmount.money,
          sellPriceInput = edit_sellPrice.money)
    }
  }

  private fun notifyClick(view: View?) {
    view?.let {
      when (view) {
        action_memoryStore -> presenter.memoryStore()
        action_memoryRecall -> presenter.memoryRecall()
        action_clear -> presenter.clear()
        action_priceToday -> presenter.selectDate(System.currentTimeMillis())
        action_priceDate -> selectDate()
        action_priceBitcoin -> presenter.priceBitcoin()
        action_priceEthereum -> presenter.priceEthereum()
        action_currencyToConvert -> showCurrencyToConvertList()
        action_operationSellEquals -> presenter.operationSellEquals()
        action_operationSellClear -> presenter.operationSellCLear()
        action_operationSellPercentagePlus -> presenter.operationSellPercentagePlus()
        action_operationSellPercentageMinus -> presenter.operationSellPercentageMinus()
      }
    }
  }

  private fun showCurrencyToConvertList() {
    val items: Array<String> = Crypto.listOfCurrencies.sortedArray()
    val builder = AlertDialog.Builder(this)
    builder.setTitle(getString(R.string.text_selectCurrency))
    builder.setItems(items, { _, item ->
      presenter.currencyToConvert(items[item])
    })
    val alert = builder.create()
    alert.show()
  }

  private fun selectDate() {
    val calendar = Calendar.getInstance()
    action_priceDate.tag?.let { calendar.timeInMillis = action_priceDate.tag as Long }
    val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, monthOfYear, dayOfMonth ->
      calendar.set(year, monthOfYear, dayOfMonth)
      presenter.selectDate(calendar.timeInMillis)

    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
    datePickerDialog.show()
  }

  private fun visitWebsiteClick() {
    try {
      startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.app_webSite))))
    } catch (e: Exception) {
    }
  }

  override fun setCryptoPriceDate(cryptoPriceDate: String, cryptoPriceDateMillis: Long) {
    action_priceDate.text = cryptoPriceDate
    action_priceDate.tag = cryptoPriceDateMillis
  }

  override fun setCryptoPrice(coinPrice: BigDecimal, coinPriceAtBuyTime: BigDecimal) {
    edit_coinPrice.money = coinPrice
    edit_coinPriceAtBuyTime.money = coinPriceAtBuyTime
  }

  override fun setCurrencyConverterState(state: Boolean) {
    action_currencyConverter.isChecked = state

    edit_coinPrice.isEnabled = state
    edit_coinPriceAtBuyTime.isEnabled = state
    text_buyTotalFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_buySingleFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_sellTotalFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_sellSingleFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_profitFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_profitSingleFiat.visibility = state.toVisibility(invisibleIsGone = true)
  }

  override fun setCurrencyToConvert(currencyToConvert: String) {
    action_currencyToConvert.text = currencyToConvert
  }

  override fun setSources(coinPrice: BigDecimal, coinPriceAtBuyTime: BigDecimal, buyPrice: BigDecimal, buyAmount: BigDecimal,
      sellPrice: BigDecimal) {
    skipTextWatchers {
      edit_coinPrice.money = coinPrice
      edit_coinPriceAtBuyTime.money = coinPriceAtBuyTime
      edit_buyPrice.money = buyPrice
      edit_buyAmount.money = buyAmount
      edit_sellPrice.money = sellPrice
    }
  }

  override fun setResults(buyTotal: BigDecimal, buyTotalFiat: BigDecimal, buySingleFiat: BigDecimal,
      sellTotal: BigDecimal,
      sellTotalFiat: BigDecimal, sellSingleFiat: BigDecimal, profit: BigDecimal,
      profitFiat: BigDecimal, profitSingleFiat: BigDecimal, sellMultiplier: BigDecimal, skipBuyTotal: Boolean) {
    skipTextWatchers {
      if (!skipBuyTotal) edit_buyTotal.money = buyTotal
      text_buyTotalFiat.money = buyTotalFiat
      text_buySingleFiat.money = buySingleFiat

      text_sellTotal.money = sellTotal
      text_sellTotalFiat.money = sellTotalFiat
      text_sellSingleFiat.money = sellSingleFiat
      text_sellMultiplier.money = sellMultiplier

      text_profit.money = profit
      text_profitFiat.money = profitFiat
      text_profitSingleFiat.money = profitSingleFiat
    }
  }

  override fun setCryptoPriceInfo(coinPriceAtBuyTimeInfo: String, coinPriceInfo: String) {
    info_coinPriceAtBuyTime.tag = coinPriceAtBuyTimeInfo
    info_coinPrice.tag = coinPriceInfo
  }

  override fun setFocus(id: Int) {
    findViewById<EditText>(id).requestFocus()
  }

  override fun setExclamationVisibility(state: Boolean) {
    text_exclamation.visibility = if (state) View.VISIBLE else View.INVISIBLE
  }

  override fun setProgressVisibility(state: Boolean) {
    if (state) progress.show() else progress.hide()
  }

  override fun showToolTip(id: Int, text: String, @ToolTipHelper.Duration duration: Int) {
    ToolTipHelper.show(view = findViewById<View>(id), text = text, duration = duration)
  }

  private inline fun skipTextWatchers(code: () -> Unit) {
    textWatcherSkip = true
    code()
    textWatcherSkip = false
  }

  private inline fun doIfSkipTextWatcherFalse(code: () -> Unit) {
    if (!textWatcherSkip) code()
  }
}