package com.travel.companion.core.ai.fake

import com.travel.companion.core.ai.AiPlannerService
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.DayPlan
import com.travel.companion.core.model.Place
import com.travel.companion.core.model.PlaceCategory
import com.travel.companion.core.model.TravelPlan
import kotlinx.coroutines.delay
import java.util.UUID
import javax.inject.Inject

/**
 * AI Planner servisi fake implementasyonu.
 * Firebase AI Logic entegrasyonu tamamlanana kadar ornek veri uretir.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
class FakeAiPlannerService @Inject constructor() : AiPlannerService {

    override suspend fun generateTravelPlan(
        cityName: String,
        budget: Budget,
        interests: List<String>,
        durationDays: Int,
    ): Result<TravelPlan> {
        // AI cagrisini simule et
        delay(2000L)

        val days = (1..durationDays).map { dayIndex ->
            DayPlan(
                dayIndex = dayIndex,
                title = "$dayIndex. Gun - ${getDayTheme(dayIndex, interests)}",
                places = generatePlacesForDay(cityName, dayIndex),
            )
        }

        return Result.success(
            TravelPlan(
                id = UUID.randomUUID().toString(),
                cityId = cityName.lowercase().replace(" ", "-"),
                cityName = cityName,
                days = days,
                budget = budget,
                interests = interests,
                createdAt = System.currentTimeMillis(),
            ),
        )
    }

    override suspend fun suggestRestaurants(
        cityName: String,
        cuisine: String,
        budget: Budget,
    ): Result<List<String>> {
        delay(1000L)
        return Result.success(
            listOf(
                "$cityName Mutfagi - Yerel lezzetler",
                "Sokak Lezzetleri Turu",
                "$cuisine Gurmesi - Premium deneyim",
            ),
        )
    }

    private fun getDayTheme(dayIndex: Int, interests: List<String>): String {
        val themes = listOf("Tarihi Yerler", "Yerel Lezzetler", "Kultur ve Sanat", "Dogal Guzellikler", "Alisveris ve Eglence")
        return themes.getOrElse(dayIndex - 1) { interests.firstOrNull() ?: "Kesfet" }
    }

    private fun generatePlacesForDay(cityName: String, dayIndex: Int): List<Place> =
        listOf(
            Place(
                id = "place-$dayIndex-1",
                name = "$cityName Merkez Meydan",
                description = "Sehrin kalbi, tarihi ve kulturel merkez.",
                imageUrl = "https://images.unsplash.com/photo-1524231757912-21f4fe3a7200",
                latitude = 41.0 + dayIndex * 0.01,
                longitude = 29.0 + dayIndex * 0.01,
                category = PlaceCategory.ATTRACTION,
                estimatedTime = "2 saat",
                rating = 4.5f,
            ),
            Place(
                id = "place-$dayIndex-2",
                name = "Yerel Restoran - Gun $dayIndex",
                description = "Bolgenin en iyi yerel lezzetlerini sunan restoran.",
                imageUrl = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4",
                latitude = 41.01 + dayIndex * 0.01,
                longitude = 29.01 + dayIndex * 0.01,
                category = PlaceCategory.RESTAURANT,
                estimatedTime = "1.5 saat",
                rating = 4.3f,
            ),
            Place(
                id = "place-$dayIndex-3",
                name = "$cityName Muzesi",
                description = "Sehrin tarihini ve kulturunu yansitan onemli muze.",
                imageUrl = "https://images.unsplash.com/photo-1566127444979-b3d2b654e3d7",
                latitude = 41.02 + dayIndex * 0.01,
                longitude = 29.02 + dayIndex * 0.01,
                category = PlaceCategory.MUSEUM,
                estimatedTime = "2.5 saat",
                rating = 4.7f,
            ),
        )
}
