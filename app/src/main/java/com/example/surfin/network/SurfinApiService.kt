package com.example.surfin.network

import com.example.surfin.data.CwaTideResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


//cwa tide api
private const val CWA_HOST_NAME = "opendata.cwa.gov.tw"
private const val CWA_API_VERSION = "v1"
private const val CWA_TIDE_SOURCE = "O-B0075-001"
private const val CWA_KEY = "CWB-35714F4C-C162-403B-BF09-664E4C82C664"
private const val CWA_TIDE_LIMIT = "10"
private const val CWA_TIDE_BASE_URL = "https://$CWA_HOST_NAME/api/$CWA_API_VERSION/rest/datastore/$CWA_TIDE_SOURCE?Authorization=$CWA_KEY&limit=$CWA_TIDE_LIMIT"

// google maps api
private const val GOOGLE_KEY = "AIzaSyBNSGwP0BlC7-4ab2j43mnusnowT27Ezb8"




private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

//cwa tide api
private val cwaTideRetrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(CWA_TIDE_BASE_URL)
    .build()


interface SurfinApiService {
    @GET("&WeatherElement=TideHeight&sort=DataTime")
    suspend fun getCwaTide():CwaTideResult
}

// school
object SurfinApi {
    val retrofitService: SurfinApiService by lazy { cwaTideRetrofit.create(SurfinApiService::class.java) }
}
