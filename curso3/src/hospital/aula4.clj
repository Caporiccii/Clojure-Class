(ns hospital.aula4
      (:use [clojure.pprint])
      (:require [hospital.model :as h.model]
                [hospital.logic :as h.logic]))


  (defn chega-em-bonzinho! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))


(defn simula-um-dia-em-paralelo-com-mapv
  "Simulação usando mapv para forçar a execução do que era lazy"
  []
  (let [hospital (atom (h.model/novo-hospital))
    pessoas [ "111" "222" "333" "444" "555" "666"]]

    (mapv #(.start (Thread. (fn [] (chega-em-bonzinho! hospital %)))) pessoas)

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-mapv)

(defn  starta-thread-de-chegada
  ([hospital]
  (fn [pessoa] (starta-thread-de-chegada hospital pessoa)))
  ([hospital pessoa]
   (.start (Thread. (fn [] (chega-em-bonzinho! hospital pessoa))))))

(defn simula-um-dia-em-paralelo-com-mapv-extraida
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [ "111" "222" "333" "444" "555" "666"]
        starta (partial starta-thread-de-chegada hospital)]

    (mapv starta pessoas )

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-mapv-extraida)



(defn  starta-thread-de-chegada
  [hospital pessoa]
   (.start (Thread. (fn [] (chega-em-bonzinho! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-mapv-partial
  []
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [ "111" "222" "333" "444" "555" "666"]
        ;       "assim tenho parcialmente os parametros e o partial lida com o resto"
        starta (partial starta-thread-de-chegada hospital)]

    (mapv starta pessoas )

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-mapv-partial)


(defn  starta-thread-de-chegada
  [hospital pessoa]
  (.start (Thread. (fn [] (chega-em-bonzinho! hospital pessoa)))))

(defn simula-um-dia-em-paralelo-com-doseq
  []
  "Preocupado para executar perante a sequencia em vezes"
  (let [hospital (atom (h.model/novo-hospital))
        pessoas [ "111" "222" "333" "444" "555" "666"]]

    (doseq [pessoa pessoas]
      (starta-thread-de-chegada hospital pessoa))

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))))


;(simula-um-dia-em-paralelo-com-doseq)



(defn simula-um-dia-em-paralelo-com-dotimes
  []
  (let [hospital (atom (h.model/novo-hospital))]

    ;preocupado somente com a quantidae de vezes que vai executar
    (dotimes [pessoa 6]
      (starta-thread-de-chegada hospital pessoa))

    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))))


(simula-um-dia-em-paralelo-com-dotimes)

;A função que estamos invocando causa efeitos colaterais,
;não é pura, ou temos código possivelmente inútil que pode ser removido
;
;Se a função não causasse nenhum efeito colateral, somente retornasse valores,
;não teríamos como saber o que aconteceu pois não temos retorno. Caso ela realmente
;não tenha efeito colateral provavelmente podemos
;remover esse código. Lembre-se que println causa efeito colateral, a impressão na tela.

