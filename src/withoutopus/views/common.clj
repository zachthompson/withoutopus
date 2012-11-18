;; withoutopus is licensed under the Creative Commons Attribution-ShareAlike 3.0 Unported License. 
;; To view a copy of this license, visit http://creativecommons.org/licenses/by-sa/3.0/.
;;
(ns withoutopus.views.common
  (:use [noir.core :only [defpartial]]
        [hiccup.element :only [link-to image]]))

;; license
(defpartial license []
  [:p#lic 
    (link-to {:rel "license"} "http://creativecommons.org/licenses/by-sa/3.0/deed.en_US" (image "http://i.creativecommons.org/l/by-sa/3.0/80x15.png" "Creative Commons License"))
    [:span {:xmlns:dct "http://purl.org/dc/terms/" :property "dct:title"} "withoutopus.org" ] 
    " is licensed under a " 
    (link-to {:rel "license"} "http://creativecommons.org/licenses/by-sa/3.0/deed.en_US" "Creative Commons Attribution-ShareAlike 3.0 Unported License") "."])
