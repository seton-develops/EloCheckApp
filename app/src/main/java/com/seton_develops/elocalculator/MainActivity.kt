package com.seton_develops.elocalculator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seton_develops.elocalculator.Model.EloViewModel
import com.seton_develops.elocalculator.Model.EloViewModelFactory
import com.seton_develops.elocalculator.Repository.EloRepository


class MainActivity : AppCompatActivity() {


    private lateinit var spinnerKCoefficients: Spinner
    private lateinit var editTextUserELO: EditText
    private lateinit var editTextOpponentELO: EditText
    private lateinit var buttonWin: ImageView
    private lateinit var radioButtonFIDE: RadioButton
    private lateinit var radioButtonUSCF: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editTextUserELO = findViewById(R.id.editTextUserELO)
        editTextOpponentELO = findViewById(R.id.editTextTextOpponentELO)
        radioButtonFIDE = findViewById((R.id.radio_FIDE))
        radioButtonUSCF = findViewById((R.id.radio_USCF))

        val eloRepository = EloRepository
        val eloDataViewModelFactory = EloViewModelFactory(eloRepository)

        val eloViewModel = ViewModelProvider(this, eloDataViewModelFactory)[EloViewModel::class.java]

        eloViewModel.getData(this)

        eloViewModel.eloData.observe(this, Observer {eloData ->
            //TODO: add other fields from eloData
            editTextUserELO.setText(eloData.userElo.toString())
            editTextOpponentELO.setText(eloData.opponentElo.toString())

            if (eloData.FIDECheck) {
                radioButtonFIDE.isChecked = true
            }
            else {
                radioButtonUSCF.isChecked = true
            }
        })


        spinnerKCoefficients = findViewById(R.id.spinnerKCoefficients)
        val spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.FIDE_K_array,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerKCoefficients.adapter = spinnerAdapter


        buttonWin = findViewById(R.id.buttonWin)


        buttonWin.setOnClickListener {
            //creates an Alert Dialog for this activity to show updated Elo Scores
            CustomAlertDialog(this,
                eloViewModel,
                result = 1.0,
                userElo = editTextUserELO.text.toString().toInt(),
                opponentElo = editTextOpponentELO.text.toString().toInt(),
                kValue = spinnerKCoefficients.selectedItem.toString().toInt(),
                radioButtonFIDE.isChecked,
                radioButtonUSCF.isChecked
            )
        }

        //TODO: Save index used for kValue
        radioButtonFIDE.setOnClickListener {
            eloViewModel.updateRadioButton(this, fideCheck = true, uscfCheck = false)
        }

        radioButtonUSCF.setOnClickListener {
            eloViewModel.updateRadioButton(this, fideCheck = false, uscfCheck = true)
        }
    }
}

