(ns traffic.entities
  (:require [brute.entity :as b]
            [traffic.components :as c]))


(defn car-renderer [color]
  (fn [context s e]
    (let [o (b/get-component s e c/Origin)
          hb (b/get-component s e c/Hitbox)
          b (:b hb)
          o (c/box-origin o b)]
      (aset context "fillStyle" color)
      (.fillRect context (:x o) (:y o) (:dx b) (:dy b) 10))))

(defn create-car
  [system opts]
  (let [car (b/create-entity)
        s (-> system
              (b/add-entity car)
              (b/add-component car (c/->Hitbox (c/surrounding-box 5 10)))
              (b/add-component car (c/->RenderFn (car-renderer "red"))))]
    (reduce (fn [s k] (if-let [opt (get opts k)]
                        (b/add-component s car opt)
                        s)) s [:position :velocity :acceleration])))
