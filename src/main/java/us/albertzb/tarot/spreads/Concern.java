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
    private static final String QUESTION = "What is your concern?";
    private static final String SUB_TITLE = "Maybe you can cope with your concern this way";
    private static final String CONVERSION_PROMPT = """
                                                    Convert the below text to third person singular
                                                    to complete the sentence 'a person who is concerned about ...'.
                                                    Reply with 'a person who is concerned about general, everyday problems.' if
                                                    the text does not make any sense.
                                                    Give only the conversion result.
                                                    \"""
                                                    """;
    private static final String PROMPT = """
                                         Using the outline below, write a supportive explanation
                                         how a person can deal with the current practical,
                                         everyday problem. Do not refer to the outline. Stay
                                         professional, do not become intimate. Do not add an introduction.
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
