package st.tutorial.mycalculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var tvInput: TextView? = null
    var lastNumeric: Boolean = false
    var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvInput = findViewById(R.id.tv_input)
    }
    fun onDigit(view: View){
        tvInput?.append((view as Button).text)
        lastNumeric = true
        lastDot = false
    }

    fun onClear(view : View) {
        tvInput?.text = " "
    }

    fun onDotClicked(view: View) {
        if(lastNumeric && !lastDot)
        {
            tvInput?.append(".")
            lastDot = true
            lastNumeric = false
        }
    }

    fun onEqual(view: View) {
        if(lastNumeric){
            var expression = tvInput?.text?.toString()
            var prefix = ""
            try {
                if (expression != null) {
                    if(expression.startsWith("-")){
                        prefix = "-"
                        expression = expression.substring(1)
                    }
                }

                if (expression != null) {
                    if(expression.contains("-")){
                        val split = expression.split("-")
                        val one = split[0]
                        val two = split[1]
                        val result = one.toDouble().minus(two.toDouble())
                        tvInput?.text = removeZeroAfterDot(result.toString())
                    }else if(expression.contains("+")){
                        val split = expression.split("+")
                        val one = split[0]
                        val two = split[1]
                        val result = one.toDouble().plus(two.toDouble())
                        tvInput?.text = removeZeroAfterDot(result.toString())
                    }
                    else if(expression.contains("*")){
                        val split = expression.split("*")
                        val one = split[0]
                        val two = split[1]
                        val result = one.toDouble()  * two.toDouble()
                        tvInput?.text = removeZeroAfterDot(result.toString())
                    }else if(expression.contains("/")){
                        val split = expression.split("/")
                        val one = split[0]
                        val two = split[1]
                        val result = one.toDouble() / two.toDouble()
                        tvInput?.text = removeZeroAfterDot(result.toString())
                    }
                }


            } catch (e: ArithmeticException) {
                Toast.makeText(this, "Error occurred: $e", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String{
        var value = result
        if(result.contains("0")){
            value = result.substring(0, result.length - 2)
        }
        return  value
    }

    fun onOperator(view: View) {
        tvInput?.text?.let {
            if(lastNumeric && !isOperatorAdded(it.toString())){
                tvInput?.append((view as Button).text)
                lastNumeric = false
                lastDot = false
            }
        }
    }

    //helper function
    private fun isOperatorAdded(value: String) : Boolean {
        return if(value.startsWith("-")){
            false
        }else{
            value.contains("+")
                    || value.contains("-")
                    || value.contains("*")
                    || value.contains("/")
        }
    }
}