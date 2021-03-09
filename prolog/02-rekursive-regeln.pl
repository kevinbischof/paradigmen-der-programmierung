% Prolog
% Kommentare werden mit Prozentzeichen geschrieben

likes(horst,lesen) .

likes(fridolin,fahrradfahren) .
likes(fridolin,trompete) .
likes(fridolin,susi) .

friends(Person1,Person2):-likes(Person1,What),likes(Person2,What) .

% Reihen:
reihe(r1,[peter,susi,horst,anna]) .
reihe(r2,[frido,benjamin,bibi,harry]) .

% Reihe 1:
nachbarn(peter,susi) .
nachbarn(susi,horst) .
nachbarn(horst,anna) .
nachbarn(anna,ralf) .

% Reihe 2:
nachbarn(frido,benjamin) .
nachbarn(benjamin,bibi) .
nachbarn(bibi,harry).

sind_nachbarn(X,Y):-nachbarn(X,Y) .
sind_nachbarn(X,Y):-nachbarn(Y,X) .

% Rekursive abfrage:
in_reihe(X,Y):-sind_nachbarn(X,Y) .
in_reihe(X,Y):-sind_nachbarn(X,Z),in_reihe(Y,Z) .
