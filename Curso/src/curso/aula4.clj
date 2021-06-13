(ns curso.aula4)

(def precos [30 700 1000])

(println (precos 0))
(println (get precos 0))
(println (get precos 2))
(println (get precos 3))
(println "valor padrão nil" (get precos 17))
;0 passa a ser valor padrão de retorno ao invés de null
(println "valor padrão 0" (get precos 17 0))
; usando get evita estouro de exception
(println (conj precos 15))

(println precos)
;increase
(println (inc 5))

;cria um novo vetor precos pegando o indice 0 e inc nele
;nada muda o primeiro vetor pq são imutaveis
(println (update precos 0 inc))
(println (update precos 1 inc))
(println precos)

(defn soma-1
  [valor]
  (println "soma " valor)
  (+ valor 1))

(println (update precos 1 soma-1))

(defn soma-3
  [valor]
  (println "soma 3 " valor)
  (+ valor 3))

(println (update precos 2 soma-3))

;Código da aula anterior
(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(defn valor-descontado
  ; "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println (map valor-descontado precos))

(println (range 10))
;(println (filter (range 10)))
(println (filter even? (range 10)))

;implementação abaixo é bem comum
(println precos)
(println "Map após o filter" (map valor-descontado (filter aplica-desconto? precos)))

;Reduce: reduz uma sequencia de valores ou vetor por exemplo pra Um grade valor
;aplica a função mais a todos os elementos dos vetores
(println(reduce + precos))

(defn minha-soma [valor1 valor2]
  (println "Soma" valor1 valor2)
  (+ valor1 valor2))

(println(reduce minha-soma precos))
(println(reduce minha-soma (range 20)))
(println(reduce minha-soma [12]))

;0 como primeiro valor
(println(reduce minha-soma 0 precos))
(println(reduce minha-soma 0 [12]))
; isso da erro (println(reduce minha-soma [])) pois não dá pra reduzir vazio



