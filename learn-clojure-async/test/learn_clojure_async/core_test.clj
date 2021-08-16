(ns learn-clojure-async.core-test
  (:require [clojure.test :refer :all]
            [learn-clojure-async.core :refer :all]
            [clojure.core.async :refer [go <!!]]))

(def fib-input 40)
(def fib-expected 102334155)

(defn- fixture
  [test-fn]
  (test-fn)
  (shutdown-agents))

(use-fixtures :once fixture)

(deftest fib-test
  (let [result (fib fib-input)]
    (is (= fib-expected result))))

(deftest fib-future-test
  (let [l (- fib-input 1)
        r (- fib-input 2)
        left (future (fib l))
        right (future (fib r))
        result (+ @left @right)]
    (is (= fib-expected result))))

(deftest fib-delay-test
  (let [l (- fib-input 1)
        r (- fib-input 2)
        left (delay (fib l))
        right (delay (fib r))
        result (+ @left @right)]
    (is (= fib-expected result))))

(deftest fib-promise-test
  (let [l (- fib-input 1)
        r (- fib-input 2)
        left (promise)
        right (promise)
        result (do (deliver left (fib l))
                   (deliver right (fib r))
                   (+ @left @right))]
    (is (= fib-expected result))))

(deftest fib-go-test
  (let [l (- fib-input 2)
        m (- fib-input 3)
        r (- fib-input 4)
        left (go (fib l))
        middle (go (fib m))
        right (go (fib r))
        result (+ (<!! left) (* (<!! middle) 2) (<!! right))]
    (is (= fib-expected result))))
