package com.seton_develops.elocalculator.Repository

import android.content.Context
import com.seton_develops.elocalculator.DataSource.UserSharedPreferences
import com.seton_develops.elocalculator.Model.EloData

object EloRepository {

    fun getData(context: Context): EloData {
        return UserSharedPreferences.getSharedPreferences(context)
    }

    fun updateData(context: Context, eloData: EloData) {
        UserSharedPreferences.updateSharedPreferences(context, eloData)
    }

    fun updateRadioGroup(context: Context,
                         fideCheck: Boolean,
                         uscfCheck: Boolean,
                         kCoefficientIndex: Int) {

        UserSharedPreferences.updateRadioGroup(context, fideCheck, uscfCheck, kCoefficientIndex)
    }


}