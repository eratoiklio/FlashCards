package com.eratoiklio.flashcards.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eratoiklio.flashcards.databinding.MainFragmentBinding
import com.eratoiklio.flashcards.viewmodel.ViewModelFactory

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory(activity?.application)).get(MainViewModel::class.java)
        viewModel.allFlashCards.observe(this, Observer {
            cards -> viewModel.adapter.setWords(cards)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = MainFragmentBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        binding.flashcardSets.adapter = viewModel.adapter
        binding.flashcardSets.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}
