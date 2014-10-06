(ns traffic.systems
  (:require [brute.entity :as b]
            [traffic.components :as c]))

(defn calculate-velocity [v f d]
  (if v
    (f v d)))

(defn update-velocity [d s e]
  (if-let [a (b/get-component s e c/Acceleration)]
    (b/update-component s e c/Velocity calculate-velocity (:f a) d)
  s))

(defn update-velocities [s d es]
  (reduce (partial update-velocity d) s es))

(defn calculate-position [p m d]
  (if p
    (let [dt (/ d 1000)
          dx (* dt (:s m) (.cos js/Math (:d m)))
          dy (* dt (:s m) (.sin js/Math (:d m)))]
      (c/->Origin (+ (:x p) dx) (+ (:y p) dy)))))

(defn update-position [d s e]
  (if-let [m (b/get-component s e c/Velocity)]
    (b/update-component s e c/Origin calculate-position m d)
    s))


(defn update-positions [s d es]
  (reduce (partial update-position d) s es))

(defn update-physics-system
  "Update Origin and Velocity Components"

  [system d]

  (let [moving (b/get-all-entities-with-component system c/Velocity)]
     (-> system
         (update-velocities d moving)
         (update-positions d moving))))
