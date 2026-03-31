package com.travel.companion.core.ai.ondevice

/**
 * [HybridAiTextService] birim testleri.
 *
 * @author Yakup Can
 * @date 2026-03-27
 */
import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

/**
 * Tests for [FeatureAvailabilityChecker] — the on-device availability stub used by
 * [HybridAiTextService]. Firebase internals cannot be instantiated in unit tests, so
 * [HybridAiTextService] itself is not constructed here. Instead, we verify the availability
 * contract that HybridAiTextService depends on.
 */
class HybridAiTextServiceTest {

    private lateinit var availabilityChecker: FeatureAvailabilityChecker

    @Before
    fun setup() {
        availabilityChecker = FeatureAvailabilityChecker()
    }

    @Test
    fun `isOnDeviceAvailable returns false while on-device API is not yet stable`() = runTest {
        val result = availabilityChecker.isOnDeviceAvailable()

        assertFalse(result)
    }

    @Test
    fun `observeStatus emits Unavailable as current stub behavior`() = runTest {
        availabilityChecker.observeStatus().test {
            val status = awaitItem()
            assertTrue(status is OnDeviceStatus.Unavailable)
            awaitComplete()
        }
    }

    @Test
    fun `observeStatus emits exactly one item`() = runTest {
        availabilityChecker.observeStatus().test {
            awaitItem()
            awaitComplete()
        }
    }

    @Test
    fun `observeStatus does not emit Ready status`() = runTest {
        availabilityChecker.observeStatus().test {
            val status = awaitItem()
            assertTrue(status !is OnDeviceStatus.Ready)
            awaitComplete()
        }
    }

    @Test
    fun `observeStatus does not emit Downloading status`() = runTest {
        availabilityChecker.observeStatus().test {
            val status = awaitItem()
            assertTrue(status !is OnDeviceStatus.Downloading)
            awaitComplete()
        }
    }

    @Test
    fun `isOnDeviceAvailable returns consistent result across multiple calls`() = runTest {
        val first = availabilityChecker.isOnDeviceAvailable()
        val second = availabilityChecker.isOnDeviceAvailable()

        assertFalse(first)
        assertFalse(second)
    }
}
