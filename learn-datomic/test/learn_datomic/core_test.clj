(ns learn-datomic.core-test
  (:require [clojure.test :as test]
            [clojure.core.async :refer [<!!]]
            [learn-datomic.core :as core]
            [environ.core :refer [env]]))

(def db-uri (env :datomic-uri))
(def db-name (env :datomic-db))
(def conn (atom nil))
(def schema
  [{:db/ident :inv/sku
    :db/valueType :db.type/long
    :db/unique :db.unique/identity
    :db/cardinality :db.cardinality/one}
   {:db/ident :inv/color
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one}
   {:db/ident :inv/size
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one}
   {:db/ident :inv/type
    :db/valueType :db.type/ref
    :db/cardinality :db.cardinality/one}])
(def data 
  [{:inv/sku 0
    :inv/color :red
    :inv/size :small
    :inv/type :hat}
   {:inv/sku 1
    :inv/color :blue
    :inv/size :medium
    :inv/type :shirt}])

(defn- setup
  []
  (if-not (<!! (core/exists? db-uri db-name :password (env :datomic-password)))
    (when (<!! (core/create-db db-uri db-name :password (env :datomic-password)))
      (reset! conn (core/create-connection db-uri db-name :password (env :datomic-password))))
    (reset! conn (<!! (core/create-connection db-uri db-name :password (env :datomic-password))))))

(defn- teardown
  []
  (reset! conn (core/destroy-connection @conn)))

(defn- fixture 
  [test-fn]
  (setup)
  (test-fn)
  (teardown))

(test/use-fixtures :once fixture)

(test/deftest test-connection
  (test/testing "Connection."
    (test/is (not (nil? @conn)))))

(test/deftest test-schema-assertion
  (test/testing "Schema Assertion."
    (let [result (core/transact @conn schema)]
      (test/is @result))))

(test/deftest test-data-assertion
  (test/testing "Data Assertion."
    (let [result (core/transact @conn data)]
      (test/is @result))))
