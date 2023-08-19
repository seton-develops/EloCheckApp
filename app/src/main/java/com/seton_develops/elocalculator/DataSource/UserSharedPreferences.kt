package com.seton_develops.elocalculator.DataSource
import android.content.Context;
import android.content.SharedPreferences;
import com.seton_develops.elocalculator.Model.EloData
import com.seton_develops.elocalculator.R

const val prefString = "userPreferences"

object UserSharedPreferences {

    private lateinit var sharedPref: SharedPreferences

    fun getSharedPreferences(context: Context): EloData {
        sharedPref = context.getSharedPreferences(prefString, Context.MODE_PRIVATE)

        val userElo = sharedPref.getInt(context.getString(R.string.user_elo_string), 1500)
        val oppElo = sharedPref.getInt(context.getString(R.string.opponent_elo_string), 1500)
        val kCoeffIndex = sharedPref.getInt(context.getString(R.string.k_coefficient_string), 0)
        val orgName = sharedPref.getString(context.getString(R.string.organization_name_string), "FIDE")

        return EloData(userElo, oppElo, kCoeffIndex, orgName)

    }

    fun updateSharedPreferences(context: Context, eloData: EloData) {
        sharedPref = context.getSharedPreferences(prefString, Context.MODE_PRIVATE)

        with (sharedPref.edit()) {
            putInt(context.getString(R.string.user_elo_string), eloData.userElo)
            putInt(context.getString(R.string.opponent_elo_string), eloData.opponentElo)
            putInt(context.getString(R.string.k_coefficient_string), eloData.kValueIndex)
            putString(context.getString(R.string.organization_name_string), eloData.organization)
            apply()
        }


    }

}