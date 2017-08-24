(ns clj-school.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [clj-school.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[clj-school started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[clj-school has shut down successfully]=-"))
   :middleware wrap-dev})
