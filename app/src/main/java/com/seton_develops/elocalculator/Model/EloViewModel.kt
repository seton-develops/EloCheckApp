package com.seton_develops.elocalculator.Model


import android.content.Context
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seton_develops.elocalculator.DataSource.UserSharedPreferences
import com.seton_develops.elocalculator.Repository.EloRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EloViewModel(private val eloRepository: EloRepository): ViewModel() {
    val eloData: MutableLiveData<EloData> = MutableLiveData()

    fun getData(context: Context) {
        eloData.value = eloRepository.getData(context)
    }

    fun updateData(context: Context, eloData: EloData) {
        eloRepository.updateData(context,eloData)
        getData(context)
    }

    fun updateRadioButton(context: Context,
                          fideCheck: Boolean,
                          uscfCheck: Boolean,
                          kCoefficientIndex: Int) {


        eloRepository.updateRadioGroup(context,fideCheck,uscfCheck, kCoefficientIndex)


        getData(context)
    }

    fun updateUserTextView(context: Context, userElo: Int) {
        eloRepository.updateUserTextView(context, userElo)
    }

    fun updateOpponentTextView(context: Context, OpponentElo: Int) {
        eloRepository.updateOpponentTextView(context, OpponentElo)
    }



}