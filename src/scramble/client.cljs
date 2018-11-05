(ns scramble.client
    (:require [rum.core  :as rum]
              [oops.core :as oops]))


(enable-console-print!)


(defonce *app-state (atom {:msg "Check that a portion of the characters can be rearranged to match the sample"}))


(defn- fetch-text! [url handler error-handler]
  (let [xhr (js/XMLHttpRequest.)]
    (doto xhr
      (oops/oset! "withCredentials" false)
      (oops/oset! "onreadystatechange" #(this-as t (let [done    (== 4 (oops/oget t "readyState"))
                                                         http-ok (== 200 (oops/oget t "status"))]
                                                     (if done (if http-ok
                                                                (handler (oops/oget xhr "responseText"))
                                                                (error-handler t))))))
      (.open "GET" url true)
      (.send))))


(defn error-handler [e]
  (let [status (oops/oget e "status")
        text   (oops/oget e "responseText")]
    (reset! *app-state {:msg (str "Connection error: " status " - " text)})))


(defn get-node [id]
  (. js/document (getElementById id)))


(defn- check-action [_]
  (let [chars  (oops/oget (get-node "chars") "value")
        sample (oops/oget (get-node "sample") "value")
        url    (str "/scramble?chars=" chars "&sample=" sample)]
    (fetch-text! url #(reset! *app-state {:msg %}) error-handler)))


(rum/defc pane [body title]
  [:div.pane
   [:div.pane-title title]
   [:div body]])


(rum/defc scramble-form < rum/reactive []
  [:div#scramble
   [:dl
    [:dd [:input#chars  {:type "text" :placeholder "Characters"}]]
    [:dd [:input#sample {:type "text" :placeholder "Sample"}]]
    [:dd [:input#check  {:type "button"
                         :on-click check-action
                         :value "Check"}]]]
   [:p [:i (:msg (rum/react *app-state))]]])


(defn mount! []
  (let [app (get-node "app")]
    (rum/mount (pane (scramble-form) "Scramble?") app)))


(defn unmount! []
  (rum/unmount (get-node "app")))


(. js/document (addEventListener "DOMContentLoaded" mount!))


(defn on-js-reload []
  (unmount!)
  (mount!))