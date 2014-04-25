(defproject url-shortener "0.1.0-SNAPSHOT"
  :description "simple url shortener"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [com.taoensso/carmine "2.4.5"]]
  :plugins [[lein-ring "0.8.10"]]
  :ring {:handler url-shortener.handler/app}
  :uberjar-name "url-shortener-standalone.jar"
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})
