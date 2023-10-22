package com.example.surfin

import android.os.Looper
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.TideHeightData
import com.example.surfin.data.TideTime
import com.example.surfin.weather.WeatherFragmentArgs
import com.example.surfin.weather.WeatherViewModel
import com.github.mikephil.charting.data.Entry
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {
    @Test
    fun `stringToDate with valid date returns correct Date`() {
        val input = "2022-01-22T14:30:00"
        val expectedDate =
            SimpleDateFormat(WeatherViewModel.DEFAULT_DATE_FORMAT, Locale.getDefault()).parse(input)

        val result = WeatherViewModel.stringToDate(input)

        assertEquals(expectedDate, result)
    }

    @Test(expected = Exception::class)
    fun `stringToDate with invalid date throws ParseException`() {
        val invalidInput = "2022-01-22 14:30:00"
        WeatherViewModel.stringToDate(invalidInput)
    }

    @Test
    fun `dateToMillis returns correct timestamp`() {
        val inputDate = SimpleDateFormat(
            WeatherViewModel.DEFAULT_DATE_FORMAT,
            Locale.getDefault()
        ).parse("2022-01-22T14:30:00")
        val expectedMillis = inputDate.time.toFloat()

        val result = WeatherViewModel.dateToMillis(inputDate)

        assertEquals(
            expectedMillis,
            result,
            0.0f
        )
    }

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `toEntryList maps TideTime correctly to Entry`() {
        val sampleDate = "2022-01-22T14:30:00"
        val sampleTideHeight = "3.5"
        val tideTimeList = listOf(
            TideTime(
                dateTime = sampleDate,
                tideHeights = TideHeightData(aboveTWVD = sampleTideHeight)
            )
        )

        val mockSpot = Spots(
            id = "someId",
            lat = 25.0,
            longitude = 121.0,
            tideStationId = "A01500",
            tempStationId = "",
            weatherStationId = "",
            wdsdStationId = "",
            uviStationId = "",
            waveStationId = "",
            title = "",
            content = "",
            photo = listOf()
        )

        val mockArgs = WeatherFragmentArgs.Builder(mockSpot).build()

        val repository = mock(SurfinRepository::class.java)
        val testViewModel =
            object : WeatherViewModel(args = mockArgs, repository = repository) {
                fun publicToEntryList(tideTimes: List<TideTime>): List<Entry> {
                    return tideTimes.toEntryList()
                }
            }

        val expectedEntry = Entry(
            WeatherViewModel.stringToDate(sampleDate).time.toFloat(),
            sampleTideHeight.toFloat()
        )
        val result = testViewModel.publicToEntryList(tideTimeList)

        assertEquals(listOf(expectedEntry), result)
    }
}