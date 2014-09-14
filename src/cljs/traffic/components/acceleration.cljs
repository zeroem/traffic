(ns traffic.components.acceleration
  (:require [traffic.components :as c]))

(defn linear [dv]
  (c/->Acceleration
   (fn [v d]
     (let [dt (/ d 1000)]
       (c/->Velocity (:d v) (+ (:s v) (* dt dv)))))))
