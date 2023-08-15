package com.project.app.moneypal.runtime.initializer

import android.content.Context
import androidx.startup.Initializer
import com.wisnu.foundation.liblifecycleloggr.ActivityLifecycleLoggr
import com.project.app.moneypal.runtime.MoneyPalApp

class ActivityLifecycleLoggrInitializer : Initializer<ActivityLifecycleLoggr> {
    override fun create(context: Context): ActivityLifecycleLoggr {
        return ActivityLifecycleLoggr().also {
            (context.applicationContext as MoneyPalApp)
                .registerActivityLifecycleCallbacks(it)
        }
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(LoggrInitializer::class.java)
}
