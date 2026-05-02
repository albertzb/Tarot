/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.ai;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.googleai.GoogleAiGeminiChatModel;
import dev.langchain4j.model.mistralai.MistralAiChatModel;
import dev.langchain4j.service.AiServices;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.albertzb.tarot.exception.TarotException;
import us.albertzb.util.Config;
import us.albertzb.util.Env;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class AIServiceFactory {

    private static final Logger LOG = LoggerFactory.getLogger(AIServiceFactory.class);
    private static final String ENV_GEMINI = "GEMINI_API_KEY";
    private static final String ENV_GOOGLE = "GOOGLE_API_KEY";
    private static final String ENV_MISTRAL = "MISTRAL_API_KEY";

    private AIServiceFactory() {
    }

    private record AiSetting(String model, double temperature, double frequencyPenalty) {

    }

    public static ChatModel createModel() {
        AiSetting setting = getAiSetting();
        return switch (setting.model()) {
            case "gemini" ->
                createGeminiModel(setting);
            case "mistral" ->
                createMistralModel(setting);
            default ->
                throw new TarotException("Invalid model value in properties: " + setting.model());
        };
    }

    private static ChatModel createMistralModel(AiSetting setting) {
        String apiKey = Env.getRequiredVar(ENV_MISTRAL);

        if (apiKey == null || apiKey.isBlank()) {
            LOG.error("Missing environment variable: {}", ENV_MISTRAL);
            throw new TarotException("Environment variable '" + ENV_MISTRAL + "' is not set.");
        }

        return MistralAiChatModel.builder()
                .apiKey(apiKey)
                .modelName("devstral-latest")
                .temperature(setting.temperature())
                .frequencyPenalty(setting.frequencyPenalty())
                .build();
    }

    private static ChatModel createGeminiModel(AiSetting setting) {
        // Gemini x.x Flash for speed/cost efficiency in an editor
        String apiKey = Env.getRequiredVarTry(ENV_GOOGLE, ENV_GEMINI);

        if (apiKey == null || apiKey.isBlank()) {
            LOG.error("Missing environment variable: {}", ENV_GOOGLE + " or " + ENV_GEMINI);
            throw new TarotException("Environment variable '" + ENV_GOOGLE + "' or '" + ENV_GEMINI + "' is not set.");
        }
        return GoogleAiGeminiChatModel.builder()
                .apiKey(apiKey)
                .modelName("gemini-2.5-flash")
                .temperature(setting.temperature())
                .frequencyPenalty(setting.frequencyPenalty())
                .build();
    }

    public static TarotAiService createService() {

        return AiServices.builder(TarotAiService.class)
                .chatModel(createModel())
                .build();
    }

    private static AiSetting getAiSetting() {
        String model = Config.get().getString("ai", "model");
        double temperature = Config.get().getDouble("ai", "temperature", 0.7);
        double frequencyPenalty = Config.get().getDouble("ai", "frequency_penalty", 0.0);

        return new AiSetting(model, temperature, frequencyPenalty);
    }
}
