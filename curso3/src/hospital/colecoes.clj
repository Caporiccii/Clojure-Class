(ns hospital.colecoes
  (:use [clojure.pprint]))

(defn testa-vetor []
  (let [espera [111 222]]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))
    ))

(testa-vetor)

(defn testa-lista []
  ;lista ligada
  (let [espera '(111 222)]
    (println espera)
    (println (conj espera 333))
    (println (conj espera 444))
    (println (pop espera))
    ))

(testa-lista)



(defn testa-conjunto []
  (let [espera #{111 222}]
    (println espera)
    (println (conj espera 111))
    (println (conj espera 333))
    (println (conj espera 444))
    ;(println (pop espera )) não rola pop em set conjunto
    ))

(testa-conjunto)


(defn testa-queue []
  (let [espera (conj clojure.lang.PersistentQueue/EMPTY "111" "222")]
    (println "Fila")
    (println (seq espera))
    (println (seq (conj espera "333")))
    (println (seq (pop espera)))                            ; seq usado para imprimir
    (println (peek espera))
    (pprint espera)))                                       ;pprint printa mais bonitinho

(testa-queue)