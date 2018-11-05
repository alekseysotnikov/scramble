(ns scramble.server
  (:require [scramble.core          :as scramble]
            [compojure.core         :as compojure]
            [compojure.route        :as route]
            [ring.util.response     :as response]
            [ring.middleware.params :as params]
            [immutant.web           :as web])
  (:gen-class))


(defn handle-scramble-response [chars sample]
  (cond
    (empty? chars) "Characters is required"
    (empty? sample) "Sample is required"
    :else (if (scramble/scramble? chars sample)
           (str "Portion of the characters '"chars"' matches the sample '"sample"'!")
           (str "No luck! Portion of the characters '"chars"' doesn't match the sample '"sample"'."))))


(compojure/defroutes routes
  (compojure/GET "/scramble" [chars sample]
    (response/response (handle-scramble-response chars sample)))
  (compojure/GET "/" []
    (->
      (response/resource-response "public/index.html")
      (response/content-type "text/html")))
  (route/resources "/")
  (route/not-found "Page not found"))


(def handler
  (params/wrap-params routes))


(defn -main [& args]
  (let [args-map (apply array-map args)
        port-str (or (get args-map "-p")
                     (get args-map "--port")
                     "8080")]
    (println "Starting web server on port" port-str)
    (web/run handler {:port (Integer/parseInt port-str)})))