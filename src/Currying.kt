fun main() {
//    val add: (Int, Int) -> Int = { x, y -> x + y }
    val add: (Int) -> (Int) -> Int = { x -> { y -> x + y }}
    val add3: (Int) -> Int = add(3)

//    println("add(5,3) ${add(5, 3)}")
    println("add(5,3) ${add(5)(3)}")
    println("add3(10) ${add3(10)}")

    val mul: (Double) -> (Double) -> Double = { x -> { y -> x * y }}
    val mwst = mul(0.9)
    val withMwst = mul (1.19)

    println("mwst(100.0) ${mwst(100.0)}")
    println("withMwst(100.0) ${withMwst (100.0)}")
}