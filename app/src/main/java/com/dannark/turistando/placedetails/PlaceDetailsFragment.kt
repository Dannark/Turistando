package com.dannark.turistando.placedetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.R
import com.dannark.turistando.database.Place
import com.dannark.turistando.databinding.FragmentPlaceDetailsBinding

class PlaceDetailsFragment : Fragment() {

    private lateinit var viewModel: PlaceViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentPlaceDetailsBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_place_details, container, false
        )

        viewModel = ViewModelProvider(this).get(PlaceViewModel::class.java)

        binding.backButton.setOnClickListener {
            this.findNavController().navigate(
                PlaceDetailsFragmentDirections.actionPlaceDetailsFragmentToHomeFragment())
        }

        binding.share.setOnClickListener {
            sharePlace()
        }

        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            // hide the share button if it doesn't resolve
            binding.share.visibility = View.GONE
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getShareIntent() : Intent{
        val shareIntent = Intent(Intent.ACTION_SEND)
        return ShareCompat.IntentBuilder.from(requireActivity())
            .setText("My Awesome App")
            .setType("text/plain")
            .intent
    }

    private fun sharePlace(){
        Log.e("PlaceDetailFragment","Sharing text")
        startActivity(getShareIntent())
    }


}