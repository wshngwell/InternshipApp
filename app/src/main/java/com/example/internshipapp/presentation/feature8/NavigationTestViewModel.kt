package com.example.internshipapp.presentation.feature8

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.internshipapp.myLog
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NavigationTestViewModel : ViewModel() {

    init {
        viewModelScope.launch {
            var i = 0
            while (true) {
                delay(1000)
                myLog("ViewModel существует: ${i++}")
            }

        }
    }
}