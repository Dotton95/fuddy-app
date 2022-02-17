package my.dotton.fuddy_app.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import my.dotton.fuddy_app.R
import my.dotton.fuddy_app.databinding.FragmentAreaBinding

class AreaFragment : BaseFragment<FragmentAreaBinding>(R.layout.fragment_area) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}