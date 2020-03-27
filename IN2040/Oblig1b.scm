#|
Oppgave 1: Par og lister

Vis hvordan man trekker ut elementet 42 fra følgende lister, bare ved bruk av car og cdr:

(Jeg har slengt på en ' noen steder for å gjøre koden kjørbar)
(f) (0 42 #t bar)

    (car (cdr (0 42 #t 'bar)))

(g) ((0 42) (#t bar))

    (car (cdr (car '((0 42) (#t 'bar))))

    Alternativt: (cadar '((0 42) (#t 'bar)))

(h) ((0) (42 #t) (bar))

    (caadr '((0) (42 #t) ('bar))), der caadr = car (car (cdr ()))

(i) Vis hvordan listen fra deloppgaven (g) over kan lages bare ved bruk av prosedyren cons og bare ved
bruk av list.

    Ved bruk av cons:

    (cons (cons 0 (cons 42 '())) (cons #t (cons 'bar '())))


    Ved bruk av list:

    ( list '(0 42) '(#t 'bar))


Oppgave 2: Rekursjon over lister og høyereordens prosedyrer

(a) På forelesning nr. 3 (ons. 4. sept.) så vi på en rekursiv egendefinert variant av den innebygde prosedyren
length. Prosedyren tar en liste som argument og teller hvor mange elementer den inneholder. Skriv en
halerekursiv versjon av denne (kall den f.eks. length2).
|#

(define (length2 items)
  (define (lst-iter lst count)
      (if (null? lst)
      count
      (lst-iter (cdr lst) (+ count 1))))
  (lst-iter items 0))

(length2 (cons 1 (cons 0 '())))


#|
(b) Skriv en rekursiv prosedyre rev-list som tar en liste som argument og returnerer en ny reversert versjon
av lista (uten å bruke den innebygde prosedyren reverse). Eksempler på kall:
? (rev-list '("towel" "a" "bring" "always"))
→ ("always" "bring" "a" "towel")
? (rev-list '())
→ ()
Ta med en kommentar som forklarer hvorvidt du har brukt ‘vanlig’ rekursjon eller spesialtilfellet halerekursjon.
Forklar også kort hvorfor du har valgt det ene fremfor det andre.

Kommentar: Jeg valgte å gå for en halerekursiv prosedyre fordi det da ikke er noen operasjoner som venter på
           resultatet fra rekursjonen. Beste argument for dette er at det tar mindre minne, men er usikker
           på hvorvidt det her lagres en ny liste for hvert rekursive kall. 
|#

(define (rev-list items)
  (define (rev-it lst init)
    (if (null? lst)
        init
        (rev-it (cdr lst) (cons (car lst) init))))
  (rev-it items '()))

(rev-list '(1 2 3 4 5))
(rev-list '("towel" "a" "bring" "always"))

#|
(c) Skriv et høyereordens predikat all? som tar et annet predikat og en liste som argument og returnerer #t
dersom alle elementer i lista tester sant for predikatet og #f ellers. Dersom vi gir den tomme lista som
argument kan den returnere #t. Kalleksempler:
? (all? odd? '(1 3 5 7 9)) → #t
? (all? odd? '(1 2 3 4 5)) → #f
? (all? odd? '()) → #t
Vis også hvordan all? kan kalles med en anonym prosedyre (lambda-uttrykk) definert slik at all? returnerer #f
 dersom noen av tallene i listeargumentet er høyere enn 10. Kalleksempler (der du skal fylle inn
uttrykket for ????):
? (all? ???? '(1 2 3 4 5)) → #t
? (all? ???? '(1 2 3 4 50)) → #f


|#

(define (all? pred items)
  (if (null? items)
      #t
  (and (pred (car items))
       (all? pred (cdr items)))))
(all? odd? '(1 3 5 7 9))
(all? odd? '(1 2 3 4 5))
(all? odd? '())

;; Her ser vi hvordan en lambdafunksjon kan brukes i all? slik at den returnerer #f hvis ett element er større
;; 10
(all? (lambda (x) (> 10 x)) '(1 2 3 4 15)) 


#|
(d) Skriv en prosedyre nth som tar to argumenter: et (indeks)tall og en liste. nth skal returnere liste-elementet
på posisjonen som indekstallet indikerer. Vi ønsker å telle fra 0, dvs. at det første elementet har indeks 0.
(Du trenger ikke bry deg om feilsjekking, vi bare antar at prosedyren ikke vil kalles med en ugyldig indeks.)
Eksempel på kall:
? (nth 2 '(47 11 12 13)) → 12
|#

(define (nth index elements)
  (define (nth-count count lst)
    (if (= count index)
        (car lst)
        (nth-count (+ count 1) (cdr lst))))
  (nth-count 0 elements))
(nth 2 '(47 11 12 13))


#|
(e) Skriv en prosedyre where som tar to argumenter: et tall og en liste. where beregner posisjonsindeks til
(første forekomst av) tallet i listen, f.eks.
? (where 3 '(1 2 3 3 4 5 3)) → 2
? (where 0 '(1 2 3 3 4 5 3)) → #f
|#

(define (where search elements)
  (define (nth-count count lst)
    (cond ((null? lst) #f)
          ((= (car lst) search) count)
          (else (nth-count (+ count 1) (cdr lst)))))
  (nth-count 0 elements))
(where 3 '(1 2 3 3 4 5 3))
(where 0 '(1 2 3 3 4 5 3))
#|
(f) I forelesningen ga vi en definisjon av prosedyren map som var noe forenklet (sammenliknet med den innebygde
 R5RS-versjonen av prosedyren) ved at vår versjon bare kan operere over én liste om gangen. Skriv en
variant map2 som opererer over to lister parallelt, dvs. kaller dens første argument (som må være en prosedyre)
 på de første elementene i listene, så på elementene på andre plass, osv. Hvis de to listene ikke har samme
antall elementer så avslutter map2 når den har kommet gjennom den korteste listen, f.eks.
? (map2 + '(1 2 3 4) '(3 4 5)) → (4 6 8)
|#

(define (map2 proc lst1 lst2)
  (if (or (null? lst1) (null? lst2))
      '()
      (cons (proc (car lst1) (car lst2))
            (map2 proc (cdr lst1) (cdr lst2)))))

(map2 * '(1 2 3 4) '(3 4 5))
#|
(g) I eksempelet over brukte vi den forhåndsdefinerte Scheme-prosedyren + som første argument til map2.
Vi skal nå i stedet ta i bruk anonyme lambda-prosedyrer for å operere over listeelementer. Vis hvordan
map2 kan kombineres med en anonym prosedyre som beregner gjennomsnittet av to tall. Med argumentene
'(1 2 3 4) og '(3 4 5) burde returverdien bli (2 3 4).

Under definerer jeg en lambda prosedyre for gjennomsnitt mellom to tall der i map2 kallet prosessen +
var i oppgaven over. Altså er det andre elementet i map2 kallet en lambda prosedyre.
|#

(map2 (lambda (x y) (/ (+ x y) 2)) '(1 2 3 4) '(3 4 5))

#|
(h) Skriv en prosedyre both? som genererer et predikat som tar to argumenter. both? selv tar ett argument som
er et predikat som kun tar ett argument. Returverdien til both? er en prosedyre som returnerer #t i tilfelle
predikatet som var argument til both? er gyldig for begge argumentene. Her er noen eksempler som viser
hva vi er ute etter:
? (map2 (both? even?) '(1 2 3) '(3 4 5)) → (#f #t #f)
? ((both? even?) 2 4) → #t
? ((both? even?) 2 5) → #f
|#

(define (both? pred)
  (lambda (arg1 arg2)
    (and (pred arg1) (pred arg2))))

((both? even?) 2 4)
((both? even?) 2 5)
#|
(i) Skriv en prosedyre self som tar en prosedyre som argument og returnerer en ny prosedyre slik at f.eks.
? ((self +) 5) → 10
? ((self *) 3) → 9
? (self +) → #<procedure>
? ((self list) "hello") → ("hello" "hello")
|#

(define (self pros)
  (lambda (x)
    (pros x x)))
((self +) 5)
((self *) 3)
(self +)
((self list) "hello")