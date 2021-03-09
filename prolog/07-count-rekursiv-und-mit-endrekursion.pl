% count(List,Size).
count([],0). % Erste Klausel, wenn die Liste leer ist
% count Prädikat
count([Head|Tail], Size):- count(Tail,SizeOfTail), Size is SizeOfTail + 1. % Zweite Liste bei einer nicht leeren Liste

/*
 * Rekursion am Beispiel von der Liste [a,b,c]
 * count( [a,b,c], SizeOfTail ) => Zweite Klausel greift. Rekursion ist hier beendet. Gesamtergebnis: Size = 3
 * count( [a,b], SizeOfTail ) => Zweite Klausel greift. Size = 2 => SizeOfTail = 2 + 1 => Size = 3
 * count( [a], SizeOfTail ) => Zweite Klausel greift. Size = 1 => SizeOfTail = 1 + 1 => Size = 2
 * count( [], SizeOfTail ) => Erste Klausel greift. Size = 0 => SizeOfTail = 0 + 1 => Size = 1
 *
 * Bei Rekursion wird ein Stack aufgebaut. Das kann bei langen Listen einen großen Speicherverbrauch erzeugen.
 */

% Endrekursive Version von count
% ecount(Liste,Size).

ecount(Liste,Size):-ecount(Liste,0,Size).
ecount([],SoFar,SoFar). % Klausel für leere Liste
ecount([_|Tail], SoFar, Size):- TempOneMore is SoFar + 1, ecount(Tail, TempOneMore, Size). % Klausel mit Endrekursion

