(defproject url-shortener "0.1.0-SNAPSHOT"
  :description "simple url shortener"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [ring/ring-jetty-adapter "1.1.6"]
                 [environ "0.5.0"]
                 [com.taoensso/carmine "2.4.5"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-environ "0.5.0"]]
  :uberjar-name "url-shortener-standalone.jar"
  :min-lein-version "2.0.0"
  :ring {:handler url-shortener.handler/app}
  :profiles {:dev {:dependencies [
                        [javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]
                  :env {:rediscloud-url "redis://localhost:6379"}}})
