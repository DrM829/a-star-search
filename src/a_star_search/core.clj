(ns a-star-search.core
  (:require [search.algorithms :as alg]
            [problems.chinese-ring-puzzle :as crp])
  (:gen-class))

(defn print-results [result path costs goal-state]
  (println (str "We explored " (count result) " states."))
  (println "The path to the solution is:")
  (doseq [b (map :Rings path)]
    (println b))
  (when costs
    (println "The path has" (count path) "steps and its cost is" (costs goal-state))))

(defn -main
  "Search for a hard-coded N-puzzle target state."
  [& args]
  (let [start-state (crp/->State 0 ["on" "on" "on" "on" "on" "on" "on" "on" "on"])
        goal-state (crp/->State -1 ["off" "off" "off" "off" "off" "off" "off" "off" "off"])
        max-states 10000
        costs nil
        ;came-from (alg/breadth-first-search crp/children max-states start-state goal-state)
        [came-from costs] (alg/shortest-path crp/children (constantly 1)
                                              max-states start-state goal-state )
        ; [came-from costs] (alg/shortest-path crp/children crp/prefer-horizontal-cost
        ;                                      max-states start-state goal-state)
        path (alg/extract-path came-from start-state goal-state)]
    (print-results came-from path costs goal-state)))

; Run (with timing) with
; (time (-main))
; or
; time lein run

(-main)
