package com.kadiroz.calculator

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.kadiroz.calculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    //Variables
    private var operator = "*"
    private var lastNumber = ""
    private var newOperator = true
    private val historyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Number button clicking actions
        binding.buttonZero.setOnClickListener { buttonNumberClick(binding.buttonZero) }
        binding.buttonOne.setOnClickListener { buttonNumberClick(binding.buttonOne) }
        binding.buttonTwo.setOnClickListener { buttonNumberClick(binding.buttonTwo) }
        binding.buttonThree.setOnClickListener { buttonNumberClick(binding.buttonThree) }
        binding.buttonFour.setOnClickListener { buttonNumberClick(binding.buttonFour) }
        binding.buttonFive.setOnClickListener { buttonNumberClick(binding.buttonFive) }
        binding.buttonSix.setOnClickListener { buttonNumberClick(binding.buttonSix) }
        binding.buttonSeven.setOnClickListener { buttonNumberClick(binding.buttonSeven) }
        binding.buttonEight.setOnClickListener { buttonNumberClick(binding.buttonEight) }
        binding.buttonNine.setOnClickListener { buttonNumberClick(binding.buttonNine) }
        binding.buttonNine.setOnClickListener { buttonNumberClick(binding.buttonNine) }

        //Calculation button for fractional numbers --- Double (.)
        binding.buttonPoint.setOnClickListener { buttonNumberClick(binding.buttonPoint) }

        //Operator buttons clicking actions
        binding.buttonMultiply.setOnClickListener { buttonOperatorAction(binding.buttonMultiply) }
        binding.buttonDivider.setOnClickListener { buttonOperatorAction(binding.buttonDivider) }
        binding.buttonPlus.setOnClickListener { buttonOperatorAction(binding.buttonPlus) }
        binding.buttonMinus.setOnClickListener { buttonOperatorAction(binding.buttonMinus) }
        binding.buttonEqual.setOnClickListener { buttonEqualAction() }

        //Editing Buttons clicking actions
        binding.buttonAllClear.setOnClickListener { buttonAllClearAction() }
        binding.buttonDelete.setOnClickListener { buttonDeleteAction() }

    }

    //Managing number Button and viewing the calculations on the textEdit views
    private fun buttonNumberClick(button: Button) {
        if (newOperator) {
            binding.textViewCalculate.text = ""
        }
        newOperator = false
        var buttonValue: String = binding.textViewCalculate.text.toString()
        buttonValue += button.text.toString()
        binding.textViewCalculate.text = buttonValue
    }

    //Actions perfored by the all operators("+","*","/","-")
    private fun buttonOperatorAction(button: Button) {
        operator = button.text.toString()
        lastNumber = binding.textViewCalculate.text.toString()
        newOperator = true
    }

    //Actions performed by the Equal button
    private fun buttonEqualAction() {
        val newNumber = binding.textViewCalculate.text.toString()
        var finalNumber: Double? = null
        when (operator) {
            "/" -> {
                finalNumber = lastNumber.toDouble() / newNumber.toDouble()
            }
            "*" -> {
                finalNumber = lastNumber.toDouble() * newNumber.toDouble()
            }
            "+" -> {
                finalNumber = lastNumber.toDouble() + newNumber.toDouble()
            }
            "-" -> {
                finalNumber = lastNumber.toDouble() - newNumber.toDouble()
            }
        }

        //write the completed calculations to the history
        val operation = "$lastNumber $operator $newNumber = $finalNumber"
        historyList.add(operation)

        //Keep the last 4 calculations on the screen
        if (historyList.size > 4) {
            historyList.removeAt(0) // Eski işlemi sil
        }
        updateHistoryTextView()
        binding.textViewCalculate.text = finalNumber.toString()
        newOperator = true
    }

    //AC button action
    private fun buttonAllClearAction() {
        binding.textViewCalculate.text = "0"
        historyList.clear()
        newOperator = true
        updateHistoryTextView()
    }

    //Viewing the last calculations under the other
    private fun updateHistoryTextView() {
        binding.textViewHistory.text = historyList.joinToString("\n")
    }

    //Delete button action
    private fun buttonDeleteAction() {
        val currentText = binding.textViewCalculate.text.toString()

        if (currentText.isNotEmpty()) {
            // Remove the last character
            val newText = currentText.substring(0, currentText.length - 1)
            binding.textViewCalculate.text = newText
        }

        // If the text becomes empty after deletion, set it to "0"
        if (binding.textViewCalculate.text.isEmpty()) {
            binding.textViewCalculate.text = "0"
            newOperator = true
        }
    }
}
