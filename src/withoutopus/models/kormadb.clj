;; withoutopus is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. 
;; To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
;;
;; Some functions derived from Noir Blog - https://github.com/ibdknox/Noir-blog
;;
(ns withoutopus.models.kormadb
  (:require [clj-time.core :as ctime]
            [clj-time.format :as tform]
            [clj-time.coerce :as coerce]
            [noir.validation :as vali]
            [immutant.cache :as ic])
   (:use korma.db korma.core))

(def posts-per-page 10)
(def date-format (tform/formatter "MM/dd/yy" (ctime/default-time-zone)))
(def time-format (tform/formatter "h:mma" (ctime/default-time-zone)))

(defdb mydb (mysql {:db nil :user nil :password nil}))
(defentity textbox (pk :ip))

(def tdc "textbox-data-cache")

(defn split-timestamp [ts] 
  (zipmap [:date :tme] (for [dt [(coerce/from-long (.getTime ts))] fmt [date-format time-format]] (tform/unparse fmt dt))))

(defn valid? [{:keys [ip msg] :as post}]
  (vali/rule (vali/has-value? msg)
             [:msg "Perhaps you should type a message first?"])
  (vali/rule (vali/max-length? msg 100)
             [:msg "Keep message below 100 characters please"])
  (vali/rule (vali/has-value? ip)
             [:ip "Can't tell who you are!"])
  (not (vali/errors? :ip :msg)))

(defn add! [{:keys [ip msg] :as post}]
  (when (valid? post)
    (exec-raw ["insert into textbox (ip, message) values (?, ?) on duplicate key update message = values(message), visits = visits+1" [ip msg]])
    (ic/delete-all (ic/cache tdc))))

(defn get-page [page]
  (map 
    #(dissoc (conj % (split-timestamp (:ts %))) :ts) 
    (select textbox (order :ts :DESC) (limit posts-per-page) (offset (* posts-per-page (dec page))))))

(defn get-post [id]
  (map 
    #(dissoc (conj % (split-timestamp (:ts %))) :ts) 
    (select textbox (where {:post_id id})))) 

(defn post-count []
  (get-in (select textbox (fields (raw "count(1) cnt"))) [0 :cnt]))

(defn load-items [{:keys [page id]}]
  (cond (nil? id) (get-page page) 
       (nil? page) (get-post id) 
        :else (get-page 1)))

(def get-items (ic/memo load-items tdc :idle 3600))

(defn max-page []
  (let [pc (post-count)] 
    (quot (+ pc (if (zero? (rem pc posts-per-page)) 0 posts-per-page))  posts-per-page)))
