import java.io.File
import java.util.*
import kotlin.math.pow
import kotlin.test.assertEquals

fun main() {
    // Pow-Funktion ist deterministisch, weil sie für denselben Input immer denselben Output liefert
    2.0.pow(5)

    // Quad-Funktion ist pur, weil sie immer für dasselbe x dasselbe Ergebnis liefert.
    // Sie ist also deterministisch
    fun quad(x: Int): Int = x * x

    // Tests
    assertEquals(25, quad(5))
    assertEquals(1, quad(1))
    assertEquals(4, quad(2))

    /*
    Die Ausgabe durch println wird auf die Konsole zugegriffen. Dies stellt einen Seiteneffekt dar.
    Die Funktion quad2 wird verlassen. Es erfolgt eine Zustandsveränderung außerhalb des Funktionskörpers.
     */
    fun quad2(x: Int): Int {
        val res = x * x
        println("Das Ergebnis ist $res")
        return res
    }

    /*
    Behebung des Seiteneffekts durch Rückgabe des Strings. Dadurch wird nicht mehr der Funktionskörper verlassen
    Der String ist dann auch testbar.
     */

    fun quad3(x: Int): Pair<Int, String> {
        val res = x * x
        val description = "Das Ergebnis ist $res"
//        return Pair(res, description)
        return res to description // to fasst die Werte als Pair zusammen. Syntax sugar.
    }

    assertEquals(25 to "Das Ergebnis ist 25", quad3(5))
    assertEquals(1 to "Das Ergebnis ist 1", quad3(1))
    assertEquals(4 to "Das Ergebnis ist 4", quad3(2))

    // Abhängigkeit zu einer globalen Variable

    fun currentAge(yearOfBirth: Int) =
        Calendar.getInstance().get(Calendar.YEAR) - yearOfBirth

    assertEquals(31, currentAge(1990))
    assertEquals(21, currentAge(2000))
    assertEquals(11, currentAge(2010))

    /*
    Durch die Verwendung der globalen Variable Calendar.YEAR erhalten wir das aktuelle Jahr.
    Daher funktionieren die Tests nur im Aktuellen Jahr. Im nächsten Jahr werden die Tests nicht mehr funktionieren.
    Dadurch ist die Funktion nicht mehr deterministisch und nicht mehr testbar.
    Calendar.getInstance().get(Calendar.YEAR) ist ein implizierter Parameter.
    Lösung: Übergabe des Referenzjahrs als Parameter
     */

    fun currentAge2(yearOfBirth: Int, referenceYear: Int = Calendar.getInstance().get(Calendar.YEAR)) =
        referenceYear - yearOfBirth

    assertEquals(30, currentAge2(1990, 2020))
    assertEquals(20, currentAge2(2000, 2020))
    assertEquals(10, currentAge2(2010, 2020))

    // Zustandsveränderung außerhalb der Funktion

    val values = arrayOf(1,2,3)

    fun first(array: Array<Int>): Int = array[0]

    //
    println(first(values)) // 1
    println(first(values)) // 1

    fun first2(array: Array<Int>): Int {
        val f = array[0]
        array.reverse()
        return f
    }

    // Seiteneffekt
    println("Seiteneffekt")
    println(first2(values)) // 1
    println(first2(values)) // 3
    println(first2(values)) // 1
    println(first2(values)) // 3
    println(first2(values)) // 1
    println(first2(values)) // 3

    /*
    Problemlösung: Arrays vermeiden. Arrays sind immer mutierbar.
    Es wird eine Liste verwendet. Listen sind nicht mutierbar.
     */

    fun first3(list: List<Int>): Int {
        val f = list[0]
        list.reversed()
        return f
    }

    val values2 = listOf(1,2,3)

    // Seiteneffekt vermieden durch list anstatt array
    println("Kein Seiteneffekt")
    println(first3(values2)) // 1
    println(first3(values2)) // 1
    println(first3(values2)) // 1
    println(first3(values2)) // 1
    println(first3(values2)) // 1
    println(first3(values2)) // 1

    // Seiteneffekte sammeln und bündeln

    fun factorialDeclarative(n: Int): Int {
        return if (n == 0) 1 else n * factorialDeclarative(n - 1)
    }

    // Seiteneffekte werden in der Methode writeToDisk gesammelt
    fun writeToDisk(filename: String, content: String) {
        val file = File(filename)
        val start = System.currentTimeMillis()
        file.writeText(content)
        val end = System.currentTimeMillis()
        println("Fertig! Das Schreiben in die Datei hat ${end - start} Millisekunden gedauert.")
    }

    val text = "Die Fakultät von 10 ist ${factorialDeclarative(10)}"

    // Ausführung der gesammelten Seiteneffekte
    writeToDisk("functions.txt", text)

}