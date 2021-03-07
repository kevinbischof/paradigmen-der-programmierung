import java.io.File

fun main() {

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

    fun writeToDisk(filename: String, content: String) {
        val file = File(filename)
        file.writeText(content)
    }


    /*
    Die Funktion measure ist eine Funktion höherer Ordnung, da sie eine Funktion als Parameter akzeptiert.
    Die Funktion ist polymorph. Der Rückgabetyp wird durch den Aufruf der Funktion erst festgelegt.
     */
    fun <A> measure(f: () -> A) {
        val start = System.currentTimeMillis()
        f()
        val end = System.currentTimeMillis()

        println("Fertig! Die Ausführung der Funktion hat ${end - start} Millisekunden gedauert.")
    }

    measure { // Unit -> A (Unit)
        val text = "Die Fakultät von 10 ist ${factorialImperative(10)}"
        writeToDisk("functions.txt", text)
    }

    measure { // Unit -> A (Int)
        factorialImperative(1_000_000_000)
    }

    /*
    Math::random :
        Unit -> Double
        Unit -> A
    */
    measure {
        Math.random()
    }

    measure(Math::random)

}
