package algebraischeDatentypen

enum class Mood {
    happy, sad, grumpy;
}

// Äquivalent von Pair ist Either. Kotlin verfügt nicht über Either. Daher wird es selbst definiert.
// Either ist die gültige Summenimplementierung
sealed class Either<out A, out B> {
    data class Left<A>(val value: A) : Either<A, Nothing>()
    data class Right<B>(val value: B) : Either<Nothing, B>()
}

sealed class Option<out A> {
    data class Some<A>(val value: A) : Option<A>()
    object None : Option<Nothing>()
}

sealed class Bool {
    object True : Bool()
    object False : Bool()
}

fun main() {
    // Pair ist ein Product, weil first AND second
//    val point = Pair(0, 1) // x und y
//    point.first // x
//    point.second // y

    /*
    Ein Pair von <Boolean, Boolean> hat 4 Kombinationen
     */
    Pair<Boolean, Boolean>(true, true)
    Pair<Boolean, Boolean>(true, false)
    Pair<Boolean, Boolean>(false, true)
    Pair<Boolean, Boolean>(false, false)

    /*
    Ein Pair von <Boolean, Unit> hat nur 2 Kombinationen
     */
    Pair<Boolean, Unit>(true, Unit)
    Pair<Boolean, Unit>(false, Unit)

    /*
    Nothing hat keine Instanzen. Nothing repräsentiert eine Value, die niemals existierte.
    Nothing kann man nicht instanziieren, da es einen privaten Constructor hat.
    Ein Pair von <Boolean, Nothing> hat 0 Kombinationen
     */
//    Pair<Boolean, Nothing>(true, ???)

    /*
    Ein Pair von <Boolean, Mood> haben 6 Kombinationen
     */
    Pair<Boolean, Mood>(true, Mood.happy)
    Pair<Boolean, Mood>(true, Mood.sad)
    Pair<Boolean, Mood>(true, Mood.grumpy)
    Pair<Boolean, Mood>(false, Mood.happy)
    Pair<Boolean, Mood>(false, Mood.sad)
    Pair<Boolean, Mood>(false, Mood.grumpy)

    /*
     Produktalgebra:

     Boolean = 2 Kombinationen (true, false)
     Boolean? = 3 Kombinationen (true, false und null)
     Unit = 1 Kombination (Unit)
     Unit? = 2 Kombinationen (Unit und null)
     Nothing = 0 Kombinationen
     Mood = 3 Kombinationen (happy, sad, grumpy)

     Pair<Boolean, Boolean> = 4 = 2 * 2
     Pair<Boolean, Unit> = 2= 2 * 1
     Pair<Unit, Unit> = 1 = 1 * 1
     Pair<Boolean, Nothing> = 0 = 2 * 0
     Pair<Boolean, Mood> = 6 = 2 * 3
     Pair<Boolean?, Unit> = 3 = (2 + 1) * 1
     Pair<Unit?, Unit> = 2 = (1 + 1) * 1
     Pair<Int, Unit> = Int * 1
     */

    // Boolean optional = 3 Kombinationen
    Pair<Boolean?, Unit>(true, Unit)
    Pair<Boolean?, Unit>(false, Unit)
    Pair<Boolean?, Unit>(null, Unit)

    // Unit optional = 2 Kombinationen
    Pair<Unit?, Unit>(Unit, Unit)
    Pair<Unit?, Unit>(null, Unit)

    /*
    Summenalgebra:

    Either<Boolean, Boolean> = 4
    Either<Boolean, Unit> = 3
    Either<Boolean, Nothing> = 2
    Either<Boolean, Mood> = 5

    Parallelität zwischen Produktalgebra Optional und Summenalgebra.
    Ein Optionaltyp ist nichts anderes als ein Summentyp.

    Option<Boolean> = 3 = 2 * 1
    -> Option<A> = A + 1
     */

    // Either<Boolean, Boolean> = 4
    val a: Either<Boolean, Boolean> = Either.Left(true)
    val b: Either<Boolean, Boolean> = Either.Left(false)
    val c: Either<Boolean, Boolean> = Either.Right(true)
    val d: Either<Boolean, Boolean> = Either.Right(false)

    // Either<Boolean, Unit> = 3
    val e: Either<Boolean, Unit> = Either.Left(true)
    val f: Either<Boolean, Unit> = Either.Left(false)
    val g: Either<Boolean, Unit> = Either.Right(Unit)

    // Either<Boolean, Nothing> = 2
    val h: Either<Boolean, Nothing> = Either.Left(true)
    val i: Either<Boolean, Nothing> = Either.Left(false)
    // val j: Either<Boolean, Nothing> = Either.Right(???) // Nicht möglich

    // Either<Boolean, Mood> = 5
    val k: Either<Boolean, Mood> = Either.Left(true)
    val l: Either<Boolean, Mood> = Either.Left(false)
    val m: Either<Boolean, Mood> = Either.Right(Mood.happy)
    val n: Either<Boolean, Mood> = Either.Right(Mood.sad)
    val o: Either<Boolean, Mood> = Either.Right(Mood.grumpy)

    /*
    Boolean kann als Either definitiert werden

     */

    val x: Option<Boolean> = Option.Some(true)
    val y: Option<Boolean> = Option.Some(false)
    val z: Option<Boolean> = Option.None

    val x1: Bool = Bool.False
    val x2: Bool = Bool.True

    // Notation für Algebraische Datentypen
    /*
    Pair<A,B>
        = (A, B)
        = A * B

    Pair<Boolean, Unit>
        = (Boolean, Unit>
        = 2 * 1
        = 2

    Either<A, B>
        = (A | B)
        = A + B

    Either<Boolean, Nothing>
        = (Boolean | Nothing)
        = Boolean + Nothing
        = 2 + 0
        = 2

        2 = 2
    Pair<Boolean, Unit> ~= Either<Boolean, Nothing>
    Ein Pair von Boolean und Unit ist isomorph zu einem Either von Boolean und Nothing
     */

    // Typen mit Algebra vereinfachen
    /*
    Vereinfachung: Typ -> Algebra -> Algebra vereinfachen -> Typ
    Either<Pair<A,B>, Pair<A,C)>>
        = Pair<A,B> + Pair<A,C>
        = (A*B) + (A*C)
        = A * (B + C)
        = Pair<A, Either<B,C>>

    Rückführung eines vereinfachten Terms: Typ -> Algebra -> Algebra ausmultiplizieren -> Typ
    Pair<Either<A,B>, Either<A,C>>
        = (A + B) * (A + C)
        = A*A + A*C + B*A + B*C
        = Either<Pair<A,B>, Either<Pair<A,C>, Either<Pair<B,A>, Pair<B,C>>>>
     */

    // Mit valider Algebra zum validen Typ
    /*
    a * 1 = a
    Pair<A, Unit> = A
    toA = Pair<A, Unit> -> A
    fromA = A -> Pair<A, Unit>
    Gleichung sagt, dass es eine Funktion geben muss, die ein Pair<A,Unit> ein A zurückgeben kann und es muss eine
    Funktion geben, die aus einem A ein Pair<A, Unit> zurückgibt.
     */

    fun <A>toA(pair: Pair<A, Unit>): A = pair.first // check
    fun <A>fromA(a:A): Pair<A, Unit> = Pair(a, Unit) // check

    // Mit invalider Algebra zum invaliden Typ

    fun <A, B> toLeft(either: Either<A, B>): A = when (either) {
        is Either.Left -> either.value // check
        is Either.Right -> error("can't produce a value of type A") // no check
    }

    /*
    Algebra von toLeft:
    Either<A, B> = A
    A + B != A
    Fix:
    A + 0 = A
    Either<A, Nothing> = A

    Hinweis: Nothing ist der "Bottom Type". Also Grundtyp von Allem.
     */

    // Fix der ersten toLeft Funktion durch Nothing
    fun <A> toLeft2(either: Either<A, Nothing>): A = when (either) {
        is Either.Left -> either.value // check
        is Either.Right -> either.value
    }

    /*
    Algebra von toLeft 3:
    Either<A, B> = A
    A + != A
    A + B = Optional + 1
    A + B = A + 1, B Element aus einer Menge von {1}
     */

    // Alternative durch Verwendung von A? und null
    fun <A, B> toLeft3(either: Either<A, B>): A? = when (either) {
        is Either.Left -> either.value // check
        is Either.Right -> null
    }

    /*
    Algebra von fromLeft:
    A = Either<A, B>
    A != A + B
    Fix:
    A = A + Nothing
    A = A + 0
    A = Either<A, Nothing>
     */

//    fun <A, B> fromLeft(a: A): Either<A, B> = Either.Left(a) // missing case

    fun <A> fromLeft(a: A): Either<A, Nothing> = Either.Left(a) // kein missing case mehr



}