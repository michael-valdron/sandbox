(defproject learn-ring "0.0.0"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/data.json "2.4.0"]
                 [ring/ring-core "1.9.4"]
                 [ring/ring-jetty-adapter "1.9.4"]
                 [ring/ring-mock "0.4.0"]
                 [metosin/reitit-ring "0.5.15"]]
  :plugins [[lein-ring "0.12.5"]]
  :repl-options {:init-ns learn-ring.core}
  :ring {:handler learn-ring.core/app}
  :main learn-ring.core)
