package my.dotton.fuddy_app.Fragment

import android.content.Context
import android.content.pm.PackageManager
import android.location.*
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.databinding.FragmentHomeBinding
import java.util.*
import java.util.jar.Manifest


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home){

    var mLocationManager : LocationManager?=null
    var mLocationListener : LocationListener?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return  binding.root
    }

    //현재 위도 경도의 주소 받아오기
    private fun getCurrentAddress(lat:Double,lon:Double):String{

        var mResult:List<Address> = Geocoder(requireContext(), Locale.KOREAN).getFromLocation(lat,lon,1)

        var admin = if(mResult.get(0).adminArea!=null) mResult.get(0).adminArea else mResult.get(0).subAdminArea
        var local = if(mResult.get(0).locality!=null) mResult.get(0).locality else mResult.get(0).subLocality

        return admin+" "+local
    }

}