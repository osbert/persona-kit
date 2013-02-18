(ns persona-kit.core
  (:require [hiccup.page :as p]
            [hiccup.def :as d]
            [clojure.string :as s]
            [clojure.data.json :as j]
            [clj-http.client :as http]
            [compojure.route :as r]
            [compojure.core :as c])
  (:use [persona-kit middleware view]))

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
