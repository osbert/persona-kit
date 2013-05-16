(ns persona-kit.friend_test
  (:use clojure.test
        persona-kit.friend
        ring.mock.request))

(defn with-token [req]
  (-> req
      (assoc :session {"__anti-forgery-token" "foo"})
      (assoc :form-params {"__anti-forgery-token" "foo"})))

(defn with-friend-info [req]
  (-> req
      (assoc-in [:session :cemerick.friend/identity] "foo@bar.com")))

(deftest wrap-persona-friend-test
  (let [app (wrap-persona-friend identity)]
    (testing "no anti-forgery-token specified"
      (testing "POST login"
        (let [response (app (request :post "/login"))]
          (is (= (:status response) 403))))

      (testing "POST logout"
        (let [response (app (request :post "/logout"))]
          (is (= (:status response) 403))))

      (testing "other routes"
        (let [req (request :get "/")
              response (app req)]
          (is (= response req)))))

    (testing "with anti-forgery token"
      (testing "POST login"
        (let [response (app (with-token (request :post "/login")))]
          (is (= (:body response) (pr-str nil)))
          (is (= (:status response) 200))))

      (testing "POST login with identity"
        (let [response (app (with-friend-info (with-token (request :post "/login"))))]
          (is (= (:body response) (pr-str "foo@bar.com")))
          (is (= (:status response) 200))))

      (testing "POST logout"
        (let [response (app (with-friend-info (with-token (request :post "/logout"))))]
          (is (= (:status response) 200))
          (is (= (get-in response [:session :identity]) nil))))

      (testing "other routes"
        (let [req (request :get "/")
              response (app req)]
          (is (= response req)))))))
