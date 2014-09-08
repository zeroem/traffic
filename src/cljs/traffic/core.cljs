(ns traffic.core
  (:require [cljs.core.async :refer [>! <! chan put! close!]]
            [traffic.render :refer [start-render-loop]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(enable-console-print!)

(def ac (chan 5))

(defn timeout [ms]
  (let [c (chan)]
    (js/setTimeout (fn [] (close! c)) ms)
    c))

(defn log [m] (.log js/console (prn-str m)))

(defn render-square [boxes ts]
  (let [board (.getElementById js/document "board")
        context (.getContext board "2d")]
    (.clearRect context 0 0 (.-width board) (.-height board))
    (dorun (map (fn [box]
                  (aset context "fillStyle" (:color box))
                  (.fillRect context (:x box) (:y box) (:size box) (:size box)))
                boxes))))

(defn render-entities [s ts]
  )

(defn rando-square []
  {:x (rand-int 640)
   :y (rand-int 480)
   :size 10
   :delta (+ 1 (rand-int 4))
   :color "#F00"})


(def stuff (start-render-loop render-square ac))


(defn update-box [b]
  (assoc b
    :x (+ (:x b) (- (rand-int 2) (rand-int 2)))
    :y (+ (:y b) (- (rand-int 2) (rand-int 2)))))

#_(go
 (loop [boxes (repeatedly 10 rando-square)]
   (>! ac boxes)
   (recur (map update-box boxes))))
