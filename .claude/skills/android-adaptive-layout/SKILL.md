---
name: android-adaptive-layout
description: Adaptive layouts for Android multi-form-factor development. WindowSizeClass, ListDetailPaneScaffold, NavigationSuiteScaffold for phone, tablet, and foldable devices. Use when building responsive UIs.
---

# Android Adaptive Layouts

## WindowSizeClass

```kotlin
// Setup in Activity
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
            TravelApp(windowSizeClass = windowSizeClass)
        }
    }
}
```

## Breakpoints

| Class | Width | Device |
|-------|-------|--------|
| Compact | < 600dp | Phone portrait |
| Medium | 600-839dp | Phone landscape, foldable, small tablet |
| Expanded | >= 840dp | Tablet landscape, desktop |

## NavigationSuiteScaffold (Adaptive Nav)

```kotlin
@Composable
fun TravelApp(windowSizeClass: WindowSizeClass) {
    var selectedTab by remember { mutableStateOf(TopLevelRoute.EXPLORE) }

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            TopLevelRoute.entries.forEach { route ->
                item(
                    icon = { Icon(route.icon, contentDescription = route.label) },
                    label = { Text(route.label) },
                    selected = selectedTab == route,
                    onClick = { selectedTab = route }
                )
            }
        }
    ) {
        // Automatically: BottomNav (compact), Rail (medium), Drawer (expanded)
        when (selectedTab) {
            TopLevelRoute.EXPLORE -> ExploreRoute()
            TopLevelRoute.PLANNER -> PlannerRoute()
            TopLevelRoute.TRANSLATOR -> TranslatorRoute()
            TopLevelRoute.GALLERY -> GalleryRoute()
        }
    }
}
```

## ListDetailPaneScaffold (List-Detail)

```kotlin
@Composable
fun PlannerAdaptiveScreen(
    state: PlannerState,
    onIntent: (PlannerIntent) -> Unit
) {
    val navigator = rememberListDetailPaneScaffoldNavigator<String>()

    ListDetailPaneScaffold(
        directive = navigator.scaffoldDirective,
        value = navigator.scaffoldValue,
        listPane = {
            ItineraryList(
                days = state.itinerary,
                onDayClick = { dayId ->
                    navigator.navigateTo(ListDetailPaneScaffoldRole.Detail, dayId)
                }
            )
        },
        detailPane = {
            navigator.currentDestination?.content?.let { dayId ->
                DayDetailPane(
                    day = state.itinerary.find { it.id == dayId },
                    onPlaceClick = { onIntent(PlannerIntent.PlaceClicked(it)) }
                )
            }
        }
    )
}
```

## Foldable Support

```kotlin
@Composable
fun FoldableAwareLayout(content: @Composable () -> Unit) {
    val foldingFeature = LocalFoldingFeature.current

    when {
        foldingFeature?.state == FoldingFeature.State.HALF_OPENED -> {
            // Tabletop mode: content on top half, controls on bottom
            TwoPane(first = { content() }, second = { Controls() })
        }
        else -> content()
    }
}
```

## Responsive Grid

```kotlin
@Composable
fun AdaptiveCityGrid(
    cities: List<City>,
    windowSizeClass: WindowSizeClass,
    onCityClick: (City) -> Unit
) {
    val columns = when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> 2
        WindowWidthSizeClass.MEDIUM -> 3
        WindowWidthSizeClass.EXPANDED -> 4
        else -> 2
    }

    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(columns),
        contentPadding = PaddingValues(16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalItemSpacing = 8.dp
    ) {
        items(cities) { city ->
            CityCard(city = city, onClick = { onCityClick(city) })
        }
    }
}
```

## Rules
- ALWAYS use WindowSizeClass (never hardcode breakpoints)
- NavigationSuiteScaffold for top-level nav (auto-adapts)
- ListDetailPaneScaffold for master-detail patterns
- Test on: phone portrait, phone landscape, tablet, foldable
- Compact = single pane, Expanded = multi-pane
- Provide meaningful content in both panes (no empty detail pane)
