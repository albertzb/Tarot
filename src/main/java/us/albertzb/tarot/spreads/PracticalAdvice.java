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
public class PracticalAdvice implements Spreadable {
    private static final int CARD_COUNT = 5;
    private static final List<String> POSITIONS = List.of(
            "Past influence",
            "Present influence",
            "Major influence",
            "Advice",
            "Outcome likely if advice is followed");
    private static final String TITLE="Practical Advice";
    private static final String SUB_TITLE = "This may help";
        

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
