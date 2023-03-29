package com.example.currencyconversiontest.ui.conversion

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.currencyconversiontest.MainCoroutinesRule
import com.example.currencyconversiontest.MockTestUtil
import com.example.currencyconversiontest.data.DataState
import com.example.currencyconversiontest.domain.FetchCurrencyConversionUsecase
import com.example.currencyconversiontest.domain.FetchCurrencyUsecase
import com.example.currencyconversiontest.model.CurrencyModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ConversionViewModelTest {
    // Subject under test
    private lateinit var viewModel: ConversionViewModel

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @MockK
    lateinit var fetchCurrencyConversionUsecase: FetchCurrencyConversionUsecase

    @MockK
    lateinit var fetchCurrencyUsecase: FetchCurrencyUsecase

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `test when CurrencyViewModel is initialized, currencies are fetched`() = runBlocking {
        // Given
        val givenPhotos = MockTestUtil.currency(3)
        val uiObserver = mockk<Observer<UiState>>(relaxed = true)
        val photosListObserver = mockk<Observer<List<CurrencyModel>>>(relaxed = true)

        // When
        coEvery { fetchCurrencyUsecase.invoke() }
            .returns(flowOf(DataState.success(givenPhotos)))

        // Invoke
        viewModel = ConversionViewModel(fetchCurrencyUsecase, fetchCurrencyConversionUsecase)
        viewModel.uiStateLiveData.observeForever(uiObserver)
        viewModel.currencyListLiveData.observeForever(photosListObserver)

        // Then
        coVerify(exactly = 1) { fetchCurrencyUsecase.invoke() }
        verify { uiObserver.onChanged(match { it == ContentState }) }
        verify { photosListObserver.onChanged(match { it.size == givenPhotos.size }) }
    }
}