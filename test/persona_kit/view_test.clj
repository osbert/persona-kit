(ns persona-kit.view-test
  (:use clojure.test
        persona-kit.view)
  (:require [hiccup.core :as hiccup]))

(deftest sign-in-button-test
  (testing "returns a vector"
    (is (vector? (sign-in-button))))

  (let [html-string (hiccup/html (sign-in-button))]
    (testing "can be converted to HTML by hiccup"
      (is (string? html-string)))

    (testing "starts with an anchor"
      (is (re-find #"^<a" html-string)))))

(deftest include-persona-test
  (testing "returns a list"
    (is (list? (include-persona))))

  (testing "can be converted to HTML by hiccup"
    (is (string? (hiccup/html (include-persona)))))

  (testing "starts with link"
    (is (re-find #"^<link" (hiccup/html (include-persona))))))
