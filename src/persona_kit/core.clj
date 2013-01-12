(ns persona-kit.core
  (:require [hiccup.page :as p]
            [hiccup.def :as d]
            [clojure.string :as s]
            [clojure.data.json :as j]
            [clj-http.client :as http]
            [compojure.route :as r]
            [compojure.core :as c]))

(defn include-persona
  "Add persona CSS styles and JS functions to page."
  []
  (list (p/include-css "/persona-kit/css/persona-buttons.css")
        (p/include-js "https://login.persona.org/include.js")))

(defn wrap-persona-resources
  "Ring middleware to handle persona resource requests."
  [handler]
  (->> handler
       (c/routes (r/resources "/persona-kit" {:root "persona-kit/public"}))))

(d/defelem sign-in-button
  "Create a persona sign-in button.  Variant can be :dark or :orange."
  [& [variant]]
  [:a {:href "#"
       :class (s/join " "
                      (remove nil?
                              ["persona-button"
                               (if variant (name variant))]))}
   [:span "Sign in with your Email"]])

(defn verify-assertion
  "Return the raw verification response as a map."
  [assertion audience]
  (if-let [http-response (http/post "https://verifier.login.persona.org/verify"
                                    {:form-params {:assertion assertion
                                                   :audience audience}})]
    (let [verification-response (j/read-json (:body http-response))]
      (if (= 200 (:status http-response))
        verification-response))
    {:status "HTTP POST returned nil/false"}))

(defn valid?
  "Return true if the verification response confirms this user's identity."
  [verification-response]
  (= "okay" (:status verification-response)))
