package com.dannark.turistando.ui.initial

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.databinding.FragmentInitialBinding
import com.dannark.turistando.repository.userpref.DefaultUserPreferencesRepository
import com.dannark.turistando.repository.userpref.FirstTimeSelection
import com.dannark.turistando.viewmodels.InitialViewModel


class InitialFragment : Fragment() {

    private lateinit var viewModel: InitialViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentInitialBinding.inflate(inflater)
        binding.lifecycleOwner = this

        val viewModeFactory = InitialViewModelFactory(requireActivity())
        viewModel = ViewModelProvider(this, viewModeFactory).get(InitialViewModel::class.java)
        binding.viewModel =viewModel

        viewModel.checkFirstTimeEnabled().observe(viewLifecycleOwner){ selection ->
            if(selection == FirstTimeSelection.FALSE){
                this.findNavController().navigate(InitialFragmentDirections.actionGlobalPostsFragment())
            }
        }

        binding.goButton.setOnClickListener {
            viewModel.saveFirstTime(false)
        }


        return binding.root
    }
}