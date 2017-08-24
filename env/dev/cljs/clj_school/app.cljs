(ns ^:figwheel-no-load clj-school.app
  (:require [clj-school.core :as core]
            [devtools.core :as devtools]))

(enable-console-print!)

(devtools/install!)

(core/init!)
