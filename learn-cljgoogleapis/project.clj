(defproject learn-cljgoogleapis "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [environ "1.2.0"]

                 [com.google.api-client/google-api-client "1.23.0"]
                 [com.google.apis/google-api-services-drive "v3-rev110-1.23.0"]]
  :repl-options {:init-ns learn-cljgoogleapis.core}
  :main learn-cljgoogleapis.core)
