package com.eratoiklio.flashcards.ui.flashcard

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.data.FlashCardDb
import com.eratoiklio.flashcards.databinding.FlashCardListItemBinding
import com.eratoiklio.flashcards.model.FlashCard
import com.eratoiklio.flashcards.model.FlashCardSet
import com.eratoiklio.flashcards.model.FlashCardSetJoin
import kotlinx.coroutines.launch

class NewFlashCardSetViewModel(application: Application, private val navController: NavController) : ViewModel() {

    private val dao: FlashCardDao = FlashCardDb.getDatabase(application).flashCardDao()
    private val flashCards: MutableList<FlashCard> = mutableListOf()
    private val selected: MutableList<Long> = mutableListOf()
    val setName: MutableLiveData<String> = MutableLiveData()
    val setDesc: MutableLiveData<String> = MutableLiveData()
    val adapter = FlashCardAdapter(selected)

    init {
        viewModelScope.launch {
            flashCards.addAll(dao.getAllFlashCards().value ?: emptyList())
            adapter.setData(flashCards)
        }
    }

    fun save() {
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
            navController.popBackStack()
        }
    }

    class FlashCardAdapter(private val selected: MutableList<Long>) :
        RecyclerView.Adapter<FlashCardAdapter.FlashCardViewHolder>() {
        private var flashCards: List<FlashCard> = emptyList()
        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FlashCardViewHolder {
            val binding = FlashCardListItemBinding.inflate(LayoutInflater.from(parent.context))
            return FlashCardViewHolder(binding.root)
        }

        override fun onBindViewHolder(holder: FlashCardViewHolder, position: Int) {
            holder.bind(flashCards[position])
        }

        override fun getItemCount() = flashCards.size

        fun setData(flashCards: List<FlashCard>) {
            this.flashCards = flashCards
            notifyDataSetChanged()
        }

        inner class FlashCardViewHolder(root: View) : RecyclerView.ViewHolder(root) {
            val firstLanguageWord = MutableLiveData<String>()
            val secondLanguageWord = MutableLiveData<String>()
            val isSelected = MutableLiveData<Boolean>()
            var cardId: Long = -1

            init {
                root.setOnClickListener {
                    if (!selected.remove(cardId)) {
                        selected.add(cardId)
                    }
                    notifyDataSetChanged()
                }
            }

            fun bind(flashCard: FlashCard) {
                firstLanguageWord.value = flashCard.mainLanguageWord
                secondLanguageWord.value = flashCard.secondLanguageWord
                isSelected.value = selected.contains(flashCard.cardId)
                cardId = flashCard.cardId
            }

        }
    }
}