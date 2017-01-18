(ns contributions-calenjar.core
  #?(:clj (:import [java.util Date Calendar])))

(def contrib-colors
  {0 "#eeeeee"
   5 "#d6e685"
   10 "#8cc665"
   15 "#44a340"
   20 "#1e6823"})

(defn day-of-week [date]
  #?(:clj (let [cal (doto (Calendar/getInstance)
                      (.setTime ^Date date))]
            (.get cal Calendar/DAY_OF_WEEK))
     :cljs (inc (.getDay date))))

(defn calendar [contribs & {:keys [date]}]
  (let [date (or date #?(:clj (Date.)
                         :cljs (js/Date.)))
        days (+ (* 7 52) (day-of-week date))]
    [:svg {:width 676 :height 104}
     [:g {:transform "translate(16, 20)"}
      (for [[week chunk] (->> contribs
                              (drop (- (count contribs) days))
                              (partition 7 7 (repeat nil))
                              (map-indexed list))]
        [:g {:transform (str "translate(" (* 13 week) ", 0)")}
         (for [[day n] (map list (range 7) chunk)
               :while n
               :let [rank (if (= n 0) 0 (* 5 (inc (quot n 5))))]]
           [:rect.day
            {:width 10 :height 10
             :x (- 13 week) :y (* 12 day)
             :fill (get contrib-colors rank)}])])
      (for [[wday dy] (map list ["Mon" "Wed" "Fri"] [20 44 69])]
        [:text.wday
         {:text-anchor :start :dx -14 :dy dy
          :style "font-size: 9px; fill: #767676"}
         wday])]]))
