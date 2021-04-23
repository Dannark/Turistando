package com.dannark.turistando.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentExploreBinding
import com.dannark.turistando.home.RecommendedPlaceListener
import com.dannark.turistando.home.RecommendedPlacesAdapter
import com.dannark.turistando.viewmodels.HomeViewModel
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis

class ExploreFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentExploreBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_explore, container, false)

        val application = requireNotNull(this.activity).application
        val userId = 1
        val viewModelFactory = HomeViewModelFactory(userId, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        // Set XML to access function and variables directly from the View Model
        binding.homeViewModel = viewModel

        // Adapters
        val placeAdapter = RecommendedPlacesAdapter(RecommendedPlaceListener { view, place ->
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            val placeCardDetailTransitionName = getString(R.string.place_card_detail_transition_name)
            val extras = FragmentNavigatorExtras(view to placeCardDetailTransitionName)
            val directions = ExploreFragmentDirections.actionExploreMenuToPlaceDetailsFragment(place)
            findNavController().navigate(directions, extras)
        })
        val friendAdapter = FriendsAdapter(FriendClickListener {
            Toast.makeText(context, "${it.lastName} não está online", Toast.LENGTH_SHORT).show()
        })
        binding.recommendedList.adapter = placeAdapter
        binding.friendList.adapter = friendAdapter

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }
}