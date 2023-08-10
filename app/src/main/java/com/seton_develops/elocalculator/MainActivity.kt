package com.seton_develops.elocalculator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner


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
            //creates an Alert Dialog for this activity to show updated Elo Scores
            CustomAlertDialog(this)
        }
    }
}

