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

package com.hrules.cryptoprofit.commons

import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import java.util.*

@RunWith(JUnit4::class)
class DateUtilsKtTest {
  @Test
  fun `given a similar timestamps when check if they are the same day then ok`() {
    val timeStamp1 = System.currentTimeMillis()
    val timeStamp2 = System.currentTimeMillis()
    assertTrue(sameDay(timeStamp1, timeStamp2))
  }

  @Test
  fun `given a different timestamps when check if they are the same day then fail`() {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    val timeStamp1 = calendar.timeInMillis
    val timeStamp2 = System.currentTimeMillis()
    assertFalse(sameDay(timeStamp1, timeStamp2))
  }

  @Test
  fun `given a timestamp when format to date then ok`() {
    val formatDate = formatDate(1513162226434, Locale.US)
    val expected = "12/13/17"
    assertEquals(expected, formatDate)
  }
}