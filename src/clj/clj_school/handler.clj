(ns clj-school.handler
  (:require [compojure.core :refer [routes wrap-routes]]
            [clj-school.layout :refer [error-page]]
            [clj-school.routes.home :refer [home-routes]]
            [clj-school.routes.services :refer [service-routes]]
            [compojure.route :as route]
            [clj-school.env :refer [defaults]]
            [mount.core :as mount]
            [clj-school.middleware :as middleware]))

(mount/defstate init-app
                :start ((or (:init defaults) identity))
                :stop  ((or (:stop defaults) identity)))

(def app-routes
  (routes
    (-> #'home-routes
        (wrap-routes middleware/wrap-csrf)
        (wrap-routes middleware/wrap-formats))
    #'service-routes
    (route/not-found
      (:body
        (error-page {:status 404
                     :title "page not found"})))))


(defn app []
  (let [app* (middleware/wrap-base #'app-routes)]
    app*))
