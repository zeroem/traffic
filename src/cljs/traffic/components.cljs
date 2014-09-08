(ns traffic.components
  (:require [traffic.math :as m]))

(defrecord Motion [^m/Direction2D d v a])
(defrecord Position [^m/Point2D p])
(defrecord RenderFn [f])
