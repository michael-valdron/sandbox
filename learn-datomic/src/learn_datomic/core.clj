(ns learn-datomic.core
  (:require [clojure.core.async :as async]
            [datomic.api :as d]))

(defn- async-operation
  [f]
  (let [c (async/chan)]
    (async/go
      (try
        (async/>! c (f))
        (catch Exception e
          (println (.getMessage e))
          (async/close! c))))
    c))

(defn- connection-operation
  [uri db-name opts f]
  (let [m-opts (apply hash-map opts)]
    (async-operation
     (fn []
       (let [base-uri (str uri db-name)]
         (->> (if-let [password (:password m-opts)]
                (format "%s?password=%s" base-uri password)
                base-uri)
              (f)))))))

(defn exists?
  [uri db-name & opts]
  (connection-operation
   uri "*" opts
   (fn [uri]
     (->> (d/get-database-names uri)
          (some (partial = db-name))))))

(defn create-db
  [uri db-name & opts]
  (connection-operation
   uri db-name opts
   (fn [uri] (d/create-database uri))))

(defn create-connection
  [uri db-name & opts]
  (connection-operation
   uri db-name opts
   (fn [uri] (d/connect uri))))

(defn destroy-connection
  [conn]
  (when-not (nil? conn)
    (d/release conn)))

(defn transact
  [conn data]
  (d/transact conn data))
