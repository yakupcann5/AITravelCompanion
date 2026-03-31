package com.travel.companion.core.data.mapper

/**
 * City mapper birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import com.travel.companion.core.database.entity.CityEntity
import com.travel.companion.core.model.City
import org.junit.Assert.assertEquals
import org.junit.Test

class CityMapperTest {

    @Test
    fun `toDomain parses valid tags JSON`() {
        val entity = createEntity(tagsJson = """["tarih","kultur","yemek"]""")

        val city = entity.toDomain()

        assertEquals(listOf("tarih", "kultur", "yemek"), city.tags)
    }

    @Test
    fun `toDomain returns empty list for empty tags JSON array`() {
        val entity = createEntity(tagsJson = "[]")

        val city = entity.toDomain()

        assertEquals(emptyList<String>(), city.tags)
    }

    @Test
    fun `toDomain returns empty list for malformed JSON`() {
        val entity = createEntity(tagsJson = "not-a-json")

        val city = entity.toDomain()

        assertEquals(emptyList<String>(), city.tags)
    }

    @Test
    fun `toDomain returns empty list for empty string`() {
        val entity = createEntity(tagsJson = "")

        val city = entity.toDomain()

        assertEquals(emptyList<String>(), city.tags)
    }

    @Test
    fun `toDomain maps all fields correctly`() {
        val entity = createEntity()

        val city = entity.toDomain()

        assertEquals("test-id", city.id)
        assertEquals("Istanbul", city.name)
        assertEquals("Turkiye", city.country)
        assertEquals("A great city", city.description)
        assertEquals("https://example.com/img.jpg", city.imageUrl)
        assertEquals(41.0, city.latitude, 0.001)
        assertEquals(29.0, city.longitude, 0.001)
    }

    @Test
    fun `toEntity serializes tags correctly`() {
        val city = City(
            id = "test-id",
            name = "Istanbul",
            country = "Turkiye",
            description = "A great city",
            imageUrl = "https://example.com/img.jpg",
            latitude = 41.0,
            longitude = 29.0,
            tags = listOf("tarih", "kultur"),
        )

        val entity = city.toEntity(isPopular = true, searchedAt = 1000L)

        assertEquals("""["tarih","kultur"]""", entity.tagsJson)
        assertEquals(true, entity.isPopular)
        assertEquals(1000L, entity.searchedAt)
    }

    @Test
    fun `toEntity with defaults has isPopular false and searchedAt null`() {
        val city = City(
            id = "test-id",
            name = "Istanbul",
            country = "Turkiye",
            description = "A great city",
            imageUrl = "https://example.com/img.jpg",
            latitude = 41.0,
            longitude = 29.0,
        )

        val entity = city.toEntity()

        assertEquals(false, entity.isPopular)
        assertEquals(null, entity.searchedAt)
    }

    @Test
    fun `roundtrip preserves all data`() {
        val original = City(
            id = "roundtrip",
            name = "Tokyo",
            country = "Japonya",
            description = "Modern city",
            imageUrl = "https://example.com/tokyo.jpg",
            latitude = 35.6,
            longitude = 139.7,
            tags = listOf("teknoloji", "kultur"),
        )

        val result = original.toEntity(isPopular = true).toDomain()

        assertEquals(original, result)
    }

    private fun createEntity(
        tagsJson: String = """["tarih"]""",
    ) = CityEntity(
        id = "test-id",
        name = "Istanbul",
        country = "Turkiye",
        description = "A great city",
        imageUrl = "https://example.com/img.jpg",
        latitude = 41.0,
        longitude = 29.0,
        tagsJson = tagsJson,
        isPopular = false,
        searchedAt = null,
    )
}
