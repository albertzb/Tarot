/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.ai;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public interface TarotAiService {
 
    @SystemMessage("{{conversionprompt}}")
    String convert(@V("conversionprompt") String conversionPrompt,
            @UserMessage String userMessage);
    
    @SystemMessage("{{spreadprompt}}")
    String interpret(
            @V("spreadprompt") String conversionPrompt,
            @UserMessage String userMessage);
}
