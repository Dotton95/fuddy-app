package my.dotton.fuddy_app

import my.dotton.fuddy_app.Model.CovidResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CovidInterface {
    @GET("getCovid19SidoInfStateJson")
    fun getSidoCovid(
        @Query("serviceKey") serviceKey:String,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("startCreateDt") startCreateDt:Int,
        @Query("endCreateDt") endCreateDt:Int,
        @Query("_type") _type:String
    ): Call<CovidResponse>

    @GET("getCovid19InfStateJson")
    fun getCovid(
        @Query("serviceKey") serviceKey:String,
        @Query("pageNo") pageNo:Int,
        @Query("numOfRows") numOfRows:Int,
        @Query("startCreateDt") startCreateDt:Int,
        @Query("endCreateDt") endCreateDt:Int,
        @Query("_type") _type:String
    ) :Call<CovidResponse>

}