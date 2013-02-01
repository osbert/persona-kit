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
                 [com.cemerick/friend "0.1.3" :exclusions [org.clojure/core.incubator org.apache.httpcomponents/httpclient ring/ring-core slingshot]]

                 ; ClojureScript dependencies
                 [org.clojure/clojurescript "0.0-1552"]
                 [shoreleave/shoreleave-remote "0.2.2"]
                 [domina "1.0.1-SNAPSHOT"]
                 [org.clojure/google-closure-library "0.0-790"]
                 [crate "0.2.1"]]
  :dev-dependencies [[jline "0.9.94"]
                     [marginalia "0.7.0-SNAPSHOT"]
                     [lein-marginalia "0.7.0-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "0.2.9"]]
  :min-lein-version "2.0.0"
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:crossovers [persona-kit.uris]
              :crossover-path "out/cljs"
              :crossover-jar true
              :builds [{
                        :jar true
                        ;; The path to the top-level ClojureScript source directory:
                        :source-path "src/cljs"
                        ;; The standard ClojureScript compiler options:
                        ;; (See the ClojureScript compiler documentation for details.)
                        :compiler {
                                   :output-to "resources/persona-kit/public/js/persona-kit.js"
                                   :optimizations :whitespace
                                   :pretty-print true}}]})
