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

package com.hrules.cryptoprofit.data.preferences

import android.content.SharedPreferences
import com.hrules.cryptoprofit.data.preferences.base.Preferences
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.CACHE_BITCOIN
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.CACHE_ETHEREUM
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.CRYPTO_CURRENCY
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.CRYPTO_PRICE_DATE
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.CRYPTO_PRICE_DATE_USE_TODAY
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.CURRENCY_CONVERTER
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.CURRENCY_TO_CONVERT
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.DEFAULT.Companion.DEFAULT_CACHE
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.DEFAULT.Companion.DEFAULT_CRYPTO_CURRENCY
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.DEFAULT.Companion.DEFAULT_CRYPTO_PRICE_DATE
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.DEFAULT.Companion.DEFAULT_CRYPTO_PRICE_DATE_USE_TODAY
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.DEFAULT.Companion.DEFAULT_CURRENCY_CONVERTER
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.DEFAULT.Companion.DEFAULT_CURRENCY_TO_CONVERT
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.DEFAULT.Companion.DEFAULT_MEMORY
import com.hrules.cryptoprofit.data.preferences.base.Preferences.Companion.MEMORY

class AndroidPreferences(private val preferences: SharedPreferences) : Preferences {
  override var cryptoPriceDate: Long
    get() = preferences.getLong(CRYPTO_PRICE_DATE,
        DEFAULT_CRYPTO_PRICE_DATE)
    set(value) = preferences.edit().putLong(CRYPTO_PRICE_DATE, value).apply()

  override var cryptoPriceDateUseToday: Boolean
    get() = preferences.getBoolean(CRYPTO_PRICE_DATE_USE_TODAY,
        DEFAULT_CRYPTO_PRICE_DATE_USE_TODAY)
    set(value) = preferences.edit().putBoolean(CRYPTO_PRICE_DATE_USE_TODAY, value).apply()

  override var currencyConverter: Boolean
    get() = preferences.getBoolean(CURRENCY_CONVERTER,
        DEFAULT_CURRENCY_CONVERTER)
    set(value) = preferences.edit().putBoolean(CURRENCY_CONVERTER, value).apply()

  override var cryptoCurrency: String
    get() = preferences.getString(CRYPTO_CURRENCY, DEFAULT_CRYPTO_CURRENCY)
    set(value) = preferences.edit().putString(CRYPTO_CURRENCY, value).apply()

  override var currencyToConvert: String
    get() = preferences.getString(CURRENCY_TO_CONVERT,
        DEFAULT_CURRENCY_TO_CONVERT)
    set(value) = preferences.edit().putString(CURRENCY_TO_CONVERT, value).apply()

  override var memory: String
    get() = preferences.getString(MEMORY,
        DEFAULT_MEMORY)
    set(value) = preferences.edit().putString(MEMORY, value).apply()

  override var cacheBitcoin: String
    get() = preferences.getString(CACHE_BITCOIN,
        DEFAULT_CACHE)
    set(value) = preferences.edit().putString(CACHE_BITCOIN, value).apply()

  override var cacheEthereum: String
    get() = preferences.getString(CACHE_ETHEREUM,
        DEFAULT_CACHE)
    set(value) = preferences.edit().putString(CACHE_ETHEREUM, value).apply()
}
