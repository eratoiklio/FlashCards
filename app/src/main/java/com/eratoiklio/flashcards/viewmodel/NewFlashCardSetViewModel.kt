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

class NewFlashCardSetViewModel(private val dao: FlashCardDao) : ViewModel() {

    private val selected: MutableList<Long> = mutableListOf()
    val adapter = FlashCardAdapter(selected)
    val flashCards: LiveData<List<FlashCard>> = dao.getAllFlashCards()
    val setName: MutableLiveData<String> = MutableLiveData()
    val setDesc: MutableLiveData<String> = MutableLiveData()

    fun setData(flashCards: List<FlashCard>) {
        adapter.setData(flashCards)
    }

    fun save(view: View) {
        if (setName.value.isNullOrBlank()) {
            return
        }
        viewModelScope.launch {
            val setId = dao.insertSet(
                FlashCardSet(
                    name = setName.value ?: "",
                    description = setDesc.value ?: ""
                )
            )
            for (id in selected) {
                dao.insertJoin(FlashCardSetJoin(id, setId))
            }
            view.findNavController().popBackStack()
        }
    }
}

class FlashCardAdapter(private val selected: MutableList<Long>) :
    RecyclerView.Adapter<FlashCardAdapter.FlashCardViewHolder>() {

    private var flashCards: List<FlashCard> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FlashCardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.flash_card_list_item, parent, false)
        return FlashCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
        holder.bind(flashCards[position])
    }

    override fun getItemCount() = flashCards.size

    fun setData(flashCards: List<FlashCard>) {
        this.flashCards = flashCards
        notifyDataSetChanged()
    }

    inner class FlashCardViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val firstLanguage = view.findViewById<TextView>(R.id.first_language)
        private val secondLanguage = view.findViewById<TextView>(R.id.second_language)
        private val isAddedCheckBox = view.findViewById<CheckBox>(R.id.is_added_checkbox)
        private var cardId: Long = -1

        init {
            itemView.setOnClickListener {
                if (!selected.remove(cardId)) {
                    selected.add(cardId)
                }
                notifyItemChanged(adapterPosition)
            }
        }

        fun bind(flashCard: FlashCard) {
            firstLanguage.text = flashCard.mainLanguageWord
            secondLanguage.text = flashCard.secondLanguageWord
            isAddedCheckBox.isChecked = selected.contains(flashCard.cardId)
            cardId = flashCard.cardId
        }
    }
}