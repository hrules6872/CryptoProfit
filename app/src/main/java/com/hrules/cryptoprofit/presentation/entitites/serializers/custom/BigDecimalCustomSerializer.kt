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

package com.hrules.cryptoprofit.presentation.entitites.serializers.custom

import com.hrules.cryptoprofit.presentation.extensions.toBigDecimalOrZero
import kotlinx.serialization.*
import kotlinx.serialization.internal.SerialClassDescImpl
import java.math.BigDecimal
import java.util.*

@Serializer(forClass = BigDecimal::class)
object BigDecimalCustomSerializer : KSerializer<BigDecimal> {
  private var locale: Locale = Locale.getDefault()

  fun init(locale: Locale) {
    this.locale = locale
  }

  override val serialClassDesc: KSerialClassDesc = SerialClassDescImpl("java.math.BigDecimal")

  override fun save(output: KOutput, obj: BigDecimal) {
    output.writeStringValue(obj.toPlainString())
  }

  override fun load(input: KInput): BigDecimal = input.readStringValue().toBigDecimalOrZero(locale)
}