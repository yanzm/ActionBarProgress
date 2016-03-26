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

package net.yanzm.actionbarprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.view.animation.PathInterpolatorCompat;
import android.view.animation.Interpolator;

/**
 * Drawable for indeterminate horizontal ProgressBar
 */
public class MaterialIndeterminateProgressDrawable extends Drawable {

    private final int trackColor;
    private final int accentColor;
    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private final RectF rect2 = new RectF();
    private final RectF rect1 = new RectF();
    private final Interpolator translateInterpolator2;
    private final Interpolator scaleInterpolator2;
    private final Interpolator translateInterpolator1;
    private final Interpolator scaleInterpolator1;

    public MaterialIndeterminateProgressDrawable(int trackColor, int accentColor) {
        this.trackColor = trackColor;
        this.accentColor = accentColor;

        {
            Path path = new Path();
            path.moveTo(0, 0);
            path.cubicTo(0.0375f, 0f, 0.128764607715f, 0.0895380946618f, 0.25f, 0.218553507947f);
            path.cubicTo(0.322410320025f, 0.295610602487f, 0.436666666667f, 0.417591408114f, 0.483333333333f, 0.489826169306f);
            path.cubicTo(0.69f, 0.80972296795f, 0.793333333333f, 0.950016125212f, 1.0f, 1.0f);
            translateInterpolator2 = PathInterpolatorCompat.create(path);
        }
        {
            Path path = new Path();
            path.moveTo(0, 0);
            path.cubicTo(0.06834272400867f, 0.01992566661414f, 0.19220331656133f, 0.15855429260523f, 0.33333333333333f, 0.34926160892842f);
            path.cubicTo(0.38410433133433f, 0.41477913453861f, 0.54945792615267f, 0.68136029463551f, 0.66666666666667f, 0.68279962777002f);
            path.cubicTo(0.752586273196f, 0.68179620963216f, 0.737253971954f, 0.878896194318f, 1f, 1f);
            scaleInterpolator2 = PathInterpolatorCompat.create(path);
        }
        {
            Path path = new Path();
            path.moveTo(0, 0);
            path.lineTo(0.2f, 0f);
            path.cubicTo(0.3958333333336f, 0.0f, 0.474845090492f, 0.206797621729f, 0.5916666666664f, 0.417082932942f);
            path.cubicTo(0.7151610251224f, 0.639379624869f, 0.81625f, 0.974556908664f, 1.0f, 1.0f);
            translateInterpolator1 = PathInterpolatorCompat.create(path);
        }
        {
            Path path = new Path();
            path.moveTo(0, 0);
            path.lineTo(0.3665f, 0f);
            path.cubicTo(0.47252618112021f, 0.062409910275f, 0.61541608570164f, 0.5f, 0.68325f, 0.5f);
            path.cubicTo(0.75475061236836f, 0.5f, 0.75725829093844f, 0.814510098964f, 1f, 1f);
            scaleInterpolator1 = PathInterpolatorCompat.create(path);
        }
    }

    @Override
    public void draw(Canvas canvas) {
        final int level = getLevel();
        final float input = level / 10000f;

        canvas.drawColor(trackColor);

        canvas.save();
        canvas.translate(canvas.getWidth() / 2f, 0);
        canvas.scale(canvas.getWidth() / 360f, 1);

        paint.setColor(accentColor);

        {
            final int saveCount = canvas.save();

            float translateX2 = translateInterpolator2.getInterpolation(input);
            canvas.translate(-197.60001f + translateX2 * 620.20002f, 0);

            float x = scaleInterpolator2.getInterpolation(input);
            float scaleX = 1.6199005127f * -Math.abs(x - 0.5f) + 0.909950256348f;
            rect2.set(-144f * scaleX, 0, 144f * scaleX, canvas.getHeight());
            canvas.drawRect(rect2, paint);

            canvas.restoreToCount(saveCount);
        }
        {
            final int saveCount = canvas.save();

            float translateX1 = translateInterpolator1.getInterpolation(input);
            canvas.translate(-522.59998f + translateX1 * 722.19999f, 0);

            float x = scaleInterpolator1.getInterpolation(input);
            float scaleX = 1.45369842529f * -Math.abs(x - 0.5f) + 0.826849212646f;
            rect1.set(-144f * scaleX, 0, 144f * scaleX, canvas.getHeight());
            canvas.drawRect(rect1, paint);

            canvas.restoreToCount(saveCount);
        }

        canvas.restore();
    }

    @Override
    public void setAlpha(int alpha) {
        // nothing to do
    }

    @Override
    public void setColorFilter(ColorFilter colorFilter) {
        paint.setColorFilter(colorFilter);
    }

    @Override
    public int getOpacity() {
        int trackColorAlpha = trackColor >>> 24;
        int accentColorAlpha = accentColor >>> 24;
        if (trackColorAlpha == 255 && accentColorAlpha == 255) {
            return PixelFormat.OPAQUE;
        }
        if (trackColorAlpha == 0 && accentColorAlpha == 0) {
            return PixelFormat.TRANSPARENT;
        }
        return PixelFormat.TRANSLUCENT;
    }

    public static MaterialIndeterminateProgressDrawable create(@NonNull Context context) {
        int accentColor = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        int trackColor = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlActivated);
        return new MaterialIndeterminateProgressDrawable(trackColor, accentColor);
    }
}
