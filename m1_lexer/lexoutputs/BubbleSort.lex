ReserveWord, class
ID, BubbleSort
Delimiter, {
ReserveWord, public
ReserveWord, static
ReserveWord, void
ReserveWord, main
Delimiter, (
ReserveWord, String
Delimiter, [
Delimiter, ]
ID, a
Delimiter, )
Delimiter, {
ReserveWord, System.out.println
Delimiter, (
ReserveWord, new
ID, BBS
Delimiter, (
Delimiter, )
Delimiter, .
ID, Start
Delimiter, (
Integer, 10
Delimiter, )
Delimiter, )
Delimiter, ;
Delimiter, }
Delimiter, }
ReserveWord, class
ID, BBS
Delimiter, {
ReserveWord, int
Delimiter, [
Delimiter, ]
ID, number
Delimiter, ;
ReserveWord, int
ID, size
Delimiter, ;
ReserveWord, public
ReserveWord, int
ID, Start
Delimiter, (
ReserveWord, int
ID, sz
Delimiter, )
Delimiter, {
ReserveWord, int
ID, aux01
Delimiter, ;
ID, aux01
Delimiter, =
ID, this
Delimiter, .
ID, Init
Delimiter, (
ID, sz
Delimiter, )
Delimiter, ;
ID, aux01
Delimiter, =
ID, this
Delimiter, .
ID, Print
Delimiter, (
Delimiter, )
Delimiter, ;
ReserveWord, System.out.println
Delimiter, (
Integer, 99999
Delimiter, )
Delimiter, ;
ID, aux01
Delimiter, =
ID, this
Delimiter, .
ID, Sort
Delimiter, (
Delimiter, )
Delimiter, ;
ID, aux01
Delimiter, =
ID, this
Delimiter, .
ID, Print
Delimiter, (
Delimiter, )
Delimiter, ;
ReserveWord, return
Integer, 0
Delimiter, ;
Delimiter, }
ReserveWord, public
ReserveWord, int
ID, Sort
Delimiter, (
Delimiter, )
Delimiter, {
ReserveWord, int
ID, nt
Delimiter, ;
ReserveWord, int
ID, i
Delimiter, ;
ReserveWord, int
ID, aux02
Delimiter, ;
ReserveWord, int
ID, aux04
Delimiter, ;
ReserveWord, int
ID, aux05
Delimiter, ;
ReserveWord, int
ID, aux06
Delimiter, ;
ReserveWord, int
ID, aux07
Delimiter, ;
ReserveWord, int
ID, j
Delimiter, ;
ReserveWord, int
ID, t
Delimiter, ;
ID, i
Delimiter, =
ID, size
Delimiter, -
Integer, 1
Delimiter, ;
ID, aux02
Delimiter, =
Integer, 0
Delimiter, -
Integer, 1
Delimiter, ;
ReserveWord, while
Delimiter, (
ID, aux02
Delimiter, <
ID, i
Delimiter, )
Delimiter, {
ID, j
Delimiter, =
Integer, 1
Delimiter, ;
ReserveWord, while
Delimiter, (
ID, j
Delimiter, <
Delimiter, (
ID, i
Delimiter, +
Integer, 1
Delimiter, )
Delimiter, )
Delimiter, {
ID, aux07
Delimiter, =
ID, j
Delimiter, -
Integer, 1
Delimiter, ;
ID, aux04
Delimiter, =
ID, number
Delimiter, [
ID, aux07
Delimiter, ]
Delimiter, ;
ID, aux05
Delimiter, =
ID, number
Delimiter, [
ID, j
Delimiter, ]
Delimiter, ;
ReserveWord, if
Delimiter, (
ID, aux05
Delimiter, <
ID, aux04
Delimiter, )
Delimiter, {
ID, aux06
Delimiter, =
ID, j
Delimiter, -
Integer, 1
Delimiter, ;
ID, t
Delimiter, =
ID, number
Delimiter, [
ID, aux06
Delimiter, ]
Delimiter, ;
ID, number
Delimiter, [
ID, aux06
Delimiter, ]
Delimiter, =
ID, number
Delimiter, [
ID, j
Delimiter, ]
Delimiter, ;
ID, number
Delimiter, [
ID, j
Delimiter, ]
Delimiter, =
ID, t
Delimiter, ;
Delimiter, }
ReserveWord, else
ID, nt
Delimiter, =
Integer, 0
Delimiter, ;
ID, j
Delimiter, =
ID, j
Delimiter, +
Integer, 1
Delimiter, ;
Delimiter, }
ID, i
Delimiter, =
ID, i
Delimiter, -
Integer, 1
Delimiter, ;
Delimiter, }
ReserveWord, return
Integer, 0
Delimiter, ;
Delimiter, }
ReserveWord, public
ReserveWord, int
ID, Print
Delimiter, (
Delimiter, )
Delimiter, {
ReserveWord, int
ID, j
Delimiter, ;
ID, j
Delimiter, =
Integer, 0
Delimiter, ;
ReserveWord, while
Delimiter, (
ID, j
Delimiter, <
Delimiter, (
ID, size
Delimiter, )
Delimiter, )
Delimiter, {
ReserveWord, System.out.println
Delimiter, (
ID, number
Delimiter, [
ID, j
Delimiter, ]
Delimiter, )
Delimiter, ;
ID, j
Delimiter, =
ID, j
Delimiter, +
Integer, 1
Delimiter, ;
Delimiter, }
ReserveWord, return
Integer, 0
Delimiter, ;
Delimiter, }
ReserveWord, public
ReserveWord, int
ID, Init
Delimiter, (
ReserveWord, int
ID, sz
Delimiter, )
Delimiter, {
ID, size
Delimiter, =
ID, sz
Delimiter, ;
ID, number
Delimiter, =
ReserveWord, new
ReserveWord, int
Delimiter, [
ID, sz
Delimiter, ]
Delimiter, ;
ID, number
Delimiter, [
Integer, 0
Delimiter, ]
Delimiter, =
Integer, 20
Delimiter, ;
ID, number
Delimiter, [
Integer, 1
Delimiter, ]
Delimiter, =
Integer, 7
Delimiter, ;
ID, number
Delimiter, [
Integer, 2
Delimiter, ]
Delimiter, =
Integer, 12
Delimiter, ;
ID, number
Delimiter, [
Integer, 3
Delimiter, ]
Delimiter, =
Integer, 18
Delimiter, ;
ID, number
Delimiter, [
Integer, 4
Delimiter, ]
Delimiter, =
Integer, 2
Delimiter, ;
ID, number
Delimiter, [
Integer, 5
Delimiter, ]
Delimiter, =
Integer, 11
Delimiter, ;
ID, number
Delimiter, [
Integer, 6
Delimiter, ]
Delimiter, =
Integer, 6
Delimiter, ;
ID, number
Delimiter, [
Integer, 7
Delimiter, ]
Delimiter, =
Integer, 9
Delimiter, ;
ID, number
Delimiter, [
Integer, 8
Delimiter, ]
Delimiter, =
Integer, 19
Delimiter, ;
ID, number
Delimiter, [
Integer, 9
Delimiter, ]
Delimiter, =
Integer, 5
Delimiter, ;
ReserveWord, return
Integer, 0
Delimiter, ;
Delimiter, }
Delimiter, }
