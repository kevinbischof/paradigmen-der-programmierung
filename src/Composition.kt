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
}