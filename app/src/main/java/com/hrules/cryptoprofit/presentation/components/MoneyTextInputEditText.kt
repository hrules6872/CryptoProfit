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
import android.support.design.widget.TextInputEditText
import android.text.InputFilter
import android.text.InputType
import android.util.AttributeSet
import android.view.Gravity
import com.hrules.cryptoprofit.R
import com.hrules.cryptoprofit.presentation.extensions.toBigDecimalOrZero
import com.hrules.cryptoprofit.presentation.extensions.toEditable
import com.hrules.cryptoprofit.presentation.extensions.toMoneyText
import java.math.BigDecimal

class MoneyTextInputEditText @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.editTextStyle) : TextInputEditText(
    context, attrs, defStyleAttr) {
  private var decimalPlacesMin: Int = DEFAULT_DECIMALS_PLACES_MIN
  private var decimalPlacesMax: Int = DEFAULT_DECIMALS_PLACES_MAX
  var money: BigDecimal
    get() = text.toString().toBigDecimalOrZero()
    set(value) {
      text = value.toMoneyText(decimalPlacesMin, decimalPlacesMax).toEditable()
    }

  init {
    attrs?.let {
      val typedArray = context.obtainStyledAttributes(it, com.hrules.cryptoprofit.R.styleable.moneyView, 0, 0)
      decimalPlacesMin = typedArray.getInt(com.hrules.cryptoprofit.R.styleable.moneyView_decimalPlacesMin, DEFAULT_DECIMALS_PLACES_MIN)
      decimalPlacesMax = typedArray.getInt(com.hrules.cryptoprofit.R.styleable.moneyView_decimalPlacesMax, DEFAULT_DECIMALS_PLACES_MAX)
      typedArray.recycle()
    }

    gravity = Gravity.END
    inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL or InputType.TYPE_CLASS_NUMBER
    filters = arrayOf<InputFilter>(InputFilter.LengthFilter(resources.getInteger(R.integer.edit_maxLength)))
  }
}
