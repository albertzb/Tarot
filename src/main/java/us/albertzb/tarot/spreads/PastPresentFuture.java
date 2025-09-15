/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.spreads;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class PastPresentFuture {
    public static final int cardCount = 3;
    
    public enum Position {
        PAST(0, "What lays in the past"),
        PRESENT(1, "Your present situation"),
        FUTURE(2, "What the future may hold");
        
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
