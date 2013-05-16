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

