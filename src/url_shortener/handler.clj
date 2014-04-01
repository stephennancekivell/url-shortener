(ns url-shortener.handler
  (:use compojure.core)
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [taoensso.carmine :as car :refer (wcar)]))


(def server1-conn {:pool {} :spec {:host "127.0.0.1" :port 6379}}); See `wcar` docstring for opts
(defmacro wcar* [& body] `(car/wcar server1-conn ~@body))

(defn saveurl [key val]
  (wcar* (car/set key val)))

(defn geturl [key]
  (wcar* (car/get key)))

(defn geturl302 [key]
  {:status 302
   :headers {
     "Content-Type" "text/html; charset=utf-8"
     "Location" (geturl key)}
   :body (geturl key)})

(defn index []
  {:status 302
   :headers {
     "Content-Type" "text/html; charset=utf-8"
     "Location" "/r/index.html"}
  })

(defroutes app-routes
  (GET "/" [] (index))
  (GET "/:id" [id] (geturl302 id))
  (POST "/:id" [id :as r]
        (saveurl id (slurp (:body r))))
  (route/resources "/r/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))