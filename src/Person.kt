data class Person(val firstname: String, val age: Int)

fun nameOfAdultsImperative(persons: List<Person>): List<String> {
    val adults = mutableListOf<String>()

    for (person in persons) {
        if (person.age >= 18) {
            adults.add(person.firstname)
        }
    }

    return adults
}

fun nameOfAdultsDeklarativ(persons: List<Person>): List<String> {
    val adults = persons.filter { it.age >= 18 }.map { it.firstname }

    return adults

    // filter und map sind Funktionen höherer Ordnung
}