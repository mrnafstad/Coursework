(load "huffman.scm")
(define (error reason arg)
  (display "Error: ")
  (display reason)
  (display ": ")
  (display arg)
  (newline)
  (scheme-report-environment -1))
#|
1 Diverse
Før vi begynner med Huffman-koding tar vi med et par mindre oppgaver der vi skal bryne oss litt på
 høyereordens prosedyrer, og lambda og let.

(a) I forelesningen den 11. september så vi på en implementasjon av datatypen par (‘cons-celler’)
som en prosedyre. Her er nok en alternativ variant av par representert som prosedyre, gitt ved følgende
konstruktor;
(define (p-cons x y)
(lambda (proc) (proc x y)))
Definer selektorene p-car og p-cdr for denne representasjonen. Vi bruker p-* i navnene så de ikke
kolliderer med de innebygde versjonene (men dere skal ikke bruke dem her). Ellers vil vi at p-car og p-cdr
skal gi samme resultat som car og cdr gjør for den innebygde versjonen av cons. Noen kalleksempler:
? (p-cons "foo" "bar")
→ #<procedure>
? (p-car (p-cons "foo" "bar"))
→ "foo"
? (p-cdr (p-cons "foo" "bar"))
→ "bar"
? (p-car (p-cdr (p-cons "zoo" (p-cons "foo" "bar"))))
→ "foo"
|#

(define (p-cons x y)
  (lambda (proc) (proc x y)))

(define (p-car proc)
  (proc (lambda (p q) p)))

(define (p-cdr proc)
  (proc (lambda (p q) q)))

(p-cons "foo" "bar")
(p-car (p-cons "foo" "bar"))
(p-cdr (p-cons "foo" "bar"))
(p-car (p-cdr (p-cons "zoo" (p-cons "foo" "bar"))))

#|
(b) Vis hvordan de to let-uttrykkene under kan skrives om til applikasjon av lambda-uttrykk.
(Forelesningsnotatene fra 11/9 er relevante her.) Oppgi også hvilken verdi uttrykkene evaluerer til.
? (define foo 42)
? (let ((foo 5)
        (x foo))
    (if (= x foo)
        'same
        'different))

? (let ((bar foo)
        (baz 'towel))
    (let ((bar (list bar baz))
          (foo baz))
       (list foo bar)))



I prosedyrene gir let og Lambda versjon samme output, henholdsvis different og (towel (42 towel))
|#

(define foo 42)

((lambda (foo x)
  (if (= x foo)
      'same
      'different))
  5 foo)

((lambda (bar baz)
   ((lambda (bar foo)
      (list foo bar))
   (list bar baz) baz))
   foo 'towel)

#|
(c) Her skal dere skrive en prosedyre infix-eval, som skal ta ett argument exp som forventes å være en
liste av tre elementer: en operand, en operator og nok en operand. Returverdien til infix-eval skal være
resultatet av å anvende operatoren på operandene. Kalleksempler:
? (define foo (list 21 + 21))
? (define baz (list 21 list 21))
? (define bar (list 84 / 2))
? (infix-eval foo) → 42
? (infix-eval baz) → (21 21)
? (infix-eval bar) → 42
|#

(define (infix-eval lst)
  (let ((x (car lst))
        (y (caddr lst))
        (proc (cadr lst)))
    (proc x y)))

(define foo (list 21 + 21))
(define baz (list 21 list 21))
(define bar (list 84 / 2))
(infix-eval foo)
(infix-eval baz)
(infix-eval bar)

#|
(d) Gitt prosedyren din infix-eval fra oppgaven over, hva blir resultatet av følgende kall? Eksemplet likner
tilsynelatende på det siste kallet over med bar som argument. Forklar kort hvorfor utfallet blir annerledes?
? (define bah '(84 / 2))
? (infix-eval bah) → ??

Dette kallet gir en feilmelding siden det andre elementet i bah her er symbolet /, og ikke operatoren 
for divisjon.

|#

(define bah '(84 / 2))
;;(infix-eval bah)
#|
2 Huffman-koding
(a) Gjør deg kjent med koden i definisjonen til decode i vår fil huffman.scm. Skriv et par setninger om
hva som er vitsen med å bruke en intern hjelpeprosedyre her, decode-1, den kalles jo med de samme
argumentene som hovedprosedyren så hvorfor ikke heller bare kalle denne?


Svar: I decode prosedyren trenger man en hjelpeprosedyre for å kunne hoppe tilbake til roten av treet når
      en blad-node er nådd. Man får i praksis en "ekstra" variabel ved å bruke hjelpeprosedyren decode-1
      ved at tree aldri oppdateres


(b) Skriv en halerekursiv versjon av decode.
|#

(define (decode2 bits tree)
  (define (decode-1 bits current-branch)
    (if (null? bits)
        '()
        (let ((next-branch
               (choose-branch (car bits) current-branch)))
          (if (leaf? next-branch)
              (cons (symbol-leaf next-branch)
                    (decode-1 (cdr bits) tree))
              (decode-1 (cdr bits) next-branch)))))
  (decode-1 bits tree))

#|
(c) Fila huffman.scm inneholder et eksempel på et kodetre og en bitkode, henholdsvis bundet til variablene
sample-tree og sample-code. Hva er resultatet av å kalle prosedyren decode fra oppgaven over med
disse som argument?
? (decode sample-code sample-tree) → (ninjas fight ninjas by night)
|#

(decode sample-code sample-tree)

#|
(d) Skriv en prosedyre encode som transformerer en sekvens av symboler til en sekvens av bits. Input er en
liste av symboler (meldingen) og et Huffman-tre (kodeboken). Output er en liste av 0 og 1. Du kan teste
prosedyrene dine med å kalle decode på returverdien fra encode og sjekke at du får samme melding:
? (decode (encode '(ninjas fight ninjas) sample-tree) sample-tree)
→ (ninjas fight ninjas)
|#
(define (encode message tree)

  ;; Hjelpemetode for å encode et enkelt ord.
  (define (encode-word word current-branch)
    (if (leaf? current-branch)
        '()     
        (let ((left (left-branch current-branch))
              (right (right-branch current-branch)))
          (cond ((element-of-set? word (symbols left)) (cons 0 (encode-word word left)))
                ((element-of-set? word (symbols right))(cons 1 (encode-word word right)))
                (else (error "Not a symbol in tree" word))))))

  ;; Hjelpemetode for å finne ut om et gitt ord finnes i et gitt sett.                
  (define (element-of-set? x set)
    (cond ((null? set) #f)
          ((equal? x (car set)) #t)
          (else (element-of-set? x (cdr set)))))
  
  (if (null? message)
       '()
       (append (encode-word (car message) tree)
               (encode (cdr message) tree))))
(decode (encode '(ninjas fight ninjas) sample-tree) sample-tree)
#|
• Litt bakgrunn for oppgave (e) under: Seksjonene Generating Huffman trees og Sets of weighted elements
under 2.3.4 i SICP beskriver en algoritme for å generere Huffman-trær. Det samme dekkes på foilene fra
forelesningen den 18/9. Algoritmen opererer på en mengde av noder der vi suksessivt slår sammen de to nodene
som har lavest frekvens. Mengden av noder er implementert som en liste. Som diskutert på forelesningen så
kan algoritmen utføres mest effektiv dersom vi sørger for å holde nodelista sortert etter frekvens. SICP-koden
i huffman.scm inneholder funksjonalitet for å generere en sortert liste av løvnoder som kan brukes for å
initialisere algoritmen; adjoin-set og make-leaf-set. For eksempel:
? (make-leaf-set '((a 2) (b 5) (c 1) (d 3) (e 1) (f 3)))
→ ((leaf e 1) (leaf c 1) (leaf a 2) (leaf f 3) (leaf d 3) (leaf b 5))

(e) Skriv en prosedyre grow-huffman-tree som tar en liste av symbol/frekvens-par og returnerer et Huffmantre.
Eksempel på kall:
? (define freqs '((a 2) (b 5) (c 1) (d 3) (e 1) (f 3)))
? (define codebook (grow-huffman-tree freqs))
? (decode (encode '(a b c) codebook) codebook) → (a b c)
|#
(define (grow-huffman-tree pairs)
  
  ;;hjelpeprosedyre for å merge par
  (define (mergers pairs)
    (if (= (length pairs) 1)
        (car pairs)
        (mergers (adjoin-set (make-code-tree (car pairs)
                                             (cadr pairs))
                             (cddr pairs)))))
  
  ;;hjelpeprosedyre for mergers som lager blad sett
  (define (make-leaf-set pairs)
    (if (null? pairs)
        '()
        (let ((pair (car pairs)))
          (adjoin-set (make-leaf (car pair)
                                 (cadr pair))
                      (make-leaf-set (cdr pairs))))))

  (mergers (make-leaf-set pairs)))

(define freqs '((a 2) (b 5) (c 1) (d 3) (e 1) (f 3)))
(define codebook (grow-huffman-tree freqs))
(decode (encode '(a b c) codebook) codebook)
#|
(f) Vi er gitt følgende alfabet av symboler med frekvenser oppgitt i parentes:
samurais (57), ninjas (20), fight (45), night (12), hide (3), in (2), ambush (2), defeat (1), the (5),
sword (4), by
(12), assassin (1), river (2), forest (1), wait (1), poison (1).
Generer et Huffman-tre for alfabetet på bakgrunn av frekvensene, og svar så på de følgende spørsmålene
med utgangspunkt i meldingen under. (Merk at denne skal forstås som en melding bestående av 17 symboler;
linjeskiftene er ikke en del av meldingen.)
– Hvor mange bits bruker det på å kode meldingen?
– Hva er den gjennomsnittlige lengden på hvert kodeord som brukes? (Vi tenker at alle symbolene representeres i
 én og samme liste slik at linjeskift ignoreres.)
– Til slutt: hva er det minste antall bits man ville trengt for å kode meldingen med en kode med fast lengde
(fixed-length code) over det samme alfabetet? Begrunn kort svaret ditt.
ninjas fight
ninjas fight ninjas
ninjas fight samurais
samurais fight
samurais fight ninjas
ninjas fight by night

Svar: Denne meldingen tar 43 (42??) bits å kode (samurais og fight har hver 2 bits,
      mens ninjas, by og night hver har 3 bits), mot
      17 [antal symboler i meldingen] *log2 16 [antall symboler i alfabetet] = 68
      bits med fast lengde på det samme alfabetet.
      I meldingen brukes det i snitt 2.53 (2.47??)  bits per tegn brukt. Dette snittet ville fort
      vokst om ett av de lavest vektede ordene, som hver bruker 7 bits, ble brukt hyppigere
      hyppigere enn frekvensen tilsier.
|#
(define freq2 '((samurais 57) (ninjas 20) (fight 45) (night 12) (hide 3)
                              (in 2) (ambush 2) (defeat 1) (the 5) (sword 4)
                              (by 12) (assassin 1) (river 2) (forest 1) (wait 1) (poison 1)))

(define codebook2 (grow-huffman-tree freq2))
(length (encode '(ninjas fight
                        ninjas fight ninjas
                        ninjas fight samurais
                        samurais fight
                        samurais fight ninjas
                        ninjas fight by night) codebook2))


#|
(g) Skriv en prosedyre huffman-leaves som tar et Huffman-tre som input og returnerer en liste med par
av symboler og frekvenser, altså det samme som vi kan bruke som utgangspunkt for å generere treet. For
eksempel, for sample-tree i huffman.scm:
? (huffman-leaves sample-tree)
→ ((ninjas 8) (fight 5) (night 1) (by 1))
|#
(define (huffman-leaves tree)
  (define (leaves-1 current-branch)
    (let ((next-left (left-branch current-branch))
          (next-right (right-branch current-branch)))
      (cond ((null? current-branch) '())
            ((leaf? next-right)
             (cons (cons (symbol-leaf next-left) (cons (weight-leaf next-left) '()))
                   (cons (cons (symbol-leaf next-right) (cons (weight-leaf next-right) '())) '())))
            ((leaf? next-left)
             (cons (cons (symbol-leaf next-left) (cons (weight-leaf next-left) '()))
                   (leaves-1 next-right)))
            (else (leaves-1 tree)))))
  (leaves-1 tree))
(huffman-leaves sample-tree)
#|
(h) Den forventede gjennomsnittlige lengden på kodeordene som genereres av et Huffman-tre kan uttrykkes som

Sum (i = 1, n) p(s_i) × |c_i|
der p(si) er sannsynligheten for det i-ende symbolet i et alfabet med n symboler og |ci| står for lengden
på kodeordet til si
. (Sagt med andre ord: |ci
| er antall bits som Huffman-treet bruker på å kode symbolet
si
.) Sannsynligheten for et symbol, p(si), er gitt ved dets relative frekvens, altså frekvensen for symbolet
delt på total frekvens for alle symboler. Skriv en prosedyre expected-codeword-length som tar et
Huffman-tre som input og beregner formelen over. Kalleksempel:
? (expected-codeword-length sample-tree) → 1
3
5|#

(define (expected-codeword-length tree)
  ;;sånn omtrentlig til å begynne med, må graves
  ;;totalvekt
  (weight tree)
  ;;blader med symboler og vekt i par
  ;;bits første symbol
  ((length (encode (caar(huffman-leaves tree)))))
  ;;vekt første symbol
  (cadr (huffman-leaves tree)))