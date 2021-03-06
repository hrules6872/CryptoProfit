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

import com.hrules.cryptoprofit.commons.sameDay
import com.hrules.cryptoprofit.data.cache.base.Cache
import com.hrules.cryptoprofit.data.cache.params.CryptoCacheParams
import com.hrules.cryptoprofit.data.preferences.AndroidPreferences
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.DEFAULT.Companion.DEFAULT_CACHE
import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import com.hrules.cryptoprofit.presentation.entitites.DEFAULT_CACHE_CREATED
import com.hrules.cryptoprofit.presentation.entitites.serializers.CryptoListSerializer
import java.util.concurrent.TimeUnit

private const val CACHE_MAX_ITEMS_SIZE = 10

class AndroidCryptoCache(private val preferences: AndroidPreferences) : Cache<CryptoCacheParams, Crypto> {
  companion object {
    val DEFAULT_CACHE_MAX_AGE: Long = TimeUnit.MINUTES.toMillis(1)
  }

  override fun get(params: CryptoCacheParams): Crypto = try {
    val cryptos: List<Crypto> = CryptoListSerializer.parse(
        if (params.cryptoCurrency == CryptoCurrency.BTC) preferences.cacheBitcoin else preferences.cacheEthereum)
    val crypto = cryptos.first {
      (sameDay(it.cacheCreated, params.timeStamp)) and (it.price(params.currencyToConvert.toString()) != Crypto.defaultPrice)
    }
    crypto.cacheDirty = System.currentTimeMillis() - crypto.cacheCreated > DEFAULT_CACHE_MAX_AGE
    crypto
  } catch (e: Exception) {
    Crypto()
  }

  override fun put(params: CryptoCacheParams, model: Crypto): Boolean = try {
    var cryptos: MutableList<Crypto> = mutableListOf()
    try {
      if (model.cacheCreated == DEFAULT_CACHE_CREATED) {
        model.cacheCreated = params.timeStamp
      }
      cryptos = CryptoListSerializer.parse(
          if (params.cryptoCurrency == CryptoCurrency.BTC) preferences.cacheBitcoin else preferences.cacheEthereum).toMutableList()
      cryptos.removeAll { sameDay(it.cacheCreated, model.cacheCreated) }
    } catch (e: Exception) {
    }
    cryptos.add(0, model)
    cryptos.dropLast(CACHE_MAX_ITEMS_SIZE) // purge
    val cryptosSerialized: String = CryptoListSerializer.stringify(cryptos)
    if (params.cryptoCurrency == CryptoCurrency.BTC) preferences.cacheBitcoin = cryptosSerialized else preferences.cacheEthereum = cryptosSerialized
    true
  } catch (e: Exception) {
    false
  }

  override fun evictAll() {
    preferences.cacheBitcoin = DEFAULT_CACHE
    preferences.cacheEthereum = DEFAULT_CACHE
  }
}