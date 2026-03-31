package com.travel.companion.core.ai.firebase

/**
 * [FirebaseAiImageService] contract testleri.
 *
 * @author Yakup Can
 * @date 2026-03-29
 */
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class FirebaseAiImageServiceTest {

    @Test
    fun `isAvailable returns true`() = runTest {
        val service = FirebaseAiImageService()
        assertTrue(service.isAvailable())
    }
}
