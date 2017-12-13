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

package com.hrules.cryptoprofit.data.preferences.base

import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency

const val PREFS_CRYPTO_PRICE_DATE = "PREFS_CRYPTO_PRICE_DATE"
const val PREFS_CRYPTO_PRICE_DATE_USE_TODAY = "PREFS_CRYPTO_PRICE_DATE_USE_TODAY"
const val PREFS_CURRENCY_CONVERTER = "PREFS_CURRENCY_CONVERTER"
const val PREFS_CRYPTO_CURRENCY = "PREFS_CRYPTO_CURRENCY"
const val PREFS_CURRENCY_TO_CONVERT = "PREFS_CURRENCY_TO_CONVERT"
const val PREFS_MEMORY = "PREFS_MEMORY"

const val PREFS_CACHE_BITCOIN = "PREFS_CACHE_BITCOIN"
const val PREFS_CACHE_ETHEREUM = "PREFS_CACHE_ETHEREUM"

interface Preferences {
  companion object {
    val PREFS_DEFAULT_CRYPTO_PRICE_DATE = System.currentTimeMillis()
    val PREFS_DEFAULT_CRYPTO_PRICE_DATE_USE_TODAY = true
    val PREFS_DEFAULT_CURRENCY_CONVERTER = false
    val PREFS_DEFAULT_PREFS_CRYPTO_CURRENCY = CryptoCurrency.BTC.name
    val PREFS_DEFAULT_CURRENCY_TO_CONVERT = "EUR"
    val PREFS_DEFAULT_MEMORY = ""

    val PREFS_DEFAULT_CACHE = ""
  }

  var cryptoPriceDate: Long
  var cryptoPriceDateUseToday: Boolean
  var currencyConverter: Boolean
  var cryptoCurrency: String
  var currencyToConvert: String
  var memory: String

  var cacheBitcoin: String
  var cacheEthereum: String
}