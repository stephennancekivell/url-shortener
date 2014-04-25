(ns url-shortener.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [taoensso.carmine :as car :refer (wcar)]
            [ring.adapter.jetty :as jetty]))


(def server1-conn {:pool {} :spec {:host "127.0.0.1" :port 6379}}); See `wcar` docstring for opts
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(defn saveurl [key val]
  (wcar* (car/set key val)))

(defn geturl [key]
  (wcar* (car/get key)))

(defn serve302 [url]
  {:status 302
   :headers {
     "Content-Type" "text/html; charset=utf-8"
     "Location" url}
  })

(defn geturl302 [key] (serve302 (geturl key)))

(defn index [] (serve302 "/r/index.html"))

(defroutes app-routes
  (GET "/" [] (index))
  (GET "/:id" [id] (geturl302 id))
  (POST "/:id" [id :as r]
        (saveurl id (slurp (:body r))))
  (route/resources "/r/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))

(defn -main [port]
  (jetty/run-jetty app {:port (Integer. port) :join? false}))