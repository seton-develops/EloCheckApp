package com.seton_develops.elocalculator

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.seton_develops.elocalculator.DataSource.UserSharedPreferences
import com.seton_develops.elocalculator.Model.EloData
import com.seton_develops.elocalculator.Model.EloViewModel


object CustomAlertDialog {

    //TODO: Add userUpdatedElo, OpponentUpdatedElo, UserUpdatedEloAmount, OpponentUpdatedEloAmount
    operator fun invoke(context: Context, eloViewModel: EloViewModel) {
        val customDialogBuilder = AlertDialog.Builder(context)

        val customLayoutInflater = LayoutInflater.from(context)
            .inflate(R.layout.activity_updated_elo, null)

        val dialogInstance = customDialogBuilder.setView(customLayoutInflater).create()
        dialogInstance.show()
        dialogInstance.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        var cancelButton: Button = customLayoutInflater.findViewById(R.id.buttonCancel)
        var acceptButton: Button = customLayoutInflater.findViewById(R.id.buttonAccept)

        cancelButton.setOnClickListener {
            dialogInstance.dismiss()
        }

        acceptButton.setOnClickListener {
            isUpdateEloScores(context, eloViewModel, result = 1.0)
            Toast.makeText(context, "accept button clicked", Toast.LENGTH_LONG).show()
            dialogInstance.dismiss()
        }

    }

    //TODO: update Elo scores in a repository (probably another singleton)
    private fun isUpdateEloScores(context: Context, eloViewModel: EloViewModel, result: Double) {
        require(result == 1.0 || result == 0.5 || result == 0.0) {"Invalid result"}

        var oppResult = 0.5

        if (result == 1.0) {
            oppResult = 0.0
        }
        else if (result == 0.0) {
            oppResult = 1.0
        }

        //TODO: Change to use eloData data type instead of hard values
        val newUserElo = EloCalculator.calculateUpdatedElos(
            userElo = 1550,
            opponentElo = 1580,
            kCoefficient = 20,
            result = result
        )

        val newOpponentElo = EloCalculator.calculateUpdatedElos(
            userElo = 1550,
            opponentElo = 1580,
            kCoefficient = 20,
            result = oppResult
        )


        eloViewModel.updateData(
            context,
            EloData(
                newUserElo,
                newOpponentElo,
                kValueIndex = 0,
                organization = "FIDE"
            )
        )

    }
}

