#|
? z1 --> ((a b) a b)
? z2 --> ((a b) a b)
? (set-car! z2 (cdr z2))
Gjør at z2 får helt lik struktur som z1, også i boks-peker diagram.
|#

(define (nested-match sym lst)
  (cond ((null? lst) 0)
        ((pair? (car lst)) (+ (nested-match sym (car lst))
                              (nested-match sym (cdr lst))))
        ((eq? sym (car lst)) (+ (nested-match sym (cdr lst)) 1))
        (else (nested-match sym (cdr lst)))))
;;(nested-match ’b ’((b) ((b a) b) a))
;;trerekursiv prosess.

((lambda (foo bar)
   (cons bar foo))
  (list 1 2) (* 2 2))
;;--> (4 1 2)
((lambda (foo)
   (display foo)
   (newline)
   ((lambda (foo)
     (display foo))
    (cons 0 (cdr foo))))
 (list 1 2))
;;--> (1 2) og (0 2)
(newline)
(define (compose p1 p2)
  (lambda (x)
    (p1 (p2 x))))

(define (add1 x) (+ x 1))
(define (add100 x) (+ x 100))
((compose add1 add100) 5)

(define (repeat p n)
  (if (= n 1)
      p
      (compose p (repeat p (- n 1)))))

((repeat add1 10) 20)


(define (eval-infix lst)
  ((cadr lst) (car lst) (caddr lst)))

(define exp1 (list 1 + 3))
(define exp2 (list 10 / 5))
(eval-infix exp1)
(eval-infix exp2)

(define (scale-tail x seq)
  (define (init in out)
    (if (null? in)
        (reverse out)
        (init (cdr in) (cons (* (car in) x) out))))
  (init seq '()))
(define foo (list 1 2 3 4))
(scale-tail 3 foo) 

(define (scale x seq)
  (if (null? seq)
      '()
      (cons (* (car seq) x) (scale x (cdr seq)))))

(define (scale-high x seq)
  (map (lambda (y) (* x y)) seq))

(define (scale! x seq)
  (define (iter rest)
    (if (null? rest)
        seq
        (begin (set-car! rest (* (car rest) x))
               (iter (cdr rest)))))
  (iter seq))
(scale! 5 '(1 2 3 4))

(define (scale-stream x seq)
  (if (stream-null? seq)
      the-empty-stream
      (cons-stream (* (stream-car seq) x) (scale x (stream-cdr seq)))))

#|
Vanlig rekursjon: 4 cons-celler
Destruktivt: 0 cons-celler
Strømmer: 1 cons-celle


for-each og map gjør begge operasjoner på listeelementer, men kun sistnevnte
returnerer en ny list. for-each har ingen returverdi, og er dermed ikke en
funksjonell prosedyre slik som map som kalles for sine returverdier. for-each
vil typisk kalles for sideeffekter som set!, display osv.
|#

(define (make-accumulator)
  (let ((sum 0)
        (history '()))
    (lamda (message num)
           (cond ((eq? message 'add) ((set! sum (+ sum num))
                                      (set! history (cons sum history))))
                 ((eq? message 'sub) ((set! sum (- sum num))
                                      (set! history (cons sum history))))
                 ((eq? message 'undo) ((set! history (nth-cdr history num))
                                       (set! sum (car history)))))
           sum)))

(define (nth-cdr items n) ; hjelpeprosedyre
  (if (zero? n)
      items
      (nth-cdr (cdr items) (- n 1))))

#|
Ved normal-order evaluation kalles prosedyrer på selve argumentuttrykkene; den
faktiske evalueringen av argumentene utsettes til vi trenger verdiene deres i
prosedyrekroppen. Dermed risikerer man at samme argument-uttrykk evalueres
flere ganger dersom det brukers flere steder i prosedyrekoppen. Ved å memoisere
evalueringen av disse unngår man dette og hvert uttrykk evalueres maksimalt én
gang. Ved applicative-order/eager evaluering oppstår ikke denne
problemstillingen siden argumenttuttrykk evalueres før prosedyrer kalles på
verdiene deres; hvert uttrykk evalueres dermed garantert kun én gang og
memoisering har ingen hensikt.
|#






























