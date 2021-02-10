package com.raywenderlich.android.spacingout.images

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.raywenderlich.android.spacingout.CoroutinesTestRule
import com.raywenderlich.android.spacingout.SpacingAnalytics
import io.mockk.every
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test


class ImagesViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutinesTestRule = CoroutinesTestRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    @Test
    fun `given mock objects for ViewModel when ViewModel is instantiated then verify method call from init`() {
        // given
        val imageListProvider = mockk<ImageListProvider>()
        val analytics = spyk<SpacingAnalytics>()

        every { analytics.logEvent("Fetching images") } answers {
            println("Changed the underlying print statement")
        }

        // when
        val objectUnderTest = ImagesViewModel(imageListProvider, analytics)

        // then
        verify(exactly = 1) { analytics.logEvent("Fetching images") }
    }

}