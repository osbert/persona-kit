(ns persona-kit.friend
  "Friend workflow, credential function, and middleware for Mozilla Persona."
  (:require [cemerick.friend :as friend]
            [cemerick.friend.workflows :as workflows]
            [compojure.core :as c]
            [ring.util.response :as r])
  (:use [persona-kit.core :only [valid? verify-assertion]]
        [persona-kit.uris :only [*login-uri* *logout-uri*]]
        [ring.middleware.anti-forgery :only [wrap-anti-forgery]]))

(defn credential-fn [{:keys [email] :as credential-map}]
  (when (valid? credential-map)
    (merge {:identity email} credential-map)))

(defn persona-workflow [audience request]
  (if (and (= (:uri request) *login-uri*)
           (= (:request-method request) :post))
    (-> request
        :params
        :assertion
        (verify-assertion audience)
        credential-fn
        (workflows/make-auth {::friend/redirect-on-auth? false ::friend/workflow :mozilla-persona}))))

(def login-handler
  (c/make-route :post *login-uri*
                (fn [request]
                  (-> (r/response (pr-str (friend/identity request)))
                      (r/content-type "application/clojure; charset=utf-8")))))

(def logout-handler
  (friend/logout
   (c/POST *logout-uri* []
           (r/response "OK"))))

(def wrap-persona-friend*
  "Insecure version without wrap-anti-forgery."
  (c/routes login-handler logout-handler))

(defn wrap-persona-friend
  "Ring middleware that handles login and logout requests."
  [handler]
  (c/routes (wrap-anti-forgery wrap-persona-friend*)
            #(handler %)))
