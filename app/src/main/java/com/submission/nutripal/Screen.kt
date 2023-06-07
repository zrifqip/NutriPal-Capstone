package com.submission.nutripal

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Foodlist : Screen("Foodlist")
    object Profile : Screen("profile")
    object Survey : Screen("survey")

}
