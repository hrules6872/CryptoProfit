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

interface Preferences {
  companion object {
    const val CRYPTO_PRICE_DATE = "CRYPTO_PRICE_DATE"
    const val CRYPTO_PRICE_DATE_USE_TODAY = "CRYPTO_PRICE_DATE_USE_TODAY"
    const val CURRENCY_CONVERTER = "CURRENCY_CONVERTER"
    const val CRYPTO_CURRENCY = "CRYPTO_CURRENCY"
    const val CURRENCY_TO_CONVERT = "CURRENCY_TO_CONVERT"
    const val MEMORY = "MEMORY"

    const val CACHE_BITCOIN = "CACHE_BITCOIN"
    const val CACHE_ETHEREUM = "CACHE_ETHEREUM"

    class DEFAULT {
      companion object {
        val DEFAULT_CRYPTO_PRICE_DATE = System.currentTimeMillis()
        const val DEFAULT_CRYPTO_PRICE_DATE_USE_TODAY = true
        const val DEFAULT_CURRENCY_CONVERTER = false
        val DEFAULT_CRYPTO_CURRENCY = CryptoCurrency.BTC.name
        const val DEFAULT_CURRENCY_TO_CONVERT = "EUR"
        const val DEFAULT_MEMORY = ""

        const val DEFAULT_CACHE = ""
      }
    }
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