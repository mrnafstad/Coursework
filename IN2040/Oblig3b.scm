;; Gruppe: Tommy Myrvik og Halvor Nafstad
;; Alle prosedyrer er implementert direkte i evaluatorTH.scm, som vi også har
;; levert sammen med Oblig3b.scm. Det er denne evaluatoren som lastes i linje 5
;; Oblig3b.scm. Kopier av prosedyrene ligger kommentert ut under, i oppgavenes
;; rekkefølge.

(load "evaluatorTH.scm")

;; (foo 2 square)
;; Dette kallet gir output 0, ettersom variablen med navn 'cond' blir gitt
;; verdi 2, som gjør at første predikament i cond-prosedyren gir true. I denne
;; evaulatoren sjekkes det hva slags datastrukturer argumentene er - i dette
;; tilfellet er 'cond' en variabel, og 'else' en prosedyre. Prosedyren cond
;; tolkes dermed som nettopp det - en prosedyre, og bruker cond-else
;; funksjonaliteten vi er vant med fra vanlig Scheme.

;; (foo 4 square)
;; Her slår else-klausulen ut, og input-prosedyren 'else' utføres
;; på 'cond'-variablen. Input-prosedyren i dette tilfellet kvadrerer input'en,
;; som her gir 16.

;; (cond ((= cond 2) 0)
;;       (else (else 4)))
;; Denne cond-klausulen er den samme som i kroppen til 'foo', men forskjellen
;; er at denne bruker 'cond' og 'else' som allerede er definert i den globale
;; rammen i stedet for å ta disse som en input. 'cond'-variablen er her definert
;; som 3, mens 'else' er nå en prosdyre som deler input-verdien på 2. 'cond' er
;; ikke lik 2, slik at (else 4) = 2 returneres.


#|
En fullstendig endring av special-form? og eval-special-form følger til slutt i
besvarelsen. 

