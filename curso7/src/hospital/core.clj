(ns hospital.core
  (:require [clojure.test.check.generators :as gen]))

(println (gen/sample gen/boolean 3))
(println (gen/sample gen/int 100))
(println (gen/sample gen/string))
(println (gen/sample gen/string-alphanumeric 511))

(println (gen/sample (gen/vector gen/int 15), 5))
(println (gen/sample (gen/vector gen/int 1 5), 5))
(println (gen/sample (gen/vector gen/int), 5))