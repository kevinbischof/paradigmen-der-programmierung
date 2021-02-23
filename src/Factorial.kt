// Berechnung Fakultät von n!
// Beispiel: 5! = 1 * 2 * 3 * 4 * 5

fun factorialImperative(n: Int): Int {
    var fac = 1
    var i = 1

    while (i <= n) {
        // multiplizieren
        fac *= i
        // inkrementieren
        i++
    }

    return fac
}

fun factorialDeclarative(n: Int): Int {
    // Keine Variablen und Schleifen verwenden
    // Lösung: Rekursion
    // Fall 1: factorial 0 => 1
    // Weitere Fälle: factorial n => n * factorial (n - 1)

    return if (n == 0) 1 else n * factorialDeclarative(n - 1)
}

fun main() {
    println("Factorial Imperative: " + factorialImperative(5))
    println("Factorial Deklarative: " + factorialDeclarative(5))
}