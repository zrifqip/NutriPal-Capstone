package com.submission.nutripal

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController

import com.submission.nutripal.R
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.submission.nutripal.LoginScreen
import com.submission.nutripal.NavigationItem
import com.submission.nutripal.ui.auth.RegisterScreen
import com.submission.nutripal.Screen
import com.submission.nutripal.ui.dashboard.FoodRecommendation.HomeScreen
import com.submission.nutripal.ui.dashboard.Profile.ProfileScreen
import com.submission.nutripal.ui.survey.SurveyRoute
import dagger.hilt.android.HiltAndroidApp

@Composable
fun NutripalNavHost(
    navController: NavHostController = rememberNavController(),
) {
    var surveyFinished by remember { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            if (surveyFinished) {
                BottomBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Login.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onLogin = {
                        navController.navigate(Screen.Survey.route)
                    },
                    onRegister = {
                        navController.navigate(Screen.Register.route)
                    },
                )
            }
            composable(Screen.Survey.route) {
                SurveyRoute(
                    onSurveyComplete = {
                        surveyFinished = true
                        navController.navigate(Screen.Home.route)
                    },
                    onNavUp = navController::navigateUp
                )
            }
            composable(Screen.Register.route) {
                RegisterScreen(
                    onRegister = {
                        navController.navigate(Screen.Login.route)
                    })
            }
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Foodlist.route) {
                //do something
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(Screen.SurveyResult.route) {
                ResultScreen()
            }
        }
    }
}




@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.survey_result),
                icon = Icons.Default.List,
                screen = Screen.SurveyResult
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            ),
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}
