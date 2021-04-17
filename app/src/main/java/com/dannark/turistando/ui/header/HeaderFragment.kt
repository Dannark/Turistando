package com.dannark.turistando.ui.header

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentHeaderBinding

class HeaderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentHeaderBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_header, container, false)

        binding.profileLayout.setOnClickListener {
            //(activity as MainActivity).openDrawer()
        }

        return binding.root
    }

}