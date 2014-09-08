(ns traffic.entites
  (:require [brute.entity :as b]
            [traffic.components :as c]))


(defn render-car [color]
  (fn [e s ctx]
    (let [p (b/get-component e s c/Position)]
      (aset context "fillStyle" color)
      (.fillRect context (:x box) (:y box) 5 10))))

(defn create-car
  [system opts]
   (let [car (b/create-entity)]
     (-> system
         (b/add-entity car)
         (b/add-component car (:position opts))
         (b/add-component car (:motion opts))
         (b/add-component car (c/RenderFn render-car)))))
