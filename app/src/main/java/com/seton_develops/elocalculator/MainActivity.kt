package com.seton_develops.elocalculator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seton_develops.elocalculator.DataSource.UserSharedPreferences
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
        buttonWin = findViewById(R.id.buttonWin)

        spinnerKCoefficients = findViewById(R.id.spinnerKCoefficients)
        val spinnerAdapter: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.FIDE_K_array,
            android.R.layout.simple_spinner_item
        )

        val spinnerAdapter2: ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(
            this,
            R.array.UCSF_K_array,
            android.R.layout.simple_spinner_item
        )
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)



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
                spinnerKCoefficients.adapter = spinnerAdapter
                spinnerKCoefficients.setSelection(eloData.kValueIndex)
            }
            else {
                radioButtonUSCF.isChecked = true
                spinnerKCoefficients.adapter = spinnerAdapter2
                spinnerKCoefficients.setSelection(eloData.kValueIndex)
            }
        })


        buttonWin.setOnClickListener {
            //creates an Alert Dialog for this activity to show updated Elo Scores
            CustomAlertDialog(this,
                eloViewModel,
                result = 1.0,
                userElo = editTextUserELO.text.toString().toInt(),
                opponentElo = editTextOpponentELO.text.toString().toInt(),
                kValue = spinnerKCoefficients.selectedItem.toString().toInt(),
                radioButtonFIDE.isChecked,
                radioButtonUSCF.isChecked,
                kIndex = spinnerKCoefficients.selectedItemPosition
            )
        }

//        spinnerKCoefficients.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View?,
//                position: Int,
//                id: Long
//            ) {
//
//
//                if (radioButtonFIDE.isChecked) {
//                    eloViewModel.updateRadioButton(context = this@MainActivity,
//                        fideCheck = true,
//                        uscfCheck = false,
//                        kCoefficientIndex = position)
//                }
//                else {
//                    eloViewModel.updateRadioButton(context = this@MainActivity,
//                        fideCheck = false,
//                        uscfCheck = true,
//                        kCoefficientIndex = position)
//
//                }
//
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {
//
//            }
//
//        }

        //TODO: Save index used for kValue
        radioButtonFIDE.setOnClickListener {
            eloViewModel.updateRadioButton(this,
                fideCheck = true,
                uscfCheck = false,
                kCoefficientIndex = spinnerKCoefficients.selectedItemPosition)

            spinnerKCoefficients.adapter = spinnerAdapter
        }

        radioButtonUSCF.setOnClickListener {
            eloViewModel.updateRadioButton(this,
                fideCheck = false,
                uscfCheck = true,
                kCoefficientIndex = spinnerKCoefficients.selectedItemPosition)

            spinnerKCoefficients.adapter = spinnerAdapter2
        }
    }
}

