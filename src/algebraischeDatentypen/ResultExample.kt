package algebraischeDatentypen

import java.awt.Button
import java.awt.Color

data class LoginCredential(val username: String, val password: String) // username and password

data class Token(val token: String)

// Token ODER Throwable -> Summentyp

/*
Mit sealed class kann ein Summentyp in Kotlin umgesetzt werden.
In einer sealed class müssen data class erstellt werden.
Um den Typen zu abstrahieren, wird die sealed class mit dem Typparameter <A> generisch gemacht.
Durch sealed kann es keine anderen Kombinationen gibt außer Success und Failure.
Die Ableitungen von Result sind versiegelt auf die beiten Implementierungen Success und Failure. Andere gibt es nicht.
<out A> -> out vor A wird später erklärt
 */
sealed class Result<out A> { // Success with value OR Failure with error
    data class Success<A>(val value: A) : Result<A>()
    data class Failure(val error: Throwable) : Result<Nothing>()
}

fun login(credential: LoginCredential, completion: (Result<Token>) -> Unit) {
    // perform login with username and password
    // call completion handler

    completion(Result.Success(Token("secureToken1234")))
    completion(Result.Failure(Throwable("oops, server down")))
}

data class Label(var text: String, var color: Color)

val label = Label("", Color.black)

fun performLoginAndUpdateUI(credential: LoginCredential) {
    login(credential) { result ->
//        when {
//            token != null -> {
//                label.text = "Hallo, $token"
//                label.color = Color.black
//            }
//            error != null -> {
//                label.text = "Upps, ein Fehler ist aufgetreten: ${error.localizedMessage}"
//                label.color = Color.red
//            }
//            else -> {
//                error("this should never happen")
//            }
//        }

        // result pattern matchen
        when (result) {
            is Result.Success -> {
                label.text = "Hallo, ${result.value.token}"
                label.color = Color.black
            }
            is Result.Failure -> {
                label.text = "Upps, ein Fehler ist aufgetreten: ${result.error.localizedMessage}"
                label.color = Color.red
            }
        }
    }
}

fun main() {


}