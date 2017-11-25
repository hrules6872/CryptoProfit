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
import android.view.View
import com.hrules.cryptoprofit.App
import com.hrules.cryptoprofit.R
import com.hrules.cryptoprofit.commons.Preferences
import com.hrules.cryptoprofit.presentation.base.BaseActivity
import com.hrules.cryptoprofit.presentation.extensions.textWatcher
import com.hrules.cryptoprofit.presentation.extensions.toVisibility
import com.hrules.cryptoprofit.presentation.presenters.MainActivityPresenter
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivityView : BaseActivity<MainActivityModel, MainActivityPresenter.Contract, MainActivityPresenter>(), MainActivityPresenter.Contract {
  override var presenter: MainActivityPresenter = MainActivityPresenter(
      Preferences(PreferenceManager.getDefaultSharedPreferences(App.instance)))

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    action_currencyConverter.setOnCheckedChangeListener { _, state -> presenter.currencyConverter(state) }

    edit_coinPrice.textWatcher { onTextChangedShout { notifyChange() } }
    edit_buyPrice.textWatcher { onTextChangedShout { notifyChange() } }
    edit_buyAmount.textWatcher { onTextChangedShout { notifyChange() } }
    edit_sellPrice.textWatcher { onTextChangedShout { notifyChange() } }

    action_example.setOnClickListener { view -> notifyClick(view) }
    action_donate.setOnClickListener { view -> notifyClick(view) }
    action_visitWebSite.setOnClickListener { view -> notifyClick(view) }
  }

  private fun notifyChange() {
    presenter.calculate(edit_coinPrice.text.toString(), edit_buyPrice.text.toString(), edit_buyAmount.text.toString(),
        edit_sellPrice.text.toString())
  }

  private fun notifyClick(view: View?) {
    view?.let {
      when (view) {
        action_example -> presenter.makeExample()
        action_donate -> donateClick()
        action_visitWebSite -> visitWebsiteClick()
      }
    }
  }

  private fun donateClick() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  private fun visitWebsiteClick() {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override fun setCurrencyConverterState(state: Boolean) {
    action_currencyConverter.isChecked = state

    edit_coinPrice.isEnabled = state
    text_profitFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_buyTotalFiat.visibility = state.toVisibility(invisibleIsGone = true)
    text_sellFiat.visibility = state.toVisibility(invisibleIsGone = true)
  }
}
