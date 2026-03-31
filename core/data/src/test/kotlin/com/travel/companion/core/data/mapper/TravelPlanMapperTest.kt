package com.travel.companion.core.data.mapper

/**
 * TravelPlan mapper birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import com.travel.companion.core.database.entity.TravelPlanEntity
import com.travel.companion.core.model.Budget
import com.travel.companion.core.model.DayPlan
import com.travel.companion.core.model.Place
import com.travel.companion.core.model.PlaceCategory
import com.travel.companion.core.model.TravelPlan
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class TravelPlanMapperTest {

    // --- Entity to Domain ---

    @Test
    fun `toDomain maps all scalar fields correctly`() {
        val entity = createEntity(
            id = "plan-1",
            cityId = "istanbul",
            cityName = "Istanbul",
            budget = "high",
            createdAt = 1711152000000L,
        )

        val domain = entity.toDomain()

        assertEquals("plan-1", domain.id)
        assertEquals("istanbul", domain.cityId)
        assertEquals("Istanbul", domain.cityName)
        assertEquals(Budget.HIGH, domain.budget)
        assertEquals(1711152000000L, domain.createdAt)
    }

    @Test
    fun `toDomain maps budget case insensitively`() {
        val lowercase = createEntity(budget = "luxury").toDomain()
        val uppercase = createEntity(budget = "LUXURY").toDomain()
        val mixed = createEntity(budget = "Luxury").toDomain()

        assertEquals(Budget.LUXURY, lowercase.budget)
        assertEquals(Budget.LUXURY, uppercase.budget)
        assertEquals(Budget.LUXURY, mixed.budget)
    }

    @Test
    fun `toDomain falls back to MEDIUM for unknown budget string`() {
        val entity = createEntity(budget = "unknown_value")

        val domain = entity.toDomain()

        assertEquals(Budget.MEDIUM, domain.budget)
    }

    @Test
    fun `toDomain parses interests JSON array`() {
        val entity = createEntity(interests = """["Tarih","Kultur","Yemek"]""")

        val domain = entity.toDomain()

        assertEquals(listOf("Tarih", "Kultur", "Yemek"), domain.interests)
    }

    @Test
    fun `toDomain returns empty list for malformed interests JSON`() {
        val entity = createEntity(interests = "not-valid-json")

        val domain = entity.toDomain()

        assertTrue(domain.interests.isEmpty())
    }

    @Test
    fun `toDomain parses daysJson into DayPlan list`() {
        val daysJson = """[{"dayIndex":0,"title":"Birinci Gun","places":[]}]"""
        val entity = createEntity(daysJson = daysJson)

        val domain = entity.toDomain()

        assertEquals(1, domain.days.size)
        assertEquals(0, domain.days[0].dayIndex)
        assertEquals("Birinci Gun", domain.days[0].title)
    }

    @Test
    fun `toDomain returns empty days list for malformed daysJson`() {
        val entity = createEntity(daysJson = "not-valid-json")

        val domain = entity.toDomain()

        assertTrue(domain.days.isEmpty())
    }

    // --- Domain to Entity ---

    @Test
    fun `toEntity maps all scalar fields correctly`() {
        val plan = createDomainPlan(
            id = "plan-2",
            cityId = "antalya",
            cityName = "Antalya",
            budget = Budget.LOW,
            createdAt = 1700000000000L,
        )

        val entity = plan.toEntity()

        assertEquals("plan-2", entity.id)
        assertEquals("antalya", entity.cityId)
        assertEquals("Antalya", entity.cityName)
        assertEquals("low", entity.budget)
        assertEquals(1700000000000L, entity.createdAt)
    }

    @Test
    fun `toEntity serializes budget as lowercase string`() {
        val mediumPlan = createDomainPlan(budget = Budget.MEDIUM).toEntity()
        val highPlan = createDomainPlan(budget = Budget.HIGH).toEntity()
        val luxuryPlan = createDomainPlan(budget = Budget.LUXURY).toEntity()

        assertEquals("medium", mediumPlan.budget)
        assertEquals("high", highPlan.budget)
        assertEquals("luxury", luxuryPlan.budget)
    }

    @Test
    fun `toEntity serializes interests as JSON array`() {
        val plan = createDomainPlan(interests = listOf("Deniz", "Spor"))

        val entity = plan.toEntity()

        assertTrue(entity.interests.contains("Deniz"))
        assertTrue(entity.interests.contains("Spor"))
        assertTrue(entity.interests.startsWith("["))
        assertTrue(entity.interests.endsWith("]"))
    }

    @Test
    fun `toEntity serializes days as JSON`() {
        val plan = createDomainPlan(
            days = listOf(DayPlan(dayIndex = 0, title = "Birinci Gun")),
        )

        val entity = plan.toEntity()

        assertTrue(entity.daysJson.contains("Birinci Gun"))
        assertTrue(entity.daysJson.startsWith("["))
        assertTrue(entity.daysJson.endsWith("]"))
    }

    @Test
    fun `toEntity serializes empty interests as empty JSON array`() {
        val plan = createDomainPlan(interests = emptyList())

        val entity = plan.toEntity()

        assertEquals("[]", entity.interests)
    }

    // --- Roundtrip ---

    @Test
    fun `roundtrip entity to domain preserves all data`() {
        val original = createEntity(
            id = "rt-1",
            cityId = "trabzon",
            cityName = "Trabzon",
            budget = "medium",
            interests = """["Dogal","Cay"]""",
            daysJson = """[{"dayIndex":0,"title":"Sahil","places":[]}]""",
            createdAt = 1711152000000L,
        )

        val domain = original.toDomain()
        val roundtripped = domain.toEntity()

        assertEquals(original.id, roundtripped.id)
        assertEquals(original.cityId, roundtripped.cityId)
        assertEquals(original.cityName, roundtripped.cityName)
        assertEquals(original.budget, roundtripped.budget)
        assertEquals(original.createdAt, roundtripped.createdAt)
    }

    @Test
    fun `roundtrip domain to entity preserves all data`() {
        val original = createDomainPlan(
            id = "rt-2",
            cityId = "cappadocia",
            cityName = "Cappadocia",
            budget = Budget.LUXURY,
            interests = listOf("Balon", "Caves"),
            days = listOf(DayPlan(dayIndex = 0, title = "Balon Turu")),
            createdAt = 1711152000000L,
        )

        val entity = original.toEntity()
        val roundtripped = entity.toDomain()

        assertEquals(original.id, roundtripped.id)
        assertEquals(original.cityId, roundtripped.cityId)
        assertEquals(original.cityName, roundtripped.cityName)
        assertEquals(original.budget, roundtripped.budget)
        assertEquals(original.interests, roundtripped.interests)
        assertEquals(original.createdAt, roundtripped.createdAt)
        assertEquals(1, roundtripped.days.size)
        assertEquals("Balon Turu", roundtripped.days[0].title)
    }

    @Test
    fun `roundtrip preserves place details inside day plans`() {
        val place = Place(
            id = "place-1",
            name = "Ayasofya",
            description = "Tarihi yapi",
            imageUrl = "https://example.com/img.jpg",
            latitude = 41.0086,
            longitude = 28.9802,
            category = PlaceCategory.ATTRACTION,
            estimatedTime = "120 dk",
            rating = 4.8f,
        )
        val original = createDomainPlan(
            days = listOf(DayPlan(dayIndex = 0, title = "Tur", places = listOf(place))),
        )

        val roundtripped = original.toEntity().toDomain()

        assertEquals(1, roundtripped.days[0].places.size)
        assertEquals("Ayasofya", roundtripped.days[0].places[0].name)
        assertEquals(PlaceCategory.ATTRACTION, roundtripped.days[0].places[0].category)
    }

    // --- Helpers ---

    private fun createEntity(
        id: String = "plan-default",
        cityId: String = "city",
        cityName: String = "City",
        budget: String = "medium",
        interests: String = "[]",
        daysJson: String = "[]",
        createdAt: Long = 1711152000000L,
    ) = TravelPlanEntity(
        id = id,
        cityId = cityId,
        cityName = cityName,
        budget = budget,
        interests = interests,
        daysJson = daysJson,
        createdAt = createdAt,
    )

    private fun createDomainPlan(
        id: String = "plan-default",
        cityId: String = "city",
        cityName: String = "City",
        budget: Budget = Budget.MEDIUM,
        interests: List<String> = listOf("Tarih"),
        days: List<DayPlan> = listOf(DayPlan(dayIndex = 0, title = "Birinci Gun")),
        createdAt: Long = 1711152000000L,
    ) = TravelPlan(
        id = id,
        cityId = cityId,
        cityName = cityName,
        days = days,
        budget = budget,
        interests = interests,
        createdAt = createdAt,
    )
}
