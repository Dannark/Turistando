package com.dannark.turistando.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.dannark.turistando.R
import com.dannark.turistando.databinding.FragmentSearchBinding
import com.dannark.turistando.viewmodels.SearchViewModel
import com.google.android.material.transition.MaterialSharedAxis

class SearchFragment : Fragment() {

    private lateinit var viewModel: SearchViewModel
    private lateinit var binding: FragmentSearchBinding

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
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = SearchViewModelFactory(application, requireNotNull(this.activity))
        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchViewModel::class.java)
        binding.viewModel = viewModel

        binding.searchField.addTextChangedListener(
                onTextChanged = { text, start, before, count ->
                    viewModel.requestSearch(text.toString())
                }
        )

        val searchAdapter = SearchAdapter(SuggestionListener{ suggestionId ->
            Toast.makeText(context, "Clicked on suggestion ${suggestionId}", Toast.LENGTH_SHORT).show()
        })

        viewModel.suggestions.observe(viewLifecycleOwner, {
            it?.let {
                searchAdapter.addHeaderAndSubmitList(it)
            }
        })

        binding.searchList.adapter = searchAdapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchToolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

}