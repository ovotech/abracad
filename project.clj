(defproject ovotech/abracad "0.4.16"
  :description "De/serialize Clojure data structures with Avro."
  :url "https://github.com/ovotech/abracad"
  :licenses [{:name "Eclipse Public License"
              :url "http://www.eclipse.org/legal/epl-v10.html"}
             {:name "Apache License, Version 2.0"
              :url "http://www.apache.org/licenses/LICENSE-2.0.html"}]
  :global-vars {*warn-on-reflection* true}
  :source-paths ["src/clojure"]
  :java-source-paths ["src/java"]
  :javac-options ["-target" "1.8" "-source" "1.8"]
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.apache.avro/avro "1.9.1"]
                 [cheshire/cheshire "5.6.1"]
                 [org.xerial.snappy/snappy-java "1.1.7.3"]]
  :plugins [[codox/codox "0.6.4"]]
  :codox {:include [abracad.avro abracad.avro.edn]}
  :aliases {"test-all" ["with-profile" ~(str "clojure-1-6:"
                                             "clojure-1-7:"
                                             "clojure-1-8")
                        "test"]}
  :profiles {:clojure-1-6 {:dependencies
                           [[org.clojure/clojure "1.6.0"]]}
             :clojure-1-7 {:dependencies
                           [[org.clojure/clojure "1.7.0"]]}
             :clojure-1-8 {:dependencies
                           [[org.clojure/clojure "1.8.0"]]}
             :ci {:deploy-repositories [["clojars" {:url           "https://clojars.org/repo"
                                                    :username      :env ;; LEIN_USERNAME
                                                    :password      :env ;; LEIN_PASSWORD
                                                    :sign-releases false}]]}})
