package my.dotton.fuddy_app.Fragment

import android.content.Context.INPUT_METHOD_SERVICE
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import com.google.android.gms.location.LocationRequest
import my.dotton.fuddy_app.BuildConfig
import my.dotton.fuddy_app.Model.WeatherResponse
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.RetrofitClient
import my.dotton.fuddy_app.WeatherInterface
import my.dotton.fuddy_app.databinding.FragmentWeatherBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round

class WeatherFragment : BaseFragment<FragmentWeatherBinding>(R.layout.fragment_weather) {

    override fun initView() {
        super.initView()
        binding.apply {
//            startLocationUpdates()

            binding.weatherSearchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    val inputMethodManager = requireContext().getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    inputMethodManager.hideSoftInputFromWindow(binding.weatherSearchview.windowToken,0)

                    try {
                        var address = Geocoder(requireContext()).getFromLocationName(query,2)
                        var lat = round(address.get(0).latitude*100)/100
                        var lon = round(address.get(0).longitude*100)/100

                        binding.weatherTvAddress.text = address.get(0).getAddressLine(0).toString()
                        getWeatherLatLonData(lat,lon,BuildConfig.WEATHER_API_KEY)
                    }catch (e:Exception) {binding.tv.text = "잠시후 재시도 해주세요"}
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean { return true }
            })

        }

    }
    //검색한 위치 기상 정보
    private fun getWeatherLatLonData(lat:Double, lon:Double, key:String){
        val weatherInterface = RetrofitClient.weatherRetrofit.create(WeatherInterface::class.java)
        weatherInterface.getWeatherLatlon(lat.toString(),lon.toString(),key).enqueue(object :
            Callback<WeatherResponse> {
            override fun onResponse(call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if(response.isSuccessful){
                    val wResult = response.body() as WeatherResponse
                    when(wResult.weather[0].id){
                        in 200..200 -> binding.weatherIvIcon.setImageResource(R.drawable.thunder)
                        in 300..599 -> binding.weatherIvIcon.setImageResource(R.drawable.rain)
                        in 600..699 -> binding.weatherIvIcon.setImageResource(R.drawable.snow)
                        in 700..799 -> binding.weatherIvIcon.setImageResource(R.drawable.mist)
                        800 -> binding.weatherIvIcon.setImageResource(R.drawable.sun_small)
                        801 -> binding.weatherIvIcon.setImageResource(R.drawable.cloud_small)
                        in 802..899 -> binding.weatherIvIcon.setImageResource(R.drawable.cloud_many)
                    }
                    binding.weatherTvTemp.text = "현재온도 : "+(wResult.main.temp-273.15).toInt().toString()+"℃"

                    binding.weatherTvWindspeedTitle.text = "풍속"
                    binding.weatherTvWindspeed.text = wResult.wind.speed.toString()+"m/s"
                    
                    binding.weatherTvHumidityTitle.text = "습도"
                    binding.weatherTvHumidity.text = wResult.main.humidity.toString()+"%"
                    
                    binding.weatherTvBlurTitle.text = "흐림"
                    binding.weatherTvBlur.text = wResult.clouds.all.toString()+"%"

                    binding.weatherTvPrecipitationTitle.text = "강수량( 1H )"
                    if(wResult.rain!=null){
                        var str = wResult.rain.toString()
                        binding.weatherTvPrecipitation.text = str.substring(8,str.length-1)+"mm"
                    }else binding.weatherTvPrecipitation.text = "0mm"

                    binding.weatherTvSnowloadTitle.text = "적설량( 1H )"
                    if(wResult.snow!=null){
                        var str2 = wResult.snow.toString()
                        binding.weatherTvSnowload.text = str2.substring(8,str2.length-1)+"mm"
                    }else binding.weatherTvSnowload.text = "0mm"

                    val format = SimpleDateFormat("HH:mm")
                    format.timeZone = TimeZone.getTimeZone("Asia/Seoul")

                    binding.weatherTvSunriseTitle.text = "일출시간"
                    binding.weatherTvSunrise.text = format.format(Date(wResult.sys.sunrise.toLong()*1000))

                    binding.weatherTvSunsetTitle.text = "일몰시간"
                    binding.weatherTvSunset.text = format.format(Date(wResult.sys.sunset.toLong()*1000))

                }else Log.d("WeatherFragment","getWeatherLatLon - onResponse : Error code ${response.code()}")
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) { Log.d("WeatherFragment",t.message?:"통신오류") }
        })
    }

    //3시간별 기상정보
    private fun getTimeWeatherLatLonData(lat:Double, lon:Double, key:String){

    }


}