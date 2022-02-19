package my.dotton.fuddy_app


import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import my.dotton.fuddy_app.Fragment.AreaFragment
import my.dotton.fuddy_app.Fragment.CovidFragment
import my.dotton.fuddy_app.Fragment.HomeFragment
import my.dotton.fuddy_app.Fragment.WeatherFragment
import my.dotton.fuddy_app.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    val MY_PERMISSION_ACCESS_ALL = 100

    override fun initView() {
        super.initView()
        //mChckPermission = checkPermissions()
        binding.apply {
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
        if(ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                var permissions = arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION )
                ActivityCompat.requestPermissions(this, permissions, MY_PERMISSION_ACCESS_ALL)
        }

    }
    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<out String>, grantResults: IntArray ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode === MY_PERMISSION_ACCESS_ALL) {
            if (grantResults.size > 0) {
                for (grant in grantResults) {
                    if (grant != PackageManager.PERMISSION_GRANTED) System.exit(0)
                }
            }
        }
    }
}


//    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when(requestCode){
//            multiplePermissionsCode -> {
//                if(grantResults.isNotEmpty()){
//                    for((i,permission) in permissions.withIndex()){
//                        if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
//                            //권한 획득 실패
//
//
//
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//}