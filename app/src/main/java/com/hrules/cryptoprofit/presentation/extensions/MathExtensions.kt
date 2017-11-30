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

package com.hrules.cryptoprofit.presentation.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

fun String.toBigDecimalOrZero(): BigDecimal {
  return try {
    val decimalFormat = NumberFormat.getInstance(Locale.getDefault()) as DecimalFormat
    decimalFormat.isParseBigDecimal = true
    decimalFormat.parseObject(this) as BigDecimal
  } catch (e: ParseException) {
    BigDecimal.ZERO
  }
}

fun BigDecimal.toOneIfZero(): BigDecimal = if (this == BigDecimal.ZERO) BigDecimal.ONE else this

fun BigDecimal.toMoneyText(decimalPlacesMin: Int = 0, decimalPlacesMax: Int = 0, group: Boolean = false): String {
  val decimalFormat = DecimalFormat()
  with(decimalFormat) {
    minimumFractionDigits = decimalPlacesMin
    maximumFractionDigits = decimalPlacesMax
    isGroupingUsed = group
  }
  return decimalFormat.format(this.stripTrailingZeros())
}