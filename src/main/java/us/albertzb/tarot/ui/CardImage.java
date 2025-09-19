/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package us.albertzb.tarot.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.albertzb.tarot.model.TarotCard;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class CardImage extends JComponent {

    private static final Logger LOG = LoggerFactory.getLogger(CardImage.class);
    private static final BufferedImage EMPTY_IMAGE = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    private static final long serialVersionUID = 1L;
    private transient final TarotCard card;
    private final boolean isHalfSize;
    private final boolean isReversed;
    private transient BufferedImage image;

    public CardImage(TarotCard card) {
        this(card, false, false);
    }

    private CardImage(TarotCard card, boolean isHalfSize, boolean isReversed) {
        this.card = card;
        this.isHalfSize = isHalfSize;
        this.isReversed = isReversed;
        image = halfSize(isHalfSize, reverse(isReversed, createImage(card.getImageBytes())));
    }

    public static CardImage create(TarotCard card, boolean isHalfSize, boolean isReversed) {
        return new CardImage(card, isHalfSize, isReversed);
    }

    private static BufferedImage createImage(byte[] imageBytes) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes)) {
            return ImageIO.read(bis);
        } catch (IOException ex) {
            return EMPTY_IMAGE;
        }
    }

    private static BufferedImage halfSize(boolean isHalfSize, BufferedImage originalImage) {
        if(!isHalfSize) {
            return originalImage;
        }
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

    private static BufferedImage reverse(boolean isReversed, BufferedImage originalImage) {
        if(!isReversed) {
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

    @Override
    public int getHeight() {
        return image.getHeight(); 
    }

    @Override
    public int getWidth() {
        return image.getWidth();
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(image.getWidth(), image.getHeight());
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
    }
    
    public String getInterpretation() {
        if(isReversed) {
            return String.join(", ", card.getReversed_keywords());
        }
        return String.join(", ", card.getUpright_keywords());
    }
    
    public String getDescription() {
        return card.getDescription();
    }
}
