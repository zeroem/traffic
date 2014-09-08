(defproject traffic "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
                 [brute "0.3.0"]]

  :plugins [[lein-cljsbuild "1.0.4-SNAPSHOT"]
            [com.keminglabs/cljx "0.4.0"]]

  :cljx {:builds [{:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :clj}

                  {:source-paths ["src/cljx"]
                   :output-path "target/classes"
                   :rules :cljs}]}

  :profiles  {:dev  {:plugins  [[com.cemerick/austin  "0.1.4"]]}}


  :source-paths ["src"]

  :cljsbuild {:builds [{:id "traffic"
              :source-paths ["src/cljs"]
              :compiler {
                :output-to "traffic.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
