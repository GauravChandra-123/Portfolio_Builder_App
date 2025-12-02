package com.example.bizzcardapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bizzcardapp.ui.theme.Typography

@Composable
fun PortfolioTheme(content: @Composable () -> Unit) {

    val lightColors = lightColorScheme(
        primary = Color(0xFF1565C0),
        secondary = Color(0xFF4FC3F7),
        tertiary = Color(0xFF81D4FA),
        background = Color(0xFFF7F9FC),
        surface = Color.White,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onSurface = Color(0xFF1A1A1A)
    )

    val shapes = Shapes(
        small = RoundedCornerShape(12.dp),
        medium = RoundedCornerShape(18.dp),
        large = RoundedCornerShape(26.dp)
    )

    val typography = Typography(
        headlineMedium = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.SemiBold),
        titleLarge = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Bold),
        bodyLarge = TextStyle(fontSize = 16.sp),
        bodyMedium = TextStyle(fontSize = 14.sp),
        bodySmall = TextStyle(fontSize = 12.sp),
        labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    )

    MaterialTheme(
        colorScheme = lightColors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}