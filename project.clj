(defproject persona-kit "0.1.0-SNAPSHOT"
  :description "Starter kit for Mozilla Persona login system for Clojure using Hiccup."
  :url "http://github.com/osbert/persona-kit"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [hiccup "1.0.2"]
                 [org.clojure/data.json "0.1.2"]
                 [clj-http "0.2.6" :exclusions [commons-codec]]
                 [compojure "1.1.3"]])
