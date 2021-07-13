(ns hospital.aula1
  (:use clojure.pprint))

(defn adiciona-paciente
  "os pacientes são um mapa"
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))
;if-let já executa caso o let seja verdadeiro, caso não ele retorna a
; exception ou outro tratamento

(defn testa-uso-pacientes []
  (let [pacientes {}
        guilherme {:id 15, :nome "Guilherme" :nascimento "18/9/1981"}
        daniela {:id 20, :nome "Daniela" :nascimento "18/9/1984"}
        paulo {:nome "Paulo" :nascimento "18/10/1983"}
        ]
    (pprint (adiciona-paciente pacientes guilherme))
    (pprint (adiciona-paciente pacientes daniela))
    (pprint (adiciona-paciente pacientes paulo))
    ))

;(testa-uso-pacientes)

(defrecord Paciente [id nome nascimento]) "interopabilidade, poder trampar com oo quando preciso e agregar simbolos"
; ^String posso forçar para o cluje que seja uma string apenas
;defrecord é ideal para interopilidade
(println (->Paciente 15 "Guilherme" "18/9/1981"))
(pprint (->Paciente 15 "Guilherme" "18/9/1981"))
(pprint (Paciente. 15 "Guilherme" "18/9/1981")) ". instancia a classe java, esperando um construtor"
(pprint (Paciente. 15 "18/9/1981" "Guilherme"))
(pprint (Paciente. "Guilherme" 15 "18/9/1981"))
(pprint (Paciente. 15 "18/9/1981" "Guilherme"))
(pprint (map->Paciente {:id 15, :nome "Guilherme" :nascimento "18/9/1981"}))

(let [guilherme (->Paciente 15 "Guilherme" "18/9/1981")]
  (println (:id guilherme))
  (println (vals guilherme))
  (println (record? guilherme))
  (println (.nome guilherme))                               ; chama o campo nome do java, teoricamente mais rapido. bind em tempo de compilação
  )

(pprint (map->Paciente {:id 15, :nome "Guilherme" :nascimento "18/9/1981" :rg "2222"}))
(pprint (Paciente. nil "Guilherme" "18/9/1981"))            ;não permite vazio pois já reclama da aridade, só nil ou o id de fato
(pprint (map->Paciente {:nome "Guilherme" :nascimento "18/9/1981" :rg "2222"}))

(pprint (assoc (Paciente. nil "Guilherme" "18/9/1981") :id 38))
(pprint (class (assoc (Paciente. nil "Guilherme" "18/9/1981") :id 38)))


(pprint (= (->Paciente 15 "Guilherme" "18/9/1981") (->Paciente 15 "Guilherme" "18/9/1981")))
(pprint (= (->Paciente 153 "Guilherme" "18/9/1981") (->Paciente 15 "Guilherme" "18/9/1981")))












