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

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal

@RunWith(JUnit4::class)
class CryptoTest {
  private lateinit var crypto: Crypto

  @Before
  fun `set up`() {
    crypto = Crypto()
  }

  @Test
  fun `given empty Crypto entity when validate then fail`() {
    assertFalse(crypto.validate())
  }

  @Test
  fun `given valid Crypto entity when validate then ok`() {
    crypto = Crypto(name = "BITCOIN")
    assertTrue(crypto.validate())
  }

  @Test
  fun `given empty Crypto entity when get prices then return default`() {
    val expected: BigDecimal = BigDecimal.ZERO
    Currency.values().forEach { assertEquals(crypto.price(it.name), expected) }
  }

  @Test
  fun `given filled Crypto entity when get prices then return ok`() {
    var value: BigDecimal = BigDecimal.ZERO
    var expected: BigDecimal = BigDecimal.ZERO
    Currency.values().forEach { crypto = crypto.price(it, BigDecimal.ONE + value++) }
    Currency.values().forEach { assertEquals(crypto.price(it.name), BigDecimal.ONE + expected++) }
  }

  @Test
  fun `given filled Crypto entity when get price with invalid currency then return default`() {
    val value: BigDecimal = BigDecimal.ONE
    val expected: BigDecimal = BigDecimal.ZERO
    Currency.values().forEach { crypto = crypto.price(it, value) }
    assertEquals(crypto.price("INVALID"), expected)
  }
}

private fun Crypto.price(currency: Currency, value: BigDecimal): Crypto =
    when (currency) {
      Currency.USD -> this.copy(priceUsd = value)
      Currency.AUD -> this.copy(priceAud = value)
      Currency.BRL -> this.copy(priceBrl = value)
      Currency.CAD -> this.copy(priceCad = value)
      Currency.CHF -> this.copy(priceChf = value)
      Currency.CLP -> this.copy(priceClp = value)
      Currency.CNY -> this.copy(priceCny = value)
      Currency.CZK -> this.copy(priceCzk = value)
      Currency.DKK -> this.copy(priceDkk = value)
      Currency.EUR -> this.copy(priceEur = value)
      Currency.GBP -> this.copy(priceGbp = value)
      Currency.HKD -> this.copy(priceHkd = value)
      Currency.HUF -> this.copy(priceHuf = value)
      Currency.IDR -> this.copy(priceIdr = value)
      Currency.ILS -> this.copy(priceIls = value)
      Currency.INR -> this.copy(priceInr = value)
      Currency.JPY -> this.copy(priceJpy = value)
      Currency.KRW -> this.copy(priceKrw = value)
      Currency.MXN -> this.copy(priceMxn = value)
      Currency.MYR -> this.copy(priceMyr = value)
      Currency.NOK -> this.copy(priceNok = value)
      Currency.NZD -> this.copy(priceNzd = value)
      Currency.PHP -> this.copy(pricePhp = value)
      Currency.PKR -> this.copy(pricePkr = value)
      Currency.PLN -> this.copy(pricePln = value)
      Currency.RUB -> this.copy(priceRub = value)
      Currency.SEK -> this.copy(priceSek = value)
      Currency.SGD -> this.copy(priceSgd = value)
      Currency.THB -> this.copy(priceThb = value)
      Currency.TRY -> this.copy(priceTry = value)
      Currency.TWD -> this.copy(priceTwd = value)
      Currency.ZAR -> this.copy(priceZar = value)
      else -> this
    }
