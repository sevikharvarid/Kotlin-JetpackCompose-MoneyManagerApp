package com.wisnu.kurniawan.benchmark.baselineprofile

import androidx.benchmark.macro.ExperimentalBaselineProfilesApi
import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.uiautomator.By
import org.junit.Rule
import org.junit.Test

/**
 * Generates a baseline profile which can be copied to `app/src/main/baseline-prof.txt`.
 */
@ExperimentalBaselineProfilesApi
class BaselineProfileGenerator {
    @get:Rule
    val baselineProfileRule = BaselineProfileRule()

    @Test
    fun startup() =
        baselineProfileRule.collectBaselineProfile(
            packageName = "com.project.app.moneypal"
        ) {
            pressHome()
            // This block defines the app's critical user journey. Here we are interested in
            // optimizing for app startup. But you can also navigate and scroll
            // through your most important UI.
            startActivityAndWait()
            device.waitForIdle()

            device.run {
                val countryItem = findObject(By.text("Albania"))
                if (countryItem != null) {
                    countryItem.click()
                    findObject(By.text("Save"))
                        .click()
                }

                findObject(By.text("Balance"))?.click()

                waitForIdle()
            }
        }
}
