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
public class TarotCard implements TarotAcceptor {
    private String name;
    private String house;
    private int number;
    private List<String> upright_keywords;
    private List<String> reversed_keywords;
    private String description;
    private String elemental_correspondence;
    private String astrological_correspondence;
    private String resourcePath;
    private byte[] imageBytes;

    // Constructor to initialize a new TarotCard object
    public TarotCard() {
        number = -1;
        upright_keywords = new ArrayList<>(4);
        reversed_keywords = new ArrayList<>(4);
    }

    public TarotCard(String name, String house, int number, List<String> upright_keywords, List<String> reversed_keywords, String description, String elemental_correspondence, String astrological_correspondence, String resourcePath) {
        this.name = name;
        this.house = house;
        this.number = number;
        this.upright_keywords = upright_keywords;
        this.reversed_keywords = reversed_keywords;
        this.description = description;
        this.elemental_correspondence = elemental_correspondence;
        this.astrological_correspondence = astrological_correspondence;
        this.resourcePath = resourcePath;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public List<String> getUpright_keywords() {
        return upright_keywords;
    }

    public void setUpright_keywords(List<String> uprightKeywords) {
        this.upright_keywords = uprightKeywords;
    }

    public List<String> getReversed_keywords() {
        return reversed_keywords;
    }

    public void setReversed_keywords(List<String> reversedKeywords) {
        this.reversed_keywords = reversedKeywords;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getElemental_correspondence() {
        return elemental_correspondence;
    }

    public void setElemental_correspondence(String elementalCorrespondence) {
        this.elemental_correspondence = elementalCorrespondence;
    }

    public String getAstrological_correspondence() {
        return astrological_correspondence;
    }

    public void setAstrological_correspondence(String astrologicalCorrespondence) {
        this.astrological_correspondence = astrologicalCorrespondence;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }

    @Override
    public String toString() {
        return "TarotCard{" + "name=" + name + ", house=" + house + ", number=" + number + ", uprightKeywords=" + upright_keywords + ", reversedKeywords=" + reversed_keywords + ", description=" + description + ", elementalCorrespondence=" + elemental_correspondence + ", astrologicalCorrespondence=" + astrological_correspondence + ", resourcePath=" + resourcePath + '}';
    }

    @Override
    public void accept(TarotCardVisitor visitor) {
        visitor.visit(this);
    }
}