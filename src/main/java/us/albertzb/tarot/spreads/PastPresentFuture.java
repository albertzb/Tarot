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
    private static final String QUESTION = "What's on your mind? I'm here to listen.";
    private static final String  SUB_TITLE = "This may help you find a new perspective.";
    private static final String CONVERSION_PROMPT = """
                                                    Convert the below text to third person singular,
                                                    completing the sentence 'a person who wants to learn about the past present and future of ...'.
                                                    Reply with 'a person who want to learn about the past present and future of themself.' if
                                                    the text does not make sense.
                                                    Give only the conversion result.
                                                    \"""
                                                    """;
    private static final String PROMPT = """
                                         Using the outline below, write a discussion of the situation
                                         a person is in. Write in second person singular. Do not 
                                         refer to the outline. Do not add an introduction.
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
