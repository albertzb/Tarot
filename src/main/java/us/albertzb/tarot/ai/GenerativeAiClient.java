/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package us.albertzb.tarot.ai;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import reactor.core.publisher.Flux;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public interface GenerativeAiClient {

    /**
     * Generates a text response based on a given prompt.
     *
     * @param prompt The user's input text.
     * @param options A map of key-value pairs for model-specific parameters
     * (e.g., temperature, max_tokens).
     * @return An Optional containing the generated text response, or empty if
     * generation fails.
     */
    Optional<String> generateText(String prompt, Map<String, Object> options);

    // For streaming text responses
    Flux<String> streamText(String prompt, Map<String, Object> options);

    /**
     * Generates a multimodal response based on a prompt and various inputs.
     * This method supports text, images, and other content types.
     *
     * @param inputs A list of different content inputs (text, image data,
     * etc.).
     * @param options A map of key-value pairs for model-specific parameters.
     * @return A GenAiResponse object containing the structured response, or
     * empty if generation fails.
     */
    Optional<GenAiResponse> generateMultimodal(List<GenAiContent> inputs, Map<String, Object> options);

    /**
     * Initiates a multi-turn conversation.
     *
     * @param prompt The initial user prompt for the conversation.
     * @param options A map of key-value pairs for model-specific parameters.
     * @return A ChatSession object for ongoing conversation management.
     */
    //ChatSession startChat(String prompt, Map<String, Object> options);

    // Nested classes or records for type safety
    record GenAiResponse(String text, List<String> additionalOutputs) {

    }

    record GenAiContent(GenAiContentType type, Object data) {

    }

    enum GenAiContentType {
        TEXT, IMAGE, AUDIO
    }
}
