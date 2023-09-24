package com.example.surfin.network

import com.example.surfin.data.CwaTempResult
import com.example.surfin.data.CwaTideResult
import com.example.surfin.data.CwaUviResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


//cwa base api
private const val CWA_HOST_NAME = "opendata.cwa.gov.tw"
private const val CWA_API_VERSION = "v1"
private const val CWA_KEY = "CWB-35714F4C-C162-403B-BF09-664E4C82C664"
private const val CWA_BASE_URL = "https://$CWA_HOST_NAME/api/$CWA_API_VERSION/"

//cwa data source
private const val CWA_TIDE_SOURCE = "O-B0075-001"
private const val CWA_TEMP_SOURCE = "O-A0003-001"
private const val CWA_UVI_SOURCE = "O-A0005-001"
private const val CWA_EARTHQUAKE_SOURCE = "E-A0015-001"


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
        @Query("WeatherElement") weatherElement: String = "TideHeight",
        @Query("sort") sort: String = "DataTime"
    ): CwaTideResult

    @GET("rest/datastore/$CWA_TEMP_SOURCE")
    suspend fun getCwaTemp(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("stationId") stationId: String ,
        @Query("elementName") elementName: String = "TEMP",
        @Query("parameterName") parameterName: String = "CITY"
    ): CwaTempResult

    @GET("rest/datastore/$CWA_TEMP_SOURCE")
    suspend fun getCwaWdsd(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("stationId") stationId: String ,
        @Query("elementName") elementName: String = "WDSD",
        @Query("parameterName") parameterName: String = "CITY"
    ): CwaTempResult

    @GET("rest/datastore/$CWA_UVI_SOURCE")
    suspend fun getCwaUvi(
        @Query("Authorization") apiKey: String = CWA_KEY,
        @Query("locationCode") locationCode: String
    ): CwaUviResult

//    @GET("rest/datastore/$CWA_EARTHQUAKE_SOURCE")
//    suspend fun getCwaEarthquake(
//        @Query("Authorization") apiKey: String = CWA_KEY,
//    ): CwaEarthquakeResult
}

// cwa tide
object SurfinApi {
    val retrofitService: SurfinApiService by lazy { cwaRetrofit.create(SurfinApiService::class.java) }
}
