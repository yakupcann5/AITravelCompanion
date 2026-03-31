package com.travel.companion.core.ai.firebase

/**
 * [FirebaseAiPlannerService] JSON deserialization ve domain mapping testleri.
 *
 * @author Yakup Can
 * @date 2026-03-29
 */
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.PlaceCategory
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FirebaseAiPlannerMappingTest {

    private val json = Json { ignoreUnknownKeys = true }

    // region parseCategory

    @Test
    fun `parseCategory returns RESTAURANT for restaurant`() {
        assertEquals(PlaceCategory.RESTAURANT, parseCategory("restaurant"))
    }

    @Test
    fun `parseCategory returns RESTAURANT for Turkish restoran`() {
        assertEquals(PlaceCategory.RESTAURANT, parseCategory("restoran"))
    }

    @Test
    fun `parseCategory returns MUSEUM for museum`() {
        assertEquals(PlaceCategory.MUSEUM, parseCategory("museum"))
    }

    @Test
    fun `parseCategory returns MUSEUM for Turkish muze`() {
        assertEquals(PlaceCategory.MUSEUM, parseCategory("muze"))
    }

    @Test
    fun `parseCategory returns PARK for park`() {
        assertEquals(PlaceCategory.PARK, parseCategory("park"))
    }

    @Test
    fun `parseCategory returns SHOPPING for shopping`() {
        assertEquals(PlaceCategory.SHOPPING, parseCategory("shopping"))
    }

    @Test
    fun `parseCategory returns SHOPPING for Turkish alisveris`() {
        assertEquals(PlaceCategory.SHOPPING, parseCategory("alisveris"))
    }

    @Test
    fun `parseCategory returns NIGHTLIFE for nightlife`() {
        assertEquals(PlaceCategory.NIGHTLIFE, parseCategory("nightlife"))
    }

    @Test
    fun `parseCategory returns HOTEL for hotel`() {
        assertEquals(PlaceCategory.HOTEL, parseCategory("hotel"))
    }

    @Test
    fun `parseCategory returns ATTRACTION for unknown input`() {
        assertEquals(PlaceCategory.ATTRACTION, parseCategory("bilinmeyen"))
    }

    @Test
    fun `parseCategory is case insensitive`() {
        assertEquals(PlaceCategory.RESTAURANT, parseCategory("RESTAURANT"))
        assertEquals(PlaceCategory.MUSEUM, parseCategory("Museum"))
    }

    // endregion

    // region Budget.toTurkish

    @Test
    fun `Budget LOW toTurkish returns dusuk`() {
        assertEquals("dusuk", Budget.LOW.toTurkish())
    }

    @Test
    fun `Budget MEDIUM toTurkish returns orta`() {
        assertEquals("orta", Budget.MEDIUM.toTurkish())
    }

    @Test
    fun `Budget HIGH toTurkish returns yuksek`() {
        assertEquals("yuksek", Budget.HIGH.toTurkish())
    }

    @Test
    fun `Budget LUXURY toTurkish returns luks`() {
        assertEquals("luks", Budget.LUXURY.toTurkish())
    }

    // endregion

    // region AiPlaceResponse.toDomain

    @Test
    fun `AiPlaceResponse toDomain maps all fields`() {
        val response = AiPlaceResponse(
            name = "Ayasofya",
            description = "Tarihi cami ve muze",
            latitude = 41.0086,
            longitude = 28.9802,
            category = "museum",
            estimatedTime = "2 saat",
            rating = 4.8f,
        )

        val place = response.toDomain()

        assertEquals("ayasofya", place.id)
        assertEquals("Ayasofya", place.name)
        assertEquals("Tarihi cami ve muze", place.description)
        assertEquals(41.0086, place.latitude, 0.0001)
        assertEquals(28.9802, place.longitude, 0.0001)
        assertEquals(PlaceCategory.MUSEUM, place.category)
        assertEquals("2 saat", place.estimatedTime)
        assertEquals(4.8f, place.rating)
    }

    @Test
    fun `AiPlaceResponse toDomain uses defaults for optional fields`() {
        val response = AiPlaceResponse(
            name = "Test Place",
            description = "Test",
        )

        val place = response.toDomain()

        assertEquals(0.0, place.latitude, 0.0001)
        assertEquals(0.0, place.longitude, 0.0001)
        assertEquals(PlaceCategory.ATTRACTION, place.category)
        assertEquals("", place.estimatedTime)
        assertEquals(0f, place.rating)
    }

    @Test
    fun `AiPlaceResponse toDomain generates id from name`() {
        val response = AiPlaceResponse(
            name = "  Topkapi Sarayi  ",
            description = "Osmanli sarayi",
        )

        assertEquals("topkapi-sarayi", response.toDomain().id)
    }

    // endregion

    // region AiDayPlanResponse.toDomain

    @Test
    fun `AiDayPlanResponse toDomain sets correct dayIndex`() {
        val response = AiDayPlanResponse(
            title = "1. Gun - Tarihi Yerler",
            places = emptyList(),
        )

        val dayPlan = response.toDomain(2)

        assertEquals(2, dayPlan.dayIndex)
        assertEquals("1. Gun - Tarihi Yerler", dayPlan.title)
        assertTrue(dayPlan.places.isEmpty())
    }

    @Test
    fun `AiDayPlanResponse toDomain maps places`() {
        val response = AiDayPlanResponse(
            title = "Gun 1",
            places = listOf(
                AiPlaceResponse(name = "Yer 1", description = "Aciklama 1"),
                AiPlaceResponse(name = "Yer 2", description = "Aciklama 2"),
            ),
        )

        val dayPlan = response.toDomain(0)

        assertEquals(2, dayPlan.places.size)
        assertEquals("Yer 1", dayPlan.places[0].name)
        assertEquals("Yer 2", dayPlan.places[1].name)
    }

    // endregion

    // region AiTravelPlanResponse.toDomain

    @Test
    fun `AiTravelPlanResponse toDomain maps cityName budget and interests`() {
        val response = AiTravelPlanResponse(
            days = listOf(
                AiDayPlanResponse(title = "Gun 1", places = emptyList()),
            ),
        )

        val plan = response.toDomain("Istanbul", Budget.HIGH, listOf("Tarih", "Yemek"))

        assertEquals("Istanbul", plan.cityName)
        assertEquals("istanbul", plan.cityId)
        assertEquals(Budget.HIGH, plan.budget)
        assertEquals(listOf("Tarih", "Yemek"), plan.interests)
        assertEquals(1, plan.days.size)
        assertTrue(plan.id.isNotBlank())
        assertTrue(plan.createdAt > 0)
    }

    @Test
    fun `AiTravelPlanResponse toDomain generates cityId from city name`() {
        val response = AiTravelPlanResponse(days = emptyList())

        val plan = response.toDomain("New York", Budget.LOW, emptyList())

        assertEquals("new-york", plan.cityId)
    }

    // endregion

    // region JSON deserialization

    @Test
    fun `AiRestaurantResponse deserializes correctly`() {
        val jsonStr = """{"restaurants": ["Restoran A", "Restoran B", "Restoran C"]}"""
        val result = json.decodeFromString<AiRestaurantResponse>(jsonStr)

        assertEquals(3, result.restaurants.size)
        assertEquals("Restoran A", result.restaurants[0])
    }

    @Test
    fun `AiTravelPlanResponse full JSON deserialization`() {
        val jsonStr = """
        {
          "days": [
            {
              "title": "1. Gun - Tarihi Yerler",
              "places": [
                {
                  "name": "Ayasofya",
                  "description": "Bizans mirasi",
                  "latitude": 41.0086,
                  "longitude": 28.9802,
                  "category": "museum",
                  "estimatedTime": "2 saat",
                  "rating": 4.8
                },
                {
                  "name": "Sultanahmet",
                  "description": "Mavi cami",
                  "latitude": 41.0054,
                  "longitude": 28.9768,
                  "category": "attraction",
                  "estimatedTime": "1.5 saat",
                  "rating": 4.6
                }
              ]
            }
          ]
        }
        """.trimIndent()

        val response = json.decodeFromString<AiTravelPlanResponse>(jsonStr)
        val plan = response.toDomain("Istanbul", Budget.MEDIUM, listOf("Tarih"))

        assertEquals(1, plan.days.size)
        assertEquals(2, plan.days[0].places.size)
        assertEquals("Ayasofya", plan.days[0].places[0].name)
        assertEquals(PlaceCategory.MUSEUM, plan.days[0].places[0].category)
        assertEquals("Sultanahmet", plan.days[0].places[1].name)
        assertEquals(PlaceCategory.ATTRACTION, plan.days[0].places[1].category)
    }

    @Test
    fun `AiTravelPlanResponse ignores unknown JSON keys`() {
        val jsonStr = """
        {
          "days": [],
          "unknownField": "should be ignored"
        }
        """.trimIndent()

        val response = json.decodeFromString<AiTravelPlanResponse>(jsonStr)
        assertTrue(response.days.isEmpty())
    }

    // endregion
}
