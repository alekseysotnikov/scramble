(defproject scramble "0.1.0-SNAPSHOT"
  :description "Minimal full stack Clojure/Script application with Rum"


  :url "https://github.com/alekseysotnikov/scramble"


  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}


  :min-lein-version "2.7.1"


  :dependencies [[org.clojure/clojure       "1.9.0"]
                 [org.clojure/clojurescript "1.10.339"]
                 [org.clojure/core.async    "0.4.474"]
                 [org.immutant/web          "2.1.10"]
                 [ring/ring-core            "1.7.1"]
                 [ring/ring-mock            "0.3.2"]
                 [binaryage/oops            "0.6.2"]
                 [compojure                 "1.6.1"]
                 [rum                       "0.11.2"]]


  :plugins [[lein-figwheel  "0.5.16"]
            [lein-cljsbuild "1.1.7" :exclusions [[org.clojure/clojure]]]]


  :source-paths ["src"]


  :main scramble.server


  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]
                :figwheel {:on-jsload "scramble.client/on-js-reload"
                           :open-urls ["http://localhost:3449/index.html"]}
                :compiler {:main                 scramble.client
                           :asset-path           "js/compiled/out"
                           :output-to            "resources/public/js/compiled/scramble.js"
                           :output-dir           "resources/public/js/compiled/out"
                           :source-map-timestamp true
                           :preloads             [devtools.preload]}}
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to     "resources/public/js/compiled/scramble.js"
                           :main          scramble.client
                           :optimizations :advanced
                           :pretty-print  false}}]}


  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler scramble.server/handler}


  :profiles {:uberjar {:aot          [scramble.server]
                       :uberjar-name "scramble.jar"
                       :auto-clean   false}
             :dev {:dependencies [[binaryage/devtools "0.9.9"]
                                  [figwheel-sidecar "0.5.16"]
                                  [cider/piggieback "0.3.1"]]
                   :source-paths ["src" "dev"]
                   :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
                   :clean-targets ^{:protect false} ["resources/public/js/compiled"
                                                     :target-path]}}


  :aliases { "package" ["do" ["clean"] ["test"] ["clean"] ["cljsbuild" "once" "min"] ["uberjar"]]})
