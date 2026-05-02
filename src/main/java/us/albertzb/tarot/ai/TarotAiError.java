/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.ai;

import java.time.Duration;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public record TarotAiError (
    String provider,
    int httpCode,
    String userMessage
){}
