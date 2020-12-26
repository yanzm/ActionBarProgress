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
package net.yanzm.actionbarprogress.sample

import android.animation.Animator
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.yanzm.actionbarprogress.MaterialIndeterminateProgressDrawable
import net.yanzm.actionbarprogress.MaterialProgressDrawable
import net.yanzm.actionbarprogress.sample.databinding.ActivityMainBinding

/**
 * usage sample of progress [MaterialProgressDrawable] and
 * [MaterialIndeterminateProgressDrawable]
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var animator: Animator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        binding.progressBar.progressDrawable = MaterialProgressDrawable.create(this)
        binding.progressBar.indeterminateDrawable =
            MaterialIndeterminateProgressDrawable.create(this)

        binding.reloadButton.setOnClickListener { load() }

        load()
    }

    private fun load() {
        binding.progressBar.visibility = View.VISIBLE
        binding.progressBar.isIndeterminate = true

        lifecycleScope.launch {
            delay(4000)

            binding.progressBar.isIndeterminate = false

            animator?.cancel()
            animator = ObjectAnimator.ofInt(binding.progressBar, "progress", 0, 100)
                .setDuration(1000)
                .apply {
                    interpolator = DecelerateInterpolator()
                    doOnEnd {
                        binding.progressBar.visibility = View.GONE
                    }
                    start()
                }
        }
    }

    override fun onDestroy() {
        animator?.cancel()
        animator = null
        super.onDestroy()
    }
}
