(ns hospital.aula6
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]))

(defn cabe-na-fila? [fila]
  (-> fila
      count
      (< 5)))

(defn chega-em [fila pessoa]
  (if (cabe-na-fila? fila)
    (conj fila pessoa)
    (throw (ex-info "Fila tá foda parceiro" {:tentando-adicionar pessoa}))))

(defn chega-em!
  "troca de referencia via ref-set"
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    (ref-set fila (chega-em @fila pessoa))))

(defn chega-em!
  "troca de referencia via"
  [hospital pessoa]
  (let [fila (get hospital :espera)]
    (alter fila chega-em pessoa)))

(defn simula-um-dia []
  (let [hospital {:espera       (ref h.model/empty-queue)
                  :laboratorio1 (ref h.model/empty-queue)
                  :laboratorio2 (ref h.model/empty-queue)
                  :laboratorio3 (ref h.model/empty-queue)}]
    (dosync
      (chega-em! hospital "guilherme")
      (chega-em! hospital "maria")
      (chega-em! hospital "lucia")
      (chega-em! hospital "daniela")
      (chega-em! hospital "ana")
      ;     (chega-em! hospital "paulo")
      )
    (pprint hospital)))

;(simula-um-dia)

(defn chega-em-async! [hospital pessoa]
  (future
    (Thread/sleep (rand 5000))
    (dosync
      (println "Tentando codigo sincronizado" pessoa)
      (chega-em! hospital pessoa))))

(defn simula-um-dia-async []
  (let [hospital {:espera       (ref h.model/empty-queue)
                  :laboratorio1 (ref h.model/empty-queue)
                  :laboratorio2 (ref h.model/empty-queue)
                  :laboratorio3 (ref h.model/empty-queue)}]
    (dotimes [pessoa 10]
      (chega-em-async! hospital pessoa))
    (future
      (Thread/sleep 8000)
      (pprint hospital))))

;(simula-um-dia-async)


(defn simula-um-dia-async []
  (let [hospital {:espera       (ref h.model/empty-queue)
                  :laboratorio1 (ref h.model/empty-queue)
                  :laboratorio2 (ref h.model/empty-queue)
                  :laboratorio3 (ref h.model/empty-queue)}
        futures (mapv #(chega-em-async! hospital %) (range 10))]
    (future
      (dotimes [n 4]
        (Thread/sleep 2000)
        (pprint hospital)
        (pprint futures)))
    ))

(defn simula-um-dia-async []
  (let [hospital {:espera       (ref h.model/empty-queue)
                  :laboratorio1 (ref h.model/empty-queue)
                  :laboratorio2 (ref h.model/empty-queue)
                  :laboratorio3 (ref h.model/empty-queue)}]
    ;global só pra olhar as exceptions no repl
    (def futures (mapv #(chega-em-async! hospital %) (range 10)))
    (future
      (dotimes [n 4]
        (Thread/sleep 2000)
        (pprint hospital)
        (pprint futures)))
    ))


(simula-um-dia-async)
;(println (future 15))
;(println (future ((Thread/sleep 1000) 15)))