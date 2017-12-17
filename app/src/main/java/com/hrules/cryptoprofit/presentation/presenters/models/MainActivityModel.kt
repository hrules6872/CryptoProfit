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

import com.hrules.cryptoprofit.presentation.base.mvp.BaseModel
import com.hrules.cryptoprofit.presentation.presenters.models.serializers.MainActivityModelSerializer
import kotlinx.serialization.Serializable
import java.math.BigDecimal

@Serializable
data class MainActivityModel(
    var coinPrice: BigDecimal = BigDecimal.ONE,
    var coinPriceAtBuyTime: BigDecimal = BigDecimal.ONE,
    var buyPrice: BigDecimal = BigDecimal.ZERO,
    var buyAmount: BigDecimal = BigDecimal.ONE,
    var sellPrice: BigDecimal = BigDecimal.ZERO
) : BaseModel<String>() {

  override fun load(from: String?) {
    from?.let {
      try {
        val model = MainActivityModelSerializer.parse(from)
        coinPrice = model.coinPrice
        coinPriceAtBuyTime = model.coinPriceAtBuyTime
        buyPrice = model.buyPrice
        buyAmount = model.buyAmount
        sellPrice = model.sellPrice
      } catch (e: Exception) {
      }
    }
  }

  override fun save(): String? {
    return MainActivityModelSerializer.stringify(this)
  }
}