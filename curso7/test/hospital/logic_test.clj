(ns hospital.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [hospital.core :refer :all]
            [hospital.logic :refer :all]
            [hospital.model :as h.model]
            [clojure.test.check.clojure-test :refer (defspec)]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [schema.core :as s]))


(s/set-fn-validation! true)
;Alt + T roda os tests
(deftest cabe-na-fila?-test

  (testing "Que cabe na fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))


  (testing "Cabe pessoas na fila de tamanho até 4 inclusive"
    (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4))]
      (is (cabe-na-fila? {:espera fila} :espera))))


  (testing "Que não cabe na fila quando ta cheio"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "Não cabe na fila quando ta mais que cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "Cabe tem gente mas n ta chea"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera))
    (is (cabe-na-fila? {:espera [1 2]} :espera)))

  (testing "Que    quando não existe departamento")
  (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raixo-x)))
  )

;teste generativo e funciona, mas o resultado dele
;parece muito uma copia do nosso código implementado
;{:espera (conj fila pessoa)} quase == o codigo orignal
;se coloquei o bug lá, provavel esta aqui ai dara true.

(defspec coloca-uma-pessoa-em-filas-menores-que-5 10
         (prop/for-all
           [fila (gen/vector gen/string-alphanumeric 0 4)
            pessoa gen/string-alphanumeric]
           (is (= {:espera (conj fila pessoa)}
                  (chega-em {:espera fila} :espera pessoa)))))

(def nome-aleatorio
  (gen/fmap clojure.string/join
            (gen/vector gen/char-alphanumeric 5 10)))

(defn transforma-vetor-em-fila [vetor]
  (reduce conj [h.model/empty-queue vetor]))

(def fila-nao-cheia-gen
  (gen/fmap
    transforma-vetor-em-fila
    (gen/vector nome-aleatorio 2 4)))

(defn transfere-ignorando-erro [hospital para]
  (try
     (transfere hospital :espera para)
     (catch clojure.lang.ExceptionInfo e
       hospital)))


(defspec transfere-deve-manter-a-quantidade-de-pessoas 5
         (prop/for-all
           [espera (gen/fmap transforma-vetor-em-fila (gen/vector nome-aleatorio 0 50))
            raio-x fila-nao-cheia-gen
            ultrasom fila-nao-cheia-gen
            vai-para (gen/vector (gen/elements [:raixo-x :ultrasom]) 0 50)]
           (let [hospital-inicial {:espera espera, :raixo-x raio-x}
                 hospital-final (reduce transfere-ignorando-erro hospital-inicial  vai-para)]
             (= (total-de-pacientes hospital-inicial)
                (total-de-pacientes hospital-final))
             )))
;teste sempre a geração para garantir que ela não seja o problema
;problema: doseq na unha gera uma multiplicação de casos, incluindo repetidos
;que n ta ligado com o que queremos
;só para mostrart que gera 50 asserts
;(deftest chega-em-test
;  (testing "Que é colocada uma pessoa em fila menor que 5"
;    (doseq [fila (gen/sample (gen/vector gen/string-alphanumeric 0 4) 10)
;            pessoa (gen/sample gen/string-alphanumeric) 5]
;      (is (cabe-na-fila? {:espera fila} :espera)))))

