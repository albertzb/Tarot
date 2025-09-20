/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package us.albertzb.tarot.ai;

import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class GeminiClientImplTest {
    private static final Logger LOG = LoggerFactory.getLogger(GeminiClientImplTest.class);

    public GeminiClientImplTest() {
    }

    /**
     * Test of create method, of class GeminiClientImpl.
     */
    @Test
    public void testCreate() {
        assertThatNoException()
                .isThrownBy(() -> {
                    GeminiClientImpl client = GeminiClientImpl.create();
                });
    }

    /**
     * Test of generateText method, of class GeminiClientImpl.
     */
    @Test
    public void testGenerateText() {
        GeminiClientImpl client = GeminiClientImpl.create();
        Optional<String> response = client
                .generateText("Tell me a Gemini AI joke.", null);
        assertThat(response)
                .hasValueSatisfying(s -> assertThat(s).isNotNull());
    }

}