Oppgave 2
a) I denne oppgaven legger vi 1+ og 1- rett inn i lista over primitive
prosedyrer:
(define primitive-procedures
  (list (list 'car car)
        (list 'cdr cdr)
        (list 'cons cons)
        (list 'null? null?)
        (list 'not not)
        (list '+ +)
        (list '- -)
        (list '* *)
        (list '/ /)
        (list '= =)
        (list 'eq? eq?)
        (list 'equal? equal?)
        (list 'display 
              (lambda (x) (display x) 'ok))
        (list 'newline 
              (lambda () (newline) 'ok))
        ;;Oppgave 2 a 1+ og 1-
        (list '1+
              (lambda (x) (+ x 1)))
        (list '1-
              (lambda (x) (- x 1)))
        ))

b) Å installere primitiver kan gjøres ved å bruke prosedyren

(define (install-primitive! name proc)
  (define-variable!
    name
    (list 'primitive proc)
    the-global-environment))

i vanlig repl, etter initialisering av det globale miljøet, før man restarter
meta-repl. Om man kjører kodesnutten under, eller bare

(install-primitive! '2+ (lambda (x) (+ x 2)))

etter at det globale miljøet er satt vil man ha en primitiv prosedyre 2+
tilgjengelig ved start av meta-repl.

|#
(set! the-global-environment (setup-environment))
(install-primitive! '2+ (lambda (x) (+ x 2)))
(read-eval-print-loop)
#|
Oppgave 3
a) and og or implementeres med prosedyrene
(define (eval-and exp env)
  (define (eval-expression-list exp)
    (cond ((null? exp) #t)
          ((last-exp? exp) (mc-eval (first-exp exp) env))
          ((mc-eval (first-exp exp) env) (eval-expression-list (rest-exps exp)))
          (else #f)))
  (eval-expression-list (cdr exp)))

(define (eval-or exp env)
  (define (eval-expression-list exp)
    (cond ((null? exp) #f)
          ((last-exp? exp) (mc-eval (first-exp exp) env))
          ((mc-eval (first-exp exp) env) #t)
          (else (eval-expression-list (rest-exps exp)))))
    (eval-expression-list (cdr exp)))

(define (and? exp)
  (tagged-list? exp 'and))

(define (or? exp)
  (tagged-list? exp 'or))

og tilleggene
        ((or? exp) #t)
        ((and? exp) #t)
og
        ((or? exp) (eval-or exp env))
        ((and? exp) (eval-and exp env))
i henholdsvis special-form? og eval-special-form.



b)
For å implementere den alternative if syntaxen med elsif grener endrer vi
eval-if og medhørende prosedyrer til følgende:

(define (eval-if2 exp env)
  (if (true? (mc-eval (if-predicate2 exp) env))
      (mc-eval (if-consequent2 exp) env)
      (if (else? (cddddr exp))
          (mc-eval (if-alternative2 exp) env)
          (eval-if2 (cddddr exp) env))))

(define (else? exp)
  (tagged-list? exp 'else))

(define (if-alternative2 exp)
  (if (not (null? (cddr (cdddr exp))))
      (caddr (cdddr exp))
      'false))

(define (if-predicate2 exp) (cadr exp))

(define (if-consequent2 exp) (cadddr exp))

Her trenger vi bare å endre én linje i eval-special-forms, nemnlig
        ((if? exp) (eval-if2 exp env))
linjen i special-form? som representerer if statements har ikke behov for noen
endring, siden 'if fortsatt er det første symbolet i et if statement.



c) let implementeres med prosedyrene
(define (let? exp)
  (tagged-list? exp 'let))
(define (let-initials exp)
  (map cadr (cadr exp)))
(define (let-parameters exp)
  (map car (cadr exp)))
(define (let-body exp)
  (cddr exp))

(define (let->lambda exp env)
  (mc-apply (make-procedure (let-parameters exp)
                        (let-body exp)
                        env)
        (let-initials exp)))
(define (eval-let exp env)
  (mc-eval (let->lambda exp env) env))

og tilleggene
        ((let? exp) #t)
og
        ((let? exp) (mc-eval (eval-let exp env) env))
i henholdsvis special-form? og eval-special-form.


d) Den alternative syntaxen til let implementeres med prosedyrene:
(define (and? exp)
  (tagged-list? exp 'and))

(define (let->lambda2 exp env)

  (define parameters '())
  (define initials '())
  (define body '())
  
  (define (set-params-inits exp)
    (if (or (and? exp) (let? exp))
        (begin
          (set! parameters (cons (cadr exp) parameters))
          (set! initials (cons (cadddr exp) initials))
          (set-params-inits (cddddr exp)))
        (set! body (cdr exp))))

  (set-params-inits exp)
  (mc-apply (make-procedure parameters
                            body
                            env)
            initials))
(define (eval-let2 exp env)
  (mc-eval (let->lambda2 exp env) env))

og endringen av en linje i eval-special-form:
        ((let? exp) (mc-eval (eval-let2 exp env) env))
der den eneste endringen er hvilken prosedyre vi mater inn i mc-eval.


Fullstendig endring av eval-special-form og special-form?:

(define (eval-special-form exp env)
  (cond ((quoted? exp) (text-of-quotation exp))
        ((assignment? exp) (eval-assignment exp env))
        ((definition? exp) (eval-definition exp env))
        ;;((if? exp) (eval-if exp env))

;;Neste linje inneholder endring for å bruke alternativ if-syntax for oppgave 3b
;;Normal if syntax er kommentert ut i linjen over
        ((if? exp) (eval-if2 exp env))

;; De neste to linjene er endringer for oppgave 3a
        ((or? exp) (eval-or exp env))
        ((and? exp) (eval-and exp env))

        ((lambda? exp)
         (make-procedure (lambda-parameters exp)
                         (lambda-body exp)
                         env))
;; Neste linje er endring for oppgave 3c og d. Alternativ let-syntax er
;; kommentert ut
        ((let? exp) (mc-eval (eval-let exp env) env))
        ;;((let? exp) (mc-eval (eval-let2 exp env) env))

        ((begin? exp) 
         (eval-sequence (begin-actions exp) env))
        ((cond? exp) (mc-eval (cond->if exp) env))))

(define (special-form? exp)
  (cond ((quoted? exp) #t)
        ((assignment? exp) #t)
        ((definition? exp) #t)
        ((if? exp) #t)
        ((lambda? exp) #t)

;; Neste linje inneholder endring for implementering av let i oppgave 3c og d
        ((let? exp) #t)

        ((begin? exp) #t)
        ((cond? exp) #t)

;; De to neste linjene inneholder endring for oppgave 3a
        ((or? exp) #t)
        ((and? exp) #t)

        (else #f)))
|#