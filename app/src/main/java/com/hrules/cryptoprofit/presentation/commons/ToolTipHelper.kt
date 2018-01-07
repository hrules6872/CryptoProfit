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

package com.hrules.cryptoprofit.presentation.commons

import android.graphics.Rect
import android.support.annotation.IntDef
import android.view.Gravity
import android.view.View
import android.widget.Toast

/*
 * +info: https://gist.github.com/romannurik/3982005
 */
const val LENGTH_SHORT = Toast.LENGTH_SHORT
const val LENGTH_LONG = Toast.LENGTH_LONG

object ToolTipHelper {
  private const val DEFAULT_TOOLTIP_HEIGHT_DP = 48

  @kotlin.annotation.Retention(AnnotationRetention.SOURCE)
  @IntDef(LENGTH_SHORT.toLong(), LENGTH_LONG.toLong()) internal annotation class Duration

  fun show(view: View, text: CharSequence, @Duration duration: Int = LENGTH_SHORT) {
    if (text.isNotEmpty()) {
      val screenPos = IntArray(2)
      view.getLocationOnScreen(screenPos)
      val viewLeft = screenPos[0]
      val viewTop = screenPos[1]

      val displayFrame = Rect()
      view.getWindowVisibleDisplayFrame(displayFrame)

      val context = view.context
      val resources = context.resources
      val viewWidth = view.width
      val viewHeight = view.height
      val viewCenterX = viewLeft + (viewWidth / 2)
      val screenWidth = resources.displayMetrics.widthPixels
      val screenHeight = resources.displayMetrics.heightPixels
      val toolTipHeight = (DEFAULT_TOOLTIP_HEIGHT_DP * resources.displayMetrics.density).toInt()

      val toast = Toast.makeText(context, text, duration)
      val showBelow = (viewTop + view.height + toolTipHeight) < screenHeight
      if (showBelow) {
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, viewCenterX - screenWidth / 2,
            viewTop - displayFrame.top + viewHeight)
      } else {
        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, viewCenterX - screenWidth / 2,
            viewTop - displayFrame.top - toolTipHeight)
      }
      toast.show()
    }
  }
}