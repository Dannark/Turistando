package com.dannark.turistando.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentHomeBinding
import com.dannark.turistando.home.RecommendedPlaceListener
import com.dannark.turistando.home.RecommendedPlacesAdapter
import com.dannark.turistando.viewmodels.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        val application = requireNotNull(this.activity).application
        val userId = 1
        val viewModelFactory = HomeViewModelFactory(userId, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        // Set XML to access function and variables directly from the View Model
        binding.homeViewModel = viewModel

        // Adapters
        val placeAdapter = RecommendedPlacesAdapter(RecommendedPlaceListener {
            viewModel.displayPlaceDetails(it)
        })
        val friendAdapter = FriendsAdapter(FriendClickListener {
            Toast.makeText(context, "${it.lastName} não está online", Toast.LENGTH_SHORT).show()
        })
        binding.recommendedList.adapter = placeAdapter
        binding.friendList.adapter = friendAdapter


        // Listeners
        viewModel.navigateToPlaceDetails.observe(viewLifecycleOwner, Observer { place ->
            place?.let {
                this.findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToPlaceDetailsFragment(place)
                )
                viewModel.onPlaceDetailsCompleted()
            }
        })

        //Observable fields
        viewModel.places.observe(viewLifecycleOwner, Observer {
            it?.let {
                placeAdapter.submitList(it)
            }
        })
        viewModel.friends.observe(viewLifecycleOwner, Observer {
            it?.let {
                friendAdapter.submitList(it)
            }
        })

        return binding.root
    }


}