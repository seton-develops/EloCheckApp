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
        val kCoeff = sharedPref.getString(context.getString(R.string.k_coefficient_string), "20")
        val orgName = sharedPref.getString(context.getString(R.string.organization_name_string), "FIDE")

        return EloData(userElo, oppElo, kCoeff, orgName)


    }

}