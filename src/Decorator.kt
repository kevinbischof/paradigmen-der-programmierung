import kotlin.random.Random

interface WordProvider {
    fun getWord(): String
    fun wordCount(): Int
}

class SimpleWordProvider : WordProvider {
    private val liste = listOf("Apfel", "Birne", "Kino", "Hallo")
    override fun getWord(): String = liste[Random.nextInt(liste.size)]
    override fun wordCount(): Int = liste.size
}

class UpperCaseDecorator(val decoratedGenerator: WordProvider) : WordProvider {
    override fun getWord(): String = decoratedGenerator.getWord().toUpperCase()
    override fun wordCount(): Int = decoratedGenerator.wordCount()
}

class MaxLengthFilter(val decoratedGenerator: WordProvider, val maxLetters: Int) : WordProvider {
    override fun getWord(): String = decoratedGenerator.getWord().substring(0, maxLetters)
    override fun wordCount(): Int = decoratedGenerator.wordCount()
}
/*
Wiederkehrender abstrakter Dekorierer, der die wiederkehrenden Funktionalitäten abbildet.
wordCount muss bspw. nicht mehr implementiert werden
 */
open class AbstractDecorator(val decoratedGenerator: WordProvider) : WordProvider {
    override fun getWord(): String = decoratedGenerator.getWord()
    override fun wordCount(): Int = decoratedGenerator.wordCount()
}

class WordDoubler(decoratedGenerator: WordProvider) : AbstractDecorator(decoratedGenerator) {
    override fun getWord(): String {
        val word = decoratedGenerator.getWord()
        return "$word $word"
    }
}

/*
Die nicht definierte Methode wird an ein anderes Objekt deligiert, das dieser Schnittstelle genügt.
Das Objekt 'decoratedGenerator' genügt dieser Schnittstelle. Dies wird durch 'by' an das Objekt deligiert.
 */
class WordTripler(val decoratedGenerator: WordProvider) : WordProvider by decoratedGenerator{
    override fun getWord(): String {
        val word = decoratedGenerator.getWord()
        return "$word $word $word"
    }
}

fun main() {
    val generator : WordProvider = MaxLengthFilter(UpperCaseDecorator(SimpleWordProvider()), 3)
    println(generator.getWord())
    println(generator.getWord())
    println(generator.getWord())
    println(generator.getWord())

    val generator2 : WordProvider = WordDoubler(UpperCaseDecorator(SimpleWordProvider()))
    println(generator2.getWord())
    println(generator2.getWord())
    println(generator2.getWord())
    println(generator2.getWord())

    val generator3 : WordProvider = WordTripler(SimpleWordProvider())
    println(generator3.getWord())
    println(generator3.getWord())
    println(generator3.getWord())
    println(generator3.getWord())

    val generator4 : WordProvider = WordTripler(MaxLengthFilter(SimpleWordProvider(), 3))
    println(generator4.getWord())
    println(generator4.getWord())
    println(generator4.getWord())
    println(generator4.getWord())
}