(ns clj-school.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[clj-school started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[clj-school has shut down successfully]=-"))
   :middleware identity})
