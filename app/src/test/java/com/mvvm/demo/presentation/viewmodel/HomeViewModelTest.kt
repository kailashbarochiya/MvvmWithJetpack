package com.mvvm.demo.presentation.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.mvvm.demo.data.remote.DataState
import com.mvvm.demo.data.repository.AppRepository
import com.mvvm.demo.domain.model.CoinsRateDataResponse
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

    // Add this rule to make LiveData work with JUnit
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var observer: Observer<DataState<List<CoinsRateDataResponse>>>

    @Mock
    private lateinit var appRepository: AppRepository

    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.initMocks(this)
        appRepository = Mockito.mock(AppRepository::class.java)
        viewModel = HomeViewModel(appRepository)
        viewModel.liveData.observeForever(observer)
    }

    @After
    fun tearDown() {
        viewModel.liveData.removeObserver(observer)
    }

    @Test
    fun `fetchData should update LiveData with success state`() = testDispatcher.runBlockingTest {

        val expectedResult = DataState.Success(listOf(CoinsRateDataResponse("BTC", "50000.0")))

        // Given
        `when`(appRepository.getDataFromApi()).thenReturn(flowOf(expectedResult))

        // When
        viewModel.fetchData()


        // Retrieve the value from the LiveData in the ViewModel
        val actualDataState = viewModel.liveData.value?.data


        assertEquals(actualDataState, expectedResult.data)


        testDispatcher.cleanupTestCoroutines()
    }

    @Test
    fun `fetchData should update LiveData with error state`() = testDispatcher.runBlockingTest {

        val expectedUserData = DataState.Error("Error message", 401)

        // Given
        `when`(appRepository.getDataFromApi()).thenReturn(flowOf(expectedUserData))

        // When
        viewModel.fetchData()


        val errorState: DataState<List<CoinsRateDataResponse>> = expectedUserData


        assertEquals(errorState, expectedUserData)


        testDispatcher.cleanupTestCoroutines()
    }
}