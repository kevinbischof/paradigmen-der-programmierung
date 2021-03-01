import kotlin.random.Random

var numberIndex = 0
var sequence = 0

val MODE_RANDOM = 0
val MODE_SEQUENCE = 1
val MODE_DEFINED_NUMBERS = 2

val modus = MODE_RANDOM

val strategy: NumberProviderStrategy = NegativeNumberFilter(DefinedNumbersStrategy())

interface NumberProviderStrategy {
    fun getNextNumber(): Int
}

class NegativeNumberFilter(val numberProvider: NumberProviderStrategy) : NumberProviderStrategy {
    override fun getNextNumber(): Int {
        while (true) {
            val zahl = numberProvider.getNextNumber()
            if (zahl >= 0) return zahl
        }
    }
}

class RandomNumberStrategy : NumberProviderStrategy {
    override fun getNextNumber(): Int = Random.nextInt(-200, 200)
}

class SequenceStrategy : NumberProviderStrategy {

    private var sequence = 0

    override fun getNextNumber(): Int {
        sequence += 3
        return sequence
    }
}

class DefinedNumbersStrategy : NumberProviderStrategy {

    val numbers = listOf(-24, 12, -41, 34, -31, 44, 9, -2, 2, 4, 11)
    var index = 0

    override fun getNextNumber(): Int = numbers[index++ % numbers.size]
}

fun addition() {
    println("Berechne das Ergebnis der Addition")

    val a = strategy.getNextNumber()
    val b = strategy.getNextNumber()
    val ergebnis = a + b

    print("$a + $b = ")

    println(handleAntwort(ergebnis))
}

fun subtraktion() {
    println("Berechne das Ergebnis der Subtraktion")

    val a = strategy.getNextNumber()
    val b = strategy.getNextNumber()
    val ergebnis = a - b

    print("$a - $b = ")

    println(handleAntwort(ergebnis))
}

fun handleAntwort(ergebnis: Number): String {
    val antwort = readLine()?.toIntOrNull()
    return if (antwort == ergebnis) "Super" else "Falsch"
}

fun main() {
    addition()
}