package com.travel.companion.feature.planner

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.DayPlan
import com.travel.companion.core.model.Place
import com.travel.companion.core.ui.LoadingIndicator

/**
 * Planner ekrani — Tercih girisi ve AI gezi plani gorunumu.
 *
 * @author Yakup Can
 * @date 2026-03-17
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun PlannerScreen(
    state: PlannerState,
    onIntent: (PlannerIntent) -> Unit,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
) {
    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.planner_title)) },
                actions = {
                    if (state.hasPlan) {
                        IconButton(onClick = { onIntent(PlannerIntent.ShowPreferences) }) {
                            Icon(Icons.Default.Tune, contentDescription = stringResource(R.string.planner_preferences_button_desc))
                        }
                    }
                },
            )
        },
        floatingActionButton = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                FloatingActionButton(
                    onClick = { onIntent(PlannerIntent.ShowVoiceSheet) },
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    Icon(Icons.Default.Mic, contentDescription = stringResource(R.string.planner_voice_assistant_desc))
                }
                if (state.hasPlan) {
                    FloatingActionButton(
                        onClick = { onIntent(PlannerIntent.SavePlan) },
                    ) {
                        if (state.isSaving) {
                            CircularProgressIndicator(modifier = Modifier.padding(8.dp))
                        } else {
                            Icon(Icons.Default.Save, contentDescription = stringResource(R.string.planner_save_button_desc))
                        }
                    }
                }
            }
        },
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            AnimatedVisibility(visible = state.showPreferences) {
                PreferencesSection(state = state, onIntent = onIntent)
            }

            when {
                state.isGenerating -> GeneratingContent()
                state.hasPlan -> ItineraryContent(state = state, onIntent = onIntent)
                !state.showPreferences -> EmptyPlanContent(onIntent = onIntent)
            }
        }
    }

    if (state.showVoiceSheet) {
        com.travel.companion.feature.planner.voice.VoiceAssistantSheet(
            isActive = state.isVoiceActive,
            transcript = state.voiceTranscript,
            onStart = { onIntent(PlannerIntent.StartVoice) },
            onStop = { onIntent(PlannerIntent.StopVoice) },
            onDismiss = { onIntent(PlannerIntent.HideVoiceSheet) },
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun PreferencesSection(
    state: PlannerState,
    onIntent: (PlannerIntent) -> Unit,
) {
    Column(modifier = Modifier.padding(16.dp)) {
        CityInput(state.cityName) { onIntent(PlannerIntent.SetCityName(it)) }
        Spacer(modifier = Modifier.height(12.dp))
        BudgetSelector(state.budget) { onIntent(PlannerIntent.SetBudget(it)) }
        Spacer(modifier = Modifier.height(12.dp))
        DurationSlider(state.durationDays) { onIntent(PlannerIntent.SetDuration(it)) }
        Spacer(modifier = Modifier.height(12.dp))
        InterestsChips(state.selectedInterests) { onIntent(PlannerIntent.ToggleInterest(it)) }
        Spacer(modifier = Modifier.height(16.dp))
        GenerateButton(state.canGenerate) { onIntent(PlannerIntent.GeneratePlan) }
    }
}

@Composable
private fun CityInput(cityName: String, onChanged: (String) -> Unit) {
    OutlinedTextField(
        value = cityName,
        onValueChange = onChanged,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(stringResource(R.string.planner_city_label)) },
        placeholder = { Text(stringResource(R.string.planner_city_placeholder)) },
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
    )
}

@Composable
private fun BudgetSelector(selectedBudget: Budget, onSelected: (Budget) -> Unit) {
    Text(stringResource(R.string.planner_budget_label), style = MaterialTheme.typography.titleSmall)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Budget.entries.forEach { budget ->
            FilterChip(
                selected = selectedBudget == budget,
                onClick = { onSelected(budget) },
                label = { Text(budget.name.lowercase().replaceFirstChar { it.uppercase() }) },
                leadingIcon = if (selectedBudget == budget) {
                    { Icon(Icons.Default.AttachMoney, contentDescription = stringResource(R.string.planner_budget_icon_desc)) }
                } else null,
            )
        }
    }
}

@Composable
private fun DurationSlider(durationDays: Int, onChanged: (Int) -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(Icons.Default.CalendarMonth, contentDescription = stringResource(R.string.planner_duration_icon_desc))
        Spacer(modifier = Modifier.width(8.dp))
        Text(stringResource(R.string.planner_duration_days, durationDays), style = MaterialTheme.typography.titleSmall)
    }
    Slider(
        value = durationDays.toFloat(),
        onValueChange = { onChanged(it.toInt()) },
        valueRange = 1f..14f,
        steps = 12,
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun InterestsChips(selectedInterests: List<String>, onToggle: (String) -> Unit) {
    Text(stringResource(R.string.planner_interests_label), style = MaterialTheme.typography.titleSmall)
    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        availableInterests.forEach { interest ->
            FilterChip(
                selected = interest in selectedInterests,
                onClick = { onToggle(interest) },
                label = { Text(interest) },
            )
        }
    }
}

@Composable
private fun GenerateButton(canGenerate: Boolean, onGenerate: () -> Unit) {
    Button(
        onClick = onGenerate,
        modifier = Modifier.fillMaxWidth(),
        enabled = canGenerate,
        shape = RoundedCornerShape(12.dp),
    ) {
        Text(stringResource(R.string.planner_generate_button))
    }
}

@Composable
private fun GeneratingContent() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        CircularProgressIndicator()
        Spacer(modifier = Modifier.height(16.dp))
        Text(stringResource(R.string.planner_generating_message), style = MaterialTheme.typography.bodyLarge)
        Text(stringResource(R.string.planner_generating_note), style = MaterialTheme.typography.bodySmall)
    }
}

@Composable
private fun ItineraryContent(
    state: PlannerState,
    onIntent: (PlannerIntent) -> Unit,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }
    val tabs = listOf(stringResource(R.string.planner_tab_itinerary), stringResource(R.string.planner_tab_map))

    Column(modifier = Modifier.fillMaxSize()) {
        // Gun secici
        LazyRow(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            itemsIndexed(state.travelPlan?.days.orEmpty()) { index, day ->
                FilterChip(
                    selected = state.selectedDayIndex == index,
                    onClick = { onIntent(PlannerIntent.SelectDay(index)) },
                    label = { Text(stringResource(R.string.planner_day_label, index + 1)) },
                )
            }
        }

        // Plan / Harita tab
        TabRow(selectedTabIndex = selectedTab) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTab == index,
                    onClick = { selectedTab = index },
                    text = { Text(title) },
                )
            }
        }

        Box(modifier = Modifier.weight(1f)) {
            when (selectedTab) {
                0 -> PlacesListContent(state = state)
                1 -> MapContent(places = state.selectedDay?.places.orEmpty())
            }
        }
    }
}

@Composable
private fun PlacesListContent(state: PlannerState) {
    state.selectedDay?.let { day ->
        Text(
            text = day.title,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
        )

        LazyColumn(
            contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(day.places, key = { it.id }) { place ->
                PlaceCard(place = place)
            }
        }
    }
}

@Composable
private fun MapContent(
    places: List<Place>,
    modifier: Modifier = Modifier,
) {
    val defaultPosition = if (places.isNotEmpty()) {
        com.google.android.gms.maps.model.LatLng(
            places.first().latitude,
            places.first().longitude,
        )
    } else {
        com.google.android.gms.maps.model.LatLng(41.0082, 28.9784)
    }

    val cameraPositionState = com.google.maps.android.compose.rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(defaultPosition, 13f)
    }

    com.google.maps.android.compose.GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
    ) {
        places.forEach { place ->
            com.google.maps.android.compose.Marker(
                state = com.google.maps.android.compose.MarkerState(
                    position = com.google.android.gms.maps.model.LatLng(
                        place.latitude,
                        place.longitude,
                    ),
                ),
                title = place.name,
                snippet = place.estimatedTime,
            )
        }

        if (places.size >= 2) {
            val routePoints = places.map { place ->
                com.google.android.gms.maps.model.LatLng(place.latitude, place.longitude)
            }
            com.google.maps.android.compose.Polyline(
                points = routePoints,
                color = androidx.compose.ui.graphics.Color(0xFF1976D2),
                width = 5f,
            )
        }
    }
}

@Composable
private fun PlaceCard(
    place: Place,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
    ) {
        Row(modifier = Modifier.padding(12.dp)) {
            AsyncImage(
                model = place.imageUrl,
                contentDescription = place.name,
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    text = place.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.AccessTime,
                            contentDescription = stringResource(R.string.planner_estimated_time_desc),
                            modifier = Modifier.height(14.dp),
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text(place.estimatedTime, style = MaterialTheme.typography.labelSmall)
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Star,
                            contentDescription = stringResource(R.string.planner_rating_desc),
                            modifier = Modifier.height(14.dp),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                        Spacer(modifier = Modifier.width(2.dp))
                        Text("${place.rating}", style = MaterialTheme.typography.labelSmall)
                    }
                }
            }
        }
    }
}

@Composable
private fun EmptyPlanContent(onIntent: (PlannerIntent) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(stringResource(R.string.planner_empty_message), style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { onIntent(PlannerIntent.ShowPreferences) }) {
            Text(stringResource(R.string.planner_enter_preferences_button))
        }
    }
}

@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
private fun PlannerScreenPreview() {
    com.travel.companion.core.designsystem.theme.TravelCompanionTheme {
        PlannerScreen(
            state = PlannerState(
                cityName = "Istanbul",
                budget = com.travel.companion.core.model.Budget.MEDIUM,
                durationDays = 3,
                selectedInterests = listOf("Tarih", "Yemek"),
                showPreferences = true,
            ),
            onIntent = {},
        )
    }
}
