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
import com.hrules.cryptoprofit.data.cache.base.Cache
import com.hrules.cryptoprofit.data.cache.params.CryptoCacheParams
import com.hrules.cryptoprofit.data.preferences.AndroidPreferences
import com.hrules.cryptoprofit.data.preferences.base.PREFS_CACHE_BITCOIN
import com.hrules.cryptoprofit.data.preferences.base.PREFS_CACHE_ETHEREUM
import com.hrules.cryptoprofit.data.preferences.base.Preferences
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.PREFS_DEFAULT_CACHE
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import com.hrules.cryptoprofit.presentation.entitites.serializers.CryptoListSerializer
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
import java.math.BigDecimal

@RunWith(MockitoJUnitRunner::class)
class AndroidCryptoCacheTest {
  private lateinit var cache: Cache<CryptoCacheParams, Crypto>
  private lateinit var preferences: Preferences
  private lateinit var cryptoExpected: Crypto

  @Mock
  private lateinit var sharedPreferences: SharedPreferences
  @Mock
  private lateinit var sharedPreferencesEditor: SharedPreferences.Editor

  @Before
  fun `set up`() {
    initMocks(this)

    preferences = AndroidPreferences(sharedPreferences)
    cache = AndroidCryptoCache(preferences as AndroidPreferences)

    cryptoExpected = Crypto(priceUsd = BigDecimal.ONE)
  }

  @Test
  fun `given a CryptoCurrency when get from cache then ok`() {
    cryptoExpected.cacheCreated = System.currentTimeMillis()
    val cryptosMock = CryptoListSerializer.stringify(listOf(cryptoExpected))
    `when`(sharedPreferences.getString(eq(PREFS_CACHE_BITCOIN), anyString()))
        .thenReturn(cryptosMock)

    val crypto = cache.get(CryptoCacheParams(CryptoCurrency.BTC))
    assertEquals(cryptoExpected, crypto)
  }

  @Test
  fun `given a CryptoCurrency when get from empty cache then ok`() {
    `when`(sharedPreferences.getString(eq(PREFS_CACHE_BITCOIN), anyString()))
        .thenReturn(PREFS_DEFAULT_CACHE)

    val crypto = cache.get(CryptoCacheParams(CryptoCurrency.BTC))
    assertEquals(Crypto(), crypto)
    assertFalse(crypto.validate())
  }

  @Test
  fun `given a CryptoCurrency when get from cache then cache is dirty`() {
    cryptoExpected.cacheCreated = System.currentTimeMillis() - AndroidCryptoCache.DEFAULT_CACHE_MAX_AGE
    val cryptosMock = CryptoListSerializer.stringify(listOf(cryptoExpected))
    `when`(sharedPreferences.getString(eq(PREFS_CACHE_BITCOIN), anyString()))
        .thenReturn(cryptosMock)

    val crypto = cache.get(CryptoCacheParams(CryptoCurrency.BTC))
    assertTrue(crypto.cacheDirty)
  }

  @Test
  fun `given a CryptoCurrency when get from cache then cache is not dirty`() {
    cryptoExpected.cacheCreated = System.currentTimeMillis()
    val cryptosMock = CryptoListSerializer.stringify(listOf(cryptoExpected))
    `when`(sharedPreferences.getString(eq(PREFS_CACHE_BITCOIN), anyString()))
        .thenReturn(cryptosMock)

    val crypto = cache.get(CryptoCacheParams(CryptoCurrency.BTC))
    assertFalse(crypto.cacheDirty)
  }

  @Test
  fun `given a CryptoCurrency when put in cache then ok`() {
    val cryptosMock = CryptoListSerializer.stringify(listOf(cryptoExpected))
    `when`(sharedPreferences.getString(eq(PREFS_CACHE_BITCOIN), anyString()))
        .thenReturn(cryptosMock)
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(
        sharedPreferencesEditor)

    assertTrue(cache.put(CryptoCacheParams(CryptoCurrency.BTC), cryptoExpected))

    verify(sharedPreferencesEditor).putString(PREFS_CACHE_BITCOIN, cryptosMock)
  }

  @Test
  fun `given clear cache when get a CryptoCurrency from cache then ok`() {
    `when`(sharedPreferences.edit()).thenReturn(sharedPreferencesEditor)
    `when`(sharedPreferencesEditor.putString(anyString(), anyString())).thenReturn(
        sharedPreferencesEditor)

    cache.evictAll()

    verify(sharedPreferencesEditor).putString(
        PREFS_CACHE_BITCOIN, Preferences.PREFS_DEFAULT_CACHE)
    verify(sharedPreferencesEditor).putString(
        PREFS_CACHE_ETHEREUM, Preferences.PREFS_DEFAULT_CACHE)
  }
}
