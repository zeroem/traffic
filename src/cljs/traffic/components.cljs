(ns traffic.components)

(defrecord Velocity [d s])
(defrecord Acceleration [f])
(defrecord Origin [x y])
(defrecord OriginBox [ox oy dx dy])
(defrecord RenderFn [f])
(defrecord Hitbox [^OriginBox b])


(defn originating-box [dx dy]
  (->OriginBox 0 0 dx dy))

(defn surrounding-box [dx dy]
  (->OriginBox (- (/ dx 2)) (- (/ dy 2)) dx dy))

(defn bounding-points [^Origin o ^OriginBox b]
  (let [ll {:x (+ (:x o) (:ox b))
            :y (+ (:y o) (:oy b))}]
    [ll
     {:x (+ (:x ll) (:dx b))
      :y (+ (:y ll) (:dy b))}]))

(defn box-origin [^Origin o ^OriginBox b]
  (->Origin (+ (:x o) (:ox b)) (+ (:y o) (:oy b))))

(defn within? [^Origin o ^OriginBox b ^Origin p]
  (let [{px :x py :y} p
        [[lx by] [rx ty]] (bounding-points o b)
        ]
      (and
       (>= px lx)
       (<= px rx)
       (>= py by)
       (<= py ty))))

(defn overlaps? [^Origin o1 ^OriginBox b1 ^Origin o2 ^OriginBox b2]
  (let [[[lx1 by1] [rx1 ty1]] (bounding-points o1 b1)
        [[lx2 by2] [rx2 ty2]] (bounding-points o2 b2)]
    (and
     (<= lx1 rx2)
     (>= rx1 lx2)
     (<= ly1 ty2)
     (>= ty1 ly2))))
