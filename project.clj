(defproject persona-kit "0.1.1-SNAPSHOT"
  :description "Starter kit for Mozilla Persona login system for Clojure using Hiccup, cemerick/friend."
  :url "http://github.com/osbert/persona-kit"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [hiccup "1.0.3"]
                 [clj-http "0.7.2" :exclusions [commons-codec]]
                 [compojure "1.1.5" :exclusions [ring/ring-core]]
                 [com.cemerick/friend "0.1.5" :exclusions [org.clojure/core.incubator org.apache.httpcomponents/httpclient slingshot]]

                 ;; ClojureScript dependencies
                 [org.clojure/clojurescript "0.0-1806"]
                 [shoreleave/shoreleave-remote "0.3.0"]
                 [domina "1.0.2-SNAPSHOT"]
                 [crate "0.2.4"]]
  :dev-dependencies [[jline "0.9.94"]
                     [marginalia "0.7.0-SNAPSHOT"]
                     [lein-marginalia "0.7.0-SNAPSHOT"]]
  :plugins [[lein-cljsbuild "0.3.2" :exclusions [org.clojure/clojure]]]
  :min-lein-version "2.0.0"
  :hooks [leiningen.cljsbuild]
  :cljsbuild {:builds
              [{:source-paths ["src/cljs"],
                :compiler
                {:pretty-print true,
                 :output-to "resources/persona-kit/public/js/persona-kit.js",
                 :optimizations :whitespace},
                :jar true}],
              :crossovers [persona-kit.uris],
              :crossover-jar true,
              :crossover-path "out/cljs"}

  )
