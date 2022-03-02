package my.dotton.fuddy_app

import my.dotton.fuddy_app.Model.CovidResponse
import my.dotton.fuddy_app.Model.CovidResponse2
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidInterface {
    @GET("getCovid19SidoInfStateJson")
    fun getSidoCovid(
        @Query("serviceKey") serviceKey:String,
    ): Call<CovidResponse2>

    @GET("getCovid19InfStateJson")
    fun getCovid(
        @Query("serviceKey") serviceKey:String
    ) :Call<CovidResponse>

}