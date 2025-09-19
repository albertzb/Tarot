/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.spreads;

import java.util.List;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class Concern implements Spreadable {
    private static final int CARD_COUNT = 4;
    private static final List<String> POSITIONS = List.of("Situation","Obstacle","Action recommended","Outcome");
    private static final String TITLE = "Concern";
    private static final String SUB_TITLE = "Maybe you can cope with your concern this way";

    @Override
    public int getCardCount() {
        return CARD_COUNT;
    }
    
    @Override
    public List<String> getPositions() {
        return POSITIONS;
    }
    
    @Override
    public String getSubTitle() {
        return SUB_TITLE;
    }
    
    @Override
    public String getTitle() {
        return TITLE;
    }
}
