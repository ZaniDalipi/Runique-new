package com.zanoapps.auth.presentation.register

import com.zanoapps.core.presentation.ui.UiText

/*When creating such action interfaces we have to look at the UI and decide what kind of actions the user can trigger
* and then we define them here */

sealed interface RegisterEvent {
    data object RegistrationSuccess : RegisterEvent
    data class Error(val error: UiText) : RegisterEvent
}