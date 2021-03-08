fun quad(x: Int): Int = x * x

fun incr(x: Int): Int = x + 1

// 1. Funktion: f: (A) -> B
// 2. Funktion: g: (B) -> C
// Ergebnis: (A) -> C
fun <A, B, C>compose(f: (A) -> B, g: (B) -> C): (A) -> C = { a -> g(f(a)) }

infix fun <A, B, C>((A) -> B).andThen(that: (B) -> C): (A) -> C = compose(this, that)

fun main() {
    println(quad(incr(1)))

    // val f = compose(::incr, ::quad) // (Int) -> Int
    // val f = compose(compose(::incr, ::quad), ::println) // (Int) -> Unit
    val f = ::incr andThen ::quad andThen ::println // (Int) -> Unit
    val g = ::incr andThen ::quad andThen ::listOf andThen { it.toString() } andThen ::println // (Int) -> Unit
    f(1)
    g(1)

    // Vorteile von Funktionskomposition am Beispiel von map
    listOf(1,2,3)
            .map(::incr andThen ::quad) // (Int) -> T

    listOf(1,2,3)
            .map(f) // (Int) -> T

    // (a -> b) -> List a -> List b
    // configuration first, data second
    fun <A, B> map(f: (A) -> B): (List<A>) -> List<B> = { list -> list.map(f) }

    val mappings = map(::incr) andThen map(::quad)
    val mappings2 = map(::incr andThen ::quad)
    mappings(listOf(1,2,3))
    mappings2(listOf(1,2,3))

    // Vorteile von Funktionskomposition am Beispiel von fold

    // fold Definition in PureScript: (b -> a -> b) -> b -> List a -> b
    val sum = listOf(1,2,3).fold(0) { acc, n -> acc + n }
    println("sum direkt mit fold: $sum")
    fun <A, B> fold(f: (B, A) -> B): (B) -> (List<A>) -> B = { initial -> { list -> list.fold(initial, f) } }
    // add als freie top level funktion
    val add: (Int, Int) -> Int = { x, y -> x + y }
    val sum2 = fold(add)(0)
    sum2(listOf(1,2,3))
    sum2(listOf(1,2,3))
    sum2(listOf(1,2,3))
    sum2(listOf(1,2,3))

    val mul: (Int, Int) -> Int = { x, y -> x * y }
    val prodF = fold(mul)(1)

}