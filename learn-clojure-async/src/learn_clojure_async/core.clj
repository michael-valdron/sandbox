(ns learn-clojure-async.core)

(defn fib
  [n]
  (cond
    (< n 0) (println "Incorrect number.")
    (or (= n 0) (= n 1)) n
    :default (+ (fib (- n 1)) (fib (- n 2)))))
