package com.seton_develops.elocalculator.Model
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.seton_develops.elocalculator.Repository.EloRepository


class EloViewModelFactory(private val repository: EloRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EloViewModel(repository) as T
    }

}