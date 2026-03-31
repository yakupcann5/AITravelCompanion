package com.travel.companion.core.data.repository

/**
 * [OfflineFirstCityRepository] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import app.cash.turbine.test
import com.travel.companion.core.data.prepopulate.CityPrepopulator
import com.travel.companion.core.database.dao.CityDao
import com.travel.companion.core.database.entity.CityEntity
import com.travel.companion.core.model.City
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

class CityRepositoryTest {

    private lateinit var cityDao: CityDao
    private lateinit var cityPrepopulator: CityPrepopulator
    private lateinit var repository: OfflineFirstCityRepository

    @Before
    fun setup() {
        cityDao = mockk(relaxed = true)
        cityPrepopulator = mockk(relaxed = true)
        repository = OfflineFirstCityRepository(cityDao, cityPrepopulator)
    }

    @Test
    fun `getPopularCities emits mapped domain models`() = runTest {
        val entities = listOf(createEntity("1", "Istanbul"), createEntity("2", "Paris"))
        every { cityDao.getPopularCities() } returns flowOf(entities)

        repository.getPopularCities().test {
            val cities = awaitItem()
            assertEquals(2, cities.size)
            assertEquals("Istanbul", cities[0].name)
            assertEquals("Paris", cities[1].name)
            awaitComplete()
        }
    }

    @Test
    fun `getRecentSearches emits mapped domain models`() = runTest {
        val entities = listOf(createEntity("1", "Antalya", searchedAt = 1000L))
        every { cityDao.getRecentSearches() } returns flowOf(entities)

        repository.getRecentSearches().test {
            val cities = awaitItem()
            assertEquals(1, cities.size)
            assertEquals("Antalya", cities[0].name)
            awaitComplete()
        }
    }

    @Test
    fun `getCity emits mapped city when found`() = runTest {
        every { cityDao.getCity("1") } returns flowOf(createEntity("1", "Istanbul"))

        repository.getCity("1").test {
            val city = awaitItem()
            assertNotNull(city)
            assertEquals("Istanbul", city?.name)
            awaitComplete()
        }
    }

    @Test
    fun `getCity emits null when not found`() = runTest {
        every { cityDao.getCity("unknown") } returns flowOf(null)

        repository.getCity("unknown").test {
            assertNull(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun `searchCityByName returns mapped city when found`() = runTest {
        coEvery { cityDao.getCityByName("Istanbul") } returns createEntity("1", "Istanbul")

        val result = repository.searchCityByName("Istanbul")

        assertNotNull(result)
        assertEquals("Istanbul", result?.name)
    }

    @Test
    fun `searchCityByName returns null when not found`() = runTest {
        coEvery { cityDao.getCityByName("Unknown") } returns null

        val result = repository.searchCityByName("Unknown")

        assertNull(result)
    }

    @Test
    fun `insertCityFromAi calls upsert with searchedAt set`() = runTest {
        val entitySlot = slot<CityEntity>()
        coEvery { cityDao.upsertCity(capture(entitySlot)) } returns Unit

        val city = City(
            id = "antalya",
            name = "Antalya",
            country = "Turkiye",
            description = "Beautiful city",
            imageUrl = "",
            latitude = 36.9,
            longitude = 30.7,
            tags = listOf("deniz"),
        )

        repository.insertCityFromAi(city)

        assertNotNull(entitySlot.captured.searchedAt)
        assertEquals("antalya", entitySlot.captured.id)
    }

    @Test
    fun `prepopulate delegates to CityPrepopulator`() = runTest {
        repository.prepopulate()

        coVerify(exactly = 1) { cityPrepopulator.prepopulateIfNeeded() }
    }

    private fun createEntity(
        id: String,
        name: String,
        searchedAt: Long? = null,
    ) = CityEntity(
        id = id,
        name = name,
        country = "Turkiye",
        description = "Description",
        imageUrl = "https://example.com/img.jpg",
        latitude = 41.0,
        longitude = 29.0,
        tagsJson = "[]",
        isPopular = searchedAt == null,
        searchedAt = searchedAt,
    )
}
