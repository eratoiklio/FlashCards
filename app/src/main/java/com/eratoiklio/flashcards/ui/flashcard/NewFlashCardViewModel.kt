package com.eratoiklio.flashcards.ui.flashcard

import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.data.FlashCardDb
import com.eratoiklio.flashcards.databinding.FlashCardSetListItemBinding
import com.eratoiklio.flashcards.model.FlashCard
import com.eratoiklio.flashcards.model.FlashCardSet
import com.eratoiklio.flashcards.model.FlashCardSetJoin
import kotlinx.coroutines.launch

class NewFlashCardViewModel(application: Application, private val navController: NavController) :
    ViewModel() {
    private val dao: FlashCardDao = FlashCardDb.getDatabase(application).flashCardDao()
    private val selected: MutableList<Long> = mutableListOf()
    val sets: LiveData<List<FlashCardSet>> = dao.getAllSets()
    val adapter = FlashCardSetAdapter(selected)
    val firstWord: MutableLiveData<String> = MutableLiveData()
    val secondWord: MutableLiveData<String> = MutableLiveData()
    val desc: MutableLiveData<String> = MutableLiveData()
    val usage: MutableLiveData<String> = MutableLiveData()

    fun save() {
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
            navController.popBackStack()
        }
    }

    class FlashCardSetAdapter(private val selected: MutableList<Long>) :
        RecyclerView.Adapter<FlashCardSetAdapter.FlashCardSetViewHolder>() {
        private var flashCardSets: List<FlashCardSet> = emptyList()

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): FlashCardSetViewHolder {
            val binding = FlashCardSetListItemBinding.inflate(LayoutInflater.from(parent.context))
            return FlashCardSetViewHolder(binding)
        }

        override fun onBindViewHolder(holder: FlashCardSetViewHolder, position: Int) {
            holder.bind(flashCardSets[position])
        }

        override fun getItemCount() = flashCardSets.size

        fun setData(flashCardSets: List<FlashCardSet>) {
            this.flashCardSets = flashCardSets
            notifyDataSetChanged()
        }

        inner class FlashCardSetViewHolder(private val binding: FlashCardSetListItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
            var setId: Long = -1

            init {
                binding.root.setOnClickListener {
                    if (!selected.remove(setId)) {
                        selected.add(setId)
                    }
                    notifyDataSetChanged()
                }
            }

            fun bind(flashCardSet: FlashCardSet) {
                binding.name.text = flashCardSet.name
                binding.isAddedCheckbox.isChecked = selected.contains(flashCardSet.setId)
                binding.isAddedCheckbox.isChecked=true
                binding.executePendingBindings()
            }

        }
    }
}