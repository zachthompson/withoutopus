(ns withoutopus.init
   (:require [immutant.web :as web]
             [immutant.util :as util]
             [noir.server :as server]
             [withoutopus.models.kormadb :as db]))


(db/cache-last-ts)
;; To start a Noir app:
(server/load-views (util/app-relative "src/withoutopus/views"))
(web/start "/" (server/gen-handler {:mode :dev :ns 'withoutopus}))
