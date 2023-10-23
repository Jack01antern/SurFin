package com.example.surfin

import androidx.test.core.app.ApplicationProvider
import com.example.surfin.data.Spots
import com.example.surfin.data.SurfinRepository
import com.example.surfin.data.TideHeightData
import com.example.surfin.data.TideTime
import com.example.surfin.util.WeatherValue
import com.example.surfin.weather.WeatherFragmentArgs
import com.example.surfin.weather.WeatherViewModel
import com.github.mikephil.charting.data.Entry
import org.junit.After
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.text.SimpleDateFormat
import java.util.Locale

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(application = SurfinApplication::class, packageName = "com.example.surfin")
class ExampleUnitTest {

    @Before
    fun setUp() {
        val mockedApp = mock(SurfinApplication::class.java)
        `when`(mockedApp.getString(anyInt())).thenReturn("晴")
        SurfinApplication.instance = mockedApp
    }

    @After
    fun tearDown() {
    }

    @Test
    fun matchWeatherValue() {
        val description = "晴"
        val matchedWeatherValue = WeatherValue.values()
            .find { SurfinApplication.instance.getString(it.descriptionResId) == description }
        val expectedValue = WeatherValue.SUNNY
        assertEquals(expectedValue, matchedWeatherValue)
    }

    @Test
    fun stringToDate() {
        val input = "2022-01-22T14:30:00"
        val expectedDate =
            SimpleDateFormat(WeatherViewModel.DEFAULT_DATE_FORMAT, Locale.getDefault()).parse(input)

        val result = WeatherViewModel.stringToDate(input)

        assertEquals(expectedDate, result)
    }

    @Test(expected = Exception::class)
    fun stringToDateException() {
        val invalidInput = "2022-01-22 14:30:00"
        WeatherViewModel.stringToDate(invalidInput)
    }

    @Test
    fun dateToMillis() {
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

    @Test
    fun toEntryList() {
        val sampleDate = "2022-01-22T14:30:00"
        val sampleTideHeight = "3.5"
        val tideTimeList = listOf(
            TideTime(
                dateTime = sampleDate,
                tideHeights = TideHeightData(aboveTWVD = sampleTideHeight)
            )
        )

        val mockSpot = Spots()

        val mockArgs = WeatherFragmentArgs.Builder(mockSpot).build()

        val mockRepository = mock(SurfinRepository::class.java)

        val mockViewModel =
            object : WeatherViewModel(args = mockArgs, repository = mockRepository) {
                fun toEntryListTest(tideTimes: List<TideTime>): List<Entry> {
                    return tideTimes.toEntryList()
                }
            }

        val expectedEntry = mutableListOf(
            Entry(
                WeatherViewModel.stringToDate(sampleDate).time.toFloat(),
                sampleTideHeight.toFloat()
            )
        )

        val result = mockViewModel.toEntryListTest(tideTimeList)

        assertEquals(expectedEntry[0].y, result[0].y)
        assertEquals(expectedEntry[0].x, result[0].x)
        assertEquals(expectedEntry.toString(), result.toString())
//        assertEquals(expectedEntry, result)

        println("array list${expectedEntry[0].y}, ${result[0].y}")
        println("array list${expectedEntry[0].x}, ${result[0].x}")
        println("array list$expectedEntry, $result")
    }

}