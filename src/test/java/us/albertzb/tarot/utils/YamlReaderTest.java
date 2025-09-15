/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package us.albertzb.tarot.utils;

import java.net.URL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import org.junit.jupiter.api.Test;
import us.albertzb.tarot.model.TarotDeck;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
class YamlReaderTest {

    private static final String CARDS_FILE = "cards/tarot/cards.yml";
    public YamlReaderTest() {
    }

    /**
     * Test of read method, of class YamlReader.
     */
    @Test
    void testRead() {
        assertThatCode(() -> {
            YamlReader.read(CARDS_FILE);
        }).doesNotThrowAnyException();
    }
    
    @Test
    void testResourcePath() {
        URL resource = YamlReaderTest.class.getClassLoader().getResource("cards/tarot/cards.yml");
        assertThat(resource).isNotNull();
    }

    @Test
    void whenCardsRead_then80Cards() {
        TarotDeck tarotDeck = YamlReader.read(CARDS_FILE);
        assertThat(tarotDeck.getCards()).hasSize(78);
    }
}
