package com.raywenderlich.android.spacingout.lookup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.android.spacingout.CoroutinesTestRule
import com.raywenderlich.android.spacingout.logEvent
import com.raywenderlich.android.spacingout.models.EarthImage
import com.raywenderlich.android.spacingout.network.SpacingOutApi
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class LookupViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun `an image is sent through to the view after coordinates are input`() {
        val mockApi = mockk<SpacingOutApi>()

        mockkObject(SpacingOutApi)

        every { SpacingOutApi.create() } returns mockApi

        coEvery { mockApi.getEarthImagery(any(), any()) } returns EarthImage("test url")

        val objectUnderTest = LookupViewModel()
        objectUnderTest.latLongInput(10f, 10f)

        assertEquals("test url", objectUnderTest.imageLiveData.value)
    }

    @Test
    fun `an event is logged whenever coordinates are input`() {
        val mockApi = mockk<SpacingOutApi>()

        mockkObject(SpacingOutApi)
        mockkStatic("com.raywenderlich.android.spacingout.UtilsKt")

        every { SpacingOutApi.create() } returns mockApi

        coEvery { mockApi.getEarthImagery(any(), any()) } returns EarthImage("test url")

        val objectUnderTest = LookupViewModel()
        objectUnderTest.latLongInput(10f, 10f)

        verify(exactly = 1) {
            objectUnderTest.logEvent(
                "image retrieved",
                mapOf("latitude" to "10.0", "longitude" to "10.0")
            )
        }
    }

}