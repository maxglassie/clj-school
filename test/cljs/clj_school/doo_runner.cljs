(ns clj-school.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [clj-school.core-test]))

(doo-tests 'clj-school.core-test)

