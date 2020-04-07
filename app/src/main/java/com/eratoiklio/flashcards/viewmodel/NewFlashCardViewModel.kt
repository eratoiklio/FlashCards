package com.eratoiklio.flashcards.viewmodel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.eratoiklio.flashcards.R
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.model.FlashCard
import com.eratoiklio.flashcards.model.FlashCardSet
import com.eratoiklio.flashcards.model.FlashCardSetJoin
import kotlinx.coroutines.launch

class NewFlashCardViewModel(private val dao: FlashCardDao) : ViewModel() {

    private val selected: MutableList<Long> = mutableListOf()
    val sets: LiveData<List<FlashCardSet>> = dao.getAllSets()
    val adapter = FlashCardSetAdapter(selected)
    val firstWord: MutableLiveData<String> = MutableLiveData()
    val secondWord: MutableLiveData<String> = MutableLiveData()
    val desc: MutableLiveData<String> = MutableLiveData()
    val usage: MutableLiveData<String> = MutableLiveData()

    fun save(view: View) {
        if (firstWord.value.isNullOrBlank() && secondWord.value.isNullOrBlank()) {
            return
        }
        viewModelScope.launch {
            val cardId = dao.insertCard(
                FlashCard(
                    0,
                    firstWord.value ?: "",
                    secondWord.value ?: "",
                    desc.value ?: "",
                    usage.value ?: ""
                )
            )
            for (id in selected) {
                dao.insertJoin(FlashCardSetJoin(cardId, id))
            }
            view.findNavController().popBackStack()
        }
    }
}

class FlashCardSetAdapter(private val selected: MutableList<Long>) :
    RecyclerView.Adapter<FlashCardSetAdapter.FlashCardSetViewHolder>() {

    private var flashCardSets: List<FlashCardSet> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardSetViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.flash_card_set_list_item, parent, false)
        return FlashCardSetViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashCardSetViewHolder, position: Int) {
        holder.bind(flashCardSets[position])
    }

    override fun getItemCount() = flashCardSets.size

    fun setData(flashCardSets: List<FlashCardSet>) {
        this.flashCardSets = flashCardSets
        notifyDataSetChanged()
    }

    inner class FlashCardSetViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val name = view.findViewById<TextView>(R.id.name)
        private val isAddedCheckBox = view.findViewById<CheckBox>(R.id.is_added_checkbox)
        private var setId: Long = -1

        init {
            itemView.setOnClickListener {
                if (!selected.remove(setId)) {
                    selected.add(setId)
                }
                notifyDataSetChanged()
            }
        }

        fun bind(flashCardSet: FlashCardSet) {
            name.text = flashCardSet.name
            isAddedCheckBox.isChecked = selected.contains(flashCardSet.setId)
        }
    }
}