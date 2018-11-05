(ns scramble.core)


(defn scramble? [chars sample]
  "Returns true if a portion of chars can be rearranged to match sample,
   otherwise returns false"
  (and (>= (count chars) (count sample))
       (let [chars'  (frequencies chars)
             sample' (frequencies sample)]
         (every? #(let [[ch freq] %
                        avail     (get chars' ch 0)]
                    (>= avail freq)) sample'))))