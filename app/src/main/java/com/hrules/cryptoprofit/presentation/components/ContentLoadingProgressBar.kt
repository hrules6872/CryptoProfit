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
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.widget.ProgressBar

private const val DEFAULT_START_TIME = -1L
private const val DEFAULT_MIN_SHOW_MILLI = 500L
private const val DEFAULT_MIN_DELAY_MILLI = 500L

class ContentLoadingProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
  : ProgressBar(context, attrs, defStyleAttr) {
  private var startTime = DEFAULT_START_TIME
  private var shown = false
  private var attachedToWindow = false

  init {
    shown = visibility == View.VISIBLE
  }

  override fun onAttachedToWindow() {
    super.onAttachedToWindow()
    attachedToWindow = true
    if (shown and (visibility != View.VISIBLE)) postDelayed(delayedShow, DEFAULT_MIN_DELAY_MILLI)
  }

  override fun onDetachedFromWindow() {
    super.onDetachedFromWindow()
    attachedToWindow = false
    removeCallbacks(delayedHide)
    removeCallbacks(delayedShow)
    if (!shown and (startTime != DEFAULT_START_TIME)) visibility = View.GONE
    startTime = DEFAULT_START_TIME
  }

  private val delayedHide = Runnable {
    visibility = View.GONE
    startTime = DEFAULT_START_TIME
  }

  private val delayedShow = Runnable {
    startTime = SystemClock.uptimeMillis()
    visibility = View.VISIBLE
  }

  fun hide() {
    if (shown) {
      shown = false
      if (attachedToWindow) removeCallbacks(delayedShow)
      val diff = SystemClock.uptimeMillis() - startTime
      if ((startTime == DEFAULT_START_TIME) or (diff >= DEFAULT_MIN_SHOW_MILLI)) {
        visibility = View.GONE
        startTime = DEFAULT_START_TIME
      } else {
        postDelayed(delayedHide, DEFAULT_MIN_SHOW_MILLI - diff)
      }
    }
  }

  fun show() {
    if (!shown) {
      shown = true
      if (attachedToWindow) {
        removeCallbacks(delayedHide)
        if (startTime == DEFAULT_START_TIME) postDelayed(delayedShow, DEFAULT_MIN_DELAY_MILLI)
      }
    }
  }
}