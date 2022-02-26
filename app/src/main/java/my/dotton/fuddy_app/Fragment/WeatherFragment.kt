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
import my.dotton.fuddy_app.Model.WeatherResponse
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.RetrofitClient
import my.dotton.fuddy_app.WeatherInterface
import my.dotton.fuddy_app.databinding.FragmentWeatherBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception
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
                    


                }else Log.d("HomeFragment","getWeatherLatLon - onResponse : Error code ${response.code()}")
            }
            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) { Log.d("HomeFragment",t.message?:"통신오류") }
        })
    }

    //3시간별 기상정보
    private fun getTimeWeatherLatLonData(lat:Double, lon:Double, key:String){

    }


}