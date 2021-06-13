(ns curso.AulaSeis)

(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}})

(defn imprime-e-15
  [valor]
  (println "valor" (class valor) valor)
  15)

(println (map imprime-e-15 pedido))

;(defn imprime-e-15
; [chave valor]
; (println chave "e" valor)
; 15)

;(println (map imprime-e-15 pedido))

(defn imprime-e-15
 [[chave valor]]                                            ;desestruturação de vetor
 (println chave "e" valor)
 15)

(println (map imprime-e-15 pedido))

(defn imprime-e-15
  [[chave valor]]                                            ;desestruturação de vetor
  ;  (println chave "e" valor)
  valor)

(println (map imprime-e-15 pedido))

(defn imprime-e-15
  [[chave valor]]                                            ;desestruturação de vetor
  ;  (println chave "e" valor)
  chave)

(println (map imprime-e-15 pedido))

(defn preco-dos-produtos
  [[chave valor]]
  (* (:quantidade valor) (:preco valor)))

(println pedido)
(println (map preco-dos-produtos pedido))
(println (reduce + (map preco-dos-produtos pedido)))

(defn preco-dos-produtos
  [[_ valor]]                                               ;pois o primeiro parametro n vou usar
  (* (:quantidade valor) (:preco valor)))

(println (map preco-dos-produtos pedido))
(println (reduce + (map preco-dos-produtos pedido)))

(defn total-do-pedido
  [pedido]
  (reduce + (map preco-dos-produtos pedido)))

(println (total-do-pedido pedido))

;THREAD LAST
(defn total-do-pedido
  [pedido]
  (->> pedido
      (map preco-dos-produtos ,,,)
      (reduce + ,,,)))


(println (total-do-pedido pedido))


;desctruct no vetor parece estranho, melhor em map hash map etc


(defn preco-total-do-produto [produto]
  (* (:quantidade produto) (:preco produto)))

;THREAD LAST
(defn total-do-pedido
  [pedido]
  (->> pedido
       vals                                                 ;pego as {} do pedido
       (map preco-total-do-produto ,,,)
       (reduce + ,,,)))

(println (total-do-pedido pedido))


(def pedido {:mochila  {:quantidade 2, :preco 80}
             :camiseta {:quantidade 3, :preco 40}
             :chaveiro {:quantidade 1}})

(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))

(println "Gratuito")
(println (filter gratuito? (vals pedido)))

(defn gratuito?
  [[_ item]]
  (<= (get item :preco 0) 0))

(println "Gratuito")
(println (filter gratuito? pedido))

(defn gratuito?
  [item]
  (<= (get item :preco 0) 0))

(println "Gratuito")
(println (filter (fn [[chave item]] (gratuito? item)) pedido))
(println (filter #(gratuito? (second %)) pedido))

(defn pago?
  [item]
  (not (gratuito? item)))

(println (pago? {:preco 20}))
(println (pago? {:preco 0}))

(println ((comp not gratuito?) {:preco 50}))
;compoe funções
;o que passar para o pago sera passado para a comp do not gratuito
(def pago? (comp not gratuito?))
(println (pago? {:preco 50}))
(println (pago? {:preco 0}))

;exercicio

(def clientes [
               { :nome "Guilherme"
                :certificados ["Clojure" "Java" "Machine Learning"] }
               { :nome "Paulo"
                :certificados ["Java" "Ciência da Computação"] }
               { :nome "Daniela"
                :certificados ["Arquitetura" "Gastronomia"] }])

(println (->> clientes (map :certificados) (map count) (reduce +)))