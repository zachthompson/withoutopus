;; withoutopus is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. 
;; To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
;;
(ns withoutopus.views.landing
  (:require [withoutopus.views.common :as common])
  (:use [noir.core :only [defpage]] [hiccup.element :only [link-to]]))

(defpage "/" []
         (common/layout
           (link-to "/textbox" "text box")))
