(defproject persona-kit "0.1.1-SNAPSHOT"
  :description "Starter kit for Mozilla Persona login system for Clojure using Hiccup, cemerick/friend."
  :url "http://github.com/osbert/persona-kit"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [hiccup "1.0.2"]
                 [org.clojure/data.json "0.1.2"]
                 [clj-http "0.2.6" :exclusions [commons-codec]]
                 [compojure "1.1.3"]
                 [com.cemerick/friend "0.1.3" :exclusions [org.clojure/core.incubator org.apache.httpcomponents/httpclient]]])
