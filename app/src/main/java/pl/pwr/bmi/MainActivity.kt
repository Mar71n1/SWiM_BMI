package pl.pwr.bmi

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var englishUnits: Boolean = false

    private val SHARED_PREFERENCES_NAME = "pl.pwr.bmi.SHARED_PREFERENCES"
    private val sharedPreferencesMasses = "masses"
    private val sharedPreferencesHeights = "heights"
    private val sharedPreferencesResults = "results"
    private val sharedPreferencesDates = "dates"

    private lateinit var lastResultsPrefs: SharedPreferences
    private lateinit var massesList: ArrayList<String>
    private lateinit var heightsList: ArrayList<String>
    private lateinit var resultsList: ArrayList<String>
    private lateinit var datesList: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lastResultsPrefs = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        massesList = loadListFromSharedPreferences(sharedPreferencesMasses)
        heightsList = loadListFromSharedPreferences(sharedPreferencesHeights)
        resultsList = loadListFromSharedPreferences(sharedPreferencesResults)
        datesList = loadListFromSharedPreferences(sharedPreferencesDates)

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

        if(massesList.size == 0)
            menu.getItem(2).setEnabled(false)

        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        if(0 < massesList.size)
        menu.getItem(2).setEnabled(true)
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
            R.id.last_results -> {
                startActivity(Intent(this, LastResultsActivity::class.java))
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

                if(massesList.size == 10) {
                    massesList.removeAt(0)
                    heightsList.removeAt(0)
                    resultsList.removeAt(0)
                    datesList.removeAt(0)
                }

                if(englishUnits == false)
                    massesList.add(String.format(Locale.US,"%.2f", bmi_main_mass_edittext.text.toString().toDouble()) + " kg")
                else
                    massesList.add(String.format(Locale.US,"%.2f", bmi_main_mass_edittext.text.toString().toDouble()) + " lb")

                if(englishUnits == false)
                    heightsList.add(String.format(Locale.US,"%.2f", bmi_main_height_edittext.text.toString().toDouble()) + " cm")
                else
                    heightsList.add(String.format(Locale.US,"%.2f", bmi_main_height_edittext.text.toString().toDouble()) + " ft")

                resultsList.add(bmi_main_bmi_number.text.toString())
                val date = java.util.Calendar.getInstance().getTime()
                val formatter = SimpleDateFormat("dd/MM/yyyy")
                datesList.add(formatter.format(date))

                saveSharedPreferencesData()
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

    private fun saveSharedPreferencesData() {
        val editor = lastResultsPrefs.edit()
        var stringBuilder = StringBuilder()

        for(string in massesList) {
            stringBuilder.append(string)
            stringBuilder.append(",")
        }
        stringBuilder.setLength(stringBuilder.length - 1)
        editor.putString(sharedPreferencesMasses, stringBuilder.toString())

        stringBuilder = StringBuilder()

        for(string in heightsList) {
            stringBuilder.append(string)
            stringBuilder.append(",")
        }
        stringBuilder.setLength(stringBuilder.length - 1)
        editor.putString(sharedPreferencesHeights, stringBuilder.toString())

        stringBuilder = StringBuilder()

        for(string in resultsList) {
            stringBuilder.append(string)
            stringBuilder.append(",")
        }
        stringBuilder.setLength(stringBuilder.length - 1)
        editor.putString(sharedPreferencesResults, stringBuilder.toString())

        stringBuilder = StringBuilder()

        for(string in datesList) {
            stringBuilder.append(string)
            stringBuilder.append(",")
        }
        stringBuilder.setLength(stringBuilder.length - 1)
        editor.putString(sharedPreferencesDates, stringBuilder.toString())

        editor.commit()
    }

    private fun loadListFromSharedPreferences(dataName: String): ArrayList<String> {
        val string = lastResultsPrefs.getString(dataName, "")
        val stringArray = string.split(",")
        val items = ArrayList<String>()

        if(string == "")
            return items

        for(item in stringArray)
            items.add(item)

        return items
    }
}
