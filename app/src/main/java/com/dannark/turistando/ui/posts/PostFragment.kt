package com.dannark.turistando.ui.posts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentPostBinding
import com.dannark.turistando.home.PostsAdapter
import com.dannark.turistando.home.PostsListener
import com.dannark.turistando.viewmodels.PostViewModel
import com.google.android.material.transition.MaterialSharedAxis

class PostFragment : Fragment() {

    private lateinit var viewModel: PostViewModel

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

        val binding = FragmentPostBinding.inflate(inflater)
        binding.lifecycleOwner = this

        viewModel = ViewModelProvider(this).get(PostViewModel::class.java)
        // Set XML to access function and variables directly from the View Model
        binding.viewModel = viewModel

        val postAdapter = PostsAdapter(PostsListener{ postId, buttonId ->
            if(buttonId == "share"){
                Toast.makeText(context, "Deletando post ${postId}", Toast.LENGTH_SHORT).show()
            }
            else if(buttonId == "like"){
                //viewModel.likePost(postId)
            }
        })

        viewModel.posts.observe(viewLifecycleOwner, {
            it?.let {
                postAdapter.addHeaderAndSubmitList(it)
            }
        })

        binding.postsList.adapter = postAdapter

        return binding.root
    }

}