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

import android.content.Context
import android.content.SharedPreferences
import android.support.test.InstrumentationRegistry
import android.support.test.filters.SmallTest
import android.support.test.runner.AndroidJUnit4
import com.hrules.cryptoprofit.data.cache.base.Cache
import com.hrules.cryptoprofit.data.cache.params.CryptoCacheParams
import com.hrules.cryptoprofit.data.preferences.AndroidPreferences
import com.hrules.cryptoprofit.data.preferences.base.Preferences
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.math.BigDecimal
import java.util.concurrent.TimeUnit


private const val DEFAULT_PREFERENCES_FILE = "preferences"

@RunWith(AndroidJUnit4::class)
@SmallTest
class AndroidCryptoCacheTest {
  private lateinit var cache: Cache<CryptoCacheParams, Crypto>
  private lateinit var sharedPreferences: SharedPreferences
  private lateinit var preferences: Preferences
  private lateinit var cryptoExpected: Crypto

  @Before
  fun before() {
    val context = InstrumentationRegistry.getContext()
    sharedPreferences = context.getSharedPreferences(DEFAULT_PREFERENCES_FILE, Context.MODE_PRIVATE)
    preferences = AndroidPreferences(sharedPreferences)
    cache = AndroidCryptoCache(preferences as AndroidPreferences)

    cryptoExpected = Crypto(priceUsd = BigDecimal.ONE)
  }

  @Test
  fun given_a_cryptocurrency_when_get_cache_then_result_default() {
    assertEquals(Crypto(), cache.get(CryptoCacheParams(CryptoCurrency.BTC)))
    assertEquals(Crypto(), cache.get(CryptoCacheParams(CryptoCurrency.ETH)))
  }

  @Test
  fun given_a_cryptocurrency_when_put_a_value_and_get_cache_then_result_ok() {
    cache.put(CryptoCacheParams(CryptoCurrency.BTC), cryptoExpected)
    cache.put(CryptoCacheParams(CryptoCurrency.ETH), cryptoExpected)

    assertEquals(cryptoExpected, cache.get(CryptoCacheParams(CryptoCurrency.BTC)))
    assertEquals(cryptoExpected, cache.get(CryptoCacheParams(CryptoCurrency.ETH)))

    val cryptoExpectedYesterday = cryptoExpected.copy()
    val yesterdayMillis = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)
    cryptoExpectedYesterday.cacheCreated = yesterdayMillis

    cache.put(CryptoCacheParams(CryptoCurrency.BTC), cryptoExpectedYesterday)
    cache.put(CryptoCacheParams(CryptoCurrency.ETH), cryptoExpectedYesterday)

    assertEquals(cryptoExpectedYesterday, cache.get(CryptoCacheParams(CryptoCurrency.BTC, yesterdayMillis)))
    assertEquals(cryptoExpectedYesterday, cache.get(CryptoCacheParams(CryptoCurrency.ETH, yesterdayMillis)))
  }

  @Test
  fun when_evict_all_then_result_default() {
    cache.evictAll()

    assertEquals(Crypto(), cache.get(CryptoCacheParams(CryptoCurrency.BTC)))
    assertEquals(Crypto(), cache.get(CryptoCacheParams(CryptoCurrency.ETH)))
  }

  @Test
  fun given_a_cryptocurrency_when_put_a_value_and_evict_all_then_result_default() {
    cache.put(CryptoCacheParams(CryptoCurrency.BTC), cryptoExpected)
    cache.evictAll()

    assertEquals(Crypto(), cache.get(CryptoCacheParams(CryptoCurrency.BTC)))
    assertEquals(Crypto(), cache.get(CryptoCacheParams(CryptoCurrency.ETH)))
  }

  @After
  fun after() {
    cache.evictAll()
  }
}