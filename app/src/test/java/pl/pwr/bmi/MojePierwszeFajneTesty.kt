package pl.pwr.bmi

import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import org.junit.Assert
import pl.pwr.bmi.logic.BmiForKgCm

class MojePierwszeFajneTesty : StringSpec() {
    init {
        "for valid mass and height return bmi"{
            val bmi = BmiForKgCm(65.0, 170.0)
            bmi.countBmi() shouldBeAround 22.491
        }

        "for mass or height lower than expected should throw exception"{
            val bmi = BmiForKgCm(0.0, 0.0)
            shouldThrow<IllegalArgumentException>{
                bmi.countBmi()
            }
        }
    }

    infix fun Double.shouldBeAround(value : Double) {
        Assert.assertEquals(value, this,0.001)
    }
}