package composition

import andThen
import compose

class Pizza(var price: Double, val toppings: List<String>)

// (a -> b) -> List a -> List b
// configuration first, data second
fun add(price: Double, topping: String): (Pizza) -> Pizza = { pizza ->
    Pizza(pizza.price + price, pizza.toppings + topping)
}

fun double(p: (Pizza) -> Pizza): (Pizza) -> Pizza = p andThen p

// listOf(1,2,3).reduce(::add) // List<Int> -> Int
// List<(Pizza) -> Pizza> -> (Pizza) -> Pizza
fun multiple(p: (Pizza) -> Pizza, n: Int): (Pizza) -> Pizza =
    List(n, { p }).reduce(::compose)

fun main() {
    val margherita = Pizza(4.99, mutableListOf("Käse"))
    val schinken = add(1.99, "Schinken")
    val ananas = add(1.99, "Ananas")
    val hawaii = schinken andThen ananas

    // Ausgabe ist composition.Pizza@3feba861 sollte aber die Werte ausgeben anstatt die Referenz
    println(hawaii(margherita))

    val extraCheese = add(0.99, "Extra Käse")
    val doubleExtraCheese = double(extraCheese)
    println(doubleExtraCheese(margherita))

    val familyHawaiiOrder = multiple(hawaii, 5) andThen extraCheese
    println(familyHawaiiOrder(margherita))
}