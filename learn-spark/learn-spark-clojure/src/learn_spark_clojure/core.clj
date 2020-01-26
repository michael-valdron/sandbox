(ns learn-spark-clojure.core
  (:require [flambo.conf :as conf]
            [flambo.api :as f]))

(def c (-> (conf/spark-conf)
           (conf/master "local[*]")
           (conf/app-name "spark_test")))

(def sc (f/spark-context c))

(def ds (f/parallelize sc [1 2 3 4 5] 4))

(defn proc []
  (-> ds
      (f/reduce (f/fn [x y] (+ x y)))))

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))

(defn add-vec []
  (let [x 67]
    (apply + [1 2 3 4 x])))

(defn -main []
  (do
    (println (proc))
    (println (add-vec))
    (foo "Michael")))
