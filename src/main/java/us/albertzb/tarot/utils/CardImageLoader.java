/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.utils;

import java.io.IOException;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.albertzb.tarot.model.TarotCard;
import us.albertzb.tarot.model.TarotCardVisitor;
import us.albertzb.tarot.model.TarotDeck;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class CardImageLoader implements TarotCardVisitor {

    private static final Logger LOG = LoggerFactory.getLogger(CardImageLoader.class);

    private final TarotDeck tarotDeck;

    public CardImageLoader(TarotDeck tarotDeck) {
        this.tarotDeck = tarotDeck;
    }

    public void load() {
        for (TarotCard card : tarotDeck.getCards()) {
            card.accept(this);
        }
    }

    @Override
    public void visit(TarotCard card) {
        String resourcePath = card.getResourcePath();

        try (InputStream inputStream = CardImageLoader.class.getClassLoader().getResourceAsStream(resourcePath)) {
            if (inputStream != null) {
                card.setImageBytes(inputStream.readAllBytes());
            }
        } catch (IOException ex) {
            LOG.error("Loading images failed: " + ex.getMessage(), ex);
        }
    }

}
