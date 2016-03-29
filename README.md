# ActionBarProgress
Material style drawable for horizontal progress in ActionBar.

library's minSkdVersion = 7

![ActionBarProgress](ActionBarProgress.png)

## Usage

```xml
<android.support.design.widget.AppBarLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <android.support.v7.widget.Toolbar
            android:id="@+id/toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <ProgressBar
            android:id="@+id/progress"
            style="?android:attr/progressBarStyleHorizontal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_gravity="bottom"
            android:indeterminateDuration="2000"
            android:maxHeight="3dp"
            android:minHeight="3dp" />

    </FrameLayout>

</android.support.design.widget.AppBarLayout>
```

```java
progressBar.setProgressDrawable(MaterialProgressDrawable.create(context));
progressBar.setIndeterminateDrawable(MaterialIndeterminateProgressDrawable.create(context));
```


## Set-up

```groovy
repositories {
    maven {
        url 'https://github.com/yanzm/ActionBarProgress/raw/master/maven-repo'
    }
}

dependencies {
    compile 'net.yanzm:actionbarprogress:1.0.0'
}
```


## License

```
Copyright 2016 Yuki Anzai, Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
