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
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import my.dotton.fuddy_app.MainActivity.Companion.mChckPermission
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.databinding.FragmentHomeBinding
import java.util.*
import java.util.jar.Manifest


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home){

    var mLocationManager : LocationManager?=null
    var mLocationListener : LocationListener?=null

    //현재 위치를 가져오기 위한 변수
    private var mFusedLocationProviderClient : FusedLocationProviderClient? = null
    
    //위치 값을 가지고 있는 객체
    lateinit var mLastLocation : Location
    
    //위치 정보 요청의 매개변수를 저장하는
    internal lateinit var mLocationRequest: LocationRequest

    private val REQUEST_PERMISSION_LOCATION = 10



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mLocationRequest = com.google.android.gms.location.LocationRequest.create().apply {
            priority = com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        return  binding.root
    }


    //현재 위도 경도의 주소 받아오기
    private fun getCurrentAddress(lat:Double,lon:Double):String{

        var mResult:List<Address> = Geocoder(requireContext(), Locale.KOREAN).getFromLocation(lat,lon,1)

        var admin = if(mResult.get(0).adminArea!=null) mResult.get(0).adminArea else mResult.get(0).subAdminArea
        var local = if(mResult.get(0).locality!=null) mResult.get(0).locality else mResult.get(0).subLocality

        return admin+" "+local
    }
    private  fun startLocationUpdates(){
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext())

        if(ActivityCompat.checkSelfPermission(requireContext(),android.Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED) return

        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest,mLocationCallback, Looper.myLooper())
    }

    private  val mLocationCallback = object : LocationCallback(){
        override fun onLocationResult(p0: LocationResult) {
            //시스템에서 받은 location 정보를 onLocationChanged()에 전달
            onLocationChanged(p0.lastLocation)
        }
    }
    fun onLocationChanged(location:Location){
        mLastLocation = location
        //요기에 이제 변경된 위도경도 저장
    }
}