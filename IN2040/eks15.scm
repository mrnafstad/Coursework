(define (list1? lst)
  (or (null? lst) (and (pair? lst)
                       (list? (cdr lst)))))
(list1? '(1 2 3))
(list1? 2)

;;(list1? foo burde returnere #f, siden car peker på seg selv og vi aldri
;; kommer til den tomme lista

(define (deep-map proc nested)
  (cond ((null? nested) '())
        ((pair? (car nested)) (cons (deep-map proc (car nested))
                                    (deep-map proc (cdr nested))))
        (else (cons (proc (car nested)) (deep-map proc (cdr nested))))))

;;deep-map gir en tre-rekursiv prosess. Antall trinn vokser lineært med antall
;;noder

(define (replace-tail x y lst)
  (define (iter in out)
    (if (null? in)
        (reverse out)
        (iter (cdr inn)
              (cons (if (eq? x (car in))
                        y
                        (car in))
                    out))))
  (iter lst '()))

(define (replacef1 x y lst)
  (cond ((null? lst) '())
        ((eq? x (car lst)) (cons y (replacef1 x y (cdr lst))))
        (else (replacef1 x y (cdr lst)))))

(define (replacef2 x y lst)
  (map (lambda (z) (if (eq? x z) y z)) lst))

(define (replace! x y lst)
  (define (iter seq)
    (if (null? seq)
        seq
        (begin
          (if (eq? x (car seq))
              (set-car! seq y))
          (iter (cdr seq)))))
  (iter lst))

(define (replace-stream x y lst)
  (cond ((stream-null? lst) the-empty-stream)
        ((eq? x (stream-car lst))
         (cons-stream y
                      (replace-stream x y (stream-cdr lst))))
        (else (replace-stream x y (stream-cdr lst)))))

#|
Vanlig rekursjon, funksjonell: 4 cons-celler
Destruktiv: Ingen cons-celler
Strømmer: 1 cons-celle
|#

#|
En rent funksjonell prosedyre kalles for sin returverdi og skal ikke ha noen
side-effekter. Ikke-funksjonelle prosedyrer kan gjerne ha side-effekter,
slik som over hvor vi har brukt set-car! for å endre verdier i en allerede
eksisterende liste.

begin vil kun returnere verdien til det sist evaluerte uttrykket, derfor er det
ingen plass for begin i rent funksjonell kode.

Høyereordens prosedyrer er prosedyrer som bruker prosedyrer som argumenter og
annvender det på andre argumenter.
|#

(define (cons x y)
  (lambda (message)
    (cond ((eq? message 'car) x)
          ((eq? message 'cdr) y)
          ((eq? message 'set-car!) (lambda (v) (set! x v)))
          ((eq? message 'set-cdr!) (lambda (v) (set! y v))))))
(define (car p)
  (p 'car))
(define (cdr p)
  (p 'cdr))

(define (set-car! p v)
  ((p 'set-car!) v))

(define (set-cdr! p v)
  ((p 'set-cdr!) v))

(define (make-queue n)
  (let ((front '())
        (rear '())
        (count 0))
    (lambda (x)
      (cond ((null? front)
             (set! front (list x))
             (set! rear front))
            (else
             (let ((tmp (list x)))
               (set-cdr! rear tmp)
               (set! rear tmp))))
      (if (= count n)
          (set! front (cdr front))
          (set! count (+ count 1)))
      front)))

(define (add-inf-streams s1 s2)
  (cons-stream (+ (stream-car s1) (stream-car s2))
               (add-inf-streams (stream-cdr s1) (stream-cdr s2))))
#|
Prosedyren add-inf-streams mangler ett basistilfelle, men dette er ikke
nødvendig med uendelige strømmer, siden de per definisjon aldri slutter.

Om vi bytter ut cons-stream med cons vil ikke prosedyren terminere, siden
uendelige strømmer krever uendelig minnebruk.

Ones blir en uendelig strøm med 1'ere: (1 . #<promise>)
integers vil ikke terminere siden vi prøver å bruke en udefinert verdi i en
aritmetisk prosess (+).
|#
























































