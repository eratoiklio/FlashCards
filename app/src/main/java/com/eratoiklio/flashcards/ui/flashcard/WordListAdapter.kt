package com.eratoiklio.flashcards.ui.flashcard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.eratoiklio.flashcards.databinding.FlashCardItemBinding
import com.eratoiklio.flashcards.model.FlashCard

class WordListAdapter(context: Context) : RecyclerView.Adapter<WordListAdapter.FlashCardViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<FlashCard>()

    inner class FlashCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mainLang: MutableLiveData<String> = MutableLiveData()
        val secondLang: MutableLiveData<String> = MutableLiveData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val binding = FlashCardItemBinding.inflate(inflater)
        val holder = FlashCardViewHolder(binding.root)
        binding.viewModel = holder
        return holder
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        val current = words[position]
        holder.mainLang.value = current.mainLanguageWord
        holder.secondLang.value = current.secondLanguageWord
    }

    internal fun setWords(words: List<FlashCard>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = words.size
}