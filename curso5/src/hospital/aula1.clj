(ns hospital.aula1
  (:use clojure.pprint)
  (:require [schema.core :as s]))

(defn adiciona-paciente
  [pacientes paciente]
  (if-let [id (:id paciente)]
    (assoc pacientes id paciente)
    (throw (ex-info "Paciente não possui id" {:paciente paciente}))))

(defn adiciona-visita
  [visitas paciente novas-visitas]
  (if (contains? visitas paciente)
    (update visitas paciente concat novas-visitas)
    (assoc  visitas paciente novas-visitas)))

(defn imprime-relatorio-do-paciente [visitas paciente]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))


(defn testa-uso-de-paciente []
  (let [guilherme {:id 15, :nome "Guilherme"}
        daniela {:id 20, :nome "Daniela"}
        paulo {:id 25, :nome "Paulo"}
        pacientes (reduce adiciona-paciente {} [guilherme daniela paulo])
        visitas {}                                          ;shadowing
        visitas (adiciona-visita visitas 15 ["01/01/2020"])
        visitas (adiciona-visita visitas 20 ["01/02/2019" , "01/01/2020"])
        visitas (adiciona-visita visitas 15 ["01/03/2019"])]
  (pprint pacientes)
  (pprint visitas)
  ;ta osso pq o simbolo paciente ta sendo usado em varios lugares
  ; e significados diferentes
  (imprime-relatorio-do-paciente visitas 15)
  ;(pprint (adiciona-visita visitas 15 ["01/01/2020"]))
  ;(pprint (adiciona-visita visitas 20 ["01/02/2019" , "01/01/2020"]))
  ;(pprint (adiciona-visita visitas 15 ["01/03/2019"]))
  ))

(testa-uso-de-paciente)


(pprint (s/validate Long 15))
;(pprint (s/validate Long "Porra"))
;(pprint (s/validate Long [15]))

(s/set-fn-validation! true)

;x segue o schema Long
(s/defn teste-simples
  [x :- Long]
  (pprint x))
(teste-simples 30)
;(teste-simples "n")
;defn do schema
(s/defn imprime-relatorio-do-paciente [visitas paciente :- Long]
  (println "Visitas do paciente" paciente "são" (get visitas paciente)))

;agora o erro ocorre em tempo de execução
;dizendo que o valor do parametro não condiz com o schema Long
;(testa-uso-de-paciente)

(s/defn novo-paciente
  [id :- Long, nome :- s/Str]
  { :id id   :nome nome })

(pprint (novo-paciente 15 "Marco"))
;(pprint (novo-paciente  "Marco" 15))



