/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
import javax.imageio.ImageIO;
import us.albertzb.tarot.model.TarotCard;
import us.albertzb.tarot.model.TarotDeck;
import us.albertzb.tarot.ui.CardImage;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class DeckStack {

    private static final BufferedImage EMPTY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private static final Random RANDOM = new Random();
    private final TarotDeck deck;

    public DeckStack(TarotDeck deck) {
        this.deck = deck;
    }

    public List<BufferedImage> draw(int number) {
        return getShuffeledCards(number)
                .map(this::createBI)
                .map(this::shrinkBI)
                .map(this::rotate180)
                .toList();
    }
    
    public List<CardImage> drawImages(int number) {
        return getShuffeledCards(number)
                .map(t -> CardImage.create(t, true, RANDOM.nextBoolean()))
                .toList();
    }
    
    public Stream<TarotCard> getShuffeledCards(int number) {
        List<TarotCard> shuffleable = new ArrayList<>(deck.getCards());

        //Always shuffle 7 times, always!
        for (int i = 0; i < 7; ++i) {
            Collections.shuffle(shuffleable);
        }

        return shuffleable.stream()
                .limit(number);
    }

    private BufferedImage createBI(TarotCard card) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(card.getImageBytes())) {
            return ImageIO.read(bis);
        } catch (IOException ex) {
            return EMPTY_IMAGE;
        }
    }

    private BufferedImage rotate180(BufferedImage originalImage) {
        if (RANDOM.nextBoolean()) {
            return originalImage;
        }

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();
        BufferedImage rotatedImage = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = rotatedImage.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        AffineTransform transform = AffineTransform
                .getRotateInstance(
                        Math.toRadians(180),
                        width / 2.0,
                        height / 2.0);

        g2d.drawImage(originalImage, transform, null);
        g2d.dispose();

        return rotatedImage;
    }

    private BufferedImage shrinkBI(BufferedImage originalImage) {
        int newWidth = originalImage.getWidth() / 2;
        int newHeight = originalImage.getHeight() / 2;

        // Create a new BufferedImage with the target dimensions
        BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, originalImage.getType());

        // Get the graphics context of the new image
        Graphics2D g2d = resizedImage.createGraphics();

        // Optional: set rendering hints for better quality scaling
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the original image onto the new one, scaling it to fit the new size
        g2d.drawImage(originalImage, 0, 0, newWidth, newHeight, null);

        g2d.dispose();

        return resizedImage;
    }
}
