package com.eratoiklio.flashcards

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.eratoiklio.flashcards.ui.main.MainFragment
import com.eratoiklio.flashcards.viewmodel.ViewModelFactory

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        ViewModelFactory.init(application, findNavController(R.id.nav_host_fragment))
    }
}
