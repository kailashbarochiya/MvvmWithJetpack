package com.mvvm.demo.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import com.mvvm.demo.R
import com.mvvm.demo.data.remote.DataState
import com.mvvm.demo.databinding.FragmentHomeBinding
import com.mvvm.demo.domain.model.CoinsRateDataResponse
import com.mvvm.demo.presentation.adapter.QuoteListAdapter
import com.mvvm.demo.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private lateinit var homeBinding: FragmentHomeBinding
    private var quoteList: MutableList<CoinsRateDataResponse> = mutableListOf()
    private var quoteListAdapter: QuoteListAdapter? = null
    private val viewModel: HomeViewModel by activityViewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    /**
     * Init Recycleview and setup Observers
     */

    private fun initView() {


        quoteListAdapter = QuoteListAdapter(quoteList)
        homeBinding.rvQuotes.adapter = quoteListAdapter


        viewModel.liveData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is DataState.Error -> {
                    Log.d("Error", it.exception)
                }

                is DataState.Loading -> {
                    Log.d("Loading", "Loading")
                }

                is DataState.Success -> {

                    if (it.data != null) {
                        quoteList = it.data as MutableList<CoinsRateDataResponse>
                        quoteListAdapter?.addData(quoteList)
                    }

                }

            }
        })


        getDataFromServer()


    }

    private fun getDataFromServer() {

        lifecycleScope.launch {
            viewModel.fetchData()
        }
    }

}
