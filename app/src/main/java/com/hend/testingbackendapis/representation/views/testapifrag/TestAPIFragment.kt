package com.hend.testingbackendapis.representation.views.testapifrag

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.hend.testingbackendapis.R
import com.hend.testingbackendapis.base.BaseFragment
import com.hend.testingbackendapis.common.NetworkUtils
import com.hend.testingbackendapis.common.Status
import com.hend.testingbackendapis.data.local.DBAdapter
import com.hend.testingbackendapis.data.local.DBHelperInstanceSingleTon
import com.hend.testingbackendapis.databinding.FragmentTestAPIBinding
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class TestAPIFragment : BaseFragment() {
    private var _binding: FragmentTestAPIBinding? = null
    private val binding get() = _binding!!
    private lateinit var methodsType: Array<String>
    private lateinit var headersMap: HashMap<String, MutableList<String>>
    private lateinit var executorService: ExecutorService
    private lateinit var handler: Handler
    private lateinit var testAPIVM: TestAPIVM
    private var methodType = "GET"
    override var bottomNavigationViewVisibility = View.VISIBLE


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTestAPIBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        init()
        val textWatcherRH = enableBtnAddRequestHeadersOrNot()
        binding.keyHeader.addTextChangedListener(textWatcherRH)
        binding.valueHeader.addTextChangedListener(textWatcherRH)
        val textWatcherCheck = enableBtnCheckOrNot()
        binding.editTextUrl.addTextChangedListener(textWatcherCheck)
        spinnerSettings()
        btnCheckSettings()
        btnAddRequestHeadersSettings()
        return binding.root
    }

    private fun init() {
        methodsType = resources.getStringArray(R.array.MethodsType)
        executorService = Executors.newSingleThreadExecutor()
        headersMap = HashMap()
        handler = Handler(Looper.getMainLooper())
        testAPIVM = ViewModelProvider(
            this, TestAPIVMF(
                DBAdapter(DBHelperInstanceSingleTon.newsInstance(requireContext())),
                executorService, handler
            )
        )[TestAPIVM::class.java]
    }

    private fun enableBtnAddRequestHeadersOrNot(): TextWatcher {
        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.btnAddRequsetHeaders.isEnabled =
                    binding.keyHeader.text.toString()
                        .isNotBlank() && binding.valueHeader.text.toString()
                        .isNotBlank()

            }
        }
        return watcher
    }

    private fun enableBtnCheckOrNot(): TextWatcher {
        val watcher: TextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.btnCheck.isEnabled = binding.editTextUrl.text.toString().isNotBlank()
            }
        }
        return watcher
    }

    private fun resetToHashmap() {
        headersMap = HashMap()
    }

    private fun resetToHeaderET(editText: EditText, editText2: EditText) {
        editText.setText("")
        editText2.setText("")
    }

    private fun spinnerSettings() {
        val adapter = activity?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_item, methodsType
            )
        }
        binding.spinnerMethodType.adapter = adapter

        binding.spinnerMethodType.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.i("test", methodsType[position])
                methodType = methodsType[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }
    }

    private fun btnCheckSettings() {
        binding.btnCheck.setOnClickListener {
            Log.i("test", "1")
            if (NetworkUtils.isNetworkAvailable(requireContext())) {
                binding.progressBar.visibility = View.VISIBLE
                if (methodType == "GET") {
                    testAPIVM.requestGET(URL(binding.editTextUrl.text.toString()), headersMap)
                        .observe(viewLifecycleOwner) {
                            when (it.status) {
                                Status.SUCCESS -> {
                                    Log.i("test", "success $it")
                                    binding.progressBar.visibility = View.GONE
                                    binding.output = it.data!!
                                    resetToHashmap()

                                }
                                Status.LOADING -> {
                                    Log.i("test", "LOADING $it")
                                    binding.progressBar.visibility = View.VISIBLE
                                }
                                Status.ERROR -> {
                                    binding.progressBar.visibility = View.GONE
                                    Log.i("test", "ERROR $it")
                                }
                            }
                        }
                } else {
                    testAPIVM.requestPOST(
                        URL(binding.editTextUrl.text.toString()),
                        binding.body.text.toString(), headersMap
                    ).observe(viewLifecycleOwner) {
                        when (it?.status) {
                            Status.SUCCESS -> {
                                Log.i("test", "success $it")
                                binding.progressBar.visibility = View.GONE
                                binding.output = it.data!!
                                resetToHashmap()
                            }
                            Status.LOADING -> {
                                Log.i("test", "LOADING $it")
                                binding.progressBar.visibility = View.VISIBLE

                            }
                            Status.ERROR -> {
                                binding.progressBar.visibility = View.GONE
                                Log.i("test", "ERROR $it")
                            }
                            else -> {
                                Log.i("test", "ERRORmm")
                            }
                        }
                    }

                }

            } else {
                showMsg("Check Your Internet")
            }
        }
    }

    private fun showMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
    }

    private fun btnAddRequestHeadersSettings() {
        binding.btnAddRequsetHeaders.setOnClickListener {
            if (headersMap.containsKey(binding.keyHeader.text.toString())) {
                headersMap.get(binding.keyHeader.text.toString())!!
                    .add(binding.keyHeader.text.toString())

                headersMap.put(
                    binding.keyHeader.text.toString(),
                    headersMap.get(binding.keyHeader.text.toString())!!
                )
            } else {
                headersMap.put(
                    binding.keyHeader.text.toString(),
                    mutableListOf(binding.valueHeader.text.toString())
                )
            }
            showMsg("Done! and  Add anothers Request Headers, if there is")
            resetToHeaderET(binding.keyHeader, binding.valueHeader)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}