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

import com.hrules.cryptoprofit.presentation.entitites.Crypto.Companion.defaultPrice
import com.hrules.cryptoprofit.presentation.entitites.qualifiers.Cacheable
import com.hrules.cryptoprofit.presentation.entitites.qualifiers.Validatable
import kotlinx.serialization.Optional
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.math.BigDecimal

const val DEFAULT_CACHE_CREATED = 0L
const val DEFAULT_CACHE_DIRTY = true

@Serializable
data class Crypto(
    @Optional @SerialName("USD") private val priceUsd: BigDecimal? = defaultPrice,
    @Optional @SerialName("AUD") private val priceAud: BigDecimal? = defaultPrice,
    @Optional @SerialName("BRL") private val priceBrl: BigDecimal? = defaultPrice,
    @Optional @SerialName("CAD") private val priceCad: BigDecimal? = defaultPrice,
    @Optional @SerialName("CHF") private val priceChf: BigDecimal? = defaultPrice,
    @Optional @SerialName("CLP") private val priceClp: BigDecimal? = defaultPrice,
    @Optional @SerialName("CNY") private val priceCny: BigDecimal? = defaultPrice,
    @Optional @SerialName("CZK") private val priceCzk: BigDecimal? = defaultPrice,
    @Optional @SerialName("DKK") private val priceDkk: BigDecimal? = defaultPrice,
    @Optional @SerialName("EUR") private val priceEur: BigDecimal? = defaultPrice,
    @Optional @SerialName("GBP") private val priceGbp: BigDecimal? = defaultPrice,
    @Optional @SerialName("HKD") private val priceHkd: BigDecimal? = defaultPrice,
    @Optional @SerialName("HUF") private val priceHuf: BigDecimal? = defaultPrice,
    @Optional @SerialName("IDR") private val priceIdr: BigDecimal? = defaultPrice,
    @Optional @SerialName("ILS") private val priceIls: BigDecimal? = defaultPrice,
    @Optional @SerialName("INR") private val priceInr: BigDecimal? = defaultPrice,
    @Optional @SerialName("JPY") private val priceJpy: BigDecimal? = defaultPrice,
    @Optional @SerialName("KRW") private val priceKrw: BigDecimal? = defaultPrice,
    @Optional @SerialName("MXN") private val priceMxn: BigDecimal? = defaultPrice,
    @Optional @SerialName("MYR") private val priceMyr: BigDecimal? = defaultPrice,
    @Optional @SerialName("NOK") private val priceNok: BigDecimal? = defaultPrice,
    @Optional @SerialName("NZD") private val priceNzd: BigDecimal? = defaultPrice,
    @Optional @SerialName("PHP") private val pricePhp: BigDecimal? = defaultPrice,
    @Optional @SerialName("PKR") private val pricePkr: BigDecimal? = defaultPrice,
    @Optional @SerialName("PLN") private val pricePln: BigDecimal? = defaultPrice,
    @Optional @SerialName("RUB") private val priceRub: BigDecimal? = defaultPrice,
    @Optional @SerialName("SEK") private val priceSek: BigDecimal? = defaultPrice,
    @Optional @SerialName("SGD") private val priceSgd: BigDecimal? = defaultPrice,
    @Optional @SerialName("THB") private val priceThb: BigDecimal? = defaultPrice,
    @Optional @SerialName("TRY") private val priceTry: BigDecimal? = defaultPrice,
    @Optional @SerialName("TWD") private val priceTwd: BigDecimal? = defaultPrice,
    @Optional @SerialName("ZAR") private val priceZar: BigDecimal? = defaultPrice
) : Validatable, Cacheable {
  @Optional
  override var cacheCreated: Long = DEFAULT_CACHE_CREATED
  @Optional
  override var cacheDirty: Boolean = DEFAULT_CACHE_DIRTY

  override fun validate(): Boolean = Currency.values().any { this.price(it.toString()) != defaultPrice }

  companion object {
    val defaultPrice: BigDecimal = BigDecimal.ZERO
    val listOfCurrencies: Array<String> = Currency.values().map { it.name }.toTypedArray()
  }

  fun price(currency: String): BigDecimal = when (currency) {
    Currency.USD.name -> priceUsd.getSafe()
    Currency.AUD.name -> priceAud.getSafe()
    Currency.BRL.name -> priceBrl.getSafe()
    Currency.CAD.name -> priceCad.getSafe()
    Currency.CHF.name -> priceChf.getSafe()
    Currency.CLP.name -> priceClp.getSafe()
    Currency.CNY.name -> priceCny.getSafe()
    Currency.CZK.name -> priceCzk.getSafe()
    Currency.DKK.name -> priceDkk.getSafe()
    Currency.EUR.name -> priceEur.getSafe()
    Currency.GBP.name -> priceGbp.getSafe()
    Currency.HKD.name -> priceHkd.getSafe()
    Currency.HUF.name -> priceHuf.getSafe()
    Currency.IDR.name -> priceIdr.getSafe()
    Currency.ILS.name -> priceIls.getSafe()
    Currency.INR.name -> priceInr.getSafe()
    Currency.JPY.name -> priceJpy.getSafe()
    Currency.KRW.name -> priceKrw.getSafe()
    Currency.MXN.name -> priceMxn.getSafe()
    Currency.MYR.name -> priceMyr.getSafe()
    Currency.NOK.name -> priceNok.getSafe()
    Currency.NZD.name -> priceNzd.getSafe()
    Currency.PHP.name -> pricePhp.getSafe()
    Currency.PKR.name -> pricePkr.getSafe()
    Currency.PLN.name -> pricePln.getSafe()
    Currency.RUB.name -> priceRub.getSafe()
    Currency.SEK.name -> priceSek.getSafe()
    Currency.SGD.name -> priceSgd.getSafe()
    Currency.THB.name -> priceThb.getSafe()
    Currency.TRY.name -> priceTry.getSafe()
    Currency.TWD.name -> priceTwd.getSafe()
    Currency.ZAR.name -> priceZar.getSafe()
    else -> defaultPrice
  }
}

enum class Currency {
  USD, AUD, BRL, CAD, CHF, CLP, CNY, CZK, DKK, EUR, GBP, HKD, HUF, IDR, ILS, INR, JPY, KRW, MXN, MYR, NOK, NZD, PHP, PKR, PLN, RUB, SEK, SGD, THB, TRY, TWD, ZAR;
}

enum class CryptoCurrency {
  BTC, ETH;
}

private fun BigDecimal?.getSafe() = this ?: defaultPrice