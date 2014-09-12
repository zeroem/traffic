(ns traffic.entities
  (:require [brute.entity :as b]
            [traffic.components :as c]))


(defn car-renderer [color]
  (fn [context s e]
    (let [p (b/get-component s e c/Position)]
      (aset context "fillStyle" color)
      (.fillRect context (:x p) (:y p) 5 10))))

(defn create-car
  [system opts]
   (let [car (b/create-entity)]
     (-> system
         (b/add-entity car)
         (b/add-component car (:position opts))
         (b/add-component car (:motion opts))
         (b/add-component car (c/->RenderFn (car-renderer "red"))))))
