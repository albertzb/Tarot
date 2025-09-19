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
public class PastPresentFuture implements Spreadable {
    private static final int CARD_COUNT = 3;
    private static final List<String> POSITIONS = List.of("What lays in the past", "Your present situation", "What the future may hold");
    private static final String TITLE = "Past-Present-Future";
    private static final String  SUB_TITLE = "This is how the cards see your situation";

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
