package com.project.app.moneypal.runtime

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Surface
import androidx.core.view.WindowCompat
import com.project.app.moneypal.R
import com.project.app.moneypal.features.host.ui.Host
import com.project.app.moneypal.foundation.window.WindowState
import com.project.app.moneypal.foundation.window.rememberWindowState
import com.project.app.moneypal.model.Theme
import com.project.app.moneypal.runtime.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var windowState: WindowState

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Wallee_Light)
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            windowState = rememberWindowState()

            Host {
                Surface {
                    MainNavHost(windowState)
                }
            }
        }
    }
}
