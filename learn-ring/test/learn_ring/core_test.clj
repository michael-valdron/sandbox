(ns learn-ring.core-test
  (:require [clojure.test :refer :all]
            [learn-ring.core :refer :all]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [ring.mock.request :as mock]))

(deftest home-test
  (let [result (app (mock/request :get "/"))
        expected {:status 200
                  :headers {"Content-Type" "text/html"}
                  :body "Hello World!"}]
    (is (= expected result))))

(deftest method-not-allowed-test
  (let [result (app (mock/request :post "/"))
        expected {:status 405
                  :headers {"Content-Type" "text/html"}
                  :body "POST for '/' not allowed!"}]
    (is (= expected result))))

(deftest not-found-test
  (let [result (app (mock/request :get "/404"))
        expected {:status 404
                  :headers {"Content-Type" "text/html"}
                  :body "Not Found!"}]
    (is (= expected result))))

(deftest public-test
  (let [result (-> (app (mock/request :get "/static/test.json"))
                   (update :headers #(select-keys % ["Content-Type"]))
                   (update :body (comp #(json/read % :key-fn keyword)
                                       io/reader)))
        expected {:status 200
                  :headers {"Content-Type" "application/json"}
                  :body {:name "ring"}}]
    (is (= expected result))))
