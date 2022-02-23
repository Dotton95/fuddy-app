package my.dotton.fuddy_app.Fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import my.dotton.fuddy_app.*
import my.dotton.fuddy_app.Model.CovidResponse

import my.dotton.fuddy_app.Model.Weather
import my.dotton.fuddy_app.Model.WeatherResponse
import my.dotton.fuddy_app.databinding.FragmentHomeBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import java.util.jar.Manifest


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    //현재 위치를 가져오기 위한 변수
    private var mFusedLocationProviderClient : FusedLocationProviderClient? = null

    //위치 값을 가지고 있는 객체
    lateinit var mLastLocation : Location

    //위치 정보 요청의 매개변수를 저장하는
    internal lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10


    override fun initView() {
        super.initView()
        binding.apply {
            mLocationRequest = LocationRequest.create().apply {
                priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            }

            startLocationUpdates()
            getCovidData()
        }
    }

    private  fun startLocationUpdates(){
        
        //FusedLocationProviderClient의 인스턴스 생성
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED
            &&ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED) 
                return
        //기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        //지정한 루퍼 스레드에서 콜백으로 위치 업데이트를 요청
        mFusedLocationProviderClient!!.requestLocationUpdates( mLocationRequest,mLocationCallback, Looper.myLooper())
    }
    //시스템으로 부터 위치 정보를 롤백으로 받음
    private  val mLocationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            //시스템에서 받은 location 정보를 onLocationChanged()에 전달
            p0.lastLocation
            onLocationChanged(p0.lastLocation)
        }
    }

    //시스템으로 부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location:Location){
        mLastLocation = location
        getCurrentAddress(mLastLocation.latitude,mLastLocation.longitude)
        getWeatherLatLonData(mLastLocation.latitude,mLastLocation.longitude, "${BuildConfig.WEATHER_API_KEY}")
    }

    //현재 위도 경도를 주소로 지오코딩 & 화면 설정
    private fun getCurrentAddress(lat:Double,lon:Double){
        var address = Geocoder(requireContext(), Locale.KOREAN).getFromLocation(lat,lon,1)

        var admin = if(address[0].adminArea!=null) ""+address[0].adminArea
                    else ""+address[0].subAdminArea
        var local = if(address[0].locality!=null) ""+address[0].locality
                    else ""+address[0].subLocality

        binding.homeTvAddress.text = admin+" "+local
    }
    //현재 위도 경도로 날씨 & 현재 온도 받아오기 
    private fun getWeatherLatLonData(lat:Double,lon:Double,key:String){
        val weatherInterface = RetrofitClient.weatherRetrofit.create(WeatherInterface::class.java)
        weatherInterface.getWeatherLatlon(lat.toString(),lon.toString(),key).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse( call: Call<WeatherResponse>, response: Response<WeatherResponse>) {
                if(response.isSuccessful){
                    val wResult = response.body() as WeatherResponse
                    when(wResult.weather[0].id){
                        in 200..200 -> binding.homeIvWeather.setImageResource(R.drawable.thunder)
                        in 300..599 -> binding.homeIvWeather.setImageResource(R.drawable.rain)
                        in 600..699 -> binding.homeIvWeather.setImageResource(R.drawable.snow)
                        in 700..799 -> binding.homeIvWeather.setImageResource(R.drawable.mist)
                        800 -> binding.homeIvWeather.setImageResource(R.drawable.sun_small)
                        801 -> binding.homeIvWeather.setImageResource(R.drawable.cloud_small)
                        in 802..899 -> binding.homeIvWeather.setImageResource(R.drawable.cloud_many)
                    }
                    binding.homeTvTemp.text = "현재온도 : "+(wResult.main.temp-273.15).toInt().toString()+"℃"
                }else Log.d("HomeFragment","getWeatherLatLon - onResponse : Error code ${response.code()}")
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) { Log.d("HomeFragment",t.message?:"통신오류") }
        })
    }

    private fun getCovidData(){
        val covidInterface = RetrofitClient.covidRetrofit.create(CovidInterface::class.java)
        covidInterface.getCovid(BuildConfig.COVID_API_KEY).enqueue(object :Callback<CovidResponse>{
            override fun onResponse(call: Call<CovidResponse>, response: Response<CovidResponse>) {
                if(response.isSuccessful()){
                    val test = response.body()
                    //binding.homeTvDecideCnt.text = test.body.items.item[0].

                }
            }

            override fun onFailure(call: Call<CovidResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}
