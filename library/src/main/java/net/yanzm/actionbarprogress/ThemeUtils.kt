/*
 * Copyright (C) 2016 Yuki Anzai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.yanzm.actionbarprogress

import android.content.Context
import android.graphics.Color
import android.util.TypedValue
import androidx.core.content.ContextCompat
import kotlin.math.roundToInt

internal object ThemeUtils {

    fun getDisabledThemeAttrColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()
        val disabledAlpha =
            if (context.theme.resolveAttribute(android.R.attr.disabledAlpha, typedValue, true)) {
                typedValue.float
            } else {
                0f
            }

        val color = getThemeAttrColor(context, attr)
        val alpha = (Color.alpha(color) * disabledAlpha).roundToInt()
        return color and 0x00ffffff or (alpha shl 24)
    }

    fun getThemeAttrColor(context: Context, attr: Int): Int {
        val typedValue = TypedValue()
        return if (context.theme.resolveAttribute(attr, typedValue, true)) {
            if (typedValue.type >= TypedValue.TYPE_FIRST_INT && typedValue.type <= TypedValue.TYPE_LAST_INT) {
                typedValue.data
            } else if (typedValue.type == TypedValue.TYPE_STRING) {
                ContextCompat.getColor(context, typedValue.resourceId)
            } else {
                0
            }
        } else {
            0
        }
    }
}
