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

import java.text.DateFormat
import java.util.*

fun sameDay(timeStamp1: Long, timeStamp2: Long): Boolean {
  val calendar1 = Calendar.getInstance()
  val calendar2 = Calendar.getInstance()
  calendar1.time = Date(timeStamp1)
  calendar2.time = Date(timeStamp2)
  return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.DAY_OF_YEAR) == calendar2.get(
      Calendar.DAY_OF_YEAR)
}

fun formatDate(timeStamp: Long, locale: Locale = Locale.getDefault()): String {
  return DateFormat.getDateInstance(DateFormat.SHORT, locale).format(timeStamp)
}
