(ns clj-school.routes.home
  (:require [clj-school.layout :as layout]
            [compojure.core :refer [defroutes GET context]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [clojure.tools.logging :as log]))

(defn home-page []
  (layout/render "home.html"))

(def beginner-geometry {:course-id "1234"
                        :name "Beginner Geometry"
                        :content "It takes a line to bisect a plane."
                        :students ["s1" "s2"]})

(def courses (atom {"1234" beginner-geometry}))

(def classes (atom {}))

(defn get-courses []
  (vals @courses))

(defn get-course [course-id]
  (log/info "[get-course] course-id:" course-id)
  (get @courses course-id))

(defn create-class [course]
  {:name (:name course)
   :course-id (:course-id course)
   :content (:content course)
   :students (:students course)})

(defn get-classes []
  [beginner-geometry])

(defroutes home-routes
  (GET "/" []
       (home-page))
  #_(context "/class" []
             (GET "/" [] (str [(get-classes)]))
             (POST "/" [course-id] (str (create-class course-id))))
  (context "/courses" []
           (GET "/" [] (str (get-courses))))
  (context "/course" []
           (GET "/" [course-id] (str course-id)))
  (GET "/docs" []
       (-> (response/ok (-> "docs/docs.md" io/resource slurp))
           (response/header "Content-Type" "text/plain; charset=utf-8"))))
