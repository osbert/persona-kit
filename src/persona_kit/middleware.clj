(ns persona-kit.middleware
  (:require [compojure.route :as r]
            [compojure.core :as c]))

(defn wrap-persona-resources
  "Ring middleware to handle persona resource requests."
  [handler]
  (->> handler
       (c/routes (r/resources "/persona-kit" {:root "persona-kit/public"}))))
