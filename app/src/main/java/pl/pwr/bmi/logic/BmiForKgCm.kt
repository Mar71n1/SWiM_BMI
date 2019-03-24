package pl.pwr.bmi.logic

//mass in kgs
//height in cms
class BmiForKgCm(override var mass: Double = 0.0, override var height: Double = 0.0): Bmi {

    //override fun countBmi() = (mass * 10000.0) / (height * height)

    override fun countBmi(): Double {
        require(mass > 20 && height > 50) { "invalid data" }
         return (mass * 10000.0) / (height * height)
    }
}