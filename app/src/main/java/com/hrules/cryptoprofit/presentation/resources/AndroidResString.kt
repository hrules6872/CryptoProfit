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

package com.hrules.cryptoprofit.presentation.resources

import com.hrules.cryptoprofit.R
import com.hrules.cryptoprofit.presentation.resources.base.ResString
import com.hrules.cryptoprofit.presentation.resources.helpers.AndroidResHelper

object AndroidResString : ResString {
  override val memoryStore: String = AndroidResHelper.getString(R.string.text_memoryStore)
  override val memoryRecall: String = AndroidResHelper.getString(R.string.text_memoryRecall)
  override val errorUnknown: String = AndroidResHelper.getString(R.string.error_unknown)
  override val errorNoConnection: String = AndroidResHelper.getString(R.string.error_noConnection)
}