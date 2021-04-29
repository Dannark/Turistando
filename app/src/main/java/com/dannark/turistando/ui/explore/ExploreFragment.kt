package com.dannark.turistando.ui.explore

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.doOnPreDraw
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentExploreBinding
import com.dannark.turistando.home.PlaceListener
import com.dannark.turistando.home.RecommendedPlacesAdapter
import com.dannark.turistando.util.getTimeArrayDiff
import com.dannark.turistando.util.toLongSum
import com.dannark.turistando.viewmodels.ExploreViewModel
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialSharedAxis

class ExploreFragment : Fragment() {

    private lateinit var viewModel: ExploreViewModel

    private lateinit var placeAdapter: RecommendedPlacesAdapter
    private lateinit var placesNearByAdapter: RecommendedPlacesAdapter
    private lateinit var friendAdapter: FriendsAdapter

    private lateinit var binding: FragmentExploreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_explore, container, false)

        val userId = 1
        val viewModelFactory = ExploreViewModelFactory(userId, requireNotNull(this.activity))

        viewModel = ViewModelProvider(this, viewModelFactory).get(ExploreViewModel::class.java)
        binding.homeViewModel = viewModel

        setupAdapters()
        setupObservableFields()
        askPermissions()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Render Back transition animation for Places Details Frag to -> this fragment
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Log.e("Test","requestCode=$requestCode")
        when (requestCode) {
            ACCESS_FINE_LOCATION.toLongSum().toInt() -> {
                if ((grantResults.isNotEmpty() &&
                                grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    viewModel.refreshPlacesNearBy(requireActivity())
                } else {
                    Toast.makeText(context, getString(R.string.warning_location_permission),
                            Toast.LENGTH_LONG).show()

                }
                return
            }
        }
    }

    private fun askPermissions(){
        if (ContextCompat.checkSelfPermission(requireContext(), ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {

            requestPermissions(arrayOf(ACCESS_FINE_LOCATION),
                ACCESS_FINE_LOCATION.toLongSum().toInt())
        }
    }

    private fun setupAdapters(){
        placeAdapter = RecommendedPlacesAdapter(PlaceListener { view, place ->
            exitTransition = MaterialElevationScale(false).apply {
                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
            }
            val placeCardDetailTransitionName = getString(R.string.place_card_detail_transition_name)
            val extras = FragmentNavigatorExtras(view to placeCardDetailTransitionName)
            val directions = ExploreFragmentDirections.actionExploreMenuToPlaceDetailsFragment(place)
            findNavController().navigate(directions, extras)
        })

        placesNearByAdapter = RecommendedPlacesAdapter(PlaceListener { view, place ->
//            exitTransition = MaterialElevationScale(false).apply {
//                duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
//            }
//            val placeCardDetailTransitionName = getString(R.string.place_card_detail_transition_name)
//            val extras = FragmentNavigatorExtras(view to placeCardDetailTransitionName)
//            val directions = ExploreFragmentDirections.actionExploreMenuToPlaceDetailsFragment(place)
//            findNavController().navigate(directions, extras)
        })

        FriendsAdapter(FriendClickListener {
            Toast.makeText(context, "${it.lastName} não está online", Toast.LENGTH_SHORT).show()
        }).also { friendAdapter = it }

        binding.recommendedList.adapter = placeAdapter
        binding.placesNearbyList.adapter = placesNearByAdapter
        binding.friendList.adapter = friendAdapter
    }

    private fun setupObservableFields(){
        viewModel.places.observe(viewLifecycleOwner, {
            it?.let {
                placeAdapter.submitList(it)
            }
        })
        viewModel.friends.observe(viewLifecycleOwner, {
            it?.let {
                friendAdapter.submitList(it)
            }
        })
        viewModel.placesNearBy.observe(viewLifecycleOwner, {
            it?.let {
                placesNearByAdapter.submitList(it)
            }
        })
        viewModel.getExploreLastUpdate().observe(viewLifecycleOwner){ timeStamp ->
            Log.e("ExploreFragment","Last exploration time was = $timeStamp")

            val diff = getTimeArrayDiff(timeStamp)
            Log.e("ExploreFragment", "Last update time ${diff.days}d ${diff.min}m")
            viewModel.refreshNewDataOnceADay(diff.min, requireNotNull(this.activity))
        }
    }
}