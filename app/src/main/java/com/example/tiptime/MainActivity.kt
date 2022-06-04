/*
 Colabs from Android
 */

package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat


class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)


        setContentView(binding.root)

        // Setup a click listener on the calculate button to calculate the tip
        binding.calculateButton.setOnClickListener { calculateTip() }

        // Set up a key listener on the EditText field to listen for "enter" button presses
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ ->
            handleKeyEvent(
                view,
                keyCode
            )
        }
    }

    /**
     * Calcular propina en base a la entrada del usuario.
     */
    private fun calculateTip() {

        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()


        if (cost == null || cost == 0.0) {
            displayTip(0.0)
            return
        }


        val tipPercentage = when (binding.tipOptions.checkedRadioButtonId) {
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        // Calcular propina
        var tip = tipPercentage * cost


        val roundUp = binding.roundUpSwitch.isChecked
        if (roundUp) {

            tip = kotlin.math.ceil(tip)
        }

        // Visibilizar en pantalla propina
        displayTip(tip)
    }


    private fun displayTip(tip: Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }


    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Esconder el teclado
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}