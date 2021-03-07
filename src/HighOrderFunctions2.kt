fun main() {
    fun <A, B> map(numbers: List<A>, transform: (A) -> B): List<B> {
        val tmp = mutableListOf<B>()

        for (n in numbers) {
            val newValue = transform(n)
            tmp.add(newValue)
        }

        return tmp.toList()
    }

    val numbers = listOf(1, 2, 3, 4, 100, 50, 70, 80)

    val incrementBy5: (Int) -> Int = { it + 5 }
    val incrementedBy5 = map(numbers, incrementBy5)

    val limitBy20and50: (Int) -> Int = {
        when {
            it < 20 -> 20
            it > 50 -> 50
            else -> it
        }
    }

    // transform: (T) -> R
    val limitedBy20and50 = map(numbers, limitBy20and50)

    val incrementBy20Percent: (Int) -> Int = { (it * 1.2).toInt() }
    val incrementedBy20Percent = map(numbers, incrementBy20Percent)

    val ones = map(numbers) { 1 }
    val mulBy5 = map(numbers) { it * 5 }

    // Liste von Strings
    val strings = listOf("Apfel", "Banane", "Birne")
    val stringsUpperCased = map(strings) { it.toUpperCase() }

    val numberOfLetters = map(strings) { it.length }

    // predicate: (T) -> Boolean
    val z = strings.filter { it.startsWith("B") }

    // action: (T) -> Unit
    strings.forEach { println(it) }
    strings.forEach(::println)

    // reduce reduziert alle Werte der Liste auf ein Int. Das nennt man Katamorphismus.
    val sum = numbers.reduce { x, y -> x + y }
    val sum2 = numbers.sum()

    println(sum)
    println(sum2)

    val mul: (Int, Int) -> Int = { x, y -> x * y }
    val prod = numbers.reduce(mul)

    println(prod)

    val max = numbers.reduce { x, y -> if (x > y) x else y }
    val max2 = numbers.maxOrNull()

    println(max)
    println(max2)

    val concat = strings.reduce { a, b -> "$a, $b" }
    val concat2 = strings.joinToString(", ")
    println(concat)
    println(concat2)

    // Berechnung Zeichenlänge aller Strings der Liste
    // <T, R> Iterable<T>.fold(initial: R, operation: (acc: R, T) -> R): R
    // fold: reduziert eine Liste von A auf ein B mit einer Funktion von A und B -> B
    val numberOfLetters2 = strings.fold(0) { acc, string -> acc + string.length }
    println(numberOfLetters2)

    // Alternative
    val numberOfLetters3 = strings.map { it.length }.sum()
    println(numberOfLetters3)

    // Weiteres Beispiel zu fold()
    val anyStringStartsWithB = strings.fold(false) { acc, string -> acc || string.startsWith("B") }
    println(anyStringStartsWithB)

    // Eigene any Funktion, die generisch ist über A
    fun <A> any(list: List<A>, p: (A) -> Boolean) =
        list.fold(false) { acc, a -> acc || p(a) }

    val anyStringStartsWithB2 = any(strings) { it.startsWith("B") }
    println(anyStringStartsWithB2)
    // predicate: (T) -> Boolean
    val anyStringStartsWithB3 = strings.any() { it.startsWith("B") }
    println(anyStringStartsWithB3)

    // Das Prädikat muss immer zutreffen
    fun <A> all(list: List<A>, p: (A) -> Boolean) =
        list.fold(true) { acc, a -> acc && p(a) }

    val allStringStartsWithB = all(strings) { it.startsWith("B") }
    println(allStringStartsWithB)

    val allMinLength4 = all(strings) { it.length > 4 }
    println(allMinLength4)

    val allMinLength5 = all(strings) { it.length > 5 }
    println(allMinLength5)

    // all in der Standardbibliothek
    val allMinLength6 = strings.all { it.length > 4 }
    println(allMinLength6)

    data class Product(
        val productName: String, val category: String, val price: Double
    )

    val products = listOf(
        Product("Kekse", "Lebensmittel", 1.99),
        Product("Nudeln", "Lebensmittel", 0.99),
        Product("Dosensuppe", "Lebensmittel", 2.39),
        Product("Süddeutsche Zeitung", "Zeitschriften", 1.40),
        Product("Der Spiegel", "Zeitschriften", 5.50),
        Product("Wischlappen", "Hygiene", 1.99),
        Product("Seife", "Hygiene", 1.49),
        Product("Shampoo", "Hygiene", 2.49),
    )

    // Liste mit Namen der Lebensmittel, die weniger als 2 Euro kosten:
    products
        .filter { it.category == "Lebensmittel" }
        .filter { it.price < 2 }
        .map { it.productName }
        .forEach(::println)

    // Liste aller Lebensmittel, die auch nach einem Rabatt von 10% noch mehr als 1.80 Euro kosten:

    products
        .filter { it.category == "Lebensmittel" }
        .map { it.copy(price = it.price * 0.9) }
        .filter { it.price > 1.80 }
        .forEach(::println)
}
