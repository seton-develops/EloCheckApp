package com.seton_develops.elocalculator.Repository

import android.content.Context
import com.seton_develops.elocalculator.DataSource.UserSharedPreferences
import com.seton_develops.elocalculator.Model.EloData

object EloRepository {

    fun getData(context: Context): EloData {
        return UserSharedPreferences.getSharedPreferences(context)
    }

    fun updateData(context: Context, eloData: EloData) {
        return UserSharedPreferences.updateSharedPreferences(context, eloData)
    }


}