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
public class Affirmation implements Spreadable {
    private static final int CARD_COUNT = 1;
    private static final List<String> POSITIONS = List.of("This day");
    private static final String TITLE = "Affirmation";
    private static final String QUESTION = "";
    private static final String SUB_TITLE = "Carry these words with you today.";
    private static final String CONVERSION_PROMPT = "";
    private static final String PROMPT = """
                                         Using the outline below, write an affirmation. The affirmation should
                                         give emotional support or encouragement to a person. If the below text has negative
                                         words, then write an affirmation that heeds caution. Do not use the text
                                         from the outline directly.
                                         Do not refer to the outline. Stay professional, do not become intimate. 
                                         Do not add an introduction.
                                         Do not create a document.
                                         \"""
                                         """;

    @Override
    public int getCardCount() {
        return CARD_COUNT;
    }

    @Override
    public String getConversionPrompt() {
        return CONVERSION_PROMPT;
    }
    
    @Override
    public List<String> getPositions() {
        return POSITIONS;
    }

    @Override
    public String getPrompt() {
        return PROMPT;
    }

    @Override
    public String getQuestion() {
        return QUESTION;
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
