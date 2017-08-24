(ns clj-school.app
  (:require [clj-school.core :as core]))

;;ignore println statements in prod
(set! *print-fn* (fn [& _]))

(core/init!)
