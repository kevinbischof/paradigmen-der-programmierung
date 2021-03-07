fun main() {

    // "Shadowing" von freien Variablen

    // x und y sind freie Variablen im Closure
    val x = 5
    val y = 3

    // Freie Variablen können im Lambda verwendet werden
    val add: () -> Int = { x + y }
    println(add()) // Returnwert: 8

    /*
    Durch Übergabe der Parameter, werden die Variablen x und y "geshadowed".
    Die lokalen Variablen verdecken die äußeren Variablen. Das nennt man Shadowing.
    Die Variablen werden gelb markiert. Hovert man über die Variablen, zeigt dies IntelliJ an
     */

    val add2: (Int, Int) -> Int = { x, y -> x + y }
    println(add2(2, 3))

    // "Capturing" von freien, veränderlichen Variablen

    var lunchTime = 12
    val isLunchTime: (Int) -> Boolean = { hour -> hour == lunchTime}
    println(isLunchTime(12)) // Returnwert: true
    lunchTime += 1
    println(isLunchTime(12)) // Returnwert: false
}