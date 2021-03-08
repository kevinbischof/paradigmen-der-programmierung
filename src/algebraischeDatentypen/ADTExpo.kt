package algebraischeDatentypen

fun main() {
    /*
    Remember:
    Unit -> 1
    Boolean -> 2

    Beispiel 1:
    a^b = b -> a
    1^2 = 2 -> 1
    Kann überführt werden in:
       = Boolean -> Unit
     */

    fun a(boolean: Boolean): Unit = Unit
    /*
    Wieviele Implementierungen dieser Funktion existieren?
    Unit kann nur einmal zurückgegeben werden. Daher existiert nur eine Implementierung.
    -> 1, weil 1^2 = 1
     */

    /*
    Beispiel 2:
    2^1 = 1 -> 2
    Kann überführt werden in:
        = Unit -> Boolean
     */

    fun b(unit: Unit): Boolean = true
    fun c(unit: Unit): Boolean = false
    /*
    Wieviele Implementierungen dieser Funktion existieren?
    Antwort: 2, da:
    2^1 = 2
     */

    /*
    Beispiel 3:
    Boolean^Mood
    Remember:
    Mood = happy, sad, grumpy

    Boolean^Mood => Mood -> Boolean
     */

    fun d(mood: Mood): Boolean = when (mood) {
        Mood.happy -> true // true // true
        Mood.sad -> false // true // false
        Mood.grumpy -> true // false // true...
    }
    /*
    Wieviele Implementierungen dieser Funktion existieren?
    Antwort: 8, da:
    Boolean^Mood
     = 2^3 = 8
     */

    /*
    Potenzgesetz 1: (a^b)^c = a^(b*c)

    Überführung in Code:
    1. Überführung der Potenzen in umgedrehte Pfeile
        (a^b)^c = a^(b*c)
        (a <- b) <- c = a <- (b * c)
    2. Flippen der Pfeile, damit es besser gelesen werden kann
        c -> (b -> a) = (b * c) -> a
    3. In Typen überführen
        (C) -> (B) -> A = (B, C) -> A
    4. Umsetzung in Code
     */

//    fun <A, B, C> to(f: (C) -> (B) -> A): (B, C) -> A = { b, c -> f(c)(b) }
//    fun <A, B, C> from(f: (B, C) -> A): (C) -> (B) -> A = { c -> { b -> f(b, c) } }

//    fun <A, B, C> to(f: (C) -> (B) -> A): (C, B) -> A = { c, b -> f(c)(b) }
//    fun <A, B, C> from(f: (C, B) -> A): (C) -> (B) -> A = { c -> { b -> f(c, b) } }

    fun <A, B, C> uncurry(f: (C) -> (B) -> A): (C, B) -> A = { c, b -> f(c)(b) }
    fun <A, B, C> curry(f: (C, B) -> A): (C) -> (B) -> A = { c -> { b -> f(c, b) } }

    /*
    from entspricht dem currying
    Currying transformiert eine beliebige Funktion mit der Struktur (C, B) -> A in eine Funktion mit
    (C) -> (B) -> A übersetzt.
    Curry kann eine Funktion mit mehreren Argumenten in eine Sequenz mit mehreren Argumenten überführen.
    Potenzgesetzt ist der mathematische Beweis, dass curry eine mathematische Fundierung hat.
     */
    fun add(x: Int, y: Int): Int = x + y

    val curriedAdd = curry(::add)
    val normalAdd = uncurry(curriedAdd)

    /*
    Potenzgesetz 2: a^1 = a

    a^1 = a
    Überführung in Code:

    1. Überführung der Potenzen in umgedrehte Pfeile
    a <- 1 = a
    2. Flippen der Pfeile, damit es besser gelesen werden kann
    1 -> a = a
    3. In Typen überführen
    Unit -> A = A
    4. Umsetzung in Code

     */
    // (Unit) ist das Gleiche wie leere Klammern ()
    fun <A> to(f: (Unit) -> A): A = f(Unit)
    fun <A> from(a: A): (Unit) -> A = { a }

    // Funktionskörper wird direkt ausgewertet
    val sum = 1 + 5

    // Ausdruck wird nicht direkt ausgewertet
    val f = { 1 + 5 }

    // Erst dann, wenn die Funktion aufgerufen wird
    f()
    // oder
    val g = { 1 + 5 }()

    /*
    eager engl. für fleißg, sofort
    lazy engl. für faul, träge
     */

//    fun <A> ifThenElse(condition: Boolean, thenBranch: A, elseBranch: A): A =
//            if (condition) thenBranch else elseBranch
    // funktion dasselbe wie:
    val x = if (2 != 5) 2 else 5

    // Beispielanwendung zu ifThenElse
    val isAdmin = false
//    ifThenElse(isAdmin, println("delete database!"), println("no admin, can't delete!"))
    /*
    Beide Branches werden ausgeführt. Egal, welcher Branch zurückgegeben wird.
    Fix: println's in Lambdas übersetzen. Dafür muss ifThenElse angepasst werden.
     */

    fun <A> ifThenElse(condition: Boolean, thenBranch: () -> A, elseBranch: () -> A): A =
            if (condition) thenBranch() else elseBranch()

    ifThenElse(isAdmin, { println("delete database!") }, { println("no admin, can't delete!") })

    /*
    Potenzgesetz 3: a^(b + c) = a^b * a^c

    a^(b + c) = a^b * a^c
    Überführung in Code:

    1. Überführung der Potenzen in umgedrehte Pfeile
    a <- (b + c) = (a <- b) * (a <- c)
    2. Flippen der Pfeile, damit es besser gelesen werden kann
    (b + c) -> a = (b -> a) * (c -> a)
    3. In Typen überführen
    Either<B, C> -> A = Pair<(B) -> A, (C) -> A>
    4. Umsetzung in Code

     */

    fun <A, B, C> to(f: (Either<B, C>) -> A): Pair<(B) -> A, (C) -> A> = Pair(
            { b -> f(Either.Left(b)) },
            { c -> f(Either.Right(c)) }
    )

    // inverse der Funktion

    fun <A, B, C> from(pair: Pair<(B) -> A, (C) -> A>): (Either<B, C>) -> A = { either ->
        // pattern matchen
        when (either) {
            is Either.Left -> pair.first(either.value)
            is Either.Right -> pair.second(either.value)
        }
    }

    /*
    Alternatives Potenzgesetz 3: (a * b)^c = a^c * b^c

    (a * b)^c = a^c * b^c
    Überführung in Code:

    1. Überführung der Potenzen in umgedrehte Pfeile
    (a * b) <- c = (a <- c) * (b <- c)
    2. Flippen der Pfeile, damit es besser gelesen werden kann
    c -> (a * b) = (c -> a) * (c -> b)
    3. In Typen überführen
    C -> (A, B) = Pair<(C) -> A, (C) -> B>
    Eine Funktion in ein Pair ist das gleiche wie ein Pair von dem jeweiligen Argument in einen der Cases.
    Wenn man einen weiteren Case hinzufügt, muss man im Pair auch einen weiteren Case hinzufügen
    C -> (A, B, D) = Pair<(C) -> A, (C) -> B, (C) -> D>
    4. Umsetzung in Code

     */

    fun <A, B, C> to(f: (C) -> Pair<A, B>): Pair<(C) -> A, (C) -> B> = Pair(
            { c -> f(c).first },
            { c -> f(c).second }
    )

    // inverse der Funktion

    fun <A,B,C> from(p: Pair<(C) -> A, (C) -> B>): (C) -> Pair<A, B> = { c ->
        Pair(p.first(c), p.second(c))
    }

    /*
    Invalides Potenzgesetz 1: a^(b * c) != a^b * a^c

    a^(b * c) != a^b * a^c
    Überführung in Code:

    1. Überführung der Potenzen in umgedrehte Pfeile
    a <- (b * c) != (a <- b) * (a <- c)
    2. Flippen der Pfeile, damit es besser gelesen werden kann
    (b * c) -> a != (b -> a) * (c -> a)
    3. In Typen überführen
    (B, C) -> A = Pair<(B) -> A, (C) -> A)>
    Ich benötige 2 Werte, um einen Wert zu bauen, dann kann ich nicht nur über einen der beiden Werte auf den Endwert
    kommen. Beispiel:
    A = 8, B = 5, C = 3
    B + C = A => 5 + 3 = 8
    B != A => 5 != 8
    C != A => 3 != 8
    4. Umsetzung in Code

     */

    // Im Code zeigt sich, dass dies nicht umgesetzt werden kann
    fun <A, B, C> to(f: (B, C) -> A): Pair<(B) -> A, (C) -> A> = Pair(
            { b -> f(b, TODO("can't provide value of type C"))},
            { c -> f(TODO("can't provide value of type B"), c)}
    )

    fun <A,B,C> from2(pair: Pair<(B) -> A, (C) -> A>): (B, C) -> A = { b, c ->
        pair.first(b) //pair.second(c) ???
    }

    // Überprüfung der to Funktion
    val introduce = to<String, String, Int> { name, age ->
        "hi, ich bin $name und bin $age jahre alt"
    }

    introduce.first("Alex")
    // error: Exception in thread "main" kotlin.NotImplementedError: An operation is not implemented: can't provide value of type C

    introduce.second(30)
    // error: Exception in thread "main" kotlin.NotImplementedError: An operation is not implemented: can't provide value of type C

}