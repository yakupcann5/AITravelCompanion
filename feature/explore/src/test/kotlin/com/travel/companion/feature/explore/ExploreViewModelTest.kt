package com.travel.companion.feature.explore

/**
 * [ExploreViewModel] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import app.cash.turbine.test
import com.travel.companion.core.ai.AiCityService
import com.travel.companion.core.data.repository.CityRepository
import com.travel.companion.core.model.City
import com.travel.companion.core.testing.MainDispatcherRule
import com.travel.companion.feature.explore.usecase.GetPopularCitiesUseCase
import com.travel.companion.feature.explore.usecase.GetRecentSearchesUseCase
import com.travel.companion.feature.explore.usecase.SearchCityWithAiUseCase
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ExploreViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var getPopularCitiesUseCase: GetPopularCitiesUseCase
    private lateinit var getRecentSearchesUseCase: GetRecentSearchesUseCase
    private lateinit var fakeAiCityService: FakeViewModelAiCityService
    private lateinit var cityRepository: CityRepository

    private val popularCities = listOf(
        createCity("istanbul", "Istanbul"),
        createCity("paris", "Paris"),
    )

    private val recentSearches = listOf(
        createCity("antalya", "Antalya"),
    )

    @Before
    fun setup() {
        getPopularCitiesUseCase = mockk()
        getRecentSearchesUseCase = mockk()
        fakeAiCityService = FakeViewModelAiCityService()
        cityRepository = mockk(relaxed = true)

        every { getPopularCitiesUseCase() } returns flowOf(popularCities)
        every { getRecentSearchesUseCase() } returns flowOf(recentSearches)
    }

    private fun createSearchUseCase() = SearchCityWithAiUseCase(cityRepository, fakeAiCityService)

    private fun createViewModel() = ExploreViewModel(
        getPopularCitiesUseCase,
        getRecentSearchesUseCase,
        createSearchUseCase(),
        cityRepository,
    )

    @Test
    fun `init loads popular cities and recent searches`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.state.value
        assertEquals(popularCities, state.popularCities)
        assertEquals(recentSearches, state.recentSearches)
    }

    @Test
    fun `SearchQueryChanged updates search query and clears search result`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(ExploreIntent.SearchQueryChanged("Tokyo"))

        assertEquals("Tokyo", viewModel.state.value.searchQuery)
        assertNull(viewModel.state.value.searchResult)
    }

    @Test
    fun `SubmitSearch with successful result updates searchResult`() = runTest {
        val searchedCity = createCity("trabzon", "Trabzon")
        coEvery { cityRepository.searchCityByName("Trabzon") } returns null
        fakeAiCityService.resultToReturn = Result.success(searchedCity)

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(ExploreIntent.SearchQueryChanged("Trabzon"))
        viewModel.onIntent(ExploreIntent.SubmitSearch)
        advanceUntilIdle()

        assertEquals(searchedCity, viewModel.state.value.searchResult)
        assertEquals(false, viewModel.state.value.isSearching)
    }

    @Test
    fun `SubmitSearch with error updates error state and emits snackbar effect`() = runTest {
        coEvery { cityRepository.searchCityByName("Bad") } returns null
        fakeAiCityService.resultToReturn = Result.failure(RuntimeException("Fail"))

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.effect.test {
            viewModel.onIntent(ExploreIntent.SearchQueryChanged("Bad"))
            viewModel.onIntent(ExploreIntent.SubmitSearch)
            advanceUntilIdle()

            val effect = awaitItem()
            assertTrue(effect is ExploreEffect.ShowSnackbar)
        }

        assertNotNull(viewModel.state.value.error)
        assertEquals(false, viewModel.state.value.isSearching)
    }

    @Test
    fun `SubmitSearch with blank query does nothing`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(ExploreIntent.SearchQueryChanged("   "))
        viewModel.onIntent(ExploreIntent.SubmitSearch)
        advanceUntilIdle()

        assertNull(viewModel.state.value.searchResult)
        assertEquals(false, viewModel.state.value.isSearching)
    }

    @Test
    fun `CityClicked emits NavigateToPlanner effect`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.effect.test {
            viewModel.onIntent(ExploreIntent.CityClicked("istanbul"))

            val effect = awaitItem()
            assertTrue(effect is ExploreEffect.NavigateToPlanner)
            assertEquals("istanbul", (effect as ExploreEffect.NavigateToPlanner).cityId)
        }
    }

    @Test
    fun `filteredPopularCities filters by name`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(ExploreIntent.SearchQueryChanged("Ist"))

        val filtered = viewModel.state.value.filteredPopularCities
        assertEquals(1, filtered.size)
        assertEquals("Istanbul", filtered[0].name)
    }

    @Test
    fun `ShowCityDetail updates detailCityId`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(ExploreIntent.ShowCityDetail("istanbul"))

        assertEquals("istanbul", viewModel.state.value.detailCityId)
    }

    @Test
    fun `DismissCityDetail clears detailCityId`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(ExploreIntent.ShowCityDetail("paris"))
        viewModel.onIntent(ExploreIntent.DismissCityDetail)

        assertNull(viewModel.state.value.detailCityId)
    }

    @Test
    fun `filteredPopularCities returns empty when no query match`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.onIntent(ExploreIntent.SearchQueryChanged("Xyz123NotACity"))

        assertTrue(viewModel.state.value.filteredPopularCities.isEmpty())
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

private class FakeViewModelAiCityService : AiCityService {
    var resultToReturn: Result<City> = Result.success(
        City("fake", "Fake", "", "", "", 0.0, 0.0, emptyList()),
    )

    override suspend fun searchCity(cityName: String): Result<City> = resultToReturn
}
