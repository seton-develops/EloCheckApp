package com.seton_develops.elocalculator

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.seton_develops.elocalculator.Model.EloData
import com.seton_develops.elocalculator.Model.EloViewModel



object CustomAlertDialog {


    operator fun invoke(context: Context,
                        eloViewModel: EloViewModel,
                        result: Double,
                        userElo: Int,
                        opponentElo: Int,
                        kValue: Int,
                        fideCheck: Boolean,
                        uscfCheck: Boolean) {

        val customDialogBuilder = AlertDialog.Builder(context)

        val customLayoutInflater = LayoutInflater.from(context)
            .inflate(R.layout.activity_updated_elo, null)

        val dialogInstance = customDialogBuilder.setView(customLayoutInflater).create()
        dialogInstance.show()
        dialogInstance.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val cancelButton: Button = customLayoutInflater.findViewById(R.id.buttonCancel)
        val acceptButton: Button = customLayoutInflater.findViewById(R.id.buttonAccept)

        val textViewUpdatedUserElo: TextView
        = customLayoutInflater.findViewById(R.id.textViewUpdatedUserElo)

        val textViewUpdatedOpponentElo: TextView
        = customLayoutInflater.findViewById(R.id.textViewUpdatedOpponentElo)

        val textViewUpdatedUserEloPoints: TextView
        = customLayoutInflater.findViewById(R.id.textViewUpdatedUserEloPoints)

        val textViewUpdatedOpponentEloPoints: TextView
                = customLayoutInflater.findViewById(R.id.textViewUpdatedOpponentEloPoints)

        //Calculate new Elo scores
        val (newUserElo, newOpponentElo) = isUpdateEloScores(result = result,
                                                            userElo = userElo,
                                                            opponentElo = opponentElo,
                                                            kValue = kValue)

        textViewUpdatedUserElo.text = newUserElo.toString()
        textViewUpdatedOpponentElo.text = newOpponentElo.toString()

        textViewUpdatedUserEloPoints.text = " (" + (newUserElo - userElo).toString() + ")"
        textViewUpdatedOpponentEloPoints.text = " (" + (newOpponentElo - opponentElo).toString() + ")"


        //change the color of the points so it is easier to see positive/ negative change
        updateTextViewColors(context,
                newUserElo - userElo,
            newOpponentElo - opponentElo,
                            textViewUpdatedUserEloPoints,
                            textViewUpdatedOpponentEloPoints )




        cancelButton.setOnClickListener {
            dialogInstance.dismiss()
        }

        acceptButton.setOnClickListener {

            //TODO: Update eloData here
            eloViewModel.updateData(context,
                                    EloData(newUserElo,
                                            newOpponentElo,
                                            kValueIndex = 0,
                                            FIDECheck = fideCheck,
                                            USCFCheck = uscfCheck
                                    )
            )


            dialogInstance.dismiss()
        }

    }


    private fun isUpdateEloScores(result: Double,
                                  userElo: Int,
                                  opponentElo: Int,
                                  kValue: Int): Pair<Int, Int> {

        require(result == 1.0 || result == 0.5 || result == 0.0) {"Invalid result"}

        var oppResult = 0.5

        if (result == 1.0) {
            oppResult = 0.0
        }
        else if (result == 0.0) {
            oppResult = 1.0
        }

        val newUserElo = EloCalculator.calculateUpdatedElos(
            userElo = userElo,
            opponentElo = opponentElo,
            kCoefficient = kValue,
            result = result
        )

        val newOpponentElo = EloCalculator.calculateUpdatedElos(
            userElo = opponentElo,
            opponentElo = userElo,
            kCoefficient = kValue,
            result = oppResult
        )

        return Pair(newUserElo, newOpponentElo)

    }


    private fun updateTextViewColors(context: Context,
                                     differenceUser: Int,
                                     differenceOpponent: Int,
                                     userTextView: TextView,
                                     opponentTextView: TextView) {

        if (differenceUser < 0) {
            userTextView.setTextColor(ContextCompat.getColor(context,R.color.red))
        }
        else if (differenceUser > 0) {
            userTextView.setTextColor(ContextCompat.getColor(context,R.color.green))
        }
        else {
            userTextView.setTextColor(ContextCompat.getColor(context,R.color.light_gray))
        }

        if (differenceOpponent < 0) {
            opponentTextView.setTextColor(ContextCompat.getColor(context,R.color.red))
        }
        else if (differenceOpponent > 0) {
            opponentTextView.setTextColor(ContextCompat.getColor(context,R.color.green))
        }
        else {
            opponentTextView.setTextColor(ContextCompat.getColor(context,R.color.light_gray))
        }
    }


}

