(ns persona-kit.view
  (:require [hiccup.page :as p]
            [clojure.string :as s]
            [hiccup.def :as d]))

(defn include-persona
  "Add persona CSS styles and JS functions to page."
  []
  (list (p/include-css "/persona-kit/css/persona-buttons.css")
        (p/include-js "https://login.persona.org/include.js")))

(d/defelem sign-in-button
  "Create a persona sign-in button.  Variant can be :dark or :orange."
  [& [variant]]
  [:a {:href "#"
       :class (s/join " "
                      (remove nil?
                              ["persona-button"
                               (if variant (name variant))]))}
   [:span "Sign in with your Email"]])
