package com.seton_develops.elocalculator

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import androidx.appcompat.app.AlertDialog
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    private lateinit var spinnerKCoefficients: Spinner
    private lateinit var buttonWin: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        spinnerKCoefficients = findViewById(R.id.spinnerKCoefficients)
        var spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.FIDE_K_array,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKCoefficients.adapter = spinnerAdapter


        buttonWin = findViewById(R.id.buttonWin)

        buttonWin.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val popupView = layoutInflater.inflate(R.layout.activity_updated_elo, null)

            var cancelButton: Button = popupView.findViewById(R.id.buttonCancel)

            val dialogInstance = dialog.setView(popupView).create()
            dialogInstance.show()
            dialogInstance.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


            cancelButton.setOnClickListener {
                dialogInstance.dismiss()
            }
        }
    }

    fun getScreenDimensions(context: Context): Pair<Int,Int> {
        val height = context.resources.displayMetrics.heightPixels
        val width  = context.resources.displayMetrics.widthPixels
        return Pair(height,width)
    }
}