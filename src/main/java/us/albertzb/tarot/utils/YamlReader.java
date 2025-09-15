/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.utils;

import java.io.InputStream;
import org.yaml.snakeyaml.Yaml;
import us.albertzb.tarot.model.TarotDeck;

/**
 * Reads tarot deck and cards from a given yaml file.
 * @author albertzb [albertzb42@gmail.com]
 */
public class YamlReader {
    
    private YamlReader() {}
    
    public static TarotDeck read(String yamlFile) {
        Yaml yaml = new Yaml();

        // Load the YAML from a resource file or a string
        InputStream inputStream = YamlReader.class
                .getClassLoader()
                .getResourceAsStream(yamlFile);

        // Use loadAs() to map the YAML data to the TarotDeck class
        TarotDeck deck = yaml.loadAs(inputStream, TarotDeck.class);

        // Access the mapped data
        return deck;
    }
}