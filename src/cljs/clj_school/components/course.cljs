(ns clj-school.components.course
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [ajax.core :refer [GET POST]]
            [clj-school.ajax :refer [load-interceptors!]]
            [clj-school.subscriptions]))

(defn course [data]
  (rf/dispatch [:get-course "1234"])
  [:div.container
   [:div
    [:ul
     [:li (str "Title: " (:title data))]
     [:li (str "Course-Id: " (:course-id data))]]]])

;; li
;; Title:
;; Course-Id
