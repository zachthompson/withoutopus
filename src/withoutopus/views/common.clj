;; withoutopus is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. 
;; To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
;;
(ns withoutopus.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.page :only [include-css html5]]
        [hiccup.element :only [link-to image]]))

;; license
(defpartial license []
  [:p {:class "license"}
    (link-to {:rel "license"} "http://creativecommons.org/licenses/by-sa/3.0/deed.en_US" (image "http://i.creativecommons.org/l/by-sa/3.0/80x15.png" "Creative Commons License"))
    [:span {:xmlns:dct "http://purl.org/dc/terms/" :property "dct:title"} "withoutopus.org" ] 
    " is licensed under a " 
    (link-to {:rel "license"} "http://creativecommons.org/licenses/by-sa/3.0/deed.en_US" "Creative Commons Attribution-ShareAlike 3.0 Unported License") "."])

;; landing page

(defpartial layout [& content]
            (html5
              [:head
               [:title "withoutopus"]
               (include-css "/staic/css/reset.css" "/static/css/default.css")]
              [:body
               [:div#wrapper
                  [:div#header
                    [:h1 (link-to "/" "withoutopus")]]
                [:div.content
                content](license)]]))

;; textbox
;; Based on Noir Blog - https://github.com/ibdknox/Noir-blog
(defpartial textbox-build-head [incls]
            [:head
             [:title "text box"]
             (include-css "/static/css/reset.css" "/static/css/textbox.css")])

(defpartial textbox-link-item [{:keys [url cls text]}]
            [:li
             (link-to {:class cls} url text)])

;; Layouts

(defpartial textbox-layout [& content]
            (html5
              (textbox-build-head [:reset :default])
              [:body
               [:div#wrapper
                [:div.content
                 [:div#header
                  [:h1 (link-to "/textbox" "text box")]
                  [:h2 "&nbsp;(a variation on "  (link-to "https://github.com/ibdknox/Noir-blog" "Noir Blog") ")"]
                  [:ul.nav
                   (textbox-link-item {:url "http://withoutopus.org" :text "more withoutopus"})]]
                 content] (license)]]))
