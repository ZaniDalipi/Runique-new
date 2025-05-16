package com.zanoapps.auth.presentation.login

import com.zanoapps.core.presentation.ui.UiText

sealed interface LoginEvents {
    data class Error(val error: UiText): LoginEvents
    data object LoginSuccess: LoginEvents
}