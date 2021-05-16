package com.dannark.turistando.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.R
import com.dannark.turistando.TuristandoApp
import com.dannark.turistando.databinding.FragmentPostBinding
import com.dannark.turistando.home.PostsAdapter
import com.dannark.turistando.home.PostsListener
import com.dannark.turistando.repository.userpref.DefaultUserPreferencesRepository
import com.dannark.turistando.repository.posts.DefaultPostsRepository
import com.dannark.turistando.repository.userpref.FirstTimeSelection
import com.dannark.turistando.viewmodels.PostViewModel
import com.google.android.material.transition.MaterialSharedAxis

class   PostFragment : Fragment() {

    private lateinit var binding: FragmentPostBinding
    private val viewModel by viewModels<PostViewModel> {
        PostViewModelFactory( DefaultUserPreferencesRepository.getInstance(requireContext()),
            (requireContext().applicationContext as TuristandoApp).postRepository )
    }

    private lateinit var postAdapter: PostsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false).apply {
            duration = resources.getInteger(R.integer.reply_motion_duration_large).toLong()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentPostBinding.inflate(inflater).apply {
            this.viewmodel = viewModel
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        setupAdapters()
        setupObservableFields()
    }

    private fun setupAdapters() {
        postAdapter = PostsAdapter(PostsListener{ postId, buttonId ->
            if(buttonId == "share"){
                Toast.makeText(context, "Deletando post ${postId}", Toast.LENGTH_SHORT).show()
            }
            else if(buttonId == "like"){
                //viewModel.likePost(postId)
            }
        })
        binding.postsList.adapter = postAdapter
    }

    private fun setupObservableFields(){
        viewModel.posts.observe(viewLifecycleOwner, {
            it?.let {
                postAdapter.addHeaderAndSubmitList(it)
            }
        })

        viewModel.checkFirstTimeEnabled().observe(viewLifecycleOwner) { s ->
            if (s == FirstTimeSelection.TRUE) {
                this.findNavController()
                    .navigate(PostFragmentDirections.actionPostFragmentToInitialFragment())
            }
        }
    }
}