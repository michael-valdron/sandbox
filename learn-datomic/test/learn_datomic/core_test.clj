(ns learn-datomic.core-test
  (:require [clojure.test :refer :all]
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
    (doto @conn
      (d/transact core/schema)
      (d/transact core/data))))

(defn- teardown
  []
  (swap! conn d/release)
  (let [uri (core/build-uri db-uri db-name (env :datomic-password))]
    (d/delete-database uri)))

(defn- fixture-foreach
  [test-fn]
  (setup)
  (test-fn)
  (teardown))

(defn- fixture-once
  [test-fn]
  (test-fn)
  (shutdown-agents))

(use-fixtures :each fixture-foreach)
(use-fixtures :once fixture-once)

(deftest test-1-assertion
  (testing "Assertion"
    (let [result (d/transact @conn [{:person/name "Luke"
                                     :person/age 26}])]
      (is (map? @result)))))

(deftest test-2-read
  (testing "Read Names"
    (let [db-context (d/db @conn)
          result (d/q '[:find ?name
                        :where [?e :person/name ?name]]
                      db-context)
          expected #{["Bob"] ["Terry"] ["Mary"] ["Alice"]}]
      (is (= expected result)))))

(deftest test-3-read
  (testing "Persons aged 30 and older"
    (let [db-context (d/db @conn)
          result (d/q '[:find ?name
                        :where
                        [?e :person/name ?name]
                        [?e :person/age ?age]
                        [(>= ?age 30)]]
                      db-context)
          expected #{["Bob"] ["Mary"]}]
      (is (= expected result)))))

(deftest test-4-read
  (testing "Count persons under 30."
    (let [db-context (d/db @conn)
          result (-> (d/q '[:find (count ?e)
                            :where
                            [?e :person/age ?age]
                            [(< ?age 30)]]
                          db-context)
                     (flatten)
                     (first))
          expected 2]
      (is (= expected result)))))

(deftest test-5-retract
  (testing "Retract person named Bob."
    (let [result (d/transact @conn [[:db/retractEntity [:person/name "Bob"]]])]
      (is (map? @result)))
    (let [db-context (d/db @conn)
          result (d/q '[:find ?name
                        :where
                        [?e :person/name ?name]
                        [(= ?name "Bob")]]
                      db-context)]
      (is (empty? result)))))
