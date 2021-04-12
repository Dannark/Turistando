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
import com.dannark.turistando.database.TuristandoDatabase
import com.dannark.turistando.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_home, container, false)

        // Database and FactoryViewModel setup
        val application = requireNotNull(this.activity).application
        val placeDataSource = TuristandoDatabase.getInstance(application).placeDao
        val postDataSource = TuristandoDatabase.getInstance(application).postDao
        val viewModelFactory = HomeViewModelFactory(placeDataSource, postDataSource, application)

        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)

        // Set XML to access function and variables directly from the View Model
        binding.homeViewModel = viewModel
        Log.e("Home","${R.drawable.landscape1}")//2131165314
        Log.e("Home","${R.drawable.landscape2}")//2131165315
        Log.e("Home","${R.drawable.landscape3}")//2131165316
        Log.e("Home","${R.drawable.lencois_maranhences}")//2131165318
        Log.e("Home","${R.drawable.pipa_rio_grande_do_norte}")//2131165350

        // Adapters
        val placeAdapter = RecommendedPlacesAdapter(RecommendedPlaceListener { placeId ->
            viewModel.onRecommendedPlaceClicked(placeId)
        })
        binding.recommendedList.adapter = placeAdapter

        val postAdapter = PostsAdapter(PostsListener{ postId, buttonId ->
            if(buttonId == "share"){
                Toast.makeText(context, "Deletando post ${postId}", Toast.LENGTH_SHORT).show()
                viewModel.deletePost(postId)
            }
            else if(buttonId == "like"){
                viewModel.likePost(postId)
            }
        })
        binding.postsList.adapter = postAdapter

        // Listeners
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

        //Observable fields
        viewModel.places.observe(viewLifecycleOwner, Observer {
            it?.let {
                placeAdapter.submitList(it)
            }
        })

        viewModel.posts.observe(viewLifecycleOwner, {
            it?.let {
                postAdapter.submitList(it)
            }
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