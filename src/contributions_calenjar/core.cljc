(ns contributions-calenjar.core)

(def contrib-colors
  {0 "#eeeeee"
   5 "#d6e685"
   10 "#8cc665"
   15 "#44a340"
   20 "#1e6823"})

(defn calendar [contribs]
  [:svg {:width 676 :height 104}
   [:g {:transform "translate(16, 20)"}
    (for [[week chunk] (map list (range 0 53)
                            (partition 7 7 (repeat nil) contribs))]
      [:g {:transform (str "translate(" (* 13 week) ", 0)")}
       (for [[day {:keys [count date]}] (map list (range 7) chunk)
             :while count]
         [:rect.day
          {:width 10 :height 10
           :x (- 13 week) :y (* 12 day)
           :fill (get contrib-colors (* 5 (inc (quot count 5))))}])])
    (for [[wday dy] (map list ["Mon" "Wed" "Fri"] [20 44 69])]
      [:text.wday
       {:text-anchor :start :dx -14 :dy dy
        :style "font-size: 9px; fill: #767676"}
       wday])]])
