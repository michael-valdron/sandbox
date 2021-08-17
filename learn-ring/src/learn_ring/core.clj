(ns learn-ring.core
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.resource :refer [wrap-resource]]
            [ring.middleware.content-type :refer [wrap-content-type]]
            [ring.middleware.not-modified :refer [wrap-not-modified]]
            [ring.util.response :as resp]
            [reitit.ring :refer [router ring-handler create-default-handler]]
            [clojure.string :as str]))

(defn method-not-allowed
  [request]
  (-> (format "%s for '%s' not allowed!"
              (-> request :request-method name str/upper-case)
              (:uri request))
      (resp/response)
      (resp/status 405)
      (resp/content-type "text/html")))

(defn not-acceptable
  [request]
  (-> (format "'%s' not acceptable!" (:uri request))
      (resp/response)
      (resp/status 406)
      (resp/content-type "text/html")))

(defn not-found
  [request]
  (-> (resp/not-found "Not Found!")
      (resp/content-type "text/html")))

(defn home
  [request]
  (-> (resp/response "Hello World!")
      (resp/content-type "text/html")))

(def app-router
  (router
    [["/" {:get home}]]))

(def app
  (-> app-router
      (ring-handler (create-default-handler
                      {:not-found not-found
                       :method-not-allowed method-not-allowed
                       :not-acceptable not-acceptable}))
      (wrap-resource "public")
      (wrap-content-type)
      (wrap-not-modified)))

(defn -main
  [& args]
  (run-jetty app {:port 8000
                  :join? false}))
