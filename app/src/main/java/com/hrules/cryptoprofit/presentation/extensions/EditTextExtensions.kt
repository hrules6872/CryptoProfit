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
import android.text.TextWatcher
import android.widget.TextView

fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

inline fun TextView.textWatcher(init: CustomTextWatcher.() -> Unit) = addTextChangedListener(CustomTextWatcher().apply(init))

@Suppress("unused")
class CustomTextWatcher : TextWatcher {
  private var _beforeTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
  private var _onTextChanged: ((CharSequence?, Int, Int, Int) -> Unit)? = null
  private var _afterTextChanged: ((Editable?) -> Unit)? = null

  private var _beforeTextChangedShout: (() -> Unit)? = null
  private var _onTextChangedShout: (() -> Unit)? = null
  private var _afterTextChangedShout: (() -> Unit)? = null

  override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    _beforeTextChanged?.invoke(s, start, count, after)
    _beforeTextChangedShout?.invoke()
  }

  override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    _onTextChanged?.invoke(s, start, before, count)
    _onTextChangedShout?.invoke()
  }

  override fun afterTextChanged(s: Editable?) {
    _afterTextChanged?.invoke(s)
    _onTextChangedShout?.invoke()
  }

  fun beforeTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
    _beforeTextChanged = listener
  }

  fun onTextChanged(listener: (CharSequence?, Int, Int, Int) -> Unit) {
    _onTextChanged = listener
  }

  fun afterTextChanged(listener: (Editable?) -> Unit) {
    _afterTextChanged = listener
  }

  fun beforeTextChangedShout(listener: () -> Unit) {
    _beforeTextChangedShout = listener
  }

  fun onTextChangedShout(listener: () -> Unit) {
    _onTextChangedShout = listener
  }

  fun afterTextChangedShout(listener: () -> Unit) {
    _afterTextChangedShout = listener
  }
}
