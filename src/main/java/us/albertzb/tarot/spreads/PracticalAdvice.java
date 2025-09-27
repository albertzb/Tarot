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
    private static final String QUESTION = "What can I help you with?";
    private static final String SUB_TITLE = "This may help";
    private static final String CONVERSION_PROMPT = """
                                                    Convert the below text to third person singular,
                                                    completing the sentence 'A person who wants practical advice about ...'.
                                                    Reply with 'a person who want some general, practical advice.' if
                                                    the text does not make sense.
                                                    Give only the conversion result.
                                                    \"""
                                                    """;
    private static final String PROMPT = """
                                         Using the outline below, write a practical advice
                                         for a person in 2nd person singular. Do not refer
                                         to the outline. Do not add an introduction.
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
