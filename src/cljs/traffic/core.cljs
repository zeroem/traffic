(ns traffic.core
  (:require [cljs.core.async :refer [>! <! chan put! close!]]
            [traffic.render :refer [start-render-loop!]]
            [traffic.components :as c]
            [traffic.entities :as e]
            [traffic.systems :as s]
            [traffic.math :as m]
            [brute.entity :as b]
            [brute.system :as bs])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(enable-console-print!)

(def ac (chan 1))

(defn timeout [ms]
  (let [c (chan)]
    (js/setTimeout (fn [] (close! c)) ms)
    c))

(defn log [m] (.log js/console (prn-str m)))

(defn call-render-fn! [context system entity]
  (let [render-fn (:f (b/get-component system entity c/RenderFn))]
    (render-fn context system entity)))


(defn render-entities [s ts]
  (let [board (.getElementById js/document "board")
        context (.getContext board "2d")
        renderable (b/get-all-entities-with-component s c/RenderFn)]
    (.clearRect context 0 0 (.-width board) (.-height board))
    (dorun (map (partial call-render-fn! context s)
                renderable))))

(def stuff (start-render-loop! render-entities ac))


(def system (-> (b/create-system)

                (e/create-car {:position (c/->Position 20 20)
                               :velocity (c/->Velocity 0 30)})

                (e/create-car {:position (c/->Position 50 50)
                               :velocity (c/->Velocity (m/deg->rad 45) 35)})

                (bs/add-system-fn s/update-physics-system)))

(def step (chan))

#_(go
 (loop [system system
        last-update (.now js/performance)]
   #_(<! step)
   (>! ac system)
   (let [current-update (.now js/performance)]
     (recur (bs/process-one-game-tick system (- current-update last-update)) current-update))))

#_(put! step :next)
