package my.dotton.fuddy_app

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"
    private const val COVID_URL = "http://openapi.data.go.kr/openapi/service/rest/Covid19/"

    private fun initRetrofit(URL:String): Retrofit = Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    val weather_retrofit = initRetrofit(WEATHER_URL)
    val covid_retrofit = initRetrofit(COVID_URL)
}