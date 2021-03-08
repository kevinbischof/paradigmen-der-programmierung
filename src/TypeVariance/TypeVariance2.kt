package TypeVariance

/*
Fallbeispiel zu Typvarianzen
Eine Shoppingwebsite verkauft Bücher und Möbelstücke, die man in einen Einkaufskorb legen kann.
Wir möchten gerne wissen, wie der Gesamtwert des Shoppingcarts ist.
 */

open class Product(open val name: String, open val price: Double)

open class Book(override val name: String, override val price: Double, val pages: Int) :
        Product(name, price)

open class Furniture(override val name: String, override val price: Double, val dimensions: Triple<Int, Int, Int>) :
        Product(name, price)

fun main() {
    // veränderliche Liste von Products
    val products = mutableListOf<Product>(
            Book("Programmieren lernen mit Kotlin", 29.99, 520),
            Furniture("Ikea Tisch", 599.99, Triple(180, 90, 60))
    )

    fun checkoutPrice(cart: MutableList<out Product>): Double {
        /*
        cart.add(Furniture("Ikea Tisch", 599.99, Triple(180, 90, 60))) durch out kann nichts zur Liste hinzugefügt
        werden. Es kann nur lesend operiert werden.
        MutableList ist veränderlich und invariant.
        List ist unveränderlich und daher invariant. Durch out können Werte vom Typ E gelesen, aber nicht geschrieben
        werden. List ist persistent in der Struktur.
         */

        var totalPrice = 0.0

        for (p in cart) {
            totalPrice += p.price
        }

        return totalPrice
    }

    // veränderliche Liste von Büchern
    val books = mutableListOf<Book>(
            Book("Programmieren lernen mit Kotlin", 29.99, 520)
    )

    checkoutPrice(products) // MutableList<Product> = MutableList<Product>
    checkoutPrice(books) // MutableList<Product> >: MutableList<Book> ohne out hier Fehler

    /*
    Covariant
        bount to Nothing
        Typ T darf spezieller sein: T <: Product
     */

    fun addSurprise(cart: MutableList<in Product>) {
        cart.add(Furniture("Lampe", 39.99, Triple(20,10,5)))
    }

    addSurprise(products) // MutableList<Product> = MutableList<Product>
    // addSurprise(books) // MutableList<Product> NOT >: MutableList<Book>

    val anything = mutableListOf<Any>(
            42, true, "irgendwas",
            Book("Programmieren lernen mit Kotlin", 29.99, 520)
    )

    addSurprise(anything) // durch Covarianz kann eine veränderliche Liste vom Typ Any eingegeben werden

    /*
    Covariant
        bount to Any
        Typ T darf allgemeiner sein: T >: Product
     */

    /*
    out: Covariant (Subtyping), bound to Nothing (Nothing ist bottom type)
         Höher als Max geht nicht, z.B. MutableList<Any>
         Max: MutableList<Product>
         Min: MutableList<Nothing>

    int: Contravariant (Supertyping), bound to Any
         Max: MutableList<Any>
         Min: MutableList<Product>
         Geht nichts unerhalb von Product, wie z.B. Books, Furniture oder Nothing.
         Trotzdem müssen die Typen verwandt sein. Int ginge zum Beispiel nicht.
     */
}