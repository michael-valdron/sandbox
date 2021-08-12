(ns learn-datomic.core-test
  (:require [clojure.test :as test]
            [learn-datomic.core :as core]
            [datomic.api :as d]
            [environ.core :refer [env]]))

(def db-uri (env :datomic-uri))
(def db-name (env :datomic-db))
(def conn (atom nil))

(defn- setup
  []
  (let [uri (core/build-uri db-uri db-name (env :datomic-password))]
    (when-not (d/create-database uri)
      (d/delete-database uri)
      (d/create-database uri))
    (reset! conn (d/connect uri))
    (d/transact @conn core/schema)))

(defn- teardown
  []  
  (swap! conn d/release)
  (let [uri (core/build-uri db-uri db-name (env :datomic-password))]
    (d/delete-database uri))
  (shutdown-agents))

(defn- fixture
  [test-fn]
  (setup)
  (test-fn)
  (teardown))

(test/use-fixtures :once fixture)

(test/deftest test-assertion
  (test/testing "Assertion"
    (let [result (d/transact @conn core/data)]
      (test/is (map? @result)))))

(test/deftest test-read-1
  (test/testing "Read Names"
    (let [db-context (d/db @conn)
          result (d/q '[:find ?name
                        :where [?e :person/name ?name]]
                      db-context)
          expected #{["Bob"] ["Terry"] ["Mary"] ["Alice"]}]
      (test/is (= expected result)))))

(test/deftest test-read-2
  (test/testing "Persons aged 30 and older"
    (let [db-context (d/db @conn)
          result (d/q '[:find ?name
                        :where [?e :person/name ?name
                                ?e :person/age ?age
                                (>= ?age 30)]]
                      db-context)
          expected #{["Bob"] ["Mary"]}]
      (test/is expected result))))

