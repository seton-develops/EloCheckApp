package com.seton_develops.elocalculator

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import androidx.appcompat.app.AlertDialog


object CustomAlertDialog {

    //TODO: Add userUpdatedElo, OpponentUpdatedElo, UserUpdatedEloAmount, OpponentUpdatedEloAmount
    operator fun invoke(context: Context) {
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
            isUpdateEloScores()
            dialogInstance.dismiss()
        }

    }

    //TODO: update Elo scores in a repository (probably another singleton)
    private fun isUpdateEloScores() {

    }
}

