package com.dannark.turistando.placedetails

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.databinding.FragmentPlaceDetailsBinding

class PlaceDetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

//        val binding: FragmentPlaceDetailsBinding = DataBindingUtil.inflate(
//            inflater, R.layout.fragment_place_details, container, false
//        )
        val application = requireNotNull(activity).application
        val binding = FragmentPlaceDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this

        //get selected Place from navigation
        val placeSelected = PlaceDetailsFragmentArgs.fromBundle(requireArguments()).selectedPlace
        val viewModeFactory = PlaceDetailViewModelFactory(placeSelected, application)

        binding.viewModel = ViewModelProvider(this, viewModeFactory).get(PlaceDetailViewModel::class.java)

        binding.backButton.setOnClickListener {
            this.findNavController().navigate(
                PlaceDetailsFragmentDirections.actionPlaceDetailsFragmentToHomeFragment())
        }

        binding.share.setOnClickListener {
            sharePlace()
        }

        if (null == getShareIntent().resolveActivity(requireActivity().packageManager)) {
            binding.share.visibility = View.GONE
        }

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun getShareIntent() : Intent{
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