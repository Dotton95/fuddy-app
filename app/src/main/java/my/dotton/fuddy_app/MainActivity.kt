package my.dotton.fuddy_app

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import my.dotton.fuddy_app.Fragment.AreaFragment
import my.dotton.fuddy_app.Fragment.CovidFragment
import my.dotton.fuddy_app.Fragment.HomeFragment
import my.dotton.fuddy_app.Fragment.WeatherFragment
import my.dotton.fuddy_app.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    //퍼미션 응답 처리 코드
    val multiplePermissionsCode = 100

    //필요한 퍼미션 리스트
    var  REQUIRED_PERMISSIONS = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION)


    //퍼미션 체크 및 권한 요청 함수
    private fun checkPermissions(){
        //거절되었거나 아직 수락하지 않은 권한을 저장할 배열
        var rejectedPermissionList = ArrayList<String>()

        //필요한 퍼미션 꺼내서 권한 받았는지 체크
        for(permission in REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this,permission)!= PackageManager.PERMISSION_GRANTED){
                rejectedPermissionList.add(permission)
            }
        }
        //거절된 퍼미션이 있다면 권한 요청
        if(rejectedPermissionList.isNotEmpty()){
            val array = arrayOfNulls<String>(rejectedPermissionList.size)
            ActivityCompat.requestPermissions(this,rejectedPermissionList.toArray(array),multiplePermissionsCode)

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkPermissions()

        supportFragmentManager.beginTransaction().replace(R.id.main_frame, HomeFragment()).commit()
        binding.mainNavigation.setOnItemSelectedListener { item->
            when(item.itemId){
                R.id.menu_home -> supportFragmentManager.beginTransaction().replace(R.id.main_frame,HomeFragment()).commit()
                R.id.menu_weather -> supportFragmentManager.beginTransaction().replace(R.id.main_frame, WeatherFragment()).commit()
                R.id.menu_covid -> supportFragmentManager.beginTransaction().replace(R.id.main_frame, CovidFragment()).commit()
                R.id.menu_area -> supportFragmentManager.beginTransaction().replace(R.id.main_frame, AreaFragment()).commit()
            }
            true
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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



}