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

import android.text.Editable
import java.math.BigDecimal
import java.math.RoundingMode

const val DECIMAL_PLACES_FIAT = 2
const val DECIMAL_PLACES_CRYPTO = 8

fun String.toBigDecimalOrZero(): BigDecimal = this.toBigDecimalOrNull() ?: BigDecimal.ZERO

fun String.toBigDecimalOrOne(): BigDecimal = this.toBigDecimalOrNull() ?: BigDecimal.ONE

fun BigDecimal.toEditable(decimals: Int = DECIMAL_PLACES_CRYPTO): Editable =
    Editable.Factory.getInstance().newEditable(this.setScale(decimals, RoundingMode.UNNECESSARY).toPlainString())