;; withoutopus is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. 
;; To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
;;
;; Derived from Noir Blog - https://github.com/ibdknox/Noir-blog
;;
(ns withoutopus.views.textbox
  (:use [noir.core :only [defpage defpartial render]]
        [hiccup.element :only [link-to]]
        [hiccup.page :only [html5 include-css]]
        [hiccup.util :only [escape-html]]
        [hiccup.form :only [form-to text-field hidden-field submit-button]]
        [withoutopus.views.common :only [license]])
  (:require [noir.validation :as vali]
            [noir.response :as resp]
            [noir.request :as req]
            [withoutopus.models.kormadb :as db]))

(defn- ip []
  (let [r (req/ring-request)]
  (or (get-in r [:headers "x-real-ip"])
      (:remote-addr r))))

(defn- perma-link [id]
  (str "/textbox/view/" id))

(defpartial error-text [errors]
            [:span {:class "error"} (apply str errors)] [:br])

(defpartial post-item [{:keys [post_id visits ip message date tme] :as post}]
            (when post
              [:li.post
               [:h2 (link-to (perma-link post_id)  (str "Poster #" post_id))]
               [:ul.dt
                [:li date]
                [:li tme]
                [:li (str "Messages " visits)]]
               [:p.msg message]]))

(defpartial pager [current]
  (map #(if (= current %) (str %) (link-to (str "/textbox/page/" %) %)) (range 1 (inc (db/max-page)))))

(defpartial textbox-page [{:keys [page id] :as request}]
  (let [items (db/get-items request)]
    (html5
      [:head [:title "text box"] 
        (include-css "http://yui.yahooapis.com/3.7.3/build/cssreset/cssreset-min.css" "/static/css/textbox-min.css")]
      [:body
        [:div#hd
          [:h1 (link-to "/textbox" "text box")]
          [:h2 "&nbsp;(a variation on "  (link-to "https://github.com/ibdknox/Noir-blog" "Noir Blog") ")"]
          (link-to {:class "nav"} "http://withoutopus.org" "more withoutopus")]
        [:ul
          [:li.post
            (form-to [:post "/textbox"]
             (vali/on-error :msg error-text)
             (text-field {:class "input" :placeholder "new message"} :msg "")
             (vali/on-error :ip error-text);
             (hidden-field {:placeholder "poster"} :ip "")
             (submit-button {:class "submit"} "Add"))]
          (map post-item items)]
          (when (nil? id) [:p#pg (pager page)])
          (license)])))

(defpage "/textbox" [] 
            (textbox-page {:page 1}))

(defpage [:post "/textbox"] {:as post}
           (if (db/add! (-> post (assoc :ip (ip)) (assoc :msg (escape-html (:msg post)))))
             (resp/redirect "/textbox")
             (render "/textbox")))

(defpage "/textbox/view/:perma" {:keys [perma]}
  (let [id (try (Integer. perma) (catch Exception e 1))]
           (textbox-page {:id id})))

(defpage "/textbox/page/:pg" {:keys [pg]}
  (let [page (try (Integer. pg) (catch Exception e 1))]
           (textbox-page {:page page})))
