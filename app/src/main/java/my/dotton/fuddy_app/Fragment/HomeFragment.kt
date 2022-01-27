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

    //퍼미션 응답 처리 코드
    val multiplePermissionsCode = 100

    //필요한 퍼미션 리스트
    var  REQUIRED_PERMISSIONS = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,
    android.Manifest.permission.ACCESS_COARSE_LOCATION)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //TODO 퍼미션 체크 부분 Activity에서 할 수 있게 바꾸기
        checkPermissions()

        return  binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            multiplePermissionsCode -> {
                if(grantResults.isNotEmpty()){
                    for((i,permission) in permissions.withIndex()){
                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            //권한 획득 실패



                        }
                    }
                }
            }
        }
    }

    //퍼미션 체크 및 권한 요청 함수
    private fun checkPermissions(){
        //거절되었거나 아직 수락하지 않은 권한을 저장할 배열
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션 꺼내서 권한 받았는지 체크
        for(permission in REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(requireContext(),permission)!= PackageManager.PERMISSION_GRANTED){
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면 권한 요청
        if(rejectedPermissionList.isNotEmpty()){
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(requireActivity(),rejectedPermissionList.toArray(array),multiplePermissionsCode)

        }
    }



    //현재 위도 경도의 주소 받아오기
    private fun getCurrentAddress(lat:Double,lon:Double):String{
        var mResult:List<Address> = Geocoder(requireContext(), Locale.KOREAN).getFromLocation(lat,lon,1)

        var admin = if(mResult.get(0).adminArea!=null) mResult.get(0).adminArea else mResult.get(0).subAdminArea
        var local = if(mResult.get(0).locality!=null) mResult.get(0).locality else mResult.get(0).subLocality

        return admin+" "+local
    }


}