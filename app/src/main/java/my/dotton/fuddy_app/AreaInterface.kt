package my.dotton.fuddy_app

import my.dotton.fuddy_app.Model.AreaResponse
import my.dotton.fuddy_app.Model.CovidResponse
import my.dotton.fuddy_app.Model.CovidResponse2
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AreaInterface {
    @GET("tn_pubr_public_food_truck_permit_area_api")
    fun getAreaData(
        @Query("type") type:String,
        @Query("serviceKey") serviceKey:String,
    ): Call<AreaResponse>
}