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

package com.hrules.cryptoprofit.presentation.components

import android.content.Context
import android.support.v7.widget.AppCompatTextView
import android.text.SpannableString
import android.text.style.UnderlineSpan
import android.util.AttributeSet
import android.widget.TextView
import java.math.BigDecimal

class MoneyTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(
    context, attrs, defStyleAttr) {
  var money: BigDecimal = BigDecimal.ZERO
    set(value) {
      text = value.toString()
    }

  override fun setText(text: CharSequence, type: TextView.BufferType) {
    val spannableString = SpannableString(text)
    spannableString.setSpan(UnderlineSpan(), 0, spannableString.length, 0)
    super.setText(spannableString, type)
  }

  // TODO: currency
  // currency position
  // decimals
}