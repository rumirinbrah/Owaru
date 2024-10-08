package com.example.todoapp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    background = darkBackground,
    onBackground = onDarkBackground,
    primary = darkPrimary ,
    secondary = darkSecondary ,
    onSecondary = onDarkSecondary,
    tertiary = darkTertiary,
    outline = darkTitle
)

private val LightColorScheme = lightColorScheme(
    background = lightBackground,
    onBackground = onLightBackground,
    primary = lightPrimary ,
    secondary = lightSecondary ,
    onSecondary = onLightSecondary,
    tertiary = lightTertiary,
    outline = lightTitle

)

@Composable
fun TodoAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme() ,
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false ,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            //if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            dynamicDarkColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.secondary.toArgb()
            WindowCompat.getInsetsController(window , view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme ,
        typography = Typography ,
        content = content
    )
}