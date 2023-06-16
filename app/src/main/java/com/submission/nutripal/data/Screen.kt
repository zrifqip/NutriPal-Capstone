package com.submission.nutripal.data

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Register : Screen("register")
    object NewSurvey : Screen("survey/new")
    object UpdateSurvey : Screen("survey/update")

    // Dashboard screens
    object Dashboard : Screen("dashboard") {
        val routeWithSlash = "${Dashboard.route}/"
        object Home : Screen("${Dashboard.routeWithSlash}home")
        object Foodlist : Screen("${Dashboard.routeWithSlash}foodlist")
        object Profile : Screen("${Dashboard.routeWithSlash}profile")
        object SurveyResult : Screen("${Dashboard.routeWithSlash}surveyresult")
    }
}