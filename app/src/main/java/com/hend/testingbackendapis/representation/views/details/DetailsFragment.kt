package com.hend.testingbackendapis.representation.views.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hend.testingbackendapis.R
import com.hend.testingbackendapis.base.BaseFragment
import com.hend.testingbackendapis.common.Constants.Companion.Response_And_Request
import com.hend.testingbackendapis.databinding.FragmentCachedBinding
import com.hend.testingbackendapis.databinding.FragmentDetailsBinding
import com.hend.testingbackendapis.model.ResponseAndRequest


class DetailsFragment : BaseFragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var responseAndRequest: ResponseAndRequest
    override var bottomNavigationViewVisibility = View.GONE


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            responseAndRequest = it.getParcelable(Response_And_Request)!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.output = responseAndRequest
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}