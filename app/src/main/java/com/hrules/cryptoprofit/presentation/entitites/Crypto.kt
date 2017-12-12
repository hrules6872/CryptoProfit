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

package com.hrules.cryptoprofit.presentation.entitites

import com.hrules.cryptoprofit.presentation.entitites.base.Model
import com.hrules.cryptoprofit.presentation.entitites.qualifiers.Cacheable
import com.hrules.cryptoprofit.presentation.entitites.qualifiers.Validatable
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Crypto(
    @Optional @SerialName("USD") private val priceUsd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("AUD") private val priceAud: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("BRL") private val priceBrl: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("CAD") private val priceCad: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("CHF") private val priceChf: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("CLP") private val priceClp: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("CNY") private val priceCny: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("CZK") private val priceCzk: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("DKK") private val priceDkk: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("EUR") private val priceEur: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("GBP") private val priceGbp: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("HKD") private val priceHkd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("HUF") private val priceHuf: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("IDR") private val priceIdr: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("ILS") private val priceIls: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("INR") private val priceInr: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("JPY") private val priceJpy: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("KRW") private val priceKrw: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("MXN") private val priceMxn: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("MYR") private val priceMyr: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("NOK") private val priceNok: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("NZD") private val priceNzd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("PHP") private val pricePhp: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("PKR") private val pricePkr: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("PLN") private val pricePln: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("RUB") private val priceRub: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("SEK") private val priceSek: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("SGD") private val priceSgd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("THB") private val priceThb: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("TRY") private val priceTry: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("TWD") private val priceTwd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("ZAR") private val priceZar: BigDecimal? = BigDecimal.ZERO
) : Model, Validatable, Cacheable {
  @Optional override var cacheCreated: Long = 0
  @Optional override var cacheDirty: Boolean = true

  override fun validate(): Boolean = Currency.values().any { this.price(it.toString()) != BigDecimal.ZERO }

  companion object {
    val listOfCurrencies: Array<String> = Currency.values().map { it.name }.toTypedArray()
  }

  fun price(currency: String): BigDecimal = when (currency) {
    Currency.USD.name -> priceUsd ?: BigDecimal.ZERO
    Currency.AUD.name -> priceAud ?: BigDecimal.ZERO
    Currency.BRL.name -> priceBrl ?: BigDecimal.ZERO
    Currency.CAD.name -> priceCad ?: BigDecimal.ZERO
    Currency.CHF.name -> priceChf ?: BigDecimal.ZERO
    Currency.CLP.name -> priceClp ?: BigDecimal.ZERO
    Currency.CNY.name -> priceCny ?: BigDecimal.ZERO
    Currency.CZK.name -> priceCzk ?: BigDecimal.ZERO
    Currency.DKK.name -> priceDkk ?: BigDecimal.ZERO
    Currency.EUR.name -> priceEur ?: BigDecimal.ZERO
    Currency.GBP.name -> priceGbp ?: BigDecimal.ZERO
    Currency.HKD.name -> priceHkd ?: BigDecimal.ZERO
    Currency.HUF.name -> priceHuf ?: BigDecimal.ZERO
    Currency.IDR.name -> priceIdr ?: BigDecimal.ZERO
    Currency.ILS.name -> priceIls ?: BigDecimal.ZERO
    Currency.INR.name -> priceInr ?: BigDecimal.ZERO
    Currency.JPY.name -> priceJpy ?: BigDecimal.ZERO
    Currency.KRW.name -> priceKrw ?: BigDecimal.ZERO
    Currency.MXN.name -> priceMxn ?: BigDecimal.ZERO
    Currency.MYR.name -> priceMyr ?: BigDecimal.ZERO
    Currency.NOK.name -> priceNok ?: BigDecimal.ZERO
    Currency.NZD.name -> priceNzd ?: BigDecimal.ZERO
    Currency.PHP.name -> pricePhp ?: BigDecimal.ZERO
    Currency.PKR.name -> pricePkr ?: BigDecimal.ZERO
    Currency.PLN.name -> pricePln ?: BigDecimal.ZERO
    Currency.RUB.name -> priceRub ?: BigDecimal.ZERO
    Currency.SEK.name -> priceSek ?: BigDecimal.ZERO
    Currency.SGD.name -> priceSgd ?: BigDecimal.ZERO
    Currency.THB.name -> priceThb ?: BigDecimal.ZERO
    Currency.TRY.name -> priceTry ?: BigDecimal.ZERO
    Currency.TWD.name -> priceTwd ?: BigDecimal.ZERO
    Currency.ZAR.name -> priceZar ?: BigDecimal.ZERO
    else -> BigDecimal.ZERO
  }
}

enum class Currency {
  USD, AUD, BRL, CAD, CHF, CLP, CNY, CZK, DKK, EUR, GBP, HKD, HUF, IDR, ILS, INR, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PKR, PLN, RUB, SEK, SGD, THB, TRY, TWD, ZAR;
}

enum class CryptoCurrency {
  BITCOIN, ETHEREUM;
}