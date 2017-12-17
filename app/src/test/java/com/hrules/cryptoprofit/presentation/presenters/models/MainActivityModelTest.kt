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

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.math.BigDecimal

@RunWith(JUnit4::class)
class MainActivityModelTest {
  @Test
  fun `given a valid data when save and load then result ok`() {
    val modelExpected = MainActivityModel(
        coinPrice = BigDecimal(8201.36),
        coinPriceAtBuyTime = BigDecimal(5001.36),
        buyPrice = BigDecimal(0.001516),
        buyAmount = BigDecimal(5),
        sellPrice = BigDecimal(0.002024))
    val modelSerialized = modelExpected.save()
    val model = MainActivityModel()
    model.load(modelSerialized)
    assertEquals(modelExpected, model)
  }
}