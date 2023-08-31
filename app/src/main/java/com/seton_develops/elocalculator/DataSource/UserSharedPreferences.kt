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
        val fideCheck = sharedPref
            .getBoolean(context.getString(R.string.FIDE_radio_string), true)
        val uscfCheck = sharedPref
            .getBoolean(context.getString(R.string.USCF_radio_string), false)

        return EloData(userElo, oppElo, kCoeffIndex, fideCheck, uscfCheck)
    }

    fun updateSharedPreferences(context: Context, eloData: EloData) {
        sharedPref = context.getSharedPreferences(prefString, Context.MODE_PRIVATE)

        with (sharedPref.edit()) {
            putInt(context.getString(R.string.user_elo_string), eloData.userElo)
            putInt(context.getString(R.string.opponent_elo_string), eloData.opponentElo)
            putInt(context.getString(R.string.k_coefficient_string), eloData.kValueIndex)
            putBoolean(context.getString(R.string.FIDE_radio_string), eloData.FIDECheck)
            putBoolean(context.getString(R.string.USCF_radio_string), eloData.USCFCheck)
            apply()
        }
    }

    fun updateRadioGroup(context: Context,
                         fideCheck: Boolean,
                         uscfCheck: Boolean,
                         kCoefficientIndex: Int) {

        sharedPref = context.getSharedPreferences(prefString, Context.MODE_PRIVATE)

        with (sharedPref.edit()) {
            putBoolean(context.getString(R.string.FIDE_radio_string),fideCheck)
            putBoolean(context.getString(R.string.USCF_radio_string), uscfCheck)
            putInt(context.getString(R.string.k_coefficient_string), kCoefficientIndex)
            apply()
        }

    }

    fun updateUserTextView(context: Context, userElo: Int) {
        sharedPref = context.getSharedPreferences(prefString, Context.MODE_PRIVATE)

        with (sharedPref.edit()) {
            putInt(context.getString(R.string.user_elo_string), userElo)
            apply()
        }

    }

    fun updateOpponentTextView(context: Context, OpponentElo: Int) {
        sharedPref = context.getSharedPreferences(prefString, Context.MODE_PRIVATE)

        with (sharedPref.edit()) {
            putInt(context.getString(R.string.opponent_elo_string), OpponentElo)
            apply()
        }

    }

}