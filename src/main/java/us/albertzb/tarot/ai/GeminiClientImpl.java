/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.ai;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;
import us.albertzb.tarot.utils.Env;

public class GeminiClientImpl implements GenerativeAiClient {

    private static final Logger LOG = LoggerFactory.getLogger(GeminiClientImpl.class);
    private static final String ENV_KEY = "GEMINI_API_KEY";
    private static final String ENV_MODEL = "GEMINI_MODEL";

    private final Client genAiClient;
    private final String modelName;

    public GeminiClientImpl(String apiKey, String modelName) {
        this.genAiClient = Client.builder().apiKey(apiKey).build();
        this.modelName = modelName;
    }
    
    /**
     * Creates the client with defaul values.
     * Throws an IllegalStateException if the api key is not found in the environment variables.
     * @return 
     */
    public static GeminiClientImpl create() {
        String apiKey = Env.getRequiredVar(ENV_KEY);
        String model = Env.getVar(ENV_MODEL, "gemini-2.5-flash");
        return new GeminiClientImpl(apiKey, model);
    }

    @Override
    public Optional<String> generateText(String prompt, Map<String, Object> options) {
        try {
            GenerateContentConfig.Builder builder = GenerateContentConfig
                    .builder();
            for(Entry<String,Object> entry : options.entrySet()) {
                switch(entry.getKey().toLowerCase()) {
                    case "temperature":
                        builder.temperature((Float)entry.getValue());
                        break;
                    case "systeminstruction":
                        List<String> texts = castToStringList(entry.getValue());
                        Part[] parts = new Part[texts.size()];
                        for(int i = 0; i < texts.size(); ++i) {
                            parts[i] = Part.fromText(texts.get(i));
                        }
                        Content systemInstruction = Content.fromParts(parts);
                        builder.systemInstruction(systemInstruction);
                        break;
                    default:
                        break;
                }
            }
            // Use the Google Gen AI SDK to call the generateContent API
            GenerateContentResponse response = genAiClient.models.generateContent(modelName, prompt, builder.build());

            // Check if the response contains text and return it
            if (response != null && response.text() != null) {
                return Optional.of(response.text());
            }
        } catch (Exception e) {
            // Log the exception and return an empty Optional
            LOG.error("Error generating text with Gemini: " + e.getMessage());
        }
        return Optional.empty();
    }

    private List<String> castToStringList(Object obj) {
        
        if(obj instanceof List<?> rawList) {
            try {
                for(Object element : rawList) {
                    if(!(element instanceof String)) {
                        return Collections.emptyList();
                    }
                }
                
                @SuppressWarnings("unchecked")
                List<String> stringList = (List<String>) rawList;
                return stringList;
                
            } catch (ClassCastException e) {
                return Collections.emptyList();
            }
        }
        
        return Collections.emptyList();
    }

    @Override
    public Flux<String> streamText(String prompt, Map<String, Object> options) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Optional<GenAiResponse> generateMultimodal(List<GenAiContent> inputs, Map<String, Object> options) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
