(ns curso.Aula3)
(defn mais-caro-que-100?
  [valor-bruto]
  (> valor-bruto 100))
;
(defn valor-descontado
  ; "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (> valor-bruto 100)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))




(defn aplica-desconto?
  [valor-bruto]
  (if (> valor-bruto 100)
    true
    false))

(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se o valor bruto for estritamente maior que 100."
  [valor-bruto]
  (if (aplica-desconto? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

;(println (valor-descontado mais-caro-que-100? 1000))

;(println (valor-descontado mais-caro-que-100? 100))


(defn aplica-desconto?
  [valor-bruto]
  (println "chamando funçaõ redefinida")
  (if (> valor-bruto 100)
    true
    ))

(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))
;(println (valor-descontado mais-caro-que-100? 1000))

;(println (valor-descontado mais-caro-que-100? 100))

(defn aplica-desconto?
  [valor-bruto]
  (println "chamando funçaõ when")                          ;when n tem retorno false
  (when (> valor-bruto 100)
    true
    ))

(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))
;(println (valor-descontado mais-caro-que-100? 1000))

;(println (valor-descontado mais-caro-que-100? 100))


(defn aplica-desconto?
  [valor-bruto]
  (println "chamando funçaõ >")
  (> valor-bruto 100)
  true
  )

(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))
;(println (valor-descontado mais-caro-que-100? 1000))

;(println (valor-descontado mais-caro-que-100? 100))


(defn aplica-desconto?
  [valor-bruto]
  (> valor-bruto 100))

(println (aplica-desconto? 1000))
(println (aplica-desconto? 100))


(defn mais-caro-que-100?
  [valor-bruto]
  (println "deixando claro invocação de mais-caro-que-100?")
  (> valor-bruto 100))

(defn valor-descontado
  "Retorna o valor com desconto de 10% se deve aplicar desconto."
  [aplica? valor-bruto]
  (if (aplica? valor-bruto)
    (let [taxa-de-desconto (/ 10 100)
          desconto         (* valor-bruto taxa-de-desconto)]
      (- valor-bruto desconto))
    valor-bruto))

(println "função como parâmetro")
(println (valor-descontado mais-caro-que-100? 1000))
(println (valor-descontado mais-caro-que-100? 100))

(defn mais-caro-que-100? [valor-bruto] (> valor-bruto 100))

(println "função sem nome")
;fn = função anonima
(fn [valor-bruto] (> valor-bruto 100))
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 1000))
(println (valor-descontado (fn [valor-bruto] (> valor-bruto 100)) 100))
; muito abreviado, errado!
(println (valor-descontado (fn [v] (> v 100)) 1000))
(println (valor-descontado (fn [v] (> v 100)) 100))
;um ou mais parametros e
(println (valor-descontado #(> %1 100) 1000))
(println (valor-descontado #(> %1 100) 100))
;um parametro
(println (valor-descontado #(> % 100) 1000))
(println (valor-descontado #(> % 100) 100))
; as funções acima nem sempre são boas praticas, pense sempre em qual melhor código
;-------------------------------------------------------------------------------------;
;definindo o simbolo como essa função
(def mais-caro-que-100? (fn [valor-bruto] (> valor-bruto 100)))
(def mais-caro-que-100? #(> % 100))

(println (valor-descontado mais-caro-que-100? 1000))
(println (valor-descontado mais-caro-que-100? 100))

; Funções que recebem ou retornam funções são chamadas de "higher order functions".

















