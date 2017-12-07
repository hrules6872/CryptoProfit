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

import android.content.SharedPreferences
import com.hrules.cryptoprofit.data.cache.BaseCache
import com.hrules.cryptoprofit.data.cache.CryptoCache
import com.hrules.cryptoprofit.data.preferences.BasePreferences
import com.hrules.cryptoprofit.data.preferences.Preferences
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel
import org.junit.Before
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainActivityPresenterTest {
  private lateinit var cache: BaseCache<CryptoCurrency, Crypto>
  private lateinit var preferences: BasePreferences
  private lateinit var presenter: MainActivityPresenter

  @Mock
  private lateinit var view: MainActivityPresenter.Contract
  @Mock
  private lateinit var sharedPreferences: SharedPreferences

  @Before
  fun `set up`() {
    MockitoAnnotations.initMocks(this)

    preferences = Preferences(sharedPreferences)
    cache = CryptoCache(preferences as Preferences)
    presenter = MainActivityPresenter(preferences, cache)
    presenter.bind(MainActivityModel(), view)
  }
}