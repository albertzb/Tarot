/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class TarotDeck {

    private List<TarotCard> cards;

    public TarotDeck() {
        cards = new ArrayList<>(80);
    }

    public TarotDeck(List<TarotCard> cards) {
        this.cards = cards;
    }

    public List<TarotCard> getCards() {
        return cards;
    }

    public void setCards(List<TarotCard> cards) {
        this.cards = cards;
    }

    @Override
    public String toString() {
        return "TarotDeck{" + "cards=" + cards + '}';
    }
}
