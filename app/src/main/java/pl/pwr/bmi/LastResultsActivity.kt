package pl.pwr.bmi

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class LastResultsActivity : AppCompatActivity() {

    private val SHARED_PREFERENCES_NAME = "pl.pwr.bmi.SHARED_PREFERENCES"
    private val sharedPreferencesMasses = "masses"
    private val sharedPreferencesHeights = "heights"
    private val sharedPreferencesResults = "results"
    private val sharedPreferencesDates = "dates"

    private lateinit var lastResultsPrefs: SharedPreferences
    private lateinit var massesList: java.util.ArrayList<String>
    private lateinit var heightsList: java.util.ArrayList<String>
    private lateinit var resultsList: java.util.ArrayList<String>
    private lateinit var datesList: java.util.ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_last_results)

        lastResultsPrefs = getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        massesList = loadListFromSharedPreferences(sharedPreferencesMasses)
        heightsList = loadListFromSharedPreferences(sharedPreferencesHeights)
        resultsList = loadListFromSharedPreferences(sharedPreferencesResults)
        datesList = loadListFromSharedPreferences(sharedPreferencesDates)

        val recyclerView = findViewById(R.id.last_results_recyclerView) as RecyclerView

        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val results = ArrayList<Result>()

        for(i in 0 until massesList.size) {
            results.add(Result(massesList[i], heightsList[i], resultsList[i], datesList[i]))
        }

        val adapter = MyAdapter(results)

        recyclerView.adapter = adapter
    }

    private fun loadListFromSharedPreferences(dataName: String): java.util.ArrayList<String> {
        val string = lastResultsPrefs.getString(dataName, "")
        val stringArray = string.split(",")
        val items = java.util.ArrayList<String>()
        for(item in stringArray)
            items.add(item)

        return items
    }
}
