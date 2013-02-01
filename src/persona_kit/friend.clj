(ns persona-kit.friend
  "Friend workflow, credential function, and middleware for Mozilla Persona."
  (:use [persona-kit.core :only [valid? verify-assertion]]
        [cemerick.friend :as friend]
        [cemerick.friend.workflows :as workflows]
        [compojure.core :as c]
        [ring.util.response :as r]))

(def *login-uri* "/login")
(def *logout-uri* "/logout")

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

(defn wrap-persona-friend
  "Ring middleware that handles login and logout requests."
  [handler]
  (c/routes
   (c/make-route :post *login-uri*
                 (fn [request]
                   (-> (r/response (pr-str (friend/identity request)))
                       (r/content-type "application/clojure; charset=utf-8"))))
   (friend/logout (c/POST *logout-uri* [] (r/response "OK")))
   #(handler %)))