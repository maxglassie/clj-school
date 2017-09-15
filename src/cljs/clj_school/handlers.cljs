(ns clj-school.handlers
  (:require [clj-school.db :as db]
            [re-frame.core :refer [dispatch reg-event-db reg-event-fx]]
            [ajax.core :as ajax]))

(reg-event-db
 :initialize-db
 (fn [_ _]
   db/default-db))

(reg-event-db
 :set-active-page
 (fn [db [_ page]]
   (assoc db :page page)))

(reg-event-db
 :set-docs
 (fn [db [_ docs]]
   (assoc db :docs docs)))

(defn get-course [course-id]
  (ajax/GET "localhost:3000/courses?id=1234"))

(reg-event-fx
 :get-course
 (fn [coeffects event]
   (let [db (:db coeffects)
         course-id (second event)
         course (:body (get-course course-id))]
     {:db (assoc db :current-course course)})))
