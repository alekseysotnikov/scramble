(ns scramble.core-test
  (:require [clojure.test  :refer [deftest is testing]]
            [scramble.core :as scramble]))

(deftest test-scramble-fun
  (testing "Strings are the same size"
    (is (= true (scramble/scramble? "dlorw" "world")))
    (is (= false (scramble/scramble? "katas" "steak")))
    (is (= false (scramble/scramble? "abbc" "abcc"))))
  (testing "Strings are of different sizes"
    (is (= true (scramble/scramble? "hhhackaaathonnn" "hackathon")))
    (is (= false (scramble/scramble? "ackathon" "hackathon")))
    (is (= false (scramble/scramble? "ackathon" "aackathon")))
    (is (= true (scramble/scramble? "abc" "")))
    (is (= false (scramble/scramble? "" "abc"))))
  (testing "nil as args"
    (is (= true (scramble/scramble? "abc" nil)))
    (is (= false (scramble/scramble? nil "abc")))
    (is (= true (scramble/scramble? nil nil)))))


