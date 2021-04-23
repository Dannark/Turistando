package com.dannark.turistando.ui.placedetails

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.MainActivity
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentPlaceDetailsBinding
import com.dannark.turistando.util.themeColor
import com.dannark.turistando.viewmodels.PlaceDetailViewModel
import com.google.android.material.transition.MaterialContainerTransform

class PlaceDetailsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            drawingViewId = R.id.myNavHostFragment
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            scrimColor = Color.TRANSPARENT
            setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        updateStatusBar()
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
            this.findNavController().popBackStack()
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

    private fun updateStatusBar(){
        (requireActivity() as MainActivity).setLightStatusBar()

    }

    override fun onDestroy() {
        super.onDestroy()
        (requireActivity() as MainActivity).setDarkStatusBar()
    }
}