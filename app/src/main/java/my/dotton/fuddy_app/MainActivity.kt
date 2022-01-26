package my.dotton.fuddy_app

import android.os.Bundle
import my.dotton.fuddy_app.Fragment.AreaFragment
import my.dotton.fuddy_app.Fragment.CovidFragment
import my.dotton.fuddy_app.Fragment.HomeFragment
import my.dotton.fuddy_app.Fragment.WeatherFragment
import my.dotton.fuddy_app.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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