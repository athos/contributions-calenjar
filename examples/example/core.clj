(ns example.core
  (:require [contributions-calenjar.core :as cal]
            [hiccup.page :refer [html5]]
            [ring.adapter.jetty :as jetty]
            [ring.util.response :as res]))

(defn app [req]
  (let [contribs (take (* 7 53) (repeatedly #(rand-int 25)))]
    (res/response
      (html5
        [:body
         {:style "font-family: Helvetica, Arial, sans-serif;"}
         (cal/calendar contribs)]))))

(defn -main []
  (jetty/run-jetty #'app {:port 8080}))
