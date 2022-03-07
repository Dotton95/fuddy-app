package my.dotton.fuddy_app

import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private const val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"
    private const val COVID_URL = "http://openapi.data.go.kr/openapi/service/rest/Covid19/"
    private const val AREA_URL  = "http://api.data.go.kr/openapi/"

    private fun initRetrofit(base_url:String, factory: Converter.Factory):Retrofit{
        //
        val interceptor = HttpLoggingInterceptor()

        if (BuildConfig.DEBUG) interceptor.level = HttpLoggingInterceptor.Level.BODY
        else interceptor.level = HttpLoggingInterceptor.Level.NONE

        var client = OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(20, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)
            .build()
        return Retrofit.Builder()
            .baseUrl(base_url)
            .client(client)
            .addConverterFactory(factory)
            .build()
    }
    val weatherRetrofit: Retrofit = initRetrofit(WEATHER_URL, GsonConverterFactory.create())
    val covidRetrofit: Retrofit = initRetrofit(COVID_URL, TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
    val areaRetrofit: Retrofit = initRetrofit(AREA_URL, TikXmlConverterFactory.create(TikXml.Builder().exceptionOnUnreadXml(false).build()))
}
