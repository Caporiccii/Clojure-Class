(ns hospital.core
  (:use clojure.pprint)
  (:require
    [clojure.test.check.generators :as gen]
    [schema-generators.generators :as g]
    [hospital.model :as h.model]
    ))

(println (gen/sample gen/boolean 3))
(println (gen/sample gen/int 100))
(println (gen/sample gen/string))
(println (gen/sample gen/string-alphanumeric 511))

(println (gen/sample (gen/vector gen/int 15), 5))
(println (gen/sample (gen/vector gen/int 1 5), 5))
(println (gen/sample (gen/vector gen/int), 5))

; o generators do schema deduz generators a partir do schema
(pprint (g/sample 10 h.model/PacienteID))
(pprint (g/sample 10 h.model/Departamento))
(pprint (g/sample 10 h.model/Hospital))
(println "Gerando com generate")
(pprint (g/generate h.model/Hospital))