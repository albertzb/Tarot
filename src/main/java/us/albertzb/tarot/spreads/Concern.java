/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.spreads;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class Concern {
    public static final int cardCount = 4;
    
    public enum Position {
        SITUATION(0, "Situation"),
        OBSTACLE(1, "Obstacle"),
        ACTION(2, "Action recommended"),
        OUTCOME(3, "Outcome");
        
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
