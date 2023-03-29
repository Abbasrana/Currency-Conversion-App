package com.example.currencyconversiontest.domain

import com.example.currencyconversiontest.MockTestUtil
import com.example.currencyconversiontest.data.DataState
import com.example.currencyconversiontest.data.repository.CurrencyRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchCurrencyUsecaseTest {
    @MockK
    private lateinit var repository: CurrencyRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking FetchCurrencyUsecase gives list of currencies`() = runBlocking {
        // Given
        val usecase = FetchCurrencyUsecase(repository)
        val givenCurrencies = MockTestUtil.currency(3)

        // When
        coEvery { repository.getCurrencies() }
            .returns(flowOf(DataState.success(givenCurrencies)))

        // Invoke
        val currenciesListFlow = usecase()

        // Then
        MatcherAssert.assertThat(currenciesListFlow, CoreMatchers.notNullValue())

        val currenciesDataState = currenciesListFlow.first()
        MatcherAssert.assertThat(currenciesDataState, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(
            currenciesDataState,
            CoreMatchers.instanceOf(DataState.Success::class.java)
        )

        val currenciesList = (currenciesDataState as DataState.Success).data
        MatcherAssert.assertThat(currenciesList, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(currenciesList.size, CoreMatchers.`is`(givenCurrencies.size))
    }
}