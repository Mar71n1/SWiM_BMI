package pl.pwr.bmi.logic

//mass in lbs
//height in fts
class BmiForLbFt(override var mass: Double = 0.0, override var height: Double = 0.0): Bmi {

    //override fun countBmi() = (mass * 703) / (height * height * 144)

    override fun countBmi(): Double {
        require(mass > 40 && height > 1) { "invalid data" }
        return (mass * 703) / (height * height * 144)
    }
}