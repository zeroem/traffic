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
  (let [car (b/create-entity)
        s (-> system
              (b/add-entity car)
              (b/add-component car (c/->RenderFn (car-renderer "red"))))]
    (reduce (fn [s k] (if-let [opt (get opts k)]
                        (b/add-component s car opt)
                        s)) s [:position :velocity :acceleration])))
