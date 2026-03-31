---
name: navigation-3
description: Navigation 3 for Jetpack Compose. User-owned back stack, NavDisplay, adaptive multi-pane scenes, type-safe routes with kotlinx-serialization. Replaces Navigation 2 (NavHost/NavController).
---

# Navigation 3 (Compose-Native)

## Key Differences from Navigation 2

| Feature | Navigation 2 | Navigation 3 |
|---------|-------------|-------------|
| Back stack | Framework-owned (NavController) | User-owned (mutableStateListOf) |
| Navigation | NavHost + NavController | NavDisplay + BackStack |
| Type safety | @Serializable routes | @Serializable routes |
| Multi-pane | Manual | Built-in adaptive scenes |
| Predictive back | Limited | Full support |

## Setup

```kotlin
// libs.versions.toml
[versions]
navigation3 = "0.1.0-alpha03"

[libraries]
navigation3-runtime = { module = "androidx.navigation3:navigation3-runtime", version.ref = "navigation3" }
navigation3-ui = { module = "androidx.navigation3:navigation3-ui", version.ref = "navigation3" }
```

## Core Concept: User-Owned Back Stack

```kotlin
@Composable
fun TravelApp() {
    // YOU own the back stack
    val backStack = remember { mutableStateListOf<Any>(ExploreRoute) }

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<ExploreRoute> {
                ExploreScreen(
                    onCityClick = { cityId -> backStack.add(PlannerRoute(cityId)) }
                )
            }
            entry<PlannerRoute> { route ->
                PlannerScreen(
                    cityId = route.cityId,
                    onBack = { backStack.removeLastOrNull() }
                )
            }
            entry<TranslatorRoute> {
                TranslatorScreen()
            }
            entry<GalleryRoute> {
                GalleryScreen()
            }
        }
    )
}
```

## Type-Safe Routes

```kotlin
@Serializable
data object ExploreRoute

@Serializable
data class PlannerRoute(val cityId: String)

@Serializable
data object TranslatorRoute

@Serializable
data object GalleryRoute

@Serializable
data class DayDetailRoute(val planId: String, val dayIndex: Int)
```

## Adaptive Multi-Pane (NavDisplay Scenes)

```kotlin
NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider { /* ... */ },
    // Automatic adaptive scenes
    sceneStrategy = rememberAdaptiveSceneStrategy()
)
```

NavDisplay automatically:
- **Compact**: Single-pane, standard forward/back
- **Medium/Expanded**: Side-by-side panes when route metadata specifies it

### Scene Metadata

```kotlin
entry<PlannerRoute>(
    metadata = mapOf(
        NavDisplay.META_LIST_DETAIL_PANE_KEY to ListDetailPaneScaffoldRole.Detail
    )
) { route ->
    PlannerScreen(cityId = route.cityId)
}
```

## Predictive Back

```kotlin
NavDisplay(
    backStack = backStack,
    onBack = { backStack.removeLastOrNull() },
    entryProvider = entryProvider { /* ... */ },
    // Built-in predictive back animation
    popTransition = { fadeOut() + slideOutHorizontally { it / 4 } },
    enterTransition = { fadeIn() + slideInHorizontally { -it / 4 } }
)
```

## Navigation with Bottom Bar

```kotlin
@Composable
fun TravelApp() {
    val backStack = remember { mutableStateListOf<Any>(ExploreRoute) }
    var selectedTab by remember { mutableStateOf(0) }

    Scaffold(
        bottomBar = {
            NavigationBar {
                tabs.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = {
                            selectedTab = index
                            backStack.clear()
                            backStack.add(tab.route)
                        },
                        icon = { Icon(tab.icon, tab.label) },
                        label = { Text(tab.label) }
                    )
                }
            }
        }
    ) { padding ->
        NavDisplay(
            backStack = backStack,
            onBack = { backStack.removeLastOrNull() },
            modifier = Modifier.padding(padding),
            entryProvider = entryProvider { /* ... */ }
        )
    }
}
```

## Deep Links

```kotlin
entry<PlannerRoute>(
    deepLinks = listOf(
        navDeepLink { uriPattern = "travelapp://planner/{cityId}" }
    )
) { route ->
    PlannerScreen(cityId = route.cityId)
}
```

## Rules
- Back stack is a simple mutableStateListOf<Any>
- Navigation = backStack.add(route), Back = backStack.removeLast()
- Use @Serializable for ALL route types
- NO NavController, NO NavHost (these are Navigation 2)
- Use NavDisplay with entryProvider
- Adaptive scenes via rememberAdaptiveSceneStrategy()
- Predictive back is built-in (just provide transitions)
