package com.eratoiklio.flashcards.ui.flashcard

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.eratoiklio.flashcards.data.FlashCardDao
import com.eratoiklio.flashcards.data.FlashCardDb
import com.eratoiklio.flashcards.model.FlashCardSet
import kotlinx.coroutines.launch

class NewFlashCardSetViewModel(application: Application, private val navController: NavController) : ViewModel() {

    private val dao: FlashCardDao = FlashCardDb.getDatabase(application).flashCardDao()

    val setName: MutableLiveData<String> = MutableLiveData()
    val setDesc: MutableLiveData<String> = MutableLiveData()

    fun save() {
        viewModelScope.launch {
            dao.insertSet(FlashCardSet(name = setName.value ?: "", description = setDesc.value ?: ""))
            navController.popBackStack()
        }
    }
}