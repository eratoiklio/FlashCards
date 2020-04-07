package com.eratoiklio.flashcards.ui.flashcard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eratoiklio.flashcards.R
import com.eratoiklio.flashcards.model.FlashCardSet

class SetListAdapter : RecyclerView.Adapter<SetListAdapter.FlashCardViewHolder>() {

    private var sets = emptyList<FlashCardSet>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flash_card_item, parent, false)
        return FlashCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        val current = sets[position]
        holder.name.text = current.name
        holder.description.text = current.description
    }

    internal fun setData(sets: List<FlashCardSet>) {
        this.sets = sets
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = sets.size

    inner class FlashCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val name: TextView = view.findViewById(R.id.name)
        val description: TextView = view.findViewById(R.id.description)
    }
}