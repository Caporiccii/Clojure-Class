(ns hospital.aula2
  (:use clojure.pprint))

(defrecord PacienteParticular [id, nome, nascimento])
(defrecord PacienteComPlano [id, nome, nascimento, plano])


;vantagem esta tudo em um bloco só
;Desvantagem concentração de tipos
;(defn deve-assinar-pre-autorizacao? [paciente procedimento valor]
;  (if (= PacienteParticular (type paciente))
;    (>= valor 50)
;    (if (= PacientePlanoDeSaude (type paciente))
;      (let [plano (get paciente :plano)]
;        (not (some #(= % procedimento) plano)))
;      true)))




(defprotocol Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor])) ;como se fosse uma interface

(extend-type PacienteParticular
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (>= valor 50)))

(extend-type PacienteComPlano
  Cobravel
  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
    (let [plano (:plano paciente)]
      (not (some #(= % procedimento) plano)))))

(let [particular (->PacienteParticular 15, "Marco", "18052001")
      plano (->PacienteComPlano 15, "Marco", "18052001", [:raixo-x, :ultrasom])]
  (pprint (deve-assinar-pre-autorizacao? particular, :raixo-x, 500))
  (pprint (deve-assinar-pre-autorizacao? particular, :raixo-x, 40))
  (pprint (deve-assinar-pre-autorizacao? plano, :raixo-x, 5000))
  (pprint (deve-assinar-pre-autorizacao? plano, :coleta-de-sangue, 500))
  )

;uma alternativa seria implementar diretamente

;(defrecord PacienteComPlano
;  [id, nome, nascimento, plano]
;  Cobravel
;  (deve-assinar-pre-autorizacao? [paciente procedimento valor]
;    (let [plano (:plano paciente)]
;      (not (some #(= % procedimento) plano)))))

(defprotocol Dateable
  (to-ms [this]))

(extend-type java.lang.Number
  Dateable
  (to-ms [this] this))

(pprint (to-ms 56))

(extend-type java.util.Date
  Dateable
  (to-ms [this] (.getTime this)))

(pprint (to-ms (java.util.Date.)))

(extend-type java.util.Calendar
  Dateable
  (to-ms [this] (to-ms (.getTime this))))

(pprint (to-ms (java.util.GregorianCalendar.)))

;Usamos Record e Protocols principalmente na integração com código Java
;Exato. Se já possuimos código Java que vamos utilizar de base para uma funcionalidade
;é comum ter que implementar interfaces através de Protocols e Records














