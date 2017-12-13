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

package com.hrules.cryptoprofit.data.network

import com.hrules.cryptoprofit.presentation.entitites.CryptoCurrency
import com.hrules.cryptoprofit.presentation.extensions.withParams
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit

private const val API_ENDPOINT = "https://min-api.cryptocompare.com/data/dayAvg?fsym={{cryptoCurrency}}&tsym={{currencyToConvert}}&toTs={{timeStamp}}&tryConversion=false"

class Network {
  fun getCryptoPrice(cryptoCurrency: CryptoCurrency, currencyToConvert: String,
      timeStamp: Long): String? {
    val request = Request.Builder().url(
        API_ENDPOINT.withParams(cryptoCurrency.name, currencyToConvert, timeStamp / TimeUnit.SECONDS.toMillis(1))).build()
    val response = OkHttpClient().newCall(request).execute()
    return response.body()?.string()
  }
}