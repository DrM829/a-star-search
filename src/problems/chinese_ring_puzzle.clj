(ns problems.chinese-ring-puzzle)

(defrecord State [leadRing Rings])

(defn children [state]
  (let [len (count (:Rings state)),
        leadRing (:leadRing state),
        Rings (:Rings state),
        nextRing (+ leadRing 1),
        R1 (nth Rings 0),
        R2 (nth Rings 1),
        R3 (nth Rings 2),
        R4 (nth Rings 3)]


    (let [children (if (= R1 "on")
        (let [Rings1 ["off" R2 R3 R4]
              leadRing1 (.indexOf Rings1 "on")]
              [(->State leadRing1 Rings1)])
        (let [Rings1 ["on" R2 R3 R4]
              leadRing1 (.indexOf Rings1 "on")]
              [(->State leadRing1 Rings1)]))]


    (if (<= nextRing (- len 1))
        (if (= (get Rings nextRing) "on")
            (let [Rings2 (assoc Rings nextRing "off"), leadRing2 (.indexOf Rings2 "on")]
                  (conj children (->State leadRing2 Rings2)))
            (let [Rings2 (assoc Rings nextRing "on"), leadRing2 (.indexOf Rings2 "on")]
                  (conj children (->State leadRing2 Rings2))))
            children))))
