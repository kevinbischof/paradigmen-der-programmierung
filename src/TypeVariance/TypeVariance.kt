package TypeVariance

open class Animal

class Cat : Animal()
class Dog : Animal()

/*
out wird vor den Typparameter geschrieben, da es sich um eine Kovarianz handelt.
Durch das out wird erlaubt, dass die Typparameter durch die Vererbungsbeziehung in einer Subtypenbeziehung stehen können
 */
class PetOwner<out A>

fun main() {
    val a: Int = 5 // Int = Int

    // Any ist eine Klasse. Daher Instanziieren über den Constructor: Any()
    val b: Any = Any() // Any = Any

    // Subtyping
    val c: Any = 5 // Any ??? Int
    val d: Any = true // Any ??? Boolean
    val e: Any = "Hallo" // Any ??? String

    /*
    Durch Subtyping kann Any Werte verschiedener Typen annehmen
            A >: B, A ist ein Supertyp von B, wo A erwartet und B gegeben wird
     val a: A =  B

     Notation:
     A >: B     A ist der Supertyp von B
     A <: B     A ist der Subtyp von B
     */

    val n1: Any = 5 // Die Regel "Any >: Int" muss greifen, damit die Zuweisung von 5 zu Any möglich ist
    // val n2: Int = Any() // Int NOT >: Any. Int ist nicht der Supertyp von Any. Es ist andersrum.

    /*
    Immer gegeben: type_x <: Any. Irgendein Typ ist immer der Subtyp von Any.
    Es kann aber einen Subtypen von type_x geben: Nothing  <: type_x <: Any

    Das Prinzip heißt: Liskov Substitution Principle (LSP)
    Das LSP sagt:
    wenn A <: B, dann gilt:
        Alles was man mit dem Supertyp B machen kann, muss man auch mit dem Subtyp A machen
        Wenn A ein Int ist, muss man auf dem Int auch die Operationen von Any aufrufen können
        diese sind:  equals(), hasCode() und toString()
     */

    val cat: Cat = Cat() // Cat = Cat
    val dog: Dog = Dog() // Dog = Dog
    // val ca2: Cat = Dog() // Cat != Dog, bzw. Cat is not related to Dog. Es gibt keine Typbeziehung untereinander.

    val animalCat: Animal = Cat() // A >: B, Animal >: Cat
    val animalDog: Animal = Dog() // A >: B, Animal >: Dog
    // val animalWhat: Animal = Any() // Animal >: Any. Geht nicht.

    val anyAnimal: Any = Cat() // Any >: Cat. Geht. Any ist Supertyp von Cat.
    val anyAnimal2: Any = Animal() // Any >: Animal >: Cat
    val anyAnimal3: Any = Cat() as Animal // Any >: Animal

    /*
    Supertypen und Subtypen sind relevant für Funktionen.
    Dieselben Regeln für Variablen gelten auch für Funktionen.
     */

    /*
    Funktion kann überladen werden durch unterschiedliche Signaturen. Signatur ist Fkt.name und Argumente.
    Da die Argumente unterschiedlich sind, kann man die Fkt. feed überladen.
     */
    fun feed(cat: Cat) = Unit
    fun feed(dog: Dog) = Unit
    fun feed(animal: Animal) = Unit

    feed(cat) // (Cat | Dog | Animal) >: Cat
    feed(dog) // (Cat | Dog | Animal) >: Dog
    feed(animalCat) // (Cat | Dog | Animal) >: Animal (>: Cat)
    feed(animalDog) // (Cat | Dog | Animal) >: Animal (>: Dog)

    /*
    Nicht gemacht werden kann:
    feed(anyAnimal) // (Cat | Dog | Animal) NOT >: Any
    feed(anyAnimal2) // (Cat | Dog | Animal) NOT >: Any
    feed(anyAnimal4) // (Cat | Dog | Animal) NOT >: Any
    feed(true) // (Cat | Dog | Animal) NOT >: Any
    feed(42) // (Cat | Dog | Animal) NOT >: Any
    Wenn Any erlaubt werden würde, könnten alle Subtypen von Any an die Fkt. übergeben werden.
    Das würde aber bei der Fkt. feed keinen Sinn machen.
     */

    // poc = petowner cat
    val poc: PetOwner<Cat> = PetOwner<Cat>() // A :> B, also PetOwner<Cat> = PetOwner<Cat>
    // petowner animal                                      Animal      >: Cat
    val poa: PetOwner<Animal> = PetOwner<Cat>() // PetOwner<Animal> NOT >: PetOwner<Cat>

    // Durch die Einführung von Generics ist die Vererbungshierarchie nicht mehr wirksam

    fun withPOC(poc: PetOwner<Cat>) = Unit
    fun withPOA(poc: PetOwner<Animal>) = Unit

    withPOC(poc) // PetOwner<Cat> = PetOwner<Cat>
    withPOA(poc) // Typemismatch: PetOwner<Animal> >: PetOwner<Cat>

    // Das Gleiche am Beispiel von List<T>

    val cats: List<Cat> = listOf(Cat()) // List<Cat> = List<Cat>
    val animals: List<Animal> = listOf(Cat()) // List<Animal> >: List<Cat>
    // Die Zuweisung einer Liste von Cats zu einer List<Animal> funktioniert wergen List<T>

    fun withAnimals(animals: List<Animal>) = Unit
    withAnimals(animals) // List<Animal> = List<Animal>
    withAnimals(cats) // List<Animal> >: List<Cat>
}