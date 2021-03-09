package entwurfsmuster

interface MessageObserver {
    fun notifyOnPost() // aktualisieren / update
}

// abstraktes Subjekt
abstract class MessagePoster {

    val observer = mutableListOf<MessageObserver>()

    fun register(obs: MessageObserver) {
        observer.add(obs)
    }

    fun unregister(obs: MessageObserver){
        observer.remove(obs)
    }

    fun notifyChange() {
        for (obs in observer) {
            obs.notifyOnPost()
        }
    }
}

class ConsolePoster: MessagePoster() {

    val messages = mutableListOf<String>()

    fun run() {
        while (true) {
            println("Bitte geben Sie eine Nachricht ein:")
            val input = readLine()
            if (input == "stop") break;
            if (input != null) {
                messages.add(input)
                notifyChange()
            }
        }
    }

    fun getLastMessage() : String = messages.last()
}

// erster Observer:
class EchoObserver(val consolePoster: ConsolePoster): MessageObserver {
    override fun notifyOnPost() {
        println(consolePoster.getLastMessage())
    }
}

// weiterer Observer:
class MessageCounter(): MessageObserver {
    private var count = 0
    override fun notifyOnPost() {
        count++
        println("Es wurden $count Nachrichten gepostet")
    }
}

fun main() {
    val consolePoster = ConsolePoster()
    consolePoster.register(EchoObserver(consolePoster))
    consolePoster.register(MessageCounter())

    // Direkte Übergabe einer Funktion, ohne vorher eine Klasse definieren zu müssen
    consolePoster.register(object: MessageObserver {
        override fun notifyOnPost() {
            if (consolePoster.getLastMessage() == "Moin") {
                println("Moin Moin! Tach!")
            }
        }
    })

    consolePoster.run()


}