(ns learn-datomic.core
  (:require  [clojure.string :as str]))

(def schema [{:db/ident :person/name
              :db/valueType :db.type/string
              :db/cardinality :db.cardinality/one}
             {:db/ident :person/age
              :db/valueType :db.type/long
              :db/cardinality :db.cardinality/one}])
(def data [{:person/name "Bob"
            :person/age 34}
           {:person/name "Terry"
            :person/age 22}
           {:person/name "Mary"
            :person/age 35}
           {:person/name "Alice"
            :person/age 21}])

(defn- trim-first
  ([s] (str/triml s))
  ([s c]
   (let [first-c (first s)]
     (if (= first-c c)
       (rest s)
       s))))

(defn- trim-end
  ([s] (str/trimr s))
  ([s c]
   (let [last-c (last s)]
     (if (= last-c c)
       (drop-last s)
       s))))

(defn build-uri
  ([db-uri db-name]
   (build-uri db-uri db-name nil))
  ([db-uri db-name password]
   (let [db-uri (trim-end db-uri)
         db-name (trim-first db-name)]
     (if-not (nil? password)
       (format "%s/%s?password=%s"
               db-uri db-name password)
       (format "%s/%s" db-uri db-name)))))
