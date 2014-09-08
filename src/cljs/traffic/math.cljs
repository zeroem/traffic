(ns traffic.math)

(defn sqrt [x] (.sqrt js/Math x))
(defn pow [x e] (.pow js/Math x e))

(defrecord Direction2D [dx dy])

(defn direction2d [dx dy] (->Direction2D dx dy))

(defn magnitude2d [^Direction2D d]
  (sqrt (+ (pow (:dx d) 2) (pow (:dy d) 2))))

(defn unit2d [^Direction2D d]
  (let [m (magnitude2d d)]
  (->Direction2D (/ (:dx d) m) (/ (:dy d) m))))

(defrecord Point2D [x y])

(defn point2d [x y] (->Point2D x y))

(defrecord Rectangle [x y dx dy])

(defn rectangle [x y dx dy] (->Rectangle x y dx dy))
