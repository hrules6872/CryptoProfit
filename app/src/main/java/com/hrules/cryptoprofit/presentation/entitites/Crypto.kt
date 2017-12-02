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

import com.hrules.cryptoprofit.presentation.entitites.base.Cacheable
import com.hrules.cryptoprofit.presentation.entitites.base.Validatable
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class Crypto(
    @SerialName("symbol") val name: String = String(),
    @Optional @SerialName("price_usd") private val priceUsd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_aud") private val priceAud: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_brl") private val priceBrl: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_cad") private val priceCad: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_chf") private val priceChf: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_clp") private val priceClp: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_cny") private val priceCny: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_czk") private val priceCzk: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_dkk") private val priceDkk: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_eur") private val priceEur: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_gbp") private val priceGbp: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_hkd") private val priceHkd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_huf") private val priceHuf: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_idr") private val priceIdr: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_ils") private val priceIls: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_inr") private val priceInr: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_jpy") private val priceJpy: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_krw") private val priceKrw: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_mxn") private val priceMxn: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_myr") private val priceMyr: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_nok") private val priceNok: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_nzd") private val priceNzd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_php") private val pricePhp: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_pkr") private val pricePkr: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_pln") private val pricePln: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_rub") private val priceRub: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_sek") private val priceSek: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_sgd") private val priceSgd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_thb") private val priceThb: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_try") private val priceTry: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_twd") private val priceTwd: BigDecimal? = BigDecimal.ZERO,
    @Optional @SerialName("price_zar") private val priceZar: BigDecimal? = BigDecimal.ZERO
) : Validatable, Cacheable {
  @Optional override var cacheCreated: Long = 0
  @Optional override var cacheDirty: Boolean = true

  override fun validate(): Boolean = name.isNotEmpty()

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