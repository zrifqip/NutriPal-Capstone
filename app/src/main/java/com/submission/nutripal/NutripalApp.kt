package com.submission.nutripal

import android.app.Activity
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController

import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.submission.nutripal.data.Screen
import com.submission.nutripal.ui.auth.LoginScreen
import com.submission.nutripal.ui.auth.RegisterScreen
import com.submission.nutripal.ui.dashboard.FoodRecommendation.HomeScreen
import com.submission.nutripal.ui.dashboard.NavigationItem
import com.submission.nutripal.ui.dashboard.Profile.ProfileScreen
import com.submission.nutripal.ui.dashboard.ResultDashboard.ResultScreen
import com.submission.nutripal.ui.survey.SurveyRoute

@Composable
fun NutripalNavHost(
    navController: NavHostController = rememberNavController(),
) {
    val activity = (LocalContext.current as? Activity)
    BackHandler {
        if (navController.currentBackStackEntry?.destination?.route == Screen.Login.route) {
            activity?.finish()
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Login.route
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLogin = {
                    navController.navigate(Screen.NewSurvey.route)
                },
                onRegister = {
                    navController.navigate(Screen.Register.route)
                },
            )
        }
        composable(
            route = Screen.NewSurvey.route
        ) {
            SurveyRoute(
                onSurveyComplete = {
                    navController.navigate(Screen.Dashboard.route)
                },
                onNavUp = {
                    navController.navigate(Screen.Login.route)
                },
                isUpdate = false
            )
        }

        composable(
            route = Screen.UpdateSurvey.route
        ) {
            SurveyRoute(
                onSurveyComplete = {
                    navController.navigate(Screen.Dashboard.route)
                },
                onNavUp = navController::navigateUp,
                isUpdate = true
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegister = {
                    navController.navigate(Screen.Login.route)
                })
        }
        composable(Screen.Dashboard.route) {
            BackHandler() {
                activity?.finish()
            }
            val dashboardNavController = rememberNavController()
            Scaffold(
                bottomBar = {
                    BottomBar(dashboardNavController)
                }
            ) { innerPadding ->
                NavHost(
                    navController = dashboardNavController,
                    startDestination = Screen.Dashboard.Home.route,
                    modifier = Modifier.padding(innerPadding)
                ) {

                    composable(Screen.Dashboard.Home.route) {
                        HomeScreen()
                    }
                    composable(Screen.Dashboard.Foodlist.route) {
                        // do something
                    }
                    composable(Screen.Dashboard.Profile.route) {
                        ProfileScreen(
                            navigateToSurvey = {
                                dashboardNavController.navigateUp()
                                navController.navigate(Screen.UpdateSurvey.route)
                            },
                            Logout = {
                                dashboardNavController.navigateUp()
                                navController.navigate(Screen.Login.route)
                            }
                        )
                    }
                    composable(Screen.Dashboard.SurveyResult.route) {
                        ResultScreen()
                    }
                }
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
                screen = Screen.Dashboard.Home
            ),
            NavigationItem(
                title = stringResource(R.string.survey_result),
                icon = Icons.Default.List,
                screen = Screen.Dashboard.SurveyResult
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Dashboard.Profile
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
