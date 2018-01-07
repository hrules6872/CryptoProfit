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

package com.hrules.cryptoprofit.presentation.resources.base

import java.math.BigDecimal

interface ResString {
  val memoryStore: String
  val memoryRecall: String
  val errorUnknown: String
  val errorNoConnection: String
  val errorEmptyPrice: String

  fun coinPriceAtBuyTimeInfo(coinPriceAtBuyTimeInfo15: BigDecimal, coinPriceAtBuyTimeInfo20: BigDecimal,
      coinPriceAtBuyTimeInfo25: BigDecimal, coinPriceAtBuyTimeInfo33: BigDecimal): String

  fun coinPriceInfo(coinPriceInfo15: BigDecimal, coinPriceInfo20: BigDecimal, coinPriceInfo25: BigDecimal,
      coinPriceInfo33: BigDecimal): String
}