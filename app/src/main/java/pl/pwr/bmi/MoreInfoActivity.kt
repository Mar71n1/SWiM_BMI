package pl.pwr.bmi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_more_info.*

class MoreInfoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_more_info)

        val bmiNumber = intent.getDoubleExtra("bmi_value", 0.0)

        bmi_more_info_number.setText(bmiNumber.toString())
        when {
            bmiNumber < 16.0 -> {
                bmi_more_info_image.setColorFilter(ContextCompat.getColor(applicationContext, R.color.bmi_main_color_severely_underweight))
                bmi_more_info_text.setText(R.string.bmi_more_info_severely_underweight_info)
            }
            bmiNumber < 18.5 -> {
                bmi_more_info_image.setColorFilter(ContextCompat.getColor(applicationContext, R.color.bmi_main_color_underweight))
                bmi_more_info_text.setText(R.string.bmi_more_info_underweight_info)
            }
            bmiNumber < 25.0 -> {
                bmi_more_info_image.setColorFilter(ContextCompat.getColor(applicationContext, R.color.bmi_main_color_normal))
                bmi_more_info_text.setText(R.string.bmi_more_info_normal_info)
            }
            bmiNumber < 30.0 -> {
                bmi_more_info_image.setColorFilter(ContextCompat.getColor(applicationContext, R.color.bmi_main_color_overweight))
                bmi_more_info_text.setText(R.string.bmi_more_info_overweight_info)
            }
            else -> {
                bmi_more_info_image.setColorFilter(ContextCompat.getColor(applicationContext, R.color.bmi_main_color_obesity))
                bmi_more_info_text.setText(R.string.bmi_more_info_obesity_info)
            }
        }
    }
}
