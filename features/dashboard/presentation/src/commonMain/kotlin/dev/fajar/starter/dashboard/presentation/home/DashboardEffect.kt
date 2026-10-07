package dev.fajar.starter.dashboard.presentation.home

sealed interface DashboardEffect {
    data object OpenSettings : DashboardEffect

    data object OpenNotifications : DashboardEffect
}
