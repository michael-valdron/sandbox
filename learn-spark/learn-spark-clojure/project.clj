(defproject learn-spark-clojure "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [yieldbot/flambo "0.8.2"]
                 [io.aviso/pretty "0.1.36"]]
  :plugins [[io.aviso/pretty "0.1.36"]]
  :profiles {:provided {:dependencies [[org.apache.spark/spark-core_2.11 "2.2.0"]]}
             :dev {:aot [learn-spark-clojure.core]}}
  :repl-options {:init-ns learn-spark-clojure.core}
  :main learn-spark-clojure.core)
