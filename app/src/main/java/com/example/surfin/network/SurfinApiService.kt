package com.example.surfin.network

import com.example.surfin.BuildConfig
import com.example.surfin.data.CwaWeatherResult
import com.example.surfin.data.CwaTideResult
import com.example.surfin.data.CwaUviResult
import com.example.surfin.data.CwaWaveResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//cwa base api
private const val CWA_HOST_NAME = "opendata.cwa.gov.tw"
private const val CWA_API_VERSION = "v1"
private const val CWA_KEY = BuildConfig.API_KEY
private const val CWA_BASE_URL = "https://$CWA_HOST_NAME/api/$CWA_API_VERSION/"

//cwa data source
private const val CWA_TIDE_SOURCE = "F-A0021-001"
private const val CWA_TEMP_SOURCE = "O-A0001-001"
private const val CWA_UVI_SOURCE = "O-A0005-001"
private const val CWA_WAVE_SOURCE = "O-B0075-001"


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

//cwa tide api
private val cwaRetrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(CWA_BASE_URL).build()


interface SurfinApiService {

    //cwa tide
    @GET("rest/datastore/$CWA_TIDE_SOURCE")
    suspend fun getCwaTide(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("LocationId") stationId: String ,
        @Query("WeatherElement") weatherElement: String = "TideHeights",
        @Query("sort") sort: String = "Date"
    ): CwaTideResult

    @GET("rest/datastore/$CWA_TEMP_SOURCE")
    suspend fun getCwaTemp(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("StationId") stationId: String ,
        @Query("elementName") elementName: String = "TEMP",
        @Query("parameterName") parameterName: String = "CITY"
    ): CwaWeatherResult

    @GET("rest/datastore/$CWA_TEMP_SOURCE")
    suspend fun getCwaWdsd(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("StationId") stationId: String ,
        @Query("elementName") elementName: String = "WDSD",
        @Query("parameterName") parameterName: String = "CITY"
    ): CwaWeatherResult

    @GET("rest/datastore/$CWA_TEMP_SOURCE")
    suspend fun getCwaWeather(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("StationId") stationId: String ,
        @Query("elementName") elementName: String = "Weather",
        @Query("parameterName") parameterName: String = "CITY"
    ): CwaWeatherResult


    @GET("rest/datastore/$CWA_UVI_SOURCE")
    suspend fun getCwaUvi(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("StationID") stationID: String
    ): CwaUviResult

    @GET("rest/datastore/$CWA_WAVE_SOURCE")
    suspend fun getCwaWave(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("StationID") StationId: String,
        @Query("WeatherElement") weatherElement: String = "WaveHeight",
        @Query("sort") sort: String = "DateTime"
    ): CwaWaveResult

}

// cwa tide
object SurfinApi {
    val retrofitService: SurfinApiService by lazy { cwaRetrofit.create(SurfinApiService::class.java) }
}
