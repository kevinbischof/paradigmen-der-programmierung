/*
 * Erläutern Sie die logische Bedeutung des folgenden Prädikats.
 * Gibt es ein einfaches bekanntes Prädikat, dass das Gleiche ausdrückt?
 */

 unbekannt(X, Xs):- append(As, [X|Bs], Xs).

 /*
  * Und welche Lösungen gibt es für ?- unbekannt(X, [1,2,3]).
  */