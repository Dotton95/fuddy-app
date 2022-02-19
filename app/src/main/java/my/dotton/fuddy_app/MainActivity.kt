package my.dotton.fuddy_app


import my.dotton.fuddy_app.Fragment.AreaFragment
import my.dotton.fuddy_app.Fragment.CovidFragment
import my.dotton.fuddy_app.Fragment.HomeFragment
import my.dotton.fuddy_app.Fragment.WeatherFragment
import my.dotton.fuddy_app.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

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