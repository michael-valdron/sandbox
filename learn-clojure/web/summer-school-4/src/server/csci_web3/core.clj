;; Reference from kenpu: https://github.com/kenpu/summer-school-2018/blob/master/csci_web3/src/server/csci_web3/core.clj
(ns csci-web3.core
  (require [ring.adapter.jetty :as jetty]
           [ring.middleware.reload :refer [wrap-reload]]
           [ring.util.response :refer [response]]
           [compojure.core :as c]
           [compojure.route :as route]
           [hiccup.core :as h]))

(defn <index> [r]
  (h/html
    [:html
     [:head
      [:title "CSCI Web3"]
      [:link {:rel "stylesheet"
              :href "/static/style.css"}]]
     [:body
      [:div {:id "main-body"}]
      [:script {:src "/static/js/main.js"}]
      ]]))


(def app (c/routes
           (c/GET "/" r (<index> r))
           (route/resources "/static" {:root "public"})
           (route/not-found (response "Not found"))))

(defn -main []
  (jetty/run-jetty (-> #'app
(wrap-reload)) {:port 7654}))