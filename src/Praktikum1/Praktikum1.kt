package Praktikum1

import kotlin.math.roundToInt
import kotlin.random.Random

interface Sensor {
    fun getTemperature(): Double
}

interface HeatingStrategy {
    fun needsHeating(last10measurements: List<Double>, desireTemp: Double): Boolean
}

// strategies
class InstantHeatingStrategy : HeatingStrategy {
    override fun needsHeating(last10measurements: List<Double>, desireTemp: Double): Boolean {
        if (last10measurements.last() < desireTemp) {
            println("InstantHeatingStrategy")
            return true
        }
        return false
    }

}

class SensibleHeatingStrategy : HeatingStrategy {
    override fun needsHeating(last10measurements: List<Double>, desireTemp: Double): Boolean {
        for (measurement in last10measurements) {
            if (measurement < desireTemp) {
                println("SensibleHeatingStrategy")
                return true
            }
        }
        return false
    }
}

class AverageHeatingStrategy : HeatingStrategy {
    override fun needsHeating(last10measurements: List<Double>, desireTemp: Double): Boolean {
        if (last10measurements.average() < desireTemp) {
            println("AverageHeatingStrategy")
            return true
        }
        return false
    }
}

class AnyHeatingStrategy(val heatingStrategies: List<HeatingStrategy>) : HeatingStrategy {
    override fun needsHeating(last10measurements: List<Double>, desireTemp: Double): Boolean {
        for (strategy in heatingStrategies) {
            println("Check strategy ${strategy.toString()}")
            if (strategy.needsHeating(last10measurements, desireTemp)) {
                return true
            }
        }
        return false
    }
}

class AllHeatingStrategy(val heatingStrategies: List<HeatingStrategy>) : HeatingStrategy {
    var count = 0
    override fun needsHeating(last10measurements: List<Double>, desireTemp: Double): Boolean {
        for (strategy in heatingStrategies) {
            if (strategy.needsHeating(last10measurements, desireTemp)) {
                count++
            }
        }
        if (count == 3) {
            count = 0
            println("Alle Strategien erfüllt.")
            return true
        }
        return false
    }
}

// end of strategies

// observer schnittstelle
interface TemperatureObserver {
    fun update(tmp: Double)
}

class Thermometer(private val sensor: Sensor) {
    fun measure(times: Int) {
        repeat(times) {
            val tmp = sensor.getTemperature()
            notfiyChange(tmp)
        }
    }

    private val observer = mutableListOf<TemperatureObserver>()

    fun register(obs: TemperatureObserver) = observer.add(obs)

    fun unregister(obs: TemperatureObserver) = observer.remove(obs)

    fun notfiyChange(tmp: Double) {
        for (obs in observer) {
            obs.update(tmp)
        }
    }
}

// observers
class TemperatureAlert(private val threshold: Double) : TemperatureObserver {
    override fun update(tmp: Double) {
        if (tmp > threshold) println("Ganz schön heiß (${tmp.roundToInt()}°C)")
    }
}

class WeatherReport() : TemperatureObserver {
    private val list = mutableListOf<Double>()
    override fun update(tmp: Double) {
        if (list.size == 100) {
            println("Weather Report. Last 100 measurements:")
            println(list)
            list.clear()
        }
        list.add(tmp)
    }
}

class HeatingSystem(val heatingStrategy: HeatingStrategy, val desireTemp: Double) : TemperatureObserver {
    private val list = mutableListOf<Double>()
    override fun update(tmp: Double) {
        if (list.size == 10) {
            val onOrOff = heatingStrategy.needsHeating(list, desireTemp)
            when (onOrOff) {
                true -> println("Heizung an")
                false -> println("Heizung aus")
            }
            list.clear()
        }
        list.add(tmp)
    }
}

// end of observers

// Decorators
class RandomSensor(private val min: Double, private val max: Double) : Sensor {
    override fun getTemperature(): Double = Random.nextDouble(min, max)
}

class UpDownSensor() : Sensor {
    var tmp: Double = 0.0
    // 1 => sinkend
    // 2 => gleichbleibend
    // 3 => steigend
    val state: Int = Random.nextInt(1, 4)
    override fun getTemperature(): Double {
        if (state == 1) {
            tmp += -1.00
        } else if (state == 3) {
            tmp += 1.00
        }
        return tmp
    }
}

class SensorLogger(private val sensor: Sensor) : Sensor {
    override fun getTemperature(): Double {
        val tmp = sensor.getTemperature()
        println(tmp)
        return tmp
    }
}

class RoundValues(private val sensor: Sensor) : Sensor {
    override fun getTemperature(): Double = sensor.getTemperature().roundToInt().toDouble()
}

class SensorLimits(private val min: Double, private val max: Double, private val sensor: Sensor) : Sensor {
    override fun getTemperature(): Double {
        val tmp = sensor.getTemperature()
        return when {
            tmp < min -> min
            tmp > max -> max
            else -> tmp
        }
    }
}
// end of decorators

fun main() {

//    val randomSensor = RandomSensor(2.0, 8.0)
//    repeat(3) {
//        println(randomSensor.getTemperature())
//    }

//    val randomSensor: Sensor = SensorLogger(RoundValues(RandomSensor(2.0, 8.0)))
//    randomSensor.getTemperature()
//    randomSensor.getTemperature()
//    randomSensor.getTemperature()
//    randomSensor.getTemperature()

//    val upDownSensor = SensorLogger(UpDownSensor())
//    upDownSensor.getTemperature()
//    upDownSensor.getTemperature()
//    upDownSensor.getTemperature()
//    upDownSensor.getTemperature()
//    upDownSensor.getTemperature()
//    upDownSensor.getTemperature()
//    upDownSensor.getTemperature()

//    val t1 = Thermometer(SensorLogger(SensorLimits(2.0, 4.0, UpDownSensor())))
//    val thermometer = Thermometer(SensorLogger(SensorLimits(-20.0, 40.0, RandomSensor(-100.0, 100.0))))
//    val thermometer = Thermometer(SensorLogger(RandomSensor(10.0, 50.0)))
    val thermometer = Thermometer(RandomSensor(10.0, 50.0))
    val sensibleHeatingStrategy = SensibleHeatingStrategy()
    val instantHeatingStrategy = InstantHeatingStrategy()
    val averageHeatingStrategy = AverageHeatingStrategy()
//    val alertObserver = TemperatureAlert(30.0)
//    val alertObserver = HeatingSystem(strategy, 17.0)
    val heatingStrategyList = listOf<HeatingStrategy>(
            sensibleHeatingStrategy, instantHeatingStrategy, averageHeatingStrategy
    )
    val anyHeatingStrategy = AnyHeatingStrategy(heatingStrategyList)
    val allHeatingStrategy = AllHeatingStrategy(heatingStrategyList)

//    val alertObserver = HeatingSystem(sensibleHeatingStrategy, 13.0)
//    val alertObserver = HeatingSystem(instantHeatingStrategy, 20.0)
//    val alertObserver = HeatingSystem(averageHeatingStrategy, 25.0)
//    val alertObserver = HeatingSystem(anyHeatingStrategy, 15.0)
    val alertObserver = HeatingSystem(allHeatingStrategy, 30.0)

//    val weatherReport = WeatherReport()
    thermometer.register(alertObserver)
//    thermometer.register(weatherReport)
    thermometer.measure(11)


}