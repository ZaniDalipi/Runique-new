package com.zanoapps.runique_new

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink
import androidx.navigation.navigation
import com.zanoapps.auth.presentation.intro.IntroScreenRoot
import com.zanoapps.auth.presentation.login.LoginScreenRoot
import com.zanoapps.auth.presentation.register.RegisterScreenRoot
import com.zanoapps.core.presentation.designsystem.util.Routes
import com.zanoapps.run.presentation.active_run.ActiveRunScreenScreenRot
import com.zanoapps.run.presentation.active_run.service.ActiveRunService
import com.zanoapps.run.presentation.run_overview.RunOverviewScreenRot

@Composable
fun NavigationRoot(
    navController: NavHostController,
    isLoggedIn: Boolean
) {
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.RUN else Routes.AUTH
    ) {
        authGraph(navController)
        runGraph(navController)

    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {

    // impl of INTRO screen navigation
    navigation(
        startDestination = Routes.INTRO,
        route = Routes.AUTH
    ) {
        composable(route = Routes.INTRO) {
            IntroScreenRoot(
                onSignUpClick = {
                    navController.navigate(Routes.REGISTER)
                },
                onSignInClick = {
                    navController.navigate(Routes.LOGIN)
                }
            )
        }

        // implement the REGISTER screen for navigation
        composable(route = Routes.REGISTER) {
            RegisterScreenRoot(
                onLoginClick = {
                    navController.navigate(Routes.LOGIN) {
                        //  // 👇 Clears the back stack up to REGISTER
                        popUpTo(Routes.REGISTER) {
                            inclusive = true // 👈 Includes REGISTER itself in the pop
                            saveState = true // 👈 Saves REGISTER's state (e.g., form data)
                        }
                        restoreState = true
                    }
                },
                onSuccessfulRegistration = {
                    navController.navigate(Routes.LOGIN)
                },
            )
        }


        // implement the LOGIN screen for navigation
        composable(Routes.LOGIN) {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(Routes.RUN) {
                        popUpTo(Routes.AUTH) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = {
                    navController.navigate(Routes.REGISTER) {
                        //  // 👇 Clears the back stack up to REGISTER
                        popUpTo(Routes.LOGIN) {
                            inclusive = true // 👈 Includes REGISTER itself in the pop
                            saveState = true // 👈 Saves REGISTER's state (e.g., form data)
                        }
                        restoreState = true
                    }

                }
            )
        }
    }
}

private fun NavGraphBuilder.runGraph(navController: NavHostController) {

    navigation(
        startDestination = Routes.RUN_OVERVIEW,
        route = Routes.RUN

    ) {
        composable(
            route = Routes.RUN_OVERVIEW
        ) {
            RunOverviewScreenRot(
                onStartClick = {
                    navController.navigate(Routes.ACTIVE_RUN)
                }
            )
        }
        composable(
            route = Routes.ACTIVE_RUN,
            deepLinks = listOf(
                navDeepLink {
                    uriPattern = "runique://active_run"
                }
            )
        ) {
            val context = LocalContext.current
            ActiveRunScreenScreenRot(
                onServiceToggle = { shouldServiceRun ->
                    if (shouldServiceRun) {
                        context.startService(
                            ActiveRunService.createStartIntent(
                                context = context,
                                activityClass = MainActivity::class.java
                            )
                        )

                    } else {
                        context.startService(
                            ActiveRunService.createStopIntent(context)
                        )
                    }

                },
            )
        }


    }
}