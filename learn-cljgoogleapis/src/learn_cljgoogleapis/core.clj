(ns learn-cljgoogleapis.core
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [environ.core :refer [env]])
  (:import [com.google.api.client.googleapis.auth.oauth2 GoogleCredential]
           [com.google.api.client.googleapis.javanet GoogleNetHttpTransport]
           [com.google.api.client.json.jackson2 JacksonFactory]
           [com.google.api.services.drive
            Drive$Builder
            DriveScopes]))

(defn- get-creds
  []
  (let [creds-file (env :creds-path)
        creds-path (when-not (or (nil? creds-file)
                                 (string/blank? creds-file))
                     (io/resource creds-file))
        in (when-not (nil? creds-path)
             (io/input-stream creds-path))]
    (when-not (nil? in)
      (.. (GoogleCredential/fromStream in)
          (createScoped [DriveScopes/DRIVE])))))

(defn -main
  [& args]
  (if-let [folder-id (env :folder-id)]
    (let [http-trans (GoogleNetHttpTransport/newTrustedTransport)
          json-factory (JacksonFactory/getDefaultInstance)]
      (if-let [creds (get-creds)]
        (let [service (.. (Drive$Builder. http-trans json-factory creds)
                          (setApplicationName "DevSandbox")
                          (build))
              q (format "'%s' in parents and trashed = false"
                        folder-id)
              result (.. service
                         (files)
                         (list)
                         (setQ q)
                         (execute))
              files (.getFiles result)]
          (println (mapv #(vector (.getId %) (.getName %)) files)))
        (println (str "Problem with creating credentials, please ensure ':creds-path' "
                      "is an enviroment variable and that the path specified in ':creds-path' "
                      "exists."))))
    (println "':folder-id' needs to be specified as an enviroment variable.")))
