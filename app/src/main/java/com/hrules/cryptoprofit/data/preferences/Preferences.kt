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
import com.hrules.cryptoprofit.data.preferences.BasePreferences.Companion.PREFS_DEFAULT_CACHE
import com.hrules.cryptoprofit.data.preferences.BasePreferences.Companion.PREFS_DEFAULT_CURRENCY_CONVERTER
import com.hrules.cryptoprofit.data.preferences.BasePreferences.Companion.PREFS_DEFAULT_CURRENCY_TO_CONVERT
import com.hrules.cryptoprofit.data.preferences.BasePreferences.Companion.PREFS_DEFAULT_MEMORY
import com.hrules.cryptoprofit.data.preferences.BasePreferences.Companion.PREFS_DEFAULT_PREFS_CRYPTO_CURRENCY

class Preferences(private val preferences: SharedPreferences) : BasePreferences {
  override var currencyConverter: Boolean
    get() = preferences.getBoolean(PREFS_CURRENCY_CONVERTER,
        PREFS_DEFAULT_CURRENCY_CONVERTER)
    set(value) = preferences.edit().putBoolean(PREFS_CURRENCY_CONVERTER, value).apply()

  override var cryptoCurrency: String
    get() = preferences.getString(PREFS_CRYPTO_CURRENCY, PREFS_DEFAULT_PREFS_CRYPTO_CURRENCY)
    set(value) = preferences.edit().putString(PREFS_CRYPTO_CURRENCY, value).apply()

  override var currencyToConvert: String
    get() = preferences.getString(PREFS_CURRENCY_TO_CONVERT,
        PREFS_DEFAULT_CURRENCY_TO_CONVERT)
    set(value) = preferences.edit().putString(PREFS_CURRENCY_TO_CONVERT, value).apply()

  override var memory: String
    get() = preferences.getString(PREFS_MEMORY,
        PREFS_DEFAULT_MEMORY)
    set(value) = preferences.edit().putString(PREFS_MEMORY, value).apply()

  override var cacheBitcoin: String
    get() = preferences.getString(PREFS_CACHE_BITCOIN,
        PREFS_DEFAULT_CACHE)
    set(value) = preferences.edit().putString(PREFS_CACHE_BITCOIN, value).apply()

  override var cacheEthereum: String
    get() = preferences.getString(PREFS_CACHE_ETHEREUM,
        PREFS_DEFAULT_CACHE)
    set(value) = preferences.edit().putString(PREFS_CACHE_ETHEREUM, value).apply()
}
