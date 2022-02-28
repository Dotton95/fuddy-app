package my.dotton.fuddy_app

import my.dotton.fuddy_app.Model.WeatherResponse
import my.dotton.fuddy_app.Model.WeatherResponse2
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
    fun getWeatherLatlon(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("appid") appid:String
    ) : Call<WeatherResponse>

    @GET("forecast")
    fun getTimeWeatherLatlon(
        @Query("lat") lat:String,
        @Query("lon") lon:String,
        @Query("appid") appid: String
    ) : Call<WeatherResponse2>
}