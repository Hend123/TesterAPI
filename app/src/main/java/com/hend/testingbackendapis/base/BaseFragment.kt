package com.hend.testingbackendapis.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hend.testingbackendapis.MainActivity
import com.hend.testingbackendapis.R

abstract class BaseFragment : Fragment() {
    protected open var bottomNavigationViewVisibility = View.VISIBLE


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_base, container, false)
        if (activity is MainActivity) {
            var mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)

        }
        return view
    }

    override fun onResume() {
        super.onResume()
        if (activity is MainActivity) {
            var mainActivity = activity as MainActivity
            mainActivity.setBottomNavigationVisibility(bottomNavigationViewVisibility)
        }
    }


}