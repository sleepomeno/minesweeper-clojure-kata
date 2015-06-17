(ns minesweeper-clojure.core
  (:require [clojure.string :as str]))

(defn read-input [src]
  (let [input (slurp src)
        [ _ & lines ] (str/split-lines input)
        board (vec (map (fn [row]
                               (vec (map (fn [square] (if (= square \x) 1 0)) row))) lines))]
    board))

(defn nr-neighbours [ board x y ]
  (let [neighbour-coords [[-1 -1]
                          [-1 0]
                          [-1 1]
                          [0 -1]
                          [0 1]
                          [1 -1]
                          [1 0]
                          [1 1]]

        result (reduce (fn [ nr [ offset-y offset-x ] ]
                  (+ nr (get-in board [(+ y offset-y) (+ x offset-x)] 0)))
                0
                neighbour-coords)]
    (if (= result 0) \space result)))

(defn solve-board [ board ]
  (map-indexed (fn [y row]
                 (map-indexed (fn [x val]
                                (if (= val 1) \B (nr-neighbours board x y))) row)) board))


(defn serialize-board [ board]
  (->> board (map #(str/join \space %)) (str/join \newline) ))

(defn solve [src]
  (-> src read-input solve-board serialize-board))

(solve "input.txt")

