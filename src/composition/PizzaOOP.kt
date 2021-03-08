package composition

class PizzaOOP(var price: Double, val toppings: MutableList<String>) {
    fun schinken() {
        price += 2.99
        toppings.add("Schinken")
    }

    fun ananas() {
        price += 0.99
        toppings.add("Ananas")
    }

    fun hawaii() {
        schinken()
        ananas()
    }

    override fun toString(): String {
        return "$price, $toppings"
    }
}

fun main() {
    val pizza = PizzaOOP(4.99, mutableListOf("Käse"))
    println(pizza)

    //Seiteneffekt wird ausgeführt
    pizza.schinken()
    println(pizza)

    //Seiteneffekt wird ausgeführt
    pizza.ananas()
    println(pizza)

    //Seiteneffekt wird ausgeführt
    pizza.hawaii()
    println(pizza)

}