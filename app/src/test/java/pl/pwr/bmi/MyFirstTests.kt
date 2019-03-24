package pl.pwr.bmi

import org.junit.Assert
import org.junit.Test
import pl.pwr.bmi.logic.BmiForKgCm

class MyFirstTests {

    @Test
    fun for_valid_data_should_return_bmi() {
        val bmi = BmiForKgCm(65.0, 170.0)
        Assert.assertEquals(22.491, bmi.countBmi(), 0.001)
    }

    @Test
    fun for_me_data_should_return_bmi() {
        val bmi = BmiForKgCm(75.0, 178.0)
        Assert.assertEquals(23.671, bmi.countBmi(), 0.001)
    }
}