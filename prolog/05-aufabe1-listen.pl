[a,b,c]=[Head|Tail] .
/*
 * Head = a,
 * Tail = [b,c].
 *
 */
[]=[Head|Tail] .
% false

[[the,cat],sat]=[Head|Tail] .
/*
 * Head = [the,cat],
 * Tail = [sat].
 */

[the,[cat,sat]]=[Head|Tail] .
/*
 * Head = the,
 * Tail = [[cat,sat]].
 */

[a+b,x+y]=[Head|Tail] .
/*
 * Head = a+b,
 * Tail = [x+y].
 */


