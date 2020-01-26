;; tut1.lisp
;; Michael Valdron

;; Sets all value prints to be capitalized
(setq *print-case* :capitalize)
;; Prints 'Hello World!\n'
(format t "Hello World!~%")
;; Prompts for name
(princ "What is your name? ")
;; Reads characters from CIN buffer and assigns to variable name
(setq name (read))
;; Prints 'Hello <name inputted>!\n'
(format t "Hello ~a!~%" name)

