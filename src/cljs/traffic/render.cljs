(ns traffic.render
  (:require [cljs.core.async :refer [<! close! chan alts!]])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(defn frame-renderer [f]
  (fn [c v ts]
    (f v ts)
    (close! c)))

(defn start-render-loop [render-fn request-chan]
  (let [terminator (chan)
        render-fn (frame-renderer render-fn)]
    (go
     (loop [[v c] (alts! [request-chan terminator])]
       (if (= c request-chan)
         (let [frame-rendered (chan)]
           (.requestAnimationFrame
            js/window
            (partial render-fn frame-rendered v))
           (<! frame-rendered)
           (recur (alts! [request-chan terminator]))))))
    [request-chan closer]))
