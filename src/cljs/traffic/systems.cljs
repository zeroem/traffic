(ns traffic.systems
  (:require [brute.entity :as b]
            [traffic.components :as c]))

(defn calculate-velocity [v dv d]
  (if v
    (let [dt (/ d 1000)]
      (c/->Velocity (:d v) (+ (:s v) (* dt dv))))))

(defn update-velocity [d s e]
  (if-let [a (b/get-component d e c/Acceleration)]
    (b/update-component s e c/Velocity calculate-velocity (:a a) d))
  s)

(defn update-velocities [s d es]
  (reduce (partial update-velocity d)s es))

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
         (update-velocities d moving)
         (update-positions d moving))))
