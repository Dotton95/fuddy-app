package my.dotton.fuddy_app.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.databinding.FragmentHomeBinding


class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {



        return  binding.root
    }


}