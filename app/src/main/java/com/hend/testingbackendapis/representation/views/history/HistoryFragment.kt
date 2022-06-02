package com.hend.testingbackendapis.representation.views.history

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hend.testingbackendapis.R
import com.hend.testingbackendapis.base.BaseFragment
import com.hend.testingbackendapis.common.Constants.Companion.Response_And_Request
import com.hend.testingbackendapis.common.Status
import com.hend.testingbackendapis.data.local.DBAdapter
import com.hend.testingbackendapis.data.local.DBHelperInstanceSingleTon
import com.hend.testingbackendapis.databinding.FragmentCachedBinding
import com.hend.testingbackendapis.model.ResponseAndRequest
import com.hend.testingbackendapis.representation.views.details.DetailsFragment
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


class HistoryFragment : BaseFragment(), HistoryAdapter.OnItemClickListener {
    private var _binding: FragmentCachedBinding? = null
    private val binding get() = _binding!!
    private lateinit var historyVM: HistoryVM
    private lateinit var executorService: ExecutorService
    override var bottomNavigationViewVisibility = View.VISIBLE
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var historys: MutableList<ResponseAndRequest>
    private var clickedSort = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCachedBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        init()
        if (savedInstanceState != null) {
            clickedSort = savedInstanceState.getBoolean("clickedSort")
        }
        getResponseAndRequests()
        return binding.root
    }

    private fun init() {
        executorService = Executors.newSingleThreadExecutor()
        historyVM = ViewModelProvider(
            this, HistoryVMF(
                DBAdapter(DBHelperInstanceSingleTon.newsInstance(requireContext())),
                executorService
            )
        )[HistoryVM::class.java]
        historyAdapter = HistoryAdapter()
        historyAdapter.setOnItemClickListener(this)
        (activity as AppCompatActivity?)!!.setSupportActionBar(binding.toolBar)
        setHasOptionsMenu(true)
        binding.historyRv.setHasFixedSize(true)
        binding.historyRv.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.historyRv.adapter = historyAdapter

    }

    private fun getResponseAndRequests() {
        historyVM.getResponseAndRequests().observe(viewLifecycleOwner) {
            when (it.status) {
                Status.SUCCESS -> {
                    Log.i("test", "success ${it.data}")
                    historys = it.data!!
                    if (clickedSort) {
                        historys.sortByDescending {
                            it.time
                        }
                    }

                    setDataHistoryRv(historys)
                }
                Status.LOADING -> {
                    Log.i("test", "LOADING $it")
                }
                Status.ERROR -> {
                    Log.i("test", "ERROR $it")
                }
            }
        }
    }

    private fun setDataHistoryRv(historyList: MutableList<ResponseAndRequest>) {
        historyAdapter.setDataAndContext(historyList, requireContext())
        historyAdapter.notifyDataSetChanged()
    }


    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.sortListByTime -> {
            historys.sortByDescending { it.time }
            historyAdapter.update(historys)
            clickedSort = true
            true
        }
        else -> {
            super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("clickedSort", clickedSort)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.top_bar_menu, menu)
        val search = menu.findItem(R.id.appSearchBar)
        val searchView = search.actionView as SearchView
        searchView.queryHint = "Search with method type"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                historyAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                historyAdapter.filter.filter(newText)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int) {
        val fragment = DetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable(Response_And_Request, historys.get(position))
        fragment.arguments = bundle
        loadFragment(fragment)
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.root_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}