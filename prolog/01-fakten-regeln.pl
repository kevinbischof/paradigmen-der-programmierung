% Prolog
% Kommentare werden mit Prozentzeichen geschrieben

student(susi) .
student(fridolin) .
student(horst) .
student(anna) .
student(elefant) .

female(susi) .
female(anna) .
male(horst) .
male(fridolin) .

likes(susi,schokolade) .
likes(susi,spiele) .
likes(susi,lesen) .

likes(horst,fahraddfahren) .
likes(horst,lesen) .

likes(fridolin,fahrradfahren) .
likes(fridolin,trompete) .
likes(fridolin,susi) .

friends(Person1,Person2):-likes(Person1,What),likes(Person2,What) .
