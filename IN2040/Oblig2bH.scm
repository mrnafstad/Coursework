;; Oppgave 1

;; a)

(define (make-counter)
  (let ((count 0))
    (lambda ()
      (set! count (+ count 1))
      count)))

(define count 42)
(define c1 (make-counter))
(define c2 (make-counter))
(c1)
(c1)
(c1)
count
(c2)


;; Oppgave 2
;; a
(define (make-stack stack)
  (define (add-to-stack lst)
    (if (null? lst)
        (set! stack stack)
        (let ((element (car lst)))
          (set! stack (cons element stack))
          (add-to-stack (cdr lst)))))
  
  (define (stack-delete!)
    (if (empty-stack? stack)
        "Error: stack empty"
        (set! stack (cdr stack))))
  
  (lambda args
    (cond
      ;;trenger en prosedyre som rekursivt graver seg ned i args for å legge det siste elementet i cdr args
      ;;først i stack
          ((eq? (car args) 'push!) (let ((objects (cdr args)))
                                     (add-to-stack objects)))      
          ((eq? (car args) 'pop!) (stack-delete!))
          ((eq? (car args) 'stack) stack)
          (else ("Symbol used incorrectly")))))

(define (empty-stack? stack)
 (null? stack))

(define s1 (make-stack (list 'foo 'bar)))
(define s2 (make-stack '()))

(s1 'pop!)

(s1 'stack)

(s2 'pop!) ;; popper en tom stack

(s2 'push! 1 2 3 4)

(s2 'stack)

(s1 'push! 'bah)

(s1 'push! 'zap 'zip 'baz)

(s1 'stack)

(display "---")
(newline)
;;b
(define (push! . args)
  ;;denne gir litt rar stack som den står nå
  (let ((stk (car args))
        (obj (cdr args)))
    (apply stk 'push! obj)))

(define (pop! stk)
  (stk 'pop!))

(define (stack stk)
  (stk 'stack))

(pop! s1)
(stack s1)
(push! s1 'foo 'faa)
(stack s1)

;; Oppgave 3

#|
 a) For boks-peker diagram se pdf. I linjen med set-cdr endrer vi det siste elementet i lista,
    altså 'e, til å bli de tre siste elementene i lista før 'e, altså 'b 'c 'd. Dermed blir
    (list-ref bar 0) og (list-ref bar 3) stående som 'a og 'd, mens vi på (list-ref bar 4) får 'b
    i stedet for 'e og (list-ref bar 5) gir 'c i stedet for den tomme lista.

 b) I (set-car! bah (cdr bah)) setter vi det første elementet i bah til å være cdr bah, som er (a towel).
    Etter (set-car! (car bah) 42) får vi ((42 towel) 42 towel) siden (car bah) og (cdr bah) peker på den
    samme lista, hvor vi setter car til 42.

 c)
|#

(define (cycle? lst)
  (define seen '())
  (define (cycle-aux lst2)
    (cond ((list? lst2) #f)
          ;;Er det juks å bruke list? her?
          ((memq (car lst2) seen) #t)
          (else (set! seen (cons (car lst2) seen))
                (cycle-aux (cdr lst2)))))
  (cycle-aux lst))



(define bar (list 'a 'b 'c 'd 'e))
(set-cdr! (cdddr bar) (cdr bar))

(define bah (list 'bring 'a 'towel))
(set-car! bah (cdr bah))
(set-car! (car bah) 42)

(cycle? '(hey ho))
(cycle? '(la la la))
(cycle? bah)
(cycle? bar)

#|
 d) Her regner jeg med at vi dere er ute etter det faktum at en sirkulær liste aldri "avsluttes"
    med den tomme lista.

 e)
|#

(define (last-pair lst)
  (if (null? (cdr lst))
      lst
       (last-pair (cdr lst))))

(define (make-ring lst)
  (let (( ring (set-cdr! (last-pair lst) lst)))
    (define (right)
      (define seen '())
      (define temp ring)
      (define (cycle-through ring)
        (if (null? ring)
            (display "Ring is empty")
            (if (memq (car ring) seen)
                (set! ring temp)
                ((set! seen (cons (car ring) seen))
                 (set! temp ring)
                 (cycle-through (cdr ring))))))
      (cycle-through ring))

    (define (top1)
      (car (ring)))

    (define (left)
      ((if (not (null? ring))
           (begin
             (set! ring (cdr ring))
             (top1)))))
  
    (lambda args
      (cond 
            ((eq? (car args) 'top) top1)
            ((eq? (car args) 'left-rotate!) left)
            ((eq? (car args) 'right) right)
            ((eq? (car args) 'delete!) (set! ring (cdr ring)))
            ((eq? (car args) 'fetch) ring)))
    ring))

(define (top ring)
  (ring 'top))

(define (delete! ring)
  (ring 'delete!))

(define (right-rotate! ring)
  (ring 'right-rotate!))

(define (left-rotate! ring)
  (ring 'left-rotate!))

(define r1 (make-ring '(1 2 3 4)))
(define r2 (make-ring '(a b c d)))

(top r1)
(top r2)
(display "---")
(newline)
(left-rotate! r1)
(top r1)
(delete! r1)
(top r1)

(left-rotate! r1)
(top r1)
(left-rotate! r1)
(top r1)























