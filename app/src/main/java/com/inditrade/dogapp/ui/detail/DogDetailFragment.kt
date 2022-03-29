package com.inditrade.dogapp.ui.detail

import android.view.LayoutInflater
import android.view.MenuItem
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import coil.request.CachePolicy
import coil.transform.RoundedCornersTransformation
import com.inditrade.dogapp.data.api.DOGIMAGE
import com.inditrade.dogapp.databinding.FragmentDogDetailBinding
import com.inditrade.dogapp.ui.base.BaseFragment
import com.inditrade.dogapp.ui.feed.DogFeedViewModel
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class DogDetailFragment() : BaseFragment<FragmentDogDetailBinding, DogFeedViewModel>() {
    override val mViewModel: DogFeedViewModel by viewModels()
    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDogDetailBinding {
        return FragmentDogDetailBinding.inflate(layoutInflater, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                requireActivity().onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun bindUi() {
        setHasOptionsMenu(true)
        val dogImage: String? = arguments?.getString(DOGIMAGE)
        Timber.e("Detail page Image url $dogImage")
        if (dogImage != null) {
            binding.fullImage.load(dogImage) {
                crossfade(true)
                memoryCachePolicy(CachePolicy.ENABLED)
                transformations(
                    RoundedCornersTransformation(10f)
                )
            }
        }
    }


    override fun bindObservers() {
    }
}