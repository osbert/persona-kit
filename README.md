# persona-kit

A Clojure micro-library designed to make it easier to implement
Mozilla Persona for user authentication:

* <code>persona-kit.core/include-persona</code> Hiccup helper to
  include CSS/JS for Mozilla Persona branded sign-in buttons.

* <code>persona-kit.core/sign-in-button</code> Hiccup helper for
  sign-in buttons with dark and orange variants.

* <code>persona-kit.core/wrap-persona</code> Ring middleware to
  handle routes for persona resources.

* <code>persona-kit.core/verify-assertion</code>Identity verification
  function which HTTP posts to verifier.login.persona.org for identity
  verification and returns the JSON response as a map.

* <code>persona-kit.core/valid?</code> can then be used to return
  whether or not the verification response succeeded.

Note to get a full implementation you still have to:

* Expose a route so clients can call verify-assertion.

* Use the results of identity verification to set some session state.

* Implement browser side logic to handle login/logout button events,
  onlogin, onlogout, and supply loggedInUser to navigator.watch().

Unfortunately, most applications will vary in how these are
accomplished, so it is considered beyond the scope of this library.

## Usage

```Clojure
;; Add persona-kit to your project.clj dependencies.
[persona-kit "0.1.0-SNAPSHOT"]

(ns my.app
  (:require [compojure.core :as c]
            [compojure.handler :as h]
            [hiccup.page :as p]
            [persona-kit.core :as i]))

;; On your login page, use include-persona to bring in persona resources, 
;; sign-in-button to show the login button.
(c/defroutes app-routes
  (c/GET "/" [] (p/html5 [:head
                          [:title "Login page"]
                          (i/include-persona)]
                         [:body (i/sign-in-button)])))


;; Add wrap-persona as middleware to handle CSS resource request.
(def app (-> (h/site app-routes)
             (i/wrap-persona)))

;; To aid in identify verification, use verify-assertion and valid? as
;; appropriate for your particular workflow.
 ```

## License

Copyright © 2013 Osbert Feng

Distributed under the Eclipse Public License, the same as Clojure.
