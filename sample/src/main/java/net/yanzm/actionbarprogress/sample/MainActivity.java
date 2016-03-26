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

package net.yanzm.actionbarprogress.sample;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import net.yanzm.actionbarprogress.MaterialIndeterminateProgressDrawable;
import net.yanzm.actionbarprogress.MaterialProgressDrawable;

/**
 * usage sample of progress {@link MaterialProgressDrawable} and
 * {@link MaterialIndeterminateProgressDrawable}
 */
public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        View button = findViewById(R.id.reload_button);
        assert button != null;
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load();
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progress);
        assert progressBar != null;
        progressBar.setProgressDrawable(MaterialProgressDrawable.create(this));
        progressBar.setIndeterminateDrawable(MaterialIndeterminateProgressDrawable.create(this));

        load();
    }

    private void load() {
        if (progressBar != null) {
            progressBar.setIndeterminate(true);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (progressBar != null) {
                        progressBar.setIndeterminate(false);
                        final ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100)
                                .setDuration(1000);
                        animator.setInterpolator(new DecelerateInterpolator());
                        animator.start();
                    }
                }
            }, 4000);
        }
    }
}
