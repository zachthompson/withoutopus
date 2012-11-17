(defproject withoutopus "0.1"
  :description "Immutant-based Noir app serviing withoutopus.org"
  :url "http://withoutopus.org"
  :license {:name "Creative Commons Attribution-ShareAlike 3.0 Unported License"
            :url "http://creativecommons.org/licenses/by-sa/3.0/"}
  :dependencies [[org.clojure/clojure "1.4.0"]
    [noir "1.3.0-beta3"]
    [clj-time "0.4.4"]
    [korma "0.3.0-beta7"]  
    [mysql/mysql-connector-java "5.1.21"]]
  :immutant {:context-path "/"
             :nrepl-port 4343})
