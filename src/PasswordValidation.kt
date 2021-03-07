fun main() {
    // Ziel: range(1, 8) and minDigits(1)
    infix fun ((String) -> Boolean).and(that: (String) -> Boolean): (String) -> Boolean = { password ->
        // simplifiziertere Version von combine
        this(password) && that(password)
    }

    // Primitive: String -> Boolean
    val noValidation: (String) -> Boolean = { password -> true }

    // Currying: Primitive wird um ein Int erweitert und wird zu einem Currying
    val minLength: (Int) -> (String) -> Boolean = { min ->
        {password -> password.length >= min }
    }

    val maxLength: (Int) -> (String) -> Boolean = { max ->
        {password -> password.length <= max }
    }

    // Nicht 100%ig gecarried
    val range: (Int, Int) -> (String) -> Boolean = { min, max ->
//        {password -> (min..max).contains(password.length) }
        minLength(min) and maxLength(max) // (String) -> Boolean && (String) -> Boolean
    }

    val minDigits: (Int) -> (String) -> Boolean = { minD ->
        { password -> password.count { it.isDigit() } >= minD }
    }

    val minUpperCased: (Int) -> (String) -> Boolean = { minU ->
        { password -> password.count { it.isUpperCase()} >= minU }
    }

    val minSymbols: (Int) -> (String) -> Boolean = { minS ->
        // Capturing einer Konstante innerhalb des Closure
        val symbols = listOf("$", "!", "§", "%", "&", "/", "=");
        // { password -> password.count { symbols.contains(it) } >= minS }
        // Ether reduktion. Argumentnamen aus Lambdas entfernen, Substitution der Namen durch Funktionsaufrufe
        { password -> password.count(symbols::contains) >= minS }
    }

    fun combine(
            first: (String) -> Boolean,
            second: (String) -> Boolean
    ): (String) -> Boolean = { password ->
        val lhs = first(password)
        val rhs = second(password)
        val combined = lhs && rhs
        combined
    }



    // String -> Boolean
    // val weakPolicy = combine(minUpperCased(1), combine(minSymbols(0), combine(range(1, 8), minDigits(1)))) // Unschön
    // Besser: Infix Functions. Funktion steht zwischen zwei Argumenten
    val weakPolicy = minUpperCased(1) and minSymbols(0) and range(1, 8) and minDigits(1)
    val mediumPolicy = minLength(16)
    val strongPolicy = minLength(20) and minDigits(3) and minSymbols(3) and minUpperCased(1)

    println(noValidation("test123"))
    println(noValidation("abcBc5"))
    println(noValidation(""))

    println(weakPolicy("test123"))
    println(weakPolicy("abcBc5"))
    println(weakPolicy(""))

    println(strongPolicy("abcdefghijklmnop!§%TWDG123456=\$"))
    println(strongPolicy("abc"))
    println(strongPolicy(""))

    val add: (Int, Int) -> Int = { x, y -> x + y}
    val sum = listOf(5,1,5,4,3,2).fold(0, add)
    val sum2 = listOf(5,1,5,4,3,2).reduce(add)
    println("sum mit fold: $sum")
    println("sum mit reduce: $sum2")

    val rules: List<(String) -> Boolean> = listOf(
            range(1, 8), minDigits(1), minSymbols(0), minUpperCased(1)
    )
    val weakPolicy2 = rules.reduce(::combine)
    val weakPolicy3 = rules.fold(noValidation, ::combine)
}