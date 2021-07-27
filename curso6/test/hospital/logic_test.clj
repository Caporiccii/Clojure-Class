(ns hospital.logic-test
  (:use clojure.pprint)
  (:require [clojure.test :refer :all]
            [hospital.core :refer :all]
            [hospital.logic :refer :all]
            [hospital.model :as h.model]
            [schema.core :as s]))
(s/set-fn-validation! true)
;Alt + T roda os tests
(deftest cabe-na-fila?-test
  ;Boundary tests
  ;exatamente na borda e one off. -1 +1. <=. >=, =.

  ;checklist de tests
  ;borda do 0
  (testing "Que cabe na fila vazia"
    (is (cabe-na-fila? {:espera []} :espera)))

  ;borda limite
  (testing "Que não cabe na fila quando ta cheio"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))
  ;:one off da borda do limite para cima
  (testing "Não cabe na fila quando ta mais que cheia"
    (is (not (cabe-na-fila? {:espera [1 2 3 4 5]} :espera))))

  (testing "Cabe tem gente mas n ta chea"
    (is (cabe-na-fila? {:espera [1 2 3 4]} :espera))
    (is (cabe-na-fila? {:espera [1 2]} :espera)))

  (testing "Que    quando não existe departamento")
  (is (not (cabe-na-fila? {:espera [1 2 3 4]}, :raixo-x)))
  )

;Teste as bordas do seu codigo
;e tbm o "meio" deles

(deftest chega-em-test

  (let [hospital-cheio
        {:espera [1 32 42 12 41]}]

    (testing "Aceita pessoas em quanto cabe"
      ;Pessima implementação pois testa que escrevemos o que escrevemos
      ;Testa que erramos o que erramos e acertamos o que acertamos
      ;(is (= (update {:espera [1, 2, 3, 4]} :espera conj 5)
      ;       (chega-em {:espera [1, 2, 3, 4, 5]}, :espera, 5)))

      (is = ({:espera [1 52 33 41 5]}
             (chega-em {:espera [1 52 33 41]} :espera 5)))
      (is (= {:espera [1 2 5]}
             (chega-em {:espera [1 2]} :espera 5))))

    ;(is (= {:hospital {:espera [1 52 33 41 5]}, :resultado :sucesso}
    ;       (chega-em {:espera [1 52 33 41]} :espera 5)))
    ;
    ;(is (= {:hospital {:espera [1 52 5]}, :resultado :sucesso}
    ;       (chega-em {:espera [1 52]} :espera 5))))

    (testing "Não aceita quando não cabe na fila"
      ;verifica que a exception foi jogada
      ;codigo classico e horrivel, usado exception generico
      ;qualquer erro vai jogar essa exception, e vamos achar que deu certo
      (is (thrown? clojure.lang.ExceptionInfo
                   (chega-em {:espera [1 32 42 12 41]} :espera 5124)))
      )))

;não use numeros sequencias em seus testes, pois podemos errar nessas sequencias

(deftest transfere-test
  (testing "Aceita pessoas se cabe"
    (let [hospital-original {:espera (conj h.model/empty-queue  "5" ) :raixo-x  h.model/empty-queue }]
      (is (= {:espera  []
              :raixo-x ["5"]}
             (transfere hospital-original :espera :raixo-x)))
      )

    (let [hospital-original {:espera  (conj h.model/empty-queue "51" "5")  :raixo-x (conj h.model/empty-queue "13")}]
      (is (= {:espera  ["5"]
              :raixo-x ["13" "51"]}
             (transfere hospital-original :espera :raixo-x)))))

  (testing "Rejeita pessoas se não cabe"
    (let [hospital-cheio {(conj h.model/empty-queue "5") (conj h.model/empty-queue "1" "2" "53" "42" "13")}]
      (is (thrown? clojure.lang.ExceptionInfo
                   (transfere hospital-cheio :espera :raixo-x)))))

  (testing "Não pode invocar transferência sem hospital"
    (is (thrown? clojure.lang.ExceptionInfo  (transfere nil :espera :raixo-x))))

  (testing "Condições obrigatorias"
    (let [hospital {:espera (conj h.model/empty-queue "5") :raixo-x (conj h.model/empty-queue "1" "2" "53" "42")}]
      (is (thrown? AssertionError (transfere hospital :nao-existe :raixo-x)))
      (is (thrown? AssertionError (transfere hospital :raixo-x :nao-existe)))
      ))

  )




;Comentarios:
;string de texto solto são faceis de quebrar
;(is (thrown? clojure.lang.ExceptionInfo "Não cabe mais ninguem" (chega-em {:espera [1 32 42 12 41]} :espera 5124)))

;mesmo que escolha a exception do genero, perigoso

;(is (thrown? IllegalStateException "Não cabe mais ninguem" (chega-em {:espera [1 32 42 12 41]} :espera 5124)))
;(is (thrown? IllegalStateException  (chega-em {:espera [1 32 42 12 41]} :espera 5124)))
;Abordagem com nulo, mas com swap deveria trabalhar em outro ponto esse cenario
;pois swap precisa devolver algo, nesse caso Hospital
; (is (nil?  (chega-em {:espera [1 32 42 12 41]} :espera 5124)))
;
;Outra maneira de testar,, inves de usar o Tipo da exception para entender o tipo do erro, uso os dados
;para isso, menos sensivel que a msg mesmo se fosse com regex
;mas ainda e trabalhoso
;(is (try
;       (chega-em {:espera [1 52 33 41 23 ]} :espera 5)
;       false
;       (catch clojure.lang.ExceptionInfo e
;         (= :impossivel-colocar-pessoa-na-fila (:tipo (ex-data e)))
;       )))

;(is (= {:hospital {:espera [1 32 42 12 41]}, :resultado :impossivel-colocar-pessoa-na-fila}
;       (chega-em hospital-cheio :espera 5124)))