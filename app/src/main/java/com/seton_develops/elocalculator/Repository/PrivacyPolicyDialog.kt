package com.seton_develops.elocalculator.Repository

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.seton_develops.elocalculator.R


object PrivacyPolicyDialog {


    operator fun invoke(context: Context) {

        val customDialogBuilder = AlertDialog.Builder(context)

        val customLayoutInflater = LayoutInflater.from(context)
            .inflate(R.layout.privacy_policy, null)

        val dialogInstance = customDialogBuilder.setView(customLayoutInflater).create()
        dialogInstance.show()
        dialogInstance.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val cancelButton: TextView = customLayoutInflater.findViewById(R.id.textViewExit)

        cancelButton.setOnClickListener {
            dialogInstance.dismiss()
        }



    }



}

