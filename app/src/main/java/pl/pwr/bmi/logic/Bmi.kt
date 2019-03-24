package pl.pwr.bmi.logic

interface Bmi {
    var mass: Double
    var height: Double

    fun countBmi() : Double
}