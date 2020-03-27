(load "prekode3a.scm")
;; Oppgave 1
;; a, b)
#|
(define (mem comm proc)
  (define (memoize proc)
    (let ((table (make-table))
          (original proc))
      ;;få in args og riktig prosedyre for rekursjon
      (lambda (x)
        (let ((prev-result (lookup x table)))
          (or prev-result
              (let ((result (proc x)))
                (insert! x result table)
                result))))))
  ;;(define (unmemoize proc))

  (cond ((eq? comm 'memoize) (memoize proc))
        ((eq? comm 'unmemoize) (unmemoize proc))))
|#
(define mem
 (let ((trigger (list 'trigger))                
       (check   (list 'check))) 
  (lambda (mem-it func)                               ;; changed
    (case mem-it
      ((memoize)
       (display " --- memoize ---\n")
       (let ((table (make-table)))                    ;; changed
        (lambda args   (if (eq? check trigger) func   ;; changed 
         (let ((prev-com-res (lookup args table))) 
           (or prev-com-res                            
               (let ((result (apply func args)))      ;; changed 
                 (insert! args result table)
                 result)))))))                        ;; changed
      ((unmemoize)
       (display " --- unmemoize --- \n")
       (let ((c check))                            ;; changed...
         (set! check trigger)  ;; or: (fluid-let ((check trigger))
         (let ((f (func)))     ;;       (func))
           (set! check c)
           f)))                                    ;; ...changed
      (else
       (display " -- Unknown command! --\n"))))))
(set! fib (mem 'memoize fib))
(fib 3)
(fib 3)
(fib 2)
(fib 4)
(set! fib (mem 'unmemoize fib))
(fib 3)

;;(set! test-proc (mem 'memoize test-proc))
;;(test-proc 40 41 42 43 44)
;;(test-proc 40 41 42 43 44)

;;(test-proc 42 43 44)

;; c) Grunnen til at at mem-fib ikke virker som den skal her er at memoiseringen
;;    blir gjort på fib. Når man i steden bruker set! fib som tidligere memoiseres
;;    selve prosedyren, fremfor memoisering av en ny variabel. Vi ser at (mem-fib 3)
;;    gir "riktig" resultat i det andre kallet, mens (mem-fib 2) ikke gir riktig resultat.
;;    Dette viser at (mem-fib 3) blir memoisert, fremfor selve fib prosedyren.

;; d)
(define (greet . args)
  ;;generer default table
  (define formals (make-table))
  (insert! 'time "day" formals)
  (insert! 'title "friend " formals)

  (define (print-greeting)
    (display "good ")
    (display (lookup 'time formals))
    (display " ")
    (display (lookup 'title formals))
    (newline))
  
  (define (handle-args lst)
    (if (null? lst)
        (print-greeting)
        (begin
          (insert! (car lst) (cadr lst) formals)
          (handle-args (cddr lst)))))
  (if (null? args)
      (print-greeting)
      (handle-args args)))
(greet)
(greet 'title "sir" 'time "morning")
(greet 'time "evening")
(greet 'title "sir" 'time "morning")
(greet 'time "afternoon" 'title "dear")
;; Oppgave 2
;; a)
(define (list-to-stream lst)
  (if (null? lst)
      '()
      (cons-stream (car lst) ( list-to-stream (cdr lst)))))

(define (stream-to-list strm . args)
  (if (null? args)
      (if (stream-null? strm)
          '()
          (cons (stream-car strm) (stream-to-list (stream-cdr strm))))
      (let ((numb (car args)))
        (if (= numb 0)
            '()
            (cons (stream-car strm) (stream-to-list (stream-cdr strm) (- numb 1)))))))

(list-to-stream '(1 2 3 4 5))
(stream-to-list (stream-interval 10 20))
(show-stream nats 15)
(stream-to-list nats 10)

;; b)



(define (stream-map proc . argstreams)
  (if (memq #t (map stream-null? argstreams))
      the-empty-stream
      (cons-stream
       (apply proc (map stream-car argstreams))
       (apply stream-map
              (cons proc (map stream-cdr argstreams))))))


;; c) Ett mulig problem her ligger i at memq rekursivt vil igjennom hele strømmen for
;;    hvert element. Dette gjør at hele fordelen med strømmer forsvinner, ved at hele
;;    strømmen må realiseres for hvert symbol som sjekkes.

;; d)

(define (remove-duplicates stream)
  (if (stream-null? stream)
      the-empty-stream
      (cons-stream (stream-car stream)
                   (remove-duplicates
                    (stream-filter (lambda (x)
                                     (not (eq? x (stream-car stream))))
                                   (stream-cdr stream))))))

(show-stream (remove-duplicates (list-to-stream '(1 2 3 1 3))))

;; e)
(newline)
(define x
  (stream-map show
              (stream-interval 0 10)))
(stream-ref x 5)
(newline)
(stream-ref x 7)

#|
? (stream-ref x 5)
-> 0
-> 1
-> 2
-> 3
-> 4
-> 5
-> 5
Her får vi først skrevet ut de første 6 tallene mellom 0 og 10 (siden telleren
begynner på 0) før det sjette elementet returneres og skrives ut. Grunnen til at
hele intervallet ikke skrives ut er bruken av stream-map som gjør at alle kallene
på show ligger i en strøm, og dermed ikke realiseres før de kalles på.

? (stream-ref x 7)
-> 6
-> 7
-> 7
Usikker på hvorfor ikke hele strømmen skrives ut her
|#

;; f) 

(define (mul-streams . args)
  (apply stream-map * args))

(show-stream (mul-streams (list-to-stream '(1 2 3))
                          (list-to-stream '(2 2 2))
                          (list-to-stream '(3 3))))

;; g)
(define factorials
  (cons-stream 1 (mul-streams factorials nats)))

(stream-ref factorials 5) 