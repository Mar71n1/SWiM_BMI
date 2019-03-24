package pl.pwr.bmi

import android.content.Intent
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import pl.pwr.bmi.logic.Bmi
import pl.pwr.bmi.logic.BmiForKgCm
import pl.pwr.bmi.logic.BmiForLbFt
import java.util.*

class MainActivity : AppCompatActivity() {

    private var englishUnits: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(savedInstanceState != null) {
            bmi_main_mass_label.text = savedInstanceState.getString("mass_label")
            bmi_main_height_label.text = savedInstanceState.getString("height_label")
            englishUnits = savedInstanceState.getBoolean("actual_units")

            val bmiNumber = savedInstanceState.getDouble("bmi_value")
            bmi_main_bmi_number.text = bmiNumber.toString()
            if(bmiNumber != 0.0) {
                when {
                    bmiNumber < 16.0 -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_severely_underweight)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_severely_underweight
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_severely_underweight
                            )
                        )
                    }
                    bmiNumber < 18.5 -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_underweight)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_underweight
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_underweight
                            )
                        )
                    }
                    bmiNumber < 25.0 -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_normal)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_normal
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_normal
                            )
                        )
                    }
                    bmiNumber < 30.0 -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_overweight)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_overweight
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_overweight
                            )
                        )
                    }
                    else -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_obesity)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_obesity
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_obesity
                            )
                        )
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.bmi_main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.about_me -> {
                startActivity(Intent(this, AboutMeActivity::class.java))
                true
            }
            R.id.change_units -> {
                if(englishUnits == false) {
                    englishUnits = true
                    bmi_main_mass_label.setText(getString(R.string.textview_mass_lb))
                    bmi_main_height_label.setText(getString(R.string.textview_height_ft))
                }
                else {
                    englishUnits = false
                    bmi_main_mass_label.setText(getString(R.string.textview_mass_kg))
                    bmi_main_height_label.setText(getString(R.string.textview_height_cm))
                }
                bmi_main_mass_edittext.getText().clear()
                bmi_main_height_edittext.getText().clear()
                bmi_main_bmi_number.setText(getString(R.string.bmi_number_default))
                bmi_main_bmi_number.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.black))
                bmi_main_bmi_text.setText(getString(R.string.bmi_textview_default))
                bmi_main_bmi_text.setTextColor(ContextCompat.getColor(applicationContext, android.R.color.black
                ))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putDouble("bmi_value", bmi_main_bmi_number.text.toString().toDouble())
        savedInstanceState.putString("mass_label", bmi_main_mass_label.text.toString())
        savedInstanceState.putString("height_label", bmi_main_height_label.text.toString())
        savedInstanceState.putBoolean("actual_units", englishUnits)

        super.onSaveInstanceState(savedInstanceState)
    }

    fun countBmi(@Suppress("UNUSED_PARAMETER")view: View) {
        if(!(bmi_main_height_edittext.text.toString() == "" || bmi_main_height_label.text.toString() == "")) {
            var bmi: Bmi = BmiForKgCm()
            if (englishUnits == true)
                bmi = BmiForLbFt()

            try {
                bmi.mass = bmi_main_mass_edittext.text.toString().toDouble()
                bmi.height = bmi_main_height_edittext.text.toString().toDouble()
                String.format(Locale.US, "%.2f", bmi.countBmi())
                bmi_main_bmi_number.setText(String.format(Locale.US, "%.2f", bmi.countBmi()))
                val bmiNumber = bmi_main_bmi_number.text.toString().toDouble()

                when {
                    bmiNumber < 16.0 -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_severely_underweight)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_severely_underweight
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_severely_underweight
                            )
                        )
                    }
                    bmiNumber < 18.5 -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_underweight)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_underweight
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_underweight
                            )
                        )
                    }
                    bmiNumber < 25.0 -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_normal)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_normal
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_normal
                            )
                        )
                    }
                    bmiNumber < 30.0 -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_overweight)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_overweight
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_overweight
                            )
                        )
                    }
                    else -> {
                        bmi_main_bmi_text.text = getString(R.string.bmi_main_obesity)
                        bmi_main_bmi_number.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_obesity
                            )
                        )
                        bmi_main_bmi_text.setTextColor(
                            ContextCompat.getColor(
                                applicationContext,
                                R.color.bmi_main_color_obesity
                            )
                        )
                    }
                }
            }
            catch(ex: IllegalArgumentException) {
                Toast.makeText(applicationContext, getString(R.string.too_small_data_error), Toast.LENGTH_SHORT).show()
            }
        }
        if(bmi_main_mass_edittext.text.toString() == "")
            bmi_main_mass_edittext.setError(getString(R.string.noinput_error))
        if(bmi_main_height_edittext.text.toString() == "")
            bmi_main_height_edittext.setError(getString(R.string.noinput_error))
    }

    fun showMoreInfo(@Suppress("UNUSED_PARAMETER")view: View) {
        val intent = Intent(this, MoreInfoActivity::class.java).apply {
            putExtra("bmi_value", bmi_main_bmi_number.text.toString().toDouble())
        }
        startActivity(intent)
    }
}
