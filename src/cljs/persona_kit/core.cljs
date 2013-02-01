(ns persona-kit.core
  "Helper functions to bind useful Persona actions to UI elements."
  (:require [crate.core :as crate]
            [crate.element :as e]
            [clojure.string :as s]
            [domina.events :as events]
            [shoreleave.remotes.request :as xhr]
            [cljs.reader :as reader]
            [persona-kit.uris :as persona-uris])
  (:use-macros [crate.def-macros :only [defpartial defelem]]))

(defelem sign-in-button
  [& [variant]]
  [:a {:href "#"
       :class (s/join " "
                      (remove nil?
                              ["persona-button"
                               (if variant (name variant))]))}
   [:span "Sign in with your Email"]])

(defn sign-in-controller [button]
  "Bind to a button to trigger Persona login behavior"
  (doto button 
    (events/listen! :click
                    (fn [evt]
                      (events/dispatch! :persona-kit/login-request {})
                      (-> js/navigator .-id .request)))))

(defn sign-out-controller [elem]
  "Bind to elem to trigger Persona logout behavior."
  (doto elem 
    (events/listen! :click
                    (fn [evt]
                      (events/dispatch! :persona-kit/logout-request {})
                      (-> js/navigator .-id .logout)))))

(defn persona-controller
  "Bind persona-controller to any UI element that will always be
loaded to setup Persona. Triggers the following JS DOM events:

:persona-kit/login
:persona-kit/logout

so that your application can know when to refresh the view.
"
  [& [elem]]
  (-> js/navigator
      .-id
      (.watch (clj->js {:loggedInUser ""
                        :onlogin (fn [assertion]
                                   (xhr/request [:post persona-uris/*login-uri*]
                                                :content {"assertion" assertion}
                                                :on-success (fn [e]
                                                              (events/dispatch! :persona-kit/login
                                                                                {:email (-> e
                                                                                            :body
                                                                                            reader/read-string
                                                                                            :current)}))))
                        :onlogout (fn []
                                    (events/dispatch! :persona-kit/logout {})
                                    (xhr/request [:post persona-uris/*logout-uri*] {}))})))
  elem)