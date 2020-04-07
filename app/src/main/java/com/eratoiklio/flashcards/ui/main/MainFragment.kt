package com.eratoiklio.flashcards.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eratoiklio.flashcards.databinding.MainFragmentBinding
import com.eratoiklio.flashcards.viewmodel.MainViewModel
import com.eratoiklio.flashcards.viewmodel.ViewModelFactory

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel =
            ViewModelProvider(this, ViewModelFactory).get(MainViewModel::class.java)
        viewModel.allSets.observe(this, Observer { cards ->
            viewModel.adapter.setData(cards)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = MainFragmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.flashcardSets.adapter = viewModel.adapter
        binding.flashcardSets.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}
