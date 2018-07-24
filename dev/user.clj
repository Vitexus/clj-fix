(ns user
  (:refer-clojure :exclude [test])
  (:require [clojure.tools.nrepl.server :refer [start-server stop-server]]))

(defn- available-port []
  (with-open [s (java.net.ServerSocket. 0)]
    (.getLocalPort s)))

(defn- nrepl-handler []
  ;; https://github.com/clojure-emacs/cider-nrepl/issues/447
  (require 'cider.nrepl)
  (ns-resolve 'cider.nrepl 'cider-nrepl-handler))

(defn dev
  "Load and switch to the 'dev' namespace."
  []
  (require 'dev)
  (in-ns 'dev)
  :loaded)

(let [port (available-port)]
  (start-server :port port :handler (nrepl-handler))
  (spit ".nrepl-port" port))
