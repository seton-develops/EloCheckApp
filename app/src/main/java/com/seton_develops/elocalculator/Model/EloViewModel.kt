package com.seton_develops.elocalculator.Model


import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seton_develops.elocalculator.Repository.EloRepository

class EloViewModel(private val eloRepository: EloRepository): ViewModel() {
    val eloData: MutableLiveData<EloData> = MutableLiveData()

    fun getData(context: Context) {
        eloData.value = eloRepository.getData(context)
    }

    fun updateData(context: Context, eloData: EloData) {
        eloRepository.updateData(context,eloData)
        getData(context)
    }

}