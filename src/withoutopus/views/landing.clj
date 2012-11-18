;; withoutopus is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. 
;; To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
;;
(ns withoutopus.views.landing
  (:use [noir.core :only [defpage]] 
    [hiccup.element :only [link-to]]
    [hiccup.page :only [include-css html5]]
    [withoutopus.views.common :only [license]]))

(defpage "/" [] 
  (html5
    [:head 
      [:title "withoutopus"] 
      (include-css "http://yui.yahooapis.com/3.7.3/build/cssreset/cssreset-min.css" "/static/css/woo-min.css")]
    [:body
      [:div#hd [:h1 (link-to "/" "withoutopus")]]
      [:div#mn (link-to "/textbox" "text box")]
      (license)]))
