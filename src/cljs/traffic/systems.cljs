(ns traffic.systems
  (:require [brute.entity :as b]
            [traffic.components :as c]))

(defn calculate-motion [m d]
  (if m
    (let [dt (/ d 1000)]
    (c/->Velocity (:d m) (+ (:s m)(* dt (:dv m))) (:dv m)))))

(defn update-motion [d s e]
  (b/update-component s e c/Velocity calculate-motion d))

(defn update-motions [s d es]
  (reduce (partial update-motion d)s es))

(defn calculate-position [p m d]
  (if p
    (let [dt (/ d 1000)
          dx (* dt (:s m) (.cos js/Math (:d m)))
          dy (* dt (:s m) (.sin js/Math (:d m)))]
      (c/->Position (+ (:x p) dx) (+ (:y p) dy)))))

(defn update-position [d s e]
  (if-let [m (b/get-component s e c/Velocity)]
    (b/update-component s e c/Position calculate-position m d)
    s))


(defn update-positions [s d es]
  (reduce (partial update-position d) s es))

(defn update-physics-system
  "Update Position and Velocity Components"
  [system d]
  (let [moving (b/get-all-entities-with-component system c/Velocity)]
     (-> system
         (update-motions d moving)
         (update-positions d moving))))
