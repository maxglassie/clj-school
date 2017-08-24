(ns user
  (:require [mount.core :as mount]
            [clj-school.figwheel :refer [start-fw stop-fw cljs]]
            clj-school.core))

(defn start []
  (mount/start-without #'clj-school.core/repl-server))

(defn stop []
  (mount/stop-except #'clj-school.core/repl-server))

(defn restart []
  (stop)
  (start))


