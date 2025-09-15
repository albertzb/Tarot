/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.spreads;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class PracticalAdvice {
    public static final int cardCount = 5;
    
    public enum Position {
        PAST(0, "Past influence"),
        PRESENT(1, "Present influence"),
        MAJOR(2, "Major influence"),
        ADVICE(3, "Advice"),
        OUTCOME(4, "Outcome likley if advice is followed");
        
        private final String title;
        private final int index;
        
        Position(int index, String title) {
            this.index = index;
            this.title = title;
        }
        
        public int getIndex() {
            return index;
        }
        
        public String getTitle() {
            return title;
        }
    }
}
