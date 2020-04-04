package com.eratoiklio.flashcards.ui.flashcard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.eratoiklio.flashcards.databinding.FlashCardItemBinding
import com.eratoiklio.flashcards.model.FlashCardSet

class SetListAdapter(context: Context) :
    RecyclerView.Adapter<SetListAdapter.FlashCardViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sets = emptyList<FlashCardSet>()

    inner class FlashCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: MutableLiveData<String> = MutableLiveData()
        val description: MutableLiveData<String> = MutableLiveData()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val binding = FlashCardItemBinding.inflate(inflater)
        val holder = FlashCardViewHolder(binding.root)
        binding.viewModel = holder
        return holder
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        val current = sets[position]
        holder.name.value = current.name
        holder.description.value = current.description
    }

    internal fun setData(sets: List<FlashCardSet>) {
        this.sets = sets
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = sets.size
}