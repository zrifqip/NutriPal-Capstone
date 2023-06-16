package com.submission.nutripal.ui.dashboard

import androidx.compose.ui.graphics.vector.ImageVector
import com.submission.nutripal.data.Screen

data class NavigationItem (
    val icon: ImageVector,
    val screen: Screen,
    val title: String
)
