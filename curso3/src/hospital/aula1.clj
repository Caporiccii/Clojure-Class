(ns hospital.aula1
  (:use [clojure.pprint])
  (:require [hospital.model :as h.model]
            [hospital.logic :as h.logic]))

(defn simula-um-dia []
  ;root binding
  (def hospital (h.model/novo-hospital))
  (def hospital (h.logic/chega-em hospital :espera "111"))
  (def hospital (h.logic/chega-em hospital :espera "222"))
  (def hospital (h.logic/chega-em hospital :espera "333"))
  ;Funciona, mas esse tanto de simbolo global
  ;com rootbinding mudando pra krl não dá
  (pprint hospital)


  (def hospital (h.logic/chega-em hospital :laboratorio1 "444"))
  (def hospital (h.logic/chega-em hospital :laboratorio3 "555"))

  (pprint hospital)

  (def hospital (h.logic/atende hospital :laboratorio1))
  (def hospital (h.logic/atende hospital :espera))

  (pprint hospital)

  (def hospital (h.logic/chega-em hospital :espera "666"))
  (def hospital (h.logic/chega-em hospital :espera "777"))
  (def hospital (h.logic/chega-em hospital :espera "888"))
  (pprint hospital)
  (def hospital (h.logic/chega-em hospital :espera "999"))
  (pprint hospital))
;(simula-um-dia)

;É sempre muito importante saber se você tem um vetor, uma fila, um mapa etc em suas mãos.
;
;
;Sim, pelo menos temos que saber se estamos trabalhando com tipos associativos ou não.


(defn chega-e-malvado [pessoa]
  (def hospital (h.logic/chega-em-pausado hospital :espera pessoa))
  (println "apos inserir" pessoa))

; Muito claro o problema de variavel global (simbolo do namespace) compartilhado por outras threads
(defn simula-um-dia-em-paralelo
  []
  (def hospital (h.model/novo-hospital))
  (.start (Thread. (fn [] (chega-e-malvado "111"))))
  (.start (Thread. (fn [] (chega-e-malvado "222"))))
  (.start (Thread. (fn [] (chega-e-malvado "333"))))
  (.start (Thread. (fn [] (chega-e-malvado "444"))))
  (.start (Thread. (fn [] (chega-e-malvado "555"))))
  (.start (Thread. (fn [] (chega-e-malvado "666"))))
  (.start (Thread. (fn [] (Thread/sleep 4000)
                     (pprint hospital)))))


(simula-um-dia-em-paralelo)

;Não temos como fugir da complexidade da concorrência e teremos que procurar uma solução dentre as
;diversas abordagens possíveis como locks, retries, single thread.
;São diversas as alternativas e vamos ver que em Clojure é comum ir pelo caminho de retries.