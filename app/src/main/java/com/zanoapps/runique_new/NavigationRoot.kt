package com.zanoapps.runique_new

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.zanoapps.auth.presentation.intro.IntroScreenRoot
import com.zanoapps.auth.presentation.login.LoginScreenRoot
import com.zanoapps.auth.presentation.register.RegisterScreenRoot
import com.zanoapps.core.presentation.designsystem.util.Routes

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.AUTH
    ) {
        authGraph(navController)

    }
}

private fun NavGraphBuilder.authGraph(navController: NavHostController) {
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
        // implement the login screen Rot
        composable(Routes.LOGIN) {
            LoginScreenRoot(
                onLoginSuccess = {
                    navController.navigate(Routes.RUN){
                        popUpTo(Routes.AUTH){
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