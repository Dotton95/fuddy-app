package my.dotton.fuddy_app

import my.dotton.fuddy_app.Model.WeatherResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherInterface {
    @GET("weather")
    fun getWeather(
        @Query("q") q:String,
        @Query("appid") appid:String
    ) : Call<WeatherResponse>

    @GET("weather")
    fun getWeatherLation(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("appid") appid:String
    ) : Call<WeatherResponse>

    @GET("forecast")
    fun getTimeWeatherLation(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("appid") appid: String
    ) : Call<WeatherResponse>
}