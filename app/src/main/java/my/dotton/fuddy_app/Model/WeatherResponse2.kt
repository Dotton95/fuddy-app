package my.dotton.fuddy_app.Model

import android.app.ActionBar
import com.google.gson.annotations.SerializedName

data class WeatherResponse2(
    val city:City,
    val cnt:Int,
    val cod:String,
    @SerializedName("list")
    val list:List<Test>,
    val message:Int
)

data class City(
    val coord: Coord,
    val country:String,
    val id:Int,
    val name:String,
    val population:Int,
    val sunrise:Int,
    val sunset:Int,
    val timezone:Int
)

data class Test(
    val clouds: Clouds,
    val dt:Int,
    val dt_txt:String,
    val main:Main,
    val pop:Double,
    val rain:Rain,
    val sys:Sys,
    val visibility:Int,
    val weather:List<Weather>,
    val wind:Wind
)