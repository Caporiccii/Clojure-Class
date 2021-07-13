(ns hospital.logic)

(defn cabe-na-fila? [hospital departamento]
  (-> hospital
      (get,,, departamento)
      count,,,
      (<,,, 5)))

(defn chega-em
  [hospital departamento pessoa]
  (if (cabe-na-fila? hospital departamento)
    (update hospital departamento conj pessoa)
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

;parece pura mas usa o random
(defn chega-em-pausado
  [hospital departamento pessoa]
  (Thread/sleep (* (rand) 200))
  (if (cabe-na-fila? hospital departamento)
    (do
      ;(Thread/sleep 1000)                                 ; do pq tem mais de uma coisa caso o if seja verdadeiro
      (update hospital departamento conj pessoa))
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

;parece pura mas usa o random, e altera o estado do random e loga
(defn chega-em-pausado-logando
  [hospital departamento pessoa]
  (println "tenta adicionar pessoa" pessoa)
  (Thread/sleep (* (rand) 2000))
  (if (cabe-na-fila? hospital departamento)
    (do
      ;(Thread/sleep 1000)
      (println "Dando update na pessoa" pessoa)
      (update hospital departamento conj pessoa)
      )
    (throw (ex-info "Fila já está cheia" {:tentando-adicionar pessoa}))))

(defn atende
  [hospital departamento]
  (update hospital departamento pop))

(defn proxima
  "Retorna o proximo paciente da fila"
  [hospital departamento]
  (-> hospital
      departamento
      peek))

(defn transfere
  "Transfere o proximo paciente da fila de para a fila para"
  [hospital de para]
  (let [pessoa (proxima hospital de)]
    (-> hospital
        (atende de)
        (chega-em para pessoa))))

(defn atende-v2
  "possivel retornar os 2 quem e onde"
  [hospital departamento]
  {:paciente (update hospital departamento peek)
   :fila     (update hospital departamento pop)})

(defn atende-chama-ambos
  "possivel retornar os 2 quem e onde"
  [hospital departamento]
  (let [fila (get hospital departamento)
        peek-pop (juxt peek pop)
        [pessoa fila-atualizada] (peek-pop fila)
        hospital-atualizado (update hospital assoc departamento fila-atualizada)]
    {:paciente pessoa
     :hospital hospital-atualizado}))

;comp recebe uma sequência de funções e retorna uma função
;capaz de invocar elas em sequência, da última para a primeira.
;juxt aplica as funções isoladamente ao mesmo valor.
;Exatamente. comp e juxt são sempre criadoras de funções.