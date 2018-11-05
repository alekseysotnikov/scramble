(ns scramble.server-test
  (:require [clojure.test      :refer :all]
            [scramble.server   :as server]
            [ring.mock.request :as mock])
  (:import (java.io File)))


(deftest test-requests
  (testing "Scramble requests"
    (is (= (server/handler (mock/request :get "/scramble?chars=hhhellooo&sample=hello"))
           {:body    "Portion of the characters 'hhhellooo' matches the sample 'hello'!"
            :headers {}
            :status  200}))
    (is (= (server/handler (mock/request :get "/scramble?chars=hhhellooo"))
           {:body    "Sample is required"
            :headers {}
            :status  200}))
    (is (= (server/handler (mock/request :get "/scramble?sample=hello"))
           {:body    "Characters is required"
            :headers {}
            :status  200})))
  (testing "Negative request"
    (is (= (server/handler (mock/request :get "/incorrect-path"))
           {:body    "Page not found"
            :headers {"Content-Type" "text/html; charset=utf-8"}
            :status  404})))
  (testing "Resource request"
    (is (instance? File (:body (server/handler (mock/request :get "/index.html")))))))