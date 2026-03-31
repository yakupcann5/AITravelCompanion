package com.travel.companion.core.ai.firebase

/**
 * [FirebaseAiCityService] JSON deserialization ve domain mapping testleri.
 *
 * @author Yakup Can
 * @date 2026-03-29
 */
import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FirebaseAiCityMappingTest {

    private val json = Json { ignoreUnknownKeys = true }

    // region AiCityResponse.toDomain

    @Test
    fun `toDomain maps all fields correctly`() {
        val response = AiCityResponse(
            name = "Istanbul",
            country = "Turkiye",
            description = "Tarihi sehir",
            latitude = 41.0082,
            longitude = 28.9784,
            tags = listOf("tarih", "kultur", "yemek"),
        )

        val city = response.toDomain()

        assertEquals("istanbul", city.id)
        assertEquals("Istanbul", city.name)
        assertEquals("Turkiye", city.country)
        assertEquals("Tarihi sehir", city.description)
        assertEquals(41.0082, city.latitude, 0.0001)
        assertEquals(28.9784, city.longitude, 0.0001)
        assertEquals(3, city.tags.size)
        assertEquals("tarih", city.tags[0])
    }

    @Test
    fun `toDomain sets empty imageUrl`() {
        val response = AiCityResponse(
            name = "Paris",
            country = "Fransa",
            description = "Isiklar sehri",
            latitude = 48.8566,
            longitude = 2.3522,
            tags = emptyList(),
        )

        assertEquals("", response.toDomain().imageUrl)
    }

    @Test
    fun `toDomain trims name and generates correct id`() {
        val response = AiCityResponse(
            name = "  New York  ",
            country = "  ABD  ",
            description = "  Buyuk elma  ",
            latitude = 40.7128,
            longitude = -74.0060,
            tags = listOf("seyahat"),
        )

        val city = response.toDomain()

        assertEquals("new-york", city.id)
        assertEquals("New York", city.name)
        assertEquals("ABD", city.country)
        assertEquals("Buyuk elma", city.description)
    }

    @Test
    fun `toDomain handles single word city name`() {
        val response = AiCityResponse(
            name = "Tokyo",
            country = "Japonya",
            description = "Modernlik ve gelenek",
            latitude = 35.6762,
            longitude = 139.6503,
            tags = listOf("teknoloji"),
        )

        assertEquals("tokyo", response.toDomain().id)
    }

    // endregion

    // region JSON deserialization

    @Test
    fun `AiCityResponse deserializes from JSON`() {
        val jsonStr = """
        {
          "name": "Barcelona",
          "country": "Ispanya",
          "description": "Gaudi'nin sehri",
          "latitude": 41.3874,
          "longitude": 2.1686,
          "tags": ["mimari", "deniz", "sanat"]
        }
        """.trimIndent()

        val response = json.decodeFromString<AiCityResponse>(jsonStr)

        assertEquals("Barcelona", response.name)
        assertEquals("Ispanya", response.country)
        assertEquals(41.3874, response.latitude, 0.0001)
        assertEquals(3, response.tags.size)
        assertTrue(response.tags.contains("mimari"))
    }

    @Test
    fun `AiCityResponse ignores unknown JSON keys`() {
        val jsonStr = """
        {
          "name": "Roma",
          "country": "Italya",
          "description": "Antik sehir",
          "latitude": 41.9028,
          "longitude": 12.4964,
          "tags": ["tarih"],
          "population": 2873000
        }
        """.trimIndent()

        val response = json.decodeFromString<AiCityResponse>(jsonStr)
        assertEquals("Roma", response.name)
    }

    @Test
    fun `Full JSON to domain mapping end-to-end`() {
        val jsonStr = """
        {
          "name": "  Kyoto  ",
          "country": "  Japonya  ",
          "description": "  Tapinaklarin sehri  ",
          "latitude": 35.0116,
          "longitude": 135.7681,
          "tags": ["kultur", "tapinak", "gelenek"]
        }
        """.trimIndent()

        val city = json.decodeFromString<AiCityResponse>(jsonStr).toDomain()

        assertEquals("kyoto", city.id)
        assertEquals("Kyoto", city.name)
        assertEquals("Japonya", city.country)
        assertEquals("Tapinaklarin sehri", city.description)
        assertEquals("", city.imageUrl)
        assertEquals(3, city.tags.size)
    }

    // endregion
}
