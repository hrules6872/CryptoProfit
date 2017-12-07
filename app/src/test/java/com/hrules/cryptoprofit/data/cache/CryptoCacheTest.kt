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

package com.hrules.cryptoprofit.data.cache

import android.content.SharedPreferences
import com.hrules.cryptoprofit.data.preferences.BasePreferences
import com.hrules.cryptoprofit.data.preferences.PREFS_CACHE_BITCOIN
import com.hrules.cryptoprofit.data.preferences.PREFS_CACHE_ETHEREUM
import com.hrules.cryptoprofit.data.preferences.Preferences
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import com.hrules.cryptoprofit.presentation.entitites.serializers.CryptoSerializer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations.initMocks
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class CryptoCacheTest {
  private lateinit var cache: BaseCache<CryptoCurrency, Crypto>
  private lateinit var preferences: BasePreferences
  private lateinit var cryptoExpected: Crypto

  @Mock
  private lateinit var sharedPreferences: SharedPreferences
  @Mock
  private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

  @Before
  fun `set up`() {
    initMocks(this)

    preferences = Preferences(sharedPreferences)
    cache = CryptoCache(preferences as Preferences)

    cryptoExpected = Crypto(name = "BTC")
  }

  @Test
  fun `given a CryptoCurrency when get from cache then ok`() {
    val cryptoExpectedSerialized = CryptoSerializer.stringify(cryptoExpected)

    `when`(sharedPreferences.getString(eq(PREFS_CACHE_BITCOIN), anyString()))
        .thenReturn(cryptoExpectedSerialized)

    val crypto = cache.get(CryptoCurrency.BITCOIN)
    assertEquals(crypto, cryptoExpected)
  }

  @Test
  fun `given a CryptoCurrency when get from cache then cache is dirty`() {
    cryptoExpected.cacheCreated = System.currentTimeMillis() - CryptoCache.DEFAULT_CACHE_MAX_AGE
    val cryptoExpectedSerialized = CryptoSerializer.stringify(cryptoExpected)

    `when`(sharedPreferences.getString(eq(PREFS_CACHE_BITCOIN), anyString()))
        .thenReturn(cryptoExpectedSerialized)

    assertTrue(cache.get(CryptoCurrency.BITCOIN).cacheDirty)
  }

  @Test
  fun `given a CryptoCurrency when get from cache then cache is not dirty`() {
    cryptoExpected.cacheCreated = System.currentTimeMillis()
    val cryptoExpectedSerialized = CryptoSerializer.stringify(cryptoExpected)

    `when`(sharedPreferences.getString(eq(PREFS_CACHE_BITCOIN), anyString()))
        .thenReturn(cryptoExpectedSerialized)

    assertFalse(cache.get(CryptoCurrency.BITCOIN).cacheDirty)
  }

  @Test
  fun `given a CryptoCurrency when put in cache then ok`() {
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(
        sharedPreferencesEditor)

    assertTrue(cache.put(CryptoCurrency.BITCOIN, cryptoExpected))

    verify(sharedPreferencesEditor).putString(PREFS_CACHE_BITCOIN, CryptoSerializer.stringify(cryptoExpected))
  }

  @Test
  fun `given clear cache when get a CryptoCurrency from cache then ok`() {
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(
        sharedPreferencesEditor)

    cache.evictAll()

    verify(sharedPreferencesEditor).putString(PREFS_CACHE_BITCOIN, BasePreferences.PREFS_DEFAULT_CACHE)
    verify(sharedPreferencesEditor).putString(PREFS_CACHE_ETHEREUM, BasePreferences.PREFS_DEFAULT_CACHE)
  }
}
