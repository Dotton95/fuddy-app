package my.dotton.fuddy_app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val WEATHER_URL = "";
    private const val COVID_URL = "";

    private fun initRetrofit(URL:String): Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val weather_retrofit = initRetrofit(WEATHER_URL)
    val covid_retrofit = initRetrofit(COVID_URL)
}