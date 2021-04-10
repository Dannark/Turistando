package com.dannark.turistando.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.MainActivity
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false
        )

        Log.i("HomeFragment", "Called ViewModelProvider")
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        val adapter = RecommendedPlacesAdapter(RecommendedPlaceListener { placeId ->
            viewModel.onRecommendedPlaceClicked(
                placeId
            )
        })
        binding.recommendedList.adapter = adapter
        adapter.submitList(viewModel.data)
        viewModel.navigateToPlaceDetails.observe(viewLifecycleOwner, Observer { place ->
            place?.let {
                Toast.makeText(context, "Navegando para Place Details ${place}", Toast.LENGTH_LONG)
                    .show()
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPlaceDetailsFragment()
                )
                viewModel.onPlaceDetailsNavigated()
            }
        })

//        binding.testeButton.setOnClickListener {
//            viewModel.nextValue()
//        }

        //Observable fields
        viewModel.count.observe(viewLifecycleOwner, Observer { newValue ->
            binding.searchPlaces.setText(newValue.toString())
        })

        viewModel.eventButtonPressed.observe(viewLifecycleOwner, Observer { pressed ->
            if (pressed) {
                viewModel.onButtonPressed()
            }
        })

        return binding.root
        //return inflater.inflate(R.layout.fragment_title, container, false)
    }
}