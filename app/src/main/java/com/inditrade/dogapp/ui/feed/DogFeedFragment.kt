package com.inditrade.dogapp.ui.feed

import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.inditrade.dogapp.R
import com.inditrade.dogapp.data.api.SUCCESS
import com.inditrade.dogapp.data.api.State
import com.inditrade.dogapp.data.models.DogFeedModel
import com.inditrade.dogapp.databinding.FragmentDogFeedBinding
import com.inditrade.dogapp.ui.adapter.DogFeedListAdapter
import com.inditrade.dogapp.ui.base.BaseFragment
import com.inditrade.dogapp.ui.detail.DogDetailFragmentArgs
import com.inditrade.dogapp.utils.hide
import com.inditrade.dogapp.utils.show
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DogFeedFragment() : BaseFragment<FragmentDogFeedBinding, DogFeedViewModel>(),
    DogItemClickListener {
    override val mViewModel: DogFeedViewModel by viewModels()
    private lateinit var searchView: SearchView
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDogFeedBinding {
        return FragmentDogFeedBinding.inflate(inflater, container, false)
    }

    override fun bindUi() {
        setHasOptionsMenu(true)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        binding.apply {
            recyclerViewFeed.setHasFixedSize(true)
            recyclerViewFeed.layoutManager =
                GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_action, menu)
        val searchItem = menu.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        val closeButton: View? = searchView.findViewById(androidx.appcompat.R.id.search_close_btn)
        closeButton?.setOnClickListener {
            searchView.setQuery("", true)
            searchView.clearFocus();
            mViewModel.fetchDogFeed()
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mViewModel.fetchDogFeedByBreed(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    override fun bindObservers() {
        mViewModel.dogFeedLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    binding.progressBar.show()
                }
                is State.Error -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    binding.progressBar.hide()
                    val apiData: DogFeedModel = state.data
                    if (apiData.status == SUCCESS) {
                        binding.recyclerViewFeed.adapter =
                            DogFeedListAdapter(apiData.imagesList, this@DogFeedFragment)
                    }
                }
            }
        })
        mViewModel.dogFeedBreedLiveData.observe(this, Observer { state ->
            when (state) {
                is State.Loading -> {
                    binding.progressBar.show()
                }
                is State.Error -> {
                    binding.progressBar.hide()
                    Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                }
                is State.Success -> {
                    binding.progressBar.hide()
                    val apiData: DogFeedModel = state.data
                    if (apiData.status == SUCCESS) {
                        binding.recyclerViewFeed.adapter =
                            DogFeedListAdapter(apiData.imagesList, this@DogFeedFragment)
                    }
                }
            }
        })
    }

    override fun onClick(dogImage: String) {
        val action = DogFeedFragmentDirections.actionFeedsToDetail(dogImage)
        findNavController().navigate(action)
        return
    }
}