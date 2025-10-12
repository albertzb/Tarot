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
public class Balance implements Spreadable {
    private static final int CARD_COUNT = 2;
    private static final List<String> POSITIONS = List.of("The left path","The right path");
    private static final String TITLE = "Balance";
    private static final String QUESTION = "Which two paths lay before you?";
    private static final String SUB_TITLE = "This may help to find direction.";
    private static final String CONVERSION_PROMPT = """
                                                    Convert the below text to third person singular
                                                    to complete the sentence 'a person who must choose between ... or ...'.
                                                    If the text does not present a choice, create a choice out of it as best as
                                                    you can.
                                                    Reply with 'a person who must decide to act or not.' if the text does not make
                                                    any sense.
                                                    Give only the conversion result.
                                                    \"""
                                                    """;
    private static final String PROMPT = """
                                         Using the outline below, write a balanced consideration of both paths for a person. The
                                         'left path' is the option left of the 'or' in the text, the 'right path' is the
                                         option right of the 'or' in the first sentence. Use second person singular.
                                         Briefly state what you think is the choice to be made. Your goal is not
                                         to find the good or right path, but to compare the paths the words describe.
                                         Paraphrase the option you discuss in the consideration of each path.
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
