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
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ScaleDrawable;
import android.support.annotation.NonNull;
import android.view.Gravity;

/**
 * Drawable for ProgressBar
 */
public class MaterialProgressDrawable extends LayerDrawable {

    /**
     * Creates a new layer drawable with the list of specified layers.
     *
     * @param layers a list of drawables to use as layers in this new drawable,
     *               must be non-null
     */
    public MaterialProgressDrawable(Drawable[] layers) {
        super(layers);
        setId(0, android.R.id.background);
        setId(1, android.R.id.secondaryProgress);
        setId(2, android.R.id.progress);
    }

    public static MaterialProgressDrawable create(@NonNull Context context) {
        Drawable[] layers = new Drawable[3];

        layers[0] = new ColorDrawable(ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal));

        Drawable secondaryProgress = new ColorDrawable(ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlActivated));
        layers[1] = new ScaleDrawable(secondaryProgress, Gravity.START, 1f, -1f);

        Drawable progress = new ColorDrawable(ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated));
        layers[2] = new ScaleDrawable(progress, Gravity.START, 1f, -1f);

        return new MaterialProgressDrawable(layers);
    }
}
