package com.travel.companion.feature.explore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.travel.companion.core.model.City

/**
 * Kesfet ekrani sehir detay paneli Compose UI.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
@Composable
internal fun CityDetailPane(
    city: City?,
    onNavigateToPlan: (cityId: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (city == null) {
        Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = stringResource(R.string.explore_city_select_prompt),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        return
    }

    Column(
        modifier = modifier.fillMaxSize().verticalScroll(rememberScrollState()),
    ) {
        AsyncImage(
            model = city.imageUrl.ifBlank { null },
            contentDescription = city.name,
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
                .clip(RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)),
            contentScale = ContentScale.Crop,
        )
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = city.name, style = MaterialTheme.typography.headlineMedium)
            Text(
                text = city.country,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = city.description, style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                city.tags.forEach { tag ->
                    SuggestionChip(
                        onClick = {},
                        label = { Text(tag, style = MaterialTheme.typography.labelSmall) },
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onNavigateToPlan(city.id) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(stringResource(R.string.explore_navigate_to_planner))
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}
