package com.travel.companion.navigation

import androidx.navigation3.runtime.NavKey

/**
 * Navigation 3 ekran gecislerini yoneten navigator sinifi.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class Navigator(private val state: NavigationState) {

    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun goBack() {
        val currentStack = state.backStacks[state.topLevelRoute] ?: return
        val currentRoute = currentStack.lastOrNull() ?: return

        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentStack.removeLastOrNull()
        }
    }
}
