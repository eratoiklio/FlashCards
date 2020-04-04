package com.eratoiklio.flashcards.ui.flashcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eratoiklio.flashcards.databinding.NewFlashCardSetBinding
import com.eratoiklio.flashcards.viewmodel.ViewModelFactory

class NewFlashCardSetFragment : Fragment() {

    private lateinit var viewModel: NewFlashCardSetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory.instance).get(NewFlashCardSetViewModel::class.java)
        viewModel.flashCards.observe(this, Observer {
                cards -> viewModel.adapter.setData(cards)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = NewFlashCardSetBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.list.adapter = viewModel.adapter
        binding.list.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}