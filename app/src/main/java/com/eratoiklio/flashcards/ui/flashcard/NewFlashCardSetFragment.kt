package com.eratoiklio.flashcards.ui.flashcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.eratoiklio.flashcards.databinding.NewFlashCardSetBinding
import com.eratoiklio.flashcards.viewmodel.ViewModelFactory

class NewFlashCardSetFragment : Fragment() {

    private lateinit var viewModel: NewFlashCardSetViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory.instance).get(NewFlashCardSetViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val binding = NewFlashCardSetBinding.inflate(layoutInflater)
        binding.viewModel = viewModel
        return binding.root
    }
}