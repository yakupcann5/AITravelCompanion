package com.travel.companion.feature.explore.usecase

/**
 * [SearchCityWithAiUseCase] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import com.travel.companion.core.ai.AiCityService
import com.travel.companion.core.data.repository.CityRepository
import com.travel.companion.core.model.City
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SearchCityWithAiUseCaseTest {

    private lateinit var cityRepository: CityRepository
    private lateinit var fakeAiCityService: FakeTestAiCityService
    private lateinit var useCase: SearchCityWithAiUseCase

    @Before
    fun setup() {
        cityRepository = mockk(relaxed = true)
        fakeAiCityService = FakeTestAiCityService()
        useCase = SearchCityWithAiUseCase(cityRepository, fakeAiCityService)
    }

    @Test
    fun `returns cached city when found in Room`() = runTest {
        val cachedCity = createCity("antalya", "Antalya")
        coEvery { cityRepository.searchCityByName("Antalya") } returns cachedCity

        val result = useCase("Antalya")

        assertTrue(result.isSuccess)
        assertEquals(cachedCity, result.getOrNull())
        assertEquals(0, fakeAiCityService.searchCallCount)
    }

    @Test
    fun `calls AI when city not found in Room and caches result`() = runTest {
        val aiCity = createCity("trabzon", "Trabzon")
        coEvery { cityRepository.searchCityByName("Trabzon") } returns null
        fakeAiCityService.resultToReturn = Result.success(aiCity)

        val result = useCase("Trabzon")

        assertTrue(result.isSuccess)
        assertEquals(aiCity, result.getOrNull())
        coVerify(exactly = 1) { cityRepository.insertCityFromAi(aiCity) }
    }

    @Test
    fun `returns error when AI fails`() = runTest {
        val exception = RuntimeException("Network error")
        coEvery { cityRepository.searchCityByName("Unknown") } returns null
        fakeAiCityService.resultToReturn = Result.failure(exception)

        val result = useCase("Unknown")

        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
        coVerify(exactly = 0) { cityRepository.insertCityFromAi(any()) }
    }

    private fun createCity(id: String, name: String) = City(
        id = id,
        name = name,
        country = "Turkiye",
        description = "Test city",
        imageUrl = "",
        latitude = 0.0,
        longitude = 0.0,
        tags = emptyList(),
    )
}

private class FakeTestAiCityService : AiCityService {
    var resultToReturn: Result<City> = Result.success(
        City("fake", "Fake", "", "", "", 0.0, 0.0, emptyList()),
    )
    var searchCallCount = 0

    override suspend fun searchCity(cityName: String): Result<City> {
        searchCallCount++
        return resultToReturn
    }
}
