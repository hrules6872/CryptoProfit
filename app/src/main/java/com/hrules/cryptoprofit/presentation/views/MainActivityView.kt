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
import com.hrules.cryptoprofit.R
import com.hrules.cryptoprofit.presentation.base.BaseActivity
import com.hrules.cryptoprofit.presentation.presenters.MainActivityPresenter
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel

class MainActivityView : BaseActivity<MainActivityModel, MainActivityPresenter.Contract, MainActivityPresenter>(), MainActivityPresenter.Contract {
  override var presenter: MainActivityPresenter = MainActivityPresenter()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
  }

  override fun onResume() {
    super.onResume()
    presenter.resume()
  }
}