fun main() {
//    // unused
//    { println("hallo") } // Unit -> Unit
//
//    // unused
//    {
//        println("hallo")
//        1 + 1
//        // Unit -> Int
//    }
//
//    // unused
//    { x: Int -> x * x} // Int -> Int
//
//    // used
//    { println("hallo Welt")}()
//
//    // used
////    val quad: (Int) -> Int = { x -> x * x}
//    val quad: (Int) -> Int = { it * it} // syntax sugar
//    val res = quad(5)
//
//    val greet: (String) -> Unit = { println("hallo, $it") }
//    greet("Alex")

//    val a = 1
//    val b = 2
//    val loop: () -> Unit = {
//        println("loop called")
//        while (true) {
//        }
//    }
//
//    val add: (Int, Int) -> Int = { x, y ->
//        println("add called")
//        x + y
//    }
//
//    val result = add(3, 4)

    // Funktionsreferenzen

    val add: (Int, Int) -> Int = {x,y -> x + y}
    val mul: (Int, Int) -> Int = {x,y -> x * y}

    // Reduzierung der Liste von Int's auf ein Int
    // Übergabe der Funktion add als Referenz an die reduce Funktion. Reduce ruft intern die add Funktion auf
    val sum = listOf(1,2,3,4,5).reduce(add)
    val prod = listOf(1,2,3,4,5).reduce(mul)
    println("sum: $sum")
    println("prod: $prod")
}