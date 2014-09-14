(ns traffic.components.acceleration
  (:require [traffic.components :as c]
            [traffic.math :as m]))

(defn- apply-acceleration [a v d]
  (let [dt (/ d 1000)]
    (c/->Velocity (:d v) (+ (:s v) (* dt a)))))

(defn linear [a]
  (c/->Acceleration
   (partial apply-acceleration a)))

(defn sinusoidal
  "Sinusoidal Acceleration Curve"
  [vi vf]
  (c/->Acceleration
   (fn [v d]
     (if (< (:s v) vf)
       (let [dv-total (- vf vi)
             dv-remaining (- vf (:s v))
             dv-percent (/ (- dv-total dv-remaining) dv-total)
             rads (* m/pi dv-percent)
             a (+ 0.1 (* (m/sin rads) dv-total))]
         (apply-acceleration a v d))))))
