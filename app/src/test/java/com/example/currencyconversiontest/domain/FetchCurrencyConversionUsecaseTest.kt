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
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class FetchCurrencyConversionUsecaseTest {
    @MockK
    private lateinit var repository: CurrencyRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test invoking FetchCurrencyConversionUsecase gives list of currencyCalculatedValues`() =
        runBlocking {
            // Given
            val usecase = FetchCurrencyConversionUsecase(repository)
            val givenCurrencies = MockTestUtil.ConversionModelTest(3)

            // When
            coEvery { repository.getCurrencyRate(any(), any()) }
                .returns(flowOf(DataState.success(givenCurrencies)))

            // Invoke
            val currListFlow = usecase(100.0, "AUD")

            // Then
            MatcherAssert.assertThat(currListFlow, CoreMatchers.notNullValue())

            val currListDataState = currListFlow.first()
            MatcherAssert.assertThat(currListDataState, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(
                currListDataState,
                CoreMatchers.instanceOf(DataState.Success::class.java)
            )

            val list = (currListDataState as DataState.Success).data
            MatcherAssert.assertThat(list, CoreMatchers.notNullValue())
            MatcherAssert.assertThat(list.size, `is`(givenCurrencies.size))
        }
}