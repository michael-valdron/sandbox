(ns app.core
    (:require [reagent.core :as r]))

(enable-console-print!)

(defn Footer [args]
    (let [{:keys [author date company]} args
          style {:style {:margin-right 30
                         :font-size "20pt"}}]
        [:div {:style {:position :fixed
                       :bottom 0
                       :left 0
                       :width "100%"
                       :background :black
                       :text-align :right 
                       :color :white}}
            [:span style author]
            [:span style company]
            [:span style "Ⓒ" date]]))

(defn Counter []
    (let [counter-state (r/atom 0)]
        (fn [message]
            [:p 
                [:span message]
                [:span {:style {:margin-left 10
                                :font-weight :bold}}
                    @counter-state]])))

(defn LinkedCounter [counter-state message & [option]]
    (fn [counter-state message & [option]]
        (let [count-value @counter-state
              color (cond
                        (< count-value 3) "#ccc"
                        (< count-value 10) "#888"
                        :else "#000")]
        [:span {:style {:color color
                        :user-select :none}
                :on-click (fn [e]
                            (when-not (= option :readonly)
                                (.preventDefault e)
                                (.stopPropagation e)
                                (swap! counter-state inc)))} message " " count-value])))

(defn App []
    (let [c1-state (r/atom 0)]
        [:div.container
            [:h1 "Global Watch Financials"]
            [:div.jumbotron
                [:h1 "Leading Machine Learning Managed Banking Products"]
                [:h2 "The one source of personal financial freedom"]
                [LinkedCounter c1-state "Number of products:" :readonly]]
            [:div.row
                [:div.col-xs-6
                    [:h3 "About us"]
                    [:p "We are a team of technos"]]
                [:div.col-xs-6
                    [:h3 "Clients"]
                    [:p "blah blah"]
                    [LinkedCounter c1-state "Number of clients:"]]]
                [Counter "Number of visitors:"]
                [Footer {:author "Ken"
                        :company "UOIT"
                        :date "2018"}]]))

(defn main [] (r/render [App] (.getElementById js/document "main-body")))

(main)