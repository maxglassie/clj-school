(ns clj-school.routes.home
  (:require [clj-school.layout :as layout]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]
            [taoensso.timbre :as log]))


(log/merge-config! {:level :debug})
(log/info "Loading....")

(defn home-page []
  (layout/render "home.html"))

(def beginner-geometry {:id "1234"
                        :name "Beginner Geometry"
                        :content "It takes a line to bisect a plane."
                        :students ["s1" "s2"]})

(def philosophy {:id "4321"
                 :name "Aristotle's Metaphysics"
                 :content "Virtue and vices in Ancient Greece"
                 :students ["s1" "s3"]})

(def computer-science {:id "2345"
                       :name "The Structure and Interpretation of Computer Programs"
                       :content "Hal and Gerry hold forth"
                       :students ["s1" "s2"]})

(defonce courses (atom {(:id beginner-geometry) beginner-geometry
                        (:id philosophy) philosophy
                        (:id computer-science) computer-science}))

(defonce classes (atom {}))

(defn get-courses []
  (log/info "getting courses")
  (vals @courses))

(defn get-course [course-id]
  (log/info "[get-course] course-id:" course-id)
  (get @courses course-id))

;; imagine a form that submits this information on the front-end
;; big question - how to create an id for the class?
;; have a course name? and then have a class-id?

(defn create-class
  ([course]
   (create-class course (java.util.UUID/randomUUID)))
  ([course class-id]
   (let [class-id (if (get @classes class-id)
                    (java.util.UUID/randomUUID)
                    class-id)]
     {:class-id class-id
      :course-name (:name course)
      :course-id (:id course)
      :content (:content course)
      :students (:students course)})))

(def beginner-geometry-1 (create-class beginner-geometry))
(def philosophy-1 (create-class philosophy))
(def computer-science-1 (create-class computer-science))

;; takes a vector as an argument
;; coerces to a seq
;; doseq assigns class to each item in the seq
;; and runs the function body
;; doseq typically does side-effects!

(defn store-classes! [cls]
  (doseq [class cls]
    (swap! classes assoc (:class-id class) class)))

;; (store-classes! [beginner-geometry-1 philosophy-1 computer-science-1])

(defn get-classes []
  @classes)

(defn get-class-by-id [id]
  (get @classes id))

(defn get-class-by-name [name]
  (let [course-name? #(= name (:course-name %))]
    (filter course-name? (vec (vals @classes)))))

;; Todo: Implement routes that work for CRUD for three diffferent classes
;; Create the classes from three different courses
;; Store the created classes in an Atom
;; Take a stab at adding a web front end
;; Run the front end with lein figwheel

(defn uuid [id]
  (try
    (java.util.UUID/fromString id)
    (catch Exception ex
      nil)))

(defroutes home-routes
  (GET "/" []
    (home-page))
  (context "/classes" []
    (GET "/" [id]
      :query-params [{id :- String nil}]
      (log/info "GET request for class:" id)
      (when-let [id (uuid id)]
        (str (get-class-by-id id))))
    (GET "/name" [name]
      :query-params [{name :- String nil}]
      (log/info "GET request for class:" name)
      (str (get-class-by-name name))))
  (context "/courses" []
    (GET "/" [id]
      :query-params [{id :- String nil}]
      (str (get-course (str id))))
    (GET "/" [] (str (get-courses)))))

(GET "/docs" []
  (-> (response/ok (-> "docs/docs.md" io/resource slurp))
      (response/header "Content-Type" "text/plain; charset=utf-8")))
