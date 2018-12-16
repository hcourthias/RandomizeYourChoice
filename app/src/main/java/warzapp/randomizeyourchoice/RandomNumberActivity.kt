package warzapp.randomizeyourchoice

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class RandomNumberActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_number)
        val minNumberEditText = findViewById<EditText>(R.id.editText_min_number)
        val maxNumberEditText = findViewById<EditText>(R.id.editText_max_number)
        val randomButton = findViewById<Button>(R.id.button_random)

        randomButton.setOnClickListener { randomizeNumber(minNumberEditText, maxNumberEditText) }
        findViewById<Button>(R.id.button_reset).setOnClickListener { resetUI() }

    }

    private fun randomizeNumber(minNumberEditText: EditText?, maxNumberEditText: EditText?) {
        hideKeyboard()
        errorHandling(minNumberEditText, maxNumberEditText)
    }

    private fun errorHandling(minNumberEditText: EditText?, maxNumberEditText: EditText?) {
        when {
            minNumberEditText?.text.toString().isEmpty() or maxNumberEditText?.text.toString().isEmpty()
            -> Snackbar.make(findViewById<View>(R.id.random_number_activity),
                getString(R.string.empy_editText), Snackbar.LENGTH_LONG).show()
            minNumberEditText?.text.toString().toLong() > maxNumberEditText?.text.toString().toLong()
            -> Snackbar.make(findViewById<View>(R.id.random_number_activity),
                getString(R.string.min_is_higher), Snackbar.LENGTH_LONG).show()
            else -> {
                val minNumber: Long = minNumberEditText?.text.toString().toLong()
                val maxNumber: Long = maxNumberEditText?.text.toString().toLong()
                val resultNumber: Long = Random.nextLong(minNumber, maxNumber + 1)
                displayResult(resultNumber)
            }
        }
    }

    private fun displayResult(resultNumber: Long) {
        hideEditText()
        findViewById<Button>(R.id.button_reset).visibility = View.VISIBLE
        findViewById<Button>(R.id.button_random).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.textView_result).text = resultNumber.toString()
        findViewById<TextView>(R.id.textView_result).visibility = View.VISIBLE
    }

    private fun resetUI() {
        findViewById<Button>(R.id.button_reset).visibility = View.INVISIBLE
        findViewById<Button>(R.id.button_random).visibility = View.VISIBLE
        findViewById<EditText>(R.id.editText_min_number).visibility = View.VISIBLE
        findViewById<EditText>(R.id.editText_max_number).visibility = View.VISIBLE
        findViewById<TextView>(R.id.textView_result).visibility = View.INVISIBLE
    }

    private fun hideEditText() {
        findViewById<EditText>(R.id.editText_min_number).visibility = View.INVISIBLE
        findViewById<EditText>(R.id.editText_max_number).visibility = View.INVISIBLE
    }

    private fun hideKeyboard() {
        val imm = applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}