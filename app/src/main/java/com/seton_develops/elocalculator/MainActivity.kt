package com.seton_develops.elocalculator



import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.seton_develops.elocalculator.Model.EloViewModel
import com.seton_develops.elocalculator.Model.EloViewModelFactory
import com.seton_develops.elocalculator.Repository.EloRepository
import com.seton_develops.elocalculator.Repository.PrivacyPolicyDialog
import java.lang.IllegalArgumentException
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    protected var isFideCheck = true

    private lateinit var spinnerKCoefficients: Spinner

    private lateinit var editTextUserELO: EditText
    private lateinit var editTextOpponentELO: EditText

    private lateinit var textViewUserChances: TextView
    private lateinit var textViewOpponentChances: TextView
    private lateinit var textViewPrivacyPolicy: TextView

    private lateinit var buttonWin: ImageView
    private lateinit var buttonDraw: ImageView
    private lateinit var buttonLose: ImageView

    private lateinit var radioButtonFIDE: RadioButton
    private lateinit var radioButtonUSCF: RadioButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        editTextUserELO = findViewById(R.id.editTextUserELO)
        editTextOpponentELO = findViewById(R.id.editTextTextOpponentELO)
        textViewUserChances = findViewById(R.id.textviewUserPercentage)
        textViewOpponentChances = findViewById(R.id.textviewOpponentPercentage)
        textViewPrivacyPolicy = findViewById(R.id.textViewPrivacyPolicy)
        radioButtonFIDE = findViewById((R.id.radio_FIDE))
        radioButtonUSCF = findViewById((R.id.radio_USCF))
        buttonWin = findViewById(R.id.buttonWin)
        buttonDraw = findViewById(R.id.buttonDraw)
        buttonLose = findViewById(R.id.buttonLose)




        spinnerKCoefficients = findViewById(R.id.spinnerKCoefficients)
        var spinnerAdapter: ArrayAdapter<CharSequence>? = null
        var spinnerAdapter2: ArrayAdapter<CharSequence>? = null

        val isTablet = resources.getBoolean(R.bool.isTablet)
        if (!isTablet) {
            spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.FIDE_K_array,
                android.R.layout.simple_spinner_item
            )

            spinnerAdapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.UCSF_K_array,
                android.R.layout.simple_spinner_item
            )

            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        else {
            spinnerAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.FIDE_K_array,
                R.layout.sw600_spinner_layout
            )

            spinnerAdapter2 = ArrayAdapter.createFromResource(
                this,
                R.array.UCSF_K_array,
                R.layout.sw600_spinner_layout
            )

            spinnerAdapter.setDropDownViewResource(R.layout.sw600_spinner_layout)
            spinnerAdapter2.setDropDownViewResource(R.layout.sw600_spinner_layout)

        }


        val eloRepository = EloRepository
        val eloDataViewModelFactory = EloViewModelFactory(eloRepository)

        val eloViewModel = ViewModelProvider(this, eloDataViewModelFactory)[EloViewModel::class.java]

        eloViewModel.getData(this)

        eloViewModel.eloData.observe(this, Observer {eloData ->
            editTextUserELO.setText(eloData.userElo.toString())
            editTextOpponentELO.setText(eloData.opponentElo.toString())

            var userPercent =
                EloCalculator.calculateExpectedValue(eloData.userElo.toString().toInt(),
                    eloData.opponentElo.toString().toInt())

            userPercent = (userPercent * 100.0)
            userPercent = (userPercent * 100.0).roundToInt() / 100.0 //Rounds to 2 decimal places

            textViewUserChances.text =  "$userPercent"
            textViewOpponentChances.text = "${((100-userPercent) * 100).roundToInt() / 100.0}"

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


        textViewPrivacyPolicy.setOnClickListener {
            PrivacyPolicyDialog(this)
        }


        buttonWin.setOnClickListener {
            //creates an Alert Dialog for this activity to show updated Elo Scores
            val userValue = editTextUserELO.text.toString().toInt()
            val oppValue = editTextOpponentELO.text.toString().toInt()

            if (radioButtonUSCF.isChecked) {
                isFideCheck = false
            }

            if (checkInputRequirements(userValue, oppValue)) {

                CustomAlertDialog(
                    this,
                    eloViewModel,
                    result = 1.0,
                    userElo = userValue,
                    opponentElo = oppValue,
                    kValue = spinnerKCoefficients.selectedItem.toString().toInt(),
                    radioButtonFIDE.isChecked,
                    radioButtonUSCF.isChecked,
                    kIndex = spinnerKCoefficients.selectedItemPosition
                )
            }
        }

        buttonDraw.setOnClickListener {
            //creates an Alert Dialog for this activity to show updated Elo Scores
            val userValue = editTextUserELO.text.toString().toInt()
            val oppValue = editTextOpponentELO.text.toString().toInt()

            if (radioButtonUSCF.isChecked) {
                isFideCheck = false
            }

            if (checkInputRequirements(userValue, oppValue)) {
                CustomAlertDialog(
                    this,
                    eloViewModel,
                    result = 0.5,
                    userElo = userValue,
                    opponentElo = oppValue,
                    kValue = spinnerKCoefficients.selectedItem.toString().toInt(),
                    radioButtonFIDE.isChecked,
                    radioButtonUSCF.isChecked,
                    kIndex = spinnerKCoefficients.selectedItemPosition
                )
            }
        }

        buttonLose.setOnClickListener {
            //creates an Alert Dialog for this activity to show updated Elo Scores
            val userValue = editTextUserELO.text.toString().toInt()
            val oppValue = editTextOpponentELO.text.toString().toInt()

            if (radioButtonUSCF.isChecked) {
                isFideCheck = false
            }

            if (checkInputRequirements(userValue, oppValue)) {

                CustomAlertDialog(
                    this,
                    eloViewModel,
                    result = 0.0,
                    userElo = userValue,
                    opponentElo = oppValue,
                    kValue = spinnerKCoefficients.selectedItem.toString().toInt(),
                    radioButtonFIDE.isChecked,
                    radioButtonUSCF.isChecked,
                    kIndex = spinnerKCoefficients.selectedItemPosition
                )
            }
        }



        radioButtonFIDE.setOnClickListener {
            eloViewModel.updateRadioButton(this,
                fideCheck = true,
                uscfCheck = false,
                kCoefficientIndex = spinnerKCoefficients.selectedItemPosition)

            spinnerKCoefficients.adapter = spinnerAdapter
            isFideCheck = true
        }

        radioButtonUSCF.setOnClickListener {
            eloViewModel.updateRadioButton(this,
                fideCheck = false,
                uscfCheck = true,
                kCoefficientIndex = spinnerKCoefficients.selectedItemPosition)

            spinnerKCoefficients.adapter = spinnerAdapter2
            isFideCheck = false
        }



        editTextOpponentELO.setOnEditorActionListener{ _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent == null ||
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {

                val currValue = editTextOpponentELO.text.toString().toInt()
                eloViewModel.updateOpponentTextView(this@MainActivity, currValue)


                val (userPercent,oppPercent) = updatePercentages(editTextUserELO.text.toString().toInt(),
                    currValue)

                textViewUserChances.text = userPercent
                textViewOpponentChances.text = oppPercent


                true
            }
            false
        }

        editTextUserELO.setOnEditorActionListener{ _, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE ||
                keyEvent == null ||
                keyEvent.keyCode == KeyEvent.KEYCODE_ENTER) {

                val currValue = editTextUserELO.text.toString().toInt()
                eloViewModel.updateUserTextView(this@MainActivity,
                                                currValue)

                val (userPercent,oppPercent) = updatePercentages(currValue,
                                                            editTextOpponentELO.text.toString().toInt())

                textViewUserChances.text = userPercent
                textViewOpponentChances.text = oppPercent


                true
            }
            false
        }


    }

    private fun updatePercentages(userElo: Int, opponentElo: Int): Pair<String, String> {
        var userPercent =
            EloCalculator.calculateExpectedValue(userElo, opponentElo)

        userPercent = (userPercent * 100.0)
        userPercent = (userPercent * 100.0).roundToInt() / 100.0 //Rounds to 2 decimal places

        return Pair(userPercent.toString(),(((100-userPercent) * 100).roundToInt() / 100.0).toString())
    }


    private fun checkInputRequirements(userElo: Int, opponentElo: Int ): Boolean {
        try {
            if (isFideCheck) {
                require(userElo >= 1000 && opponentElo >= 1000) {"FIDE Rating >= 1000"}
            } else {
                require(userElo >= 100 && opponentElo >= 100) {"UCSF Rating >= 100"}
            }

            require(userElo <= 4000 && opponentElo <= 4000) {"Max Rating <= 4000"}
        }
        catch (e: IllegalArgumentException) {
            Toast.makeText(this,
                "Rating does not meet the Elo requirements for the selected organization",
                Toast.LENGTH_LONG).show()
            return false
        }

        return true

    }




}





