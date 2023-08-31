package com.seton_develops.elocalculator


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seton_develops.elocalculator.DataSource.UserSharedPreferences
import com.seton_develops.elocalculator.Model.EloViewModel
import com.seton_develops.elocalculator.Model.EloViewModelFactory
import com.seton_develops.elocalculator.Repository.EloRepository
import java.sql.Time
import java.util.*
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {


    private lateinit var spinnerKCoefficients: Spinner
    private lateinit var editTextUserELO: EditText
    private lateinit var editTextOpponentELO: EditText
    private lateinit var textViewUserChances: TextView
    private lateinit var textViewOpponentChances: TextView
    private lateinit var buttonWin: ImageView
    private lateinit var radioButtonFIDE: RadioButton
    private lateinit var radioButtonUSCF: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editTextUserELO = findViewById(R.id.editTextUserELO)
        editTextOpponentELO = findViewById(R.id.editTextTextOpponentELO)
        textViewUserChances = findViewById(R.id.textviewUserPercentage)
        textViewOpponentChances = findViewById(R.id.textviewOpponentPercentage)
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

            var userPercent =
                EloCalculator.calculateExpectedValue(eloData.userElo.toString().toInt(),
                    eloData.opponentElo.toString().toInt())

            userPercent = (userPercent * 100.0)
            userPercent = (userPercent * 100.0).roundToInt() / 100.0 //Rounds to 2 decimal places



            textViewUserChances.setText( "$userPercent%")
            textViewOpponentChances.setText("${((100-userPercent) * 100).roundToInt() / 100.0}%")

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



        editTextOpponentELO.setOnEditorActionListener{view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent == null ||
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {

                val currValue = editTextOpponentELO.text.toString().toInt()
                eloViewModel.updateOpponentTextView(this@MainActivity, currValue)


                val (userPercent,oppPercent) = updatePercentages(editTextUserELO.text.toString().toInt(),
                    currValue)

                textViewUserChances.setText(userPercent)
                textViewOpponentChances.setText(oppPercent)


                true
            }
            false
        }

        editTextUserELO.setOnEditorActionListener{view, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent == null ||
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {

                val currValue = editTextUserELO.text.toString().toInt()
                eloViewModel.updateUserTextView(this@MainActivity,
                                                currValue)

                val (userPercent,oppPercent) = updatePercentages(currValue,
                                                            editTextOpponentELO.text.toString().toInt())

                textViewUserChances.setText(userPercent)
                textViewOpponentChances.setText(oppPercent)


                true
            }
            false
        }





    }

    fun updatePercentages(userElo: Int, opponentElo: Int): Pair<String, String> {
        var userPercent =
            EloCalculator.calculateExpectedValue(userElo, opponentElo)

        userPercent = (userPercent * 100.0)
        userPercent = (userPercent * 100.0).roundToInt() / 100.0 //Rounds to 2 decimal places

        return Pair(userPercent.toString(),(((100-userPercent) * 100).roundToInt() / 100.0).toString())
    }


}





