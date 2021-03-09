% fakultaet(N, N-Fakultaet)
% -------------------------
fakultaet(0,1) .
fakultaet(N,F) :-
    N > 0,
    N1 is N - 1,
    fakultaet(N1,F1),
    F is N * F1 .