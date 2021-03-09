contains(X,[X|_]).
constains(X,[_|Tail):- contains(X,Tail).

% myappend(ListeA,ListeB,ListeC).
% ListeA und ListeB sollen in ListeC zusammengefügt werden
myapped([],L,L).
myappend([HeadA|TailA],ListeB,[HeadA|C_Teil]):-myappend(TailA,ListeB,C_Teil).

/*
 * myappend([a,b,c],[1,2,3],X).
 * X = [a,b,c,1,2,3].
 *
 * myappend([a,b,c],[],X).
 * X = [a,b,c].
 *
 * myappend([],[500],X).
 * X = [500].
 *
 * myappend([a,b,c],[x,y,z],[a,b,c,d,e,f,g]).
 * false
 */