(ns curso.Aula5)

(def estoque {"Mochila" 10 "Camiseta" 5})

(println estoque)

(def estoque {"Mochila" 10, "Camiseta" 5})

(def estoque {"Mochila"  10
              "Camiseta" 5})

(println estoque)

(println "Temos" (count estoque) "elementos ")

(println "Chaves são:" (keys estoque))
(println "Valores são:" (vals estoque))
; não há garantia da ondem desses valores

;keyword
;:mochila
(def estoque {:mochila  10
              :camiseta 5})

(println (assoc estoque :cadeira 3))
(println estoque)
(println (assoc estoque :mochila 1))

(println estoque)
(println (update estoque :mochila inc))

(defn tira-um
  [valor]
  (println "Tira um de" valor)
  (- valor 1))

(println (update estoque :mochila tira-um))
(println (update estoque :mochila #(- % 3)))

(println (dissoc estoque :mochila))

;simbolo estoque sempre imutavel

(def pedido {:mochila {:quantidade 2, :preço 80}
             :camiseta {:quantidade 3, :preço 40}})

(println pedido)

(def pedido (assoc pedido :chaveiro { :quantidade 1, :preço 10}))

(println pedido)

(println (pedido :mochila))                                 ;mapa como função porem mais raro
(println (get pedido :mochila))
(println (get pedido :cadeira))                             ;n existe
(println (get pedido :cadeira {}))                          ;n existe
(println (:mochila pedido))                                 ; keymap como função
(println (:cadeira pedido))
(println (:cadeira pedido {}))

(println (:quantidade (:mochila pedido)))

(println (update-in pedido [:mochila :quantidade] inc))

; THREADING FIRST
(println (-> pedido
             :mochila
             :quantidade))
; assim tbm dá
(-> pedido
    :mochila
    :quantidade
    println)

;exercicio:
(def
  clientes {
            :15 {
                 :nome "Guilherme"
                 :certificados ["Clojure" "Java" "Machine Learning"] } })

(println (-> clientes
             :15
             :certificados count))
