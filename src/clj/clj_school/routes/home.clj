(ns clj-school.routes.home
  (:require [clj-school.layout :as layout]
            [compojure.core :refer [defroutes GET context]]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [taoensso.timbre :as log]))


(log/merge-config! {:level :debug})
(log/info "Loading....")

(defn home-page []
  (layout/render "home.html"))

(def beginner-geometry {:course-id "1234"
                        :name "Beginner Geometry"
                        :content "It takes a line to bisect a plane."
                        :students ["s1" "s2"]})

(def courses (atom {"1234" beginner-geometry}))

(def classes (atom {}))

(defn get-courses []
  (log/info "getting courses")
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

;; TODO: Implement routes that work for CRUD for three diffferent classes
;; Create the classes from three different courses
;; Store the created classes in an Atom
;; Take a stab at adding a web front end
;; Run the front end with lein figwheel

(defroutes home-routes
  (GET "/" []
       (home-page))
  (context "/classes" []
           (GET "/" [] (str (get-classes))))
  (context "/class" []
           (GET "/" [] (str beginner-geometry)))
  (context "/courses" []
           (GET "/" [] (str (get-courses))))
  (context "/course/:id" [id]
           (GET "/" [id] (str (get-course (str id))))))

(GET "/docs" []
     (-> (response/ok (-> "docs/docs.md" io/resource slurp))
         (response/header "Content-Type" "text/plain; charset=utf-8")))
