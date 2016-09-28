; we used the two rules of chinese ring puzzle:
; 1) the first ring, which is hooked in the very front of the loop, can be taken off or put back on the loop any time;
; 2) the only other ring that can be taken off from or put back on the loop is the ring next to the first ring.
(ns problems.chinese-ring-puzzle)
;added two more fileds to each state:
; 1. num-rings-off contains the number of rings that are off for each state.
; 2. last-ring-off contains the index number of the ring that is "off" for each state.
(defrecord State [leadRing Rings num-rings-on last-ring-off])

(defn children [state]
  (let [len (count (:Rings state)),
        leadRing (:leadRing state),
        Rings (:Rings state),
        nextRing (+ leadRing 1)
        R1 (nth Rings 0)]


    (let [children (if (= R1 "on")
        (let [Rings1 (assoc Rings 0 "off")
              leadRing1 (.indexOf Rings1 "on")
              num-rings-on-1 (count (filter #(= "on" %) Rings1))
              last-ring-off-1 (.lastIndexOf Rings1 "off")]
              [(->State leadRing1 Rings1 num-rings-on-1 last-ring-off-1)])
        (let [Rings1 (assoc Rings 0 "on")
              leadRing1 (.indexOf Rings1 "on")
              num-rings-on-1 (count (filter #(= "on" %) Rings1))
              last-ring-off-1 (.lastIndexOf Rings1 "off")]
              [(->State leadRing1 Rings1 num-rings-on-1 last-ring-off-1)]))]


    (if (< nextRing len)
        (if (= (get Rings nextRing) "on")
            (let [Rings2 (assoc Rings nextRing "off")
                  leadRing2 (.indexOf Rings2 "on")
                  num-rings-on-2 (count (filter #(= "on" %) Rings2))
                  last-ring-off-2 (.lastIndexOf Rings2 "off")]
                  (conj children (->State leadRing2 Rings2 num-rings-on-2 last-ring-off-2)))
            (let [Rings2 (assoc Rings nextRing "on")
                  leadRing2 (.indexOf Rings2 "on")
                  num-rings-on-2 (count (filter #(= "on" %) Rings2))
                  last-ring-off-2 (.lastIndexOf Rings2 "off")]
                  (conj children (->State leadRing2 Rings2 num-rings-on-2 last-ring-off-2))))
            children))))

(defn heuristic-function [state]
  (+ (:num-rings-on state) (- 9 (:last-ring-off state))))


;(count (get (group-by count ["on" "off" "on" "off"]) 3))
;(- (- (count ["on" "off" "on" "off"]) 1) (.indexOf (reverse ["on" "off" "on" "off"]) "off"))
;(count (filter #(= "off" %) ["on" "off" "on" "off"]))
;(count (filter (fn [s] (= "off" s)) ["on" "off" "on" "off"]))
;(.lastIndexOf [1 2 3 2 1] 1)
