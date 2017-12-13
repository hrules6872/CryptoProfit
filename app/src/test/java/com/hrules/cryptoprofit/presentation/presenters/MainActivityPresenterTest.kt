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
import com.hrules.cryptoprofit.data.cache.AndroidCryptoCache
import com.hrules.cryptoprofit.data.cache.base.Cache
import com.hrules.cryptoprofit.data.cache.params.CryptoCacheParams
import com.hrules.cryptoprofit.data.preferences.AndroidPreferences
import com.hrules.cryptoprofit.data.preferences.base.Preferences
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.presenters.models.MainActivityModel
import com.hrules.cryptoprofit.presentation.resources.base.ResId
import com.hrules.cryptoprofit.presentation.resources.base.ResString
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@RunWith(JUnit4::class)
class MainActivityPresenterTest {
  private lateinit var cache: Cache<CryptoCacheParams, Crypto>
  private lateinit var preferences: Preferences
  private lateinit var presenter: MainActivityPresenter

  @Mock
  private lateinit var view: MainActivityPresenter.Contract
  @Mock
  private lateinit var sharedPreferences: SharedPreferences
  @Mock
  private lateinit var resId: ResId
  @Mock
  private lateinit var resString: ResString

  @Before
  fun `set up`() {
    MockitoAnnotations.initMocks(this)

    preferences = AndroidPreferences(sharedPreferences)
    cache = AndroidCryptoCache(preferences as AndroidPreferences)
    presenter = MainActivityPresenter(resId, resString, preferences, cache)
    presenter.bind(MainActivityModel(), view)
  }

  @Test
  fun `dummy`() {
    assertTrue(true)
  }
}