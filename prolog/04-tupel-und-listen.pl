% Simple Tupel examples
(1,2,3) = (1,2,3) . % true
(1,2,3) = (1,2,3,4) . % false
(A,B,C) = (1,2,3) . % true
(1,2,3) = (A,B,C) . % true
(A,2,C) = (1 , B, 3) . % true

% Simple list examples
[1,2,3] = [1,2,3] . % true
[1,2,3] = [X,Y,Z] . % true
[1,2,3] = [1,2,3] . % true
[1,2,3] = [X,X,Z] . % geht nicht -> false

% Aufsplitten einer Liste in Head und Tail:
[a,b,c] = [Head|Tail] .
[a,b,c] = [X|Xs] .
[] = [Head|Tail] . % geht nicht -> false
[a] = [Head|Tail] . % a = Head, [] = Tail

