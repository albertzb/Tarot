/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package us.albertzb.tarot.ai;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class GeminiClientImplTest {

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
    }

}
