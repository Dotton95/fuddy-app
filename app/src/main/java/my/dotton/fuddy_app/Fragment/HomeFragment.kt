package my.dotton.fuddy_app.Fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import android.os.Looper
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
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.databinding.FragmentHomeBinding
import java.util.*
import java.util.jar.Manifest


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    var mLocationManager: LocationManager? = null
    var mLocationListener: LocationListener? = null

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
        binding.lat.text = "위도 - "+mLastLocation.latitude
        binding.lon.text = "경도 - "+mLastLocation.longitude

        getCurrentAddress(mLastLocation.latitude,mLastLocation.longitude)
    }

    //현재 위도 경도를 받아와 주소확인
    private fun getCurrentAddress(lat:Double,lon:Double):String{
        var address = Geocoder(requireContext(), Locale.KOREAN).getFromLocation(lat,lon,1)

        var admin = if(address[0].adminArea!=null) ""+address[0].adminArea
                    else ""+address[0].subAdminArea
        var local = if(address[0].locality!=null) ""+address[0].locality
                    else ""+address[0].subLocality

        return admin+" "+local
    }
}
