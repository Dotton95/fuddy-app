package my.dotton.fuddy_app.Model


data class WeatherResponse(
    val base:String,
    val clouds:Clouds,
    val cod:Int,
    val coord:Coord,
    val dt:Int,
    val id:Int,
    val main:Main,
    val name:String,
    val rain:Rain,
    val snow:Snow,
    val sys:Sys,
    val timezone:Int,
    val visibility:Int,
    val weather:List<Weather>,
    val wind:Wind
)

data class Clouds(
    val all:Int
)

data class Coord(
    val lat:Double,
    val lon:Double
)

data class Main(
    val feels_like:Double,
    val humidity:Int,
    val pressur:Int,
    val temp:Double,
    val temp_max:Double,
    val temp_min:Double
)

data class Rain(
    val `1h`:Double
)

data class Snow(
    val `1h`:Double
)
data class Sys(
    val country:String,
    val id:Int,
    val sunrise:Int,
    val sunset:Int,
    val type:Int
)

data class Weather(
    val description:String,
    val icon:String,
    val id:Int,
    val main:String
)

data class Wind(
    val deg:Int,
    val speed:Double
)