---
name: maps-compose
description: Google Maps SDK for Jetpack Compose. Markers, polylines, custom info windows, camera animations, and route visualization. Use when integrating maps into Compose screens.
---

# Google Maps Compose

## Setup

```kotlin
// libs.versions.toml
[versions]
maps-compose = "6.4.1"
play-services-maps = "19.1.0"

[libraries]
maps-compose = { module = "com.google.maps.android:maps-compose", version.ref = "maps-compose" }
play-services-maps = { module = "com.google.android.gms:play-services-maps", version.ref = "play-services-maps" }
```

```xml
<!-- AndroidManifest.xml -->
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="${MAPS_API_KEY}" />
```

```properties
# local.properties (git ignored)
MAPS_API_KEY=your_key_here
```

## Basic Map

```kotlin
@Composable
fun TravelMap(
    places: List<Place>,
    selectedPlace: Place?,
    onPlaceClick: (Place) -> Unit,
    modifier: Modifier = Modifier
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(
            LatLng(places.first().lat, places.first().lng),
            12f
        )
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(isMyLocationEnabled = false),
        uiSettings = MapUiSettings(zoomControlsEnabled = true)
    ) {
        places.forEach { place ->
            Marker(
                state = MarkerState(position = LatLng(place.lat, place.lng)),
                title = place.name,
                snippet = place.description,
                onClick = {
                    onPlaceClick(place)
                    false // show info window
                }
            )
        }
    }
}
```

## Route Polyline

```kotlin
@Composable
fun RouteMap(
    routePoints: List<LatLng>,
    markers: List<Place>,
    modifier: Modifier = Modifier
) {
    GoogleMap(modifier = modifier) {
        // Route line
        Polyline(
            points = routePoints,
            color = MaterialTheme.colorScheme.primary,
            width = 8f,
            pattern = listOf(Dot(), Gap(10f))
        )

        // Numbered markers
        markers.forEachIndexed { index, place ->
            MarkerComposable(
                state = MarkerState(LatLng(place.lat, place.lng)),
                title = place.name
            ) {
                NumberedMarker(number = index + 1)
            }
        }
    }
}
```

## Camera Animation

```kotlin
// Animate to a place
LaunchedEffect(selectedPlace) {
    selectedPlace?.let { place ->
        cameraPositionState.animate(
            update = CameraUpdateFactory.newLatLngZoom(
                LatLng(place.lat, place.lng), 15f
            ),
            durationMs = 500
        )
    }
}

// Fit all markers in view
LaunchedEffect(places) {
    if (places.isNotEmpty()) {
        val bounds = LatLngBounds.builder().apply {
            places.forEach { include(LatLng(it.lat, it.lng)) }
        }.build()
        cameraPositionState.animate(
            CameraUpdateFactory.newLatLngBounds(bounds, 64)
        )
    }
}
```

## Custom Info Window

```kotlin
MarkerInfoWindowContent(
    state = MarkerState(position = latLng),
    title = place.name
) { marker ->
    Card(modifier = Modifier.width(200.dp)) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(model = place.imageUrl, contentDescription = null)
            Text(marker.title ?: "", style = MaterialTheme.typography.titleSmall)
            Text(place.description, style = MaterialTheme.typography.bodySmall)
        }
    }
}
```

## Rules
- API key in local.properties (never commit)
- Use rememberCameraPositionState for camera control
- Animate camera changes (don't snap)
- MarkerComposable for custom marker UI
- Handle location permission separately (not in map composable)
- Use LatLngBounds to auto-fit markers
