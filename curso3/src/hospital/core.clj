(ns hospital.core
  (:require [hospital.model :as h.model])
  (:use [clojure.pprint]))                                  ;use para uso direto

(let [hospital-do-marco (h.model/novo-hospital)]
  (pprint hospital-do-marco))

(pprint h.model/empty-queue)
