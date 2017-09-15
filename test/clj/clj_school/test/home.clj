(ns clj-school.routes.home-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clj-school.handler :as handler]
            [clj-school.routes.home :refer :all]))

(def app (handler/app))

(deftest test-app
  (testing "classes returns ok"
    (let [response (app (mock/request :get "/classes"))]
      (is (= 200 (:status response)))))

  (testing "classes returns correct data structure"
    (let [response (app (mock/request :get "/classes"))]
      (is (= (str (get-classes)) (:body response)))))

  ;; I want "/class/:id" to find the class by it's id
  (testing "gets-class-by-id"
    (let [class (get-class-by-id :3)]
      (is (= class computer-science-1))))
  (testing "GET /class/:id returns the class"
    (let [response (app (mock/request :get "/classes/1"))]
      (println response)
      (is (= (get-class-by-id :3) (:body response))))))
