package com.shreyashkore.macrobenchmark

import android.content.Intent
import androidx.benchmark.macro.CompilationMode
import androidx.benchmark.macro.FrameTimingMetric
import androidx.benchmark.macro.StartupMode
import androidx.benchmark.macro.StartupTimingMetric
import androidx.benchmark.macro.junit4.MacrobenchmarkRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Direction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.filters.LargeTest


/**
 * This is an example startup benchmark.
 *
 * It navigates to the device's home screen, and launches the default activity.
 *
 * Before running this benchmark:
 * 1) switch your app's active build variant in the Studio (affects Studio runs only)
 * 2) add `<profileable android:shell="true" />` to your app's manifest, within the `<application>` tag
 *
 * Run this benchmark from Studio to see startup measurements, and captured system traces
 * for investigating your app's performance.
 */
@LargeTest
@RunWith(AndroidJUnit4::class)
class ExampleStartupBenchmark {
    @get:Rule
    val benchmarkRule = MacrobenchmarkRule()

    @Test
    fun startup() = benchmarkRule.measureRepeated(
        packageName = "com.shreyashkore.composevsview",
        metrics = listOf(StartupTimingMetric()),
        iterations = 5,
        startupMode = StartupMode.COLD
    ) {
        pressHome()
        startActivityAndWait()
    }

    @Test
    fun scrollList() {
        benchmarkRule.measureRepeated(
            packageName = "com.shreyashkore.composevsview",
            metrics = listOf(FrameTimingMetric()),
            compilationMode = CompilationMode.None(),
            startupMode = StartupMode.WARM,
            iterations = 3,
            setupBlock = {
                val intent = Intent("$packageName.RECYCLER_VIEW_ACTIVITY")
                startActivityAndWait(intent)
            }
        ) {
            val recycler = device.findObject(By.res(packageName, "recycler"))
            recycler.setGestureMargin(device.displayWidth / 5)
            repeat(3) { recycler.fling(Direction.DOWN) }
        }
    }

    @Test
    fun scrollComposeList() {
        benchmarkRule.measureRepeated(
            packageName = "com.shreyashkore.composevsview",
            metrics = listOf(FrameTimingMetric()),
            compilationMode = CompilationMode.None(),
            startupMode = StartupMode.WARM,
            iterations = 3,
            setupBlock = {
                val intent = Intent("$packageName.COMPOSE_ACTIVITY")
                startActivityAndWait(intent)
            }
        ) {

            val column = device.findObject(By.res("lazy_column"))

            column.setGestureMargin(device.displayWidth / 5)
            repeat(3) { column.fling(Direction.DOWN) }
        }
    }
}