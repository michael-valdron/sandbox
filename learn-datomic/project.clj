(defproject learn-datomic "0.0.0"
  :description "learn-datomic sandbox project."
  :license {:name "Datomic Free Edition License"
            :url "https://www.datomic.com/datomic-free-edition-license.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/core.async "1.3.618"]
                 [environ "1.2.0"]
                 [com.datomic/datomic-free "0.9.5697"]]
  :repl-options {:init-ns learn-datomic.core})
