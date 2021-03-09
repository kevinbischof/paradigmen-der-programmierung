% Bäume
% Leerer Baum:
tree(-). % - => nichts

% Baumknoten mit Inhalt X
tree(n(X,L,R)):-tree(L),tree(R).

tree(n(30,-,-)).
% => true

tree(n(30,n(10,-,-),-)).
% => true

tree(n(30,n(10,-,-),n(2,-,-))).
% => true

tree(42).
% => false

% Bessere Schreibweise

K1=n(30,-,-),K2=n(10,-,-),W=n(2,K1,K2).
/*
 * K1 = n(30, -, -),
 * K2 = n(10, -, -),
 * W = n(2, n(30, -, -), n(10, -, -)).
 */

% Verwedung von defnode
defnode(k1, n(1,-,-)).
defnode(k2, n(8,-,-)).
