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

package com.hrules.cryptoprofit.presentation.entitites.serializers

import com.hrules.cryptoprofit.presentation.entitites.Crypto
import com.hrules.cryptoprofit.presentation.entitites.serializers.base.Serializer
import com.hrules.cryptoprofit.presentation.entitites.serializers.custom.BigDecimalCustomSerializer
import kotlinx.serialization.SerialContext
import kotlinx.serialization.json.JSON
import java.math.BigDecimal
import java.util.*


object CryptoSerializer : Serializer<Crypto> {
  private val bigDecimalCustomSerializer = BigDecimalCustomSerializer
  private val JSONParser: JSON = JSON(unquoted = true, nonstrict = true,
      context = SerialContext().apply { registerSerializer(BigDecimal::class, bigDecimalCustomSerializer) }
  )

  init {
    bigDecimalCustomSerializer.init(Locale.US)
  }

  override fun stringify(input: Crypto): String = JSONParser.stringify(input)

  override fun parse(input: String): Crypto = JSONParser.parse(input)
}