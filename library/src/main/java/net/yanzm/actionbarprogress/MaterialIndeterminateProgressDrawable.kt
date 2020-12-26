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
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.core.view.animation.PathInterpolatorCompat
import net.yanzm.actionbarprogress.ThemeUtils.getDisabledThemeAttrColor
import net.yanzm.actionbarprogress.ThemeUtils.getThemeAttrColor
import kotlin.math.abs

/**
 * Drawable for indeterminate horizontal ProgressBar
 */
class MaterialIndeterminateProgressDrawable private constructor(
    private val trackColor: Int,
    private val accentColor: Int
) : Drawable() {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect2 = RectF()
    private val rect1 = RectF()

    private val translateInterpolator2 = PathInterpolatorCompat.create(
        Path().apply {
            moveTo(0f, 0f)
            cubicTo(0.0375f, 0f, 0.128764607715f, 0.0895380946618f, 0.25f, 0.218553507947f)
            cubicTo(
                0.322410320025f,
                0.295610602487f,
                0.436666666667f,
                0.417591408114f,
                0.483333333333f,
                0.489826169306f
            )
            cubicTo(0.69f, 0.80972296795f, 0.793333333333f, 0.950016125212f, 1.0f, 1.0f)
        }
    )
    private val scaleInterpolator2 = PathInterpolatorCompat.create(
        Path().apply {
            moveTo(0f, 0f)
            cubicTo(
                0.06834272400867f,
                0.01992566661414f,
                0.19220331656133f,
                0.15855429260523f,
                0.33333333333333f,
                0.34926160892842f
            )
            cubicTo(
                0.38410433133433f,
                0.41477913453861f,
                0.54945792615267f,
                0.68136029463551f,
                0.66666666666667f,
                0.68279962777002f
            )
            cubicTo(
                0.752586273196f,
                0.68179620963216f,
                0.737253971954f,
                0.878896194318f,
                1f,
                1f
            )
        }
    )
    private val translateInterpolator1 = PathInterpolatorCompat.create(
        Path().apply {
            moveTo(0f, 0f)
            lineTo(0.2f, 0f)
            cubicTo(
                0.3958333333336f,
                0.0f,
                0.474845090492f,
                0.206797621729f,
                0.5916666666664f,
                0.417082932942f
            )
            cubicTo(0.7151610251224f, 0.639379624869f, 0.81625f, 0.974556908664f, 1.0f, 1.0f)
        }
    )
    private val scaleInterpolator1 = PathInterpolatorCompat.create(
        Path().apply {
            moveTo(0f, 0f)
            lineTo(0.3665f, 0f)
            cubicTo(
                0.47252618112021f,
                0.062409910275f,
                0.61541608570164f,
                0.5f,
                0.68325f,
                0.5f
            )
            cubicTo(0.75475061236836f, 0.5f, 0.75725829093844f, 0.814510098964f, 1f, 1f)
        }
    )

    override fun draw(canvas: Canvas) {
        val input = level / 10000f

        canvas.drawColor(trackColor)

        val width = bounds.width()
        val height = bounds.height()

        canvas.save()
        canvas.translate(width / 2f, 0f)
        canvas.scale(width / 360f, 1f)

        paint.color = accentColor

        run {
            val saveCount = canvas.save()
            val translateX2 = translateInterpolator2.getInterpolation(input)
            canvas.translate(-197.60001f + translateX2 * 620.20002f, 0f)
            val x = scaleInterpolator2.getInterpolation(input)
            val scaleX = 1.6199005127f * -abs(x - 0.5f) + 0.909950256348f
            rect2.set(-144f * scaleX, 0f, 144f * scaleX, height.toFloat())
            canvas.drawRect(rect2, paint)
            canvas.restoreToCount(saveCount)
        }
        run {
            val saveCount = canvas.save()
            val translateX1 = translateInterpolator1.getInterpolation(input)
            canvas.translate(-522.59998f + translateX1 * 722.19999f, 0f)
            val x = scaleInterpolator1!!.getInterpolation(input)
            val scaleX = 1.45369842529f * -abs(x - 0.5f) + 0.826849212646f
            rect1.set(-144f * scaleX, 0f, 144f * scaleX, height.toFloat())
            canvas.drawRect(rect1, paint)
            canvas.restoreToCount(saveCount)
        }
        canvas.restore()
    }

    override fun setAlpha(alpha: Int) {
        // nothing to do
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        paint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int {
        val trackColorAlpha = trackColor ushr 24
        val accentColorAlpha = accentColor ushr 24
        return when {
            trackColorAlpha == 255 && accentColorAlpha == 255 -> PixelFormat.OPAQUE
            trackColorAlpha == 0 && accentColorAlpha == 0 -> PixelFormat.TRANSPARENT
            else -> PixelFormat.TRANSLUCENT
        }
    }

    companion object {
        fun create(context: Context): MaterialIndeterminateProgressDrawable {
            val accentColor = getThemeAttrColor(context, R.attr.colorControlActivated)
            val trackColor = getDisabledThemeAttrColor(context, R.attr.colorControlActivated)
            return create(trackColor, accentColor)
        }

        fun create(trackColor: Int, accentColor: Int): MaterialIndeterminateProgressDrawable {
            return MaterialIndeterminateProgressDrawable(trackColor, accentColor)
        }
    }
}
