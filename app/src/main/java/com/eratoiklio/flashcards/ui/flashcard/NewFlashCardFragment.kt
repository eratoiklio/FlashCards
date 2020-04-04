package com.eratoiklio.flashcards.ui.flashcard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.eratoiklio.flashcards.databinding.NewFlashCardBinding
import com.eratoiklio.flashcards.databinding.NewFlashCardSetBinding
import com.eratoiklio.flashcards.viewmodel.ViewModelFactory

class NewFlashCardFragment : Fragment() {
    private lateinit var viewModel: NewFlashCardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory.instance
        ).get(NewFlashCardViewModel::class.java)
        viewModel.sets.observe(this, Observer { sets ->
            viewModel.adapter.setData(sets)
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val binding = NewFlashCardBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.list.adapter = viewModel.adapter
        binding.list.layoutManager = LinearLayoutManager(context)
        return binding.root
    }
}