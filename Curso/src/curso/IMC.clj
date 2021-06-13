(ns curso.IMC)

(defn calcula-imc
  [peso, altura]
  (/ peso (exp altura 2)))

(println "Resultado IMC" (format "%2.2f" (calcula-imc 60.0 1.65)))
(println "Resultado IMC" (calcula-imc 60 1.65))

;operador logico em clojure tbm é uma função
;exemplo de implementação no metodo define-obesidade
;operadores são and,or,not
;TODO passar para o papel
(defn define-obesidade
  [peso altura]
  (let [valor (calcula-imc peso altura)]
    (when (< valor 18.5)
      (println "Abaixo do peso:" valor))
    ;if usando operador logico
    (if (and (> valor 18.5) (< valor 24.9))
      (println "peso normal:" valor))
    ;(if (or (< valor 18.5) (> valor 24.9))
    ; (println "bsjdbhv do peso:" valor))
    ) "Fora")

;(if (< a  1 && > 1 2 ))


(println (define-obesidade 49.0 1.65))

(defn exp [x n]
  (reduce * (repeat n x)))

;(println (exp 1.65 2))