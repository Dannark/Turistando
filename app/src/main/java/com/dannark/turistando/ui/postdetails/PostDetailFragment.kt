package com.dannark.turistando.ui.postdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentPostBinding

class PostDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentPostBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_post_detail, container, false)



        return binding.root
    }


}