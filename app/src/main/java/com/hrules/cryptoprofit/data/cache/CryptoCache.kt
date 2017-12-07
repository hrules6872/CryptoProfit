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

import com.hrules.cryptoprofit.data.preferences.BasePreferences.Companion.PREFS_DEFAULT_CACHE
import com.hrules.cryptoprofit.data.preferences.Preferences
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import com.hrules.cryptoprofit.presentation.entitites.serializers.CryptoSerializer
import java.util.concurrent.TimeUnit

class CryptoCache(private val preferences: Preferences) : BaseCache<CryptoCurrency, Crypto> {
  companion object {
    val DEFAULT_CACHE_MAX_AGE: Long = TimeUnit.SECONDS.toMillis(30)
  }

  override fun get(input: CryptoCurrency): Crypto =
      try {
        val crypto: Crypto = CryptoSerializer.parse(
            if (input == CryptoCurrency.BITCOIN) preferences.cacheBitcoin else preferences.cacheEthereum)
        crypto.cacheDirty = (System.currentTimeMillis() - crypto.cacheCreated) > DEFAULT_CACHE_MAX_AGE
        crypto
      } catch (e: Exception) {
        Crypto()
      }

  override fun put(input: CryptoCurrency, value: Crypto): Boolean =
      try {
        value.cacheCreated = System.currentTimeMillis()
        val cryptoSerialized: String = CryptoSerializer.stringify(value)
        if (input == CryptoCurrency.BITCOIN) preferences.cacheBitcoin = cryptoSerialized else preferences.cacheEthereum = cryptoSerialized
        true
      } catch (e: Exception) {
        false
      }


  override fun evictAll() {
    preferences.cacheBitcoin = PREFS_DEFAULT_CACHE
    preferences.cacheEthereum = PREFS_DEFAULT_CACHE
  }
}