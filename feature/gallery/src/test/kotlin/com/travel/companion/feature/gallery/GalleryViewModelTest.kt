package com.travel.companion.feature.gallery

/**
 * [GalleryViewModel] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-31
 */
import app.cash.turbine.test
import com.travel.companion.core.ai.AiImageService
import com.travel.companion.core.data.repository.GeneratedImageRepository
import com.travel.companion.core.model.GalleryImage
import com.travel.companion.core.testing.MainDispatcherRule
import com.travel.companion.feature.gallery.usecase.GenerateImageUseCase
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GalleryViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private var fakeImageResult: Result<ByteArray> = Result.success(ByteArray(0))
    private val fakeAiImageService = object : AiImageService {
        override suspend fun generateImage(prompt: String) = fakeImageResult
        override suspend fun isAvailable() = true
    }

    private val imagesFlow = MutableStateFlow(sampleImages())
    private lateinit var generatedImageRepository: GeneratedImageRepository

    @Before
    fun setup() {
        generatedImageRepository = mockk(relaxed = true)
        every { generatedImageRepository.getAllImages() } returns imagesFlow
    }

    private fun createViewModel() = GalleryViewModel(
        generateImageUseCase = GenerateImageUseCase(fakeAiImageService),
        generatedImageRepository = generatedImageRepository,
    )

    @Test fun `LoadImages loads images from repository`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        assertFalse(vm.state.value.isLoading)
        assertTrue(vm.state.value.images.isNotEmpty())
    }

    @Test fun `PromptChanged updates prompt`() = runTest {
        val vm = createViewModel()
        vm.onIntent(GalleryIntent.PromptChanged("sunset"))
        assertEquals("sunset", vm.state.value.prompt)
    }

    @Test fun `GenerateImage with blank prompt does nothing`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        val count = vm.state.value.images.size
        vm.onIntent(GalleryIntent.GenerateImage)
        advanceUntilIdle()
        assertEquals(count, vm.state.value.images.size)
    }

    @Test fun `GenerateImage with success emits ImageSaved effect`() = runTest {
        fakeImageResult = Result.success(ByteArray(0))
        val vm = createViewModel()
        advanceUntilIdle()
        vm.onIntent(GalleryIntent.PromptChanged("Istanbul night"))

        vm.effect.test {
            vm.onIntent(GalleryIntent.GenerateImage)
            advanceUntilIdle()
            assertTrue(awaitItem() is GalleryEffect.ImageSaved)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test fun `GenerateImage with success calls repository saveGeneratedImage`() = runTest {
        fakeImageResult = Result.success(ByteArray(0))
        val vm = createViewModel()
        advanceUntilIdle()
        vm.onIntent(GalleryIntent.PromptChanged("Paris night"))
        vm.onIntent(GalleryIntent.GenerateImage)
        advanceUntilIdle()

        coVerify(exactly = 1) { generatedImageRepository.saveGeneratedImage(eq("Paris night"), any()) }
        assertFalse(vm.state.value.isGenerating)
        assertEquals("", vm.state.value.prompt)
    }

    @Test fun `GenerateImage with failure emits ShowSnackbar effect`() = runTest {
        fakeImageResult = Result.failure(RuntimeException("Service unavailable"))
        val vm = createViewModel()
        advanceUntilIdle()
        vm.onIntent(GalleryIntent.PromptChanged("Some prompt"))

        vm.effect.test {
            vm.onIntent(GalleryIntent.GenerateImage)
            advanceUntilIdle()
            assertTrue(awaitItem() is GalleryEffect.ShowSnackbar)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test fun `GenerateImage with failure sets isGenerating to false`() = runTest {
        fakeImageResult = Result.failure(RuntimeException("err"))
        val vm = createViewModel()
        advanceUntilIdle()
        vm.onIntent(GalleryIntent.PromptChanged("Tokyo"))
        vm.onIntent(GalleryIntent.GenerateImage)
        advanceUntilIdle()

        assertFalse(vm.state.value.isGenerating)
    }

    @Test fun `SelectImage and ClearSelection`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        val id = vm.state.value.images.first().id

        vm.onIntent(GalleryIntent.SelectImage(id))
        assertEquals(id, vm.state.value.selectedImageId)

        vm.onIntent(GalleryIntent.ClearSelection)
        assertNull(vm.state.value.selectedImageId)
    }

    @Test fun `ShareImage emits ShareImage effect`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        val target = vm.state.value.images.first()

        vm.effect.test {
            vm.onIntent(GalleryIntent.ShareImage(target.id))
            assertTrue(awaitItem() is GalleryEffect.ShareImage)
        }
    }

    @Test fun `ToggleFavorite calls repository updateFavorite`() = runTest {
        val vm = createViewModel()
        advanceUntilIdle()
        val target = vm.state.value.images.first()

        vm.onIntent(GalleryIntent.ToggleFavorite(target.id))
        advanceUntilIdle()

        coVerify(exactly = 1) {
            generatedImageRepository.updateFavorite(target.id, !target.isFavorite)
        }
    }

    @Test fun `LoadImages when repository is empty calls upsertImage for samples`() = runTest {
        imagesFlow.value = emptyList()
        coEvery { generatedImageRepository.upsertImage(any()) } coAnswers {
            imagesFlow.value = sampleImages()
        }

        val vm = createViewModel()
        advanceUntilIdle()

        coVerify(atLeast = 1) { generatedImageRepository.upsertImage(any()) }
        assertFalse(vm.state.value.isLoading)
    }

    private fun sampleImages(): List<GalleryImage> = sampleGalleryImages
}
