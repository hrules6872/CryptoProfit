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

package com.hrules.cryptoprofit.presentation.presenters.models

import android.os.Bundle
import com.evernote.android.state.State
import com.evernote.android.state.StateSaver
import com.hrules.cryptoprofit.presentation.base.mvp.BaseModel
import com.hrules.cryptoprofit.presentation.presenters.models.serializers.BigDecimalSerializer
import kotlinx.serialization.SerialContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JSON
import java.math.BigDecimal

@Serializable
data class MainActivityModel(
    @State @Serializable(with = BigDecimalSerializer::class) var coinPrice: BigDecimal = BigDecimal.ONE,
    @State @Serializable(with = BigDecimalSerializer::class) var buyPrice: BigDecimal = BigDecimal.ZERO,
    @State @Serializable(with = BigDecimalSerializer::class) var buyAmount: BigDecimal = BigDecimal.ONE,
    @State @Serializable(with = BigDecimalSerializer::class) var sellPrice: BigDecimal = BigDecimal.ZERO
) : BaseModel<Bundle>() {

  companion object {
    val JSONParser: JSON = JSON(unquoted = true,
        context = SerialContext().apply { registerSerializer(BigDecimal::class, BigDecimalSerializer) }
    )
  }

  override fun load(from: Bundle?) {
    from?.let {
      StateSaver.restoreInstanceState(this, from)
    }
  }

  override fun save(to: Bundle?) {
    to?.let {
      StateSaver.saveInstanceState(this, to)
    }
  }
}