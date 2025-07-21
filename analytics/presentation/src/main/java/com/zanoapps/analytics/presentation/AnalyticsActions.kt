package com.zanoapps.analytics.presentation

sealed interface AnalyticsActions {
    data object OnBackClick: AnalyticsActions
}