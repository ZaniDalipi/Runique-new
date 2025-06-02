package com.zanoapps.auth.presentation.register

/*When creating such action interfaces we have to look at the UI and decide what kind of actions the user can trigger
* and then we define them here */

sealed interface RegisterAction {
    data object OnTogglePasswordVisibilityCheck : RegisterAction
    data object OnLoginClick : RegisterAction
    data object OnRegisterClick : RegisterAction
}