(ns hospital.model
  (:require [schema.core :as s]))

(def empty-queue clojure.lang.PersistentQueue/EMPTY)

(defn novo-hospital []
  {:espera       empty-queue
   :laboratorio1 empty-queue
   :laboratorio2 empty-queue
   :laboratorio3 empty-queue})

(s/def PacienteID s/Str)
(s/def Departamento (s/queue PacienteID))
(s/def Hospital {s/Keyword Departamento})

;apenas para testes
;(s/validate PacienteID "Marco")
;(s/validate PacienteID 15)
(s/validate Departamento (conj empty-queue "Marco", "Luca"))
;(s/validate Hospital {:espera (conj empty-queue "Marco", "Luca")})