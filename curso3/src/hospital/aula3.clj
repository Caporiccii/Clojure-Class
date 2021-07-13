(ns hospital.aula3
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

;Usando Let: shadowing não refaz o simbolo local, cria outro simbolo e esconde o anterior

(defn testa-atomao []
  (let [hospital-silveira (atom {:espera h.model/empty-queue})]
    (println hospital-silveira)
    (pprint hospital-silveira)
    (pprint (deref hospital-silveira))                      ;dereferencia
    (pprint @hospital-silveira)                      ;dereferencia o atomo, quando eu só quero saber o valor que esta dentro

    ;não é assim que altera o valor de um atomo
    (pprint (assoc @hospital-silveira :laboratorio1 h.model/empty-queue))
    (pprint @hospital-silveira)

    ;! indica efeito colateral da função
    ;uma das maneiras de alterar conteudo num atomo
    (swap! hospital-silveira assoc :laboratorio1 h.model/empty-queue)
    (pprint @hospital-silveira)

    (swap! hospital-silveira assoc :laboratorio2 h.model/empty-queue)
    (pprint @hospital-silveira)

    ;update tradicional imutavel, com dereferencia sem efeito colateral
    (update @hospital-silveira :laboratorio1 conj "111")

    (swap! hospital-silveira update :laboratorio1 conj "111" )
    (pprint hospital-silveira)

    ))

;(testa-atomao)


(defn chega-e-malvado! [hospital pessoa]
  ;swap necessita chamar uma função que não tenha problema ao dar retry
  ;caso quebre a atomicidade ele retry, sem problema de lock nas threads, porem há busy retry cuidado com isso
  (swap! hospital h.logic/chega-em-pausado-logando :espera pessoa)
  (println "apos inserir" pessoa))


(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
  (.start (Thread. (fn [] (chega-e-malvado! hospital "111"))))
  (.start (Thread. (fn [] (chega-e-malvado! hospital "222"))))
  (.start (Thread. (fn [] (chega-e-malvado! hospital "333"))))
  (.start (Thread. (fn [] (chega-e-malvado! hospital "444"))))
  (.start (Thread. (fn [] (chega-e-malvado! hospital "555"))))
  (.start (Thread. (fn [] (chega-e-malvado! hospital "666"))))
  (.start (Thread. (fn [] (Thread/sleep 4000)
                     (pprint hospital))))))
;aqui força situação de retry
;(simula-um-dia-em-paralelo)


(defn chega-em-bonzinho! [hospital pessoa]
  (swap! hospital h.logic/chega-em :espera pessoa)
  (println "apos inserir" pessoa))


(defn simula-um-dia-em-paralelo
  []
  (let [hospital (atom (h.model/novo-hospital))]
    (.start (Thread. (fn [] (chega-em-bonzinho! hospital "111"))))
    (.start (Thread. (fn [] (chega-em-bonzinho! hospital "222"))))
    (.start (Thread. (fn [] (chega-em-bonzinho! hospital "333"))))
    (.start (Thread. (fn [] (chega-em-bonzinho! hospital "444"))))
    (.start (Thread. (fn [] (chega-em-bonzinho! hospital "555"))))
    (.start (Thread. (fn [] (chega-em-bonzinho! hospital "666"))))
    (.start (Thread. (fn [] (Thread/sleep 4000)
                       (pprint hospital))))))

;sem forçar retry (busy retry) pode ou não acontecer
(simula-um-dia-em-paralelo)

;atom não preciso me preocupar com concorrencia