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
import android.util.AttributeSet
import android.view.Gravity
import com.hrules.cryptoprofit.R
import com.hrules.cryptoprofit.presentation.extensions.toBigDecimalOrZero
import com.hrules.cryptoprofit.presentation.extensions.toMoneyText
import java.math.BigDecimal

const val DEFAULT_SYMBOL = ""
const val DEFAULT_SYMBOL_LEFT_SIDE = true
const val DEFAULT_DECIMALS_PLACES_MIN = 2
const val DEFAULT_DECIMALS_PLACES_MAX = 8
const val DEFAULT_SIGNED = false

class MoneyTextView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : AppCompatTextView(
    context, attrs, defStyleAttr) {
  private lateinit var currencySymbol: String
  private var currencySymbolLeftSide: Boolean = DEFAULT_SYMBOL_LEFT_SIDE
  private var decimalPlacesMin: Int = DEFAULT_DECIMALS_PLACES_MIN
  private var decimalPlacesMax: Int = DEFAULT_DECIMALS_PLACES_MAX
  private var signed: Boolean = DEFAULT_SIGNED
  var money: BigDecimal
    get() = text.toString().toBigDecimalOrZero()
    set(value) {
      val moneyText = value.toMoneyText(decimalPlacesMin = decimalPlacesMin, decimalPlacesMax = decimalPlacesMax, group = true)
      val moneyTextSign = if (signed && value > BigDecimal.ZERO) "+" else ""
      text = if (currencySymbolLeftSide) "$currencySymbol $moneyTextSign$moneyText" else "$moneyTextSign$moneyText$currencySymbol"
    }

  init {
    attrs?.let {
      val typedArray = context.obtainStyledAttributes(it, R.styleable.moneyView, 0, 0)
      currencySymbol = typedArray.getString(R.styleable.moneyView_currencySymbol) ?: DEFAULT_SYMBOL
      currencySymbolLeftSide = typedArray.getBoolean(R.styleable.moneyView_currencySymbolLeftSide, DEFAULT_SYMBOL_LEFT_SIDE)
      decimalPlacesMin = typedArray.getInt(R.styleable.moneyView_decimalPlacesMin, DEFAULT_DECIMALS_PLACES_MIN)
      decimalPlacesMax = typedArray.getInt(R.styleable.moneyView_decimalPlacesMax, DEFAULT_DECIMALS_PLACES_MAX)
      signed = typedArray.getBoolean(R.styleable.moneyView_signed, DEFAULT_SIGNED)
      typedArray.recycle()
    }

    gravity = Gravity.END
  }
}