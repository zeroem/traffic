(ns traffic.math)

(defn sqrt [x] (.sqrt js/Math x))
(defn pow [x e] (.pow js/Math x e))
(def pi (.-PI js/Math))
(defn deg->rad [deg] (* (/ pi 180) deg))
