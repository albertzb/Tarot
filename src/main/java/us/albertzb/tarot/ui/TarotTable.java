/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package us.albertzb.tarot.ui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.html.HTMLDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.albertzb.tarot.ai.GeminiClientImpl;
import us.albertzb.tarot.ai.GenerativeAiClient;
import us.albertzb.tarot.model.TarotDeck;
import us.albertzb.tarot.spreads.Concern;
import us.albertzb.tarot.spreads.PastPresentFuture;
import us.albertzb.tarot.spreads.PracticalAdvice;
import us.albertzb.tarot.spreads.Spreadable;
import us.albertzb.tarot.utils.CardImageLoader;
import us.albertzb.tarot.utils.DeckStack;
import us.albertzb.tarot.utils.Env;
import us.albertzb.tarot.utils.YamlReader;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class TarotTable extends javax.swing.JFrame {

    private static final Logger LOG = LoggerFactory.getLogger(TarotTable.class);
    private static final long serialVersionUID = 1L;
    private static final String CARDS_FILE = "cards/tarot/cards.yml";
    private static final String P_TEMPLATE = "<html><body><h1>%s</h1><p>%s</p><p id=\"foot\" /></body></html>";
    private static final String I_TEMPLATE = "<h2>%s</h2><p>%s</p>";
    private static final String I_WARNING = "Problem when writing interpretation";
    private static final String BOLD_REG = "\\*\\*(.*?)\\*\\*";
    private static final String BOLD_INJ = "<b>$1</b>";
    private static final String IT_REG = "\\*(.*)?\\*";
    private static final String IT_INJ = "<i>$1</i>";

    private transient final DeckStack stack;
    private transient Spreadable spread;
    private transient List<CardImage> images;
    private boolean isAISupported;

    /**
     * Creates new form TarotTable
     */
    public TarotTable() {
        initComponents();
        spreadPnl.setLayout(new CenterRowLayout(20));
        waitPnl.setLayout(new CenterRowLayout(0));
        waitPnl.setVisible(false);
        splitPnl.setDividerLocation(0.667);
        TarotDeck deck = readDeck();
        SwingUtilities.invokeLater(() -> {
            CardImageLoader imageLoader = new CardImageLoader(deck);
            imageLoader.load();
        });
        stack = new DeckStack(deck);
        isAISupported = Env.hasVar("GOOGLE_API_KEY", "GEMINI_API_KEY");
        copyBtn.setVisible(!isAISupported);
    }

    private void closeFrame() {
        LOG.info("Closing the table");
        SwingUtilities.invokeLater(() -> {
            this.dispose();
        });
    }

    private TarotDeck readDeck() {
        return YamlReader.read(CARDS_FILE);
    }

    private void prepareSpread(Spreadable spread) {
        this.spread = spread;
        images = stack.drawImages(spread.getCardCount());
        //show the image in the middle of the table
        spreadPnl.removeAll();
        for (CardImage image : images) {
            spreadPnl.add(image);
        }
        spreadPnl.revalidate();
        spreadPnl.repaint();

        interpretationPnl.setText(String.format(P_TEMPLATE, spread.getTitle(), spread.getSubTitle()));
        HTMLDocument doc = (HTMLDocument) interpretationPnl.getDocument();
        IntStream.range(0, spread.getPositionCount())
                .forEachOrdered(i -> {
                    Element footer = doc.getElement("foot");
                    String description = String.format(I_TEMPLATE, spread.getPositions().get(i), images.get(i).getDescription());
                    try {
                        doc.insertBeforeStart(footer, description);
                    } catch (IOException | BadLocationException ex) {
                        LOG.warn(I_WARNING, ex);
                    }
                });

        if (isAISupported) {
            waitPnl.setVisible(true);
            new SwingWorker<Void, Void>() {
                @Override
                protected Void doInBackground() throws Exception {
                    GenerativeAiClient client = GeminiClientImpl.create();
                    client.generateText(getPrompt(), null).ifPresent(iStr -> {
                        Element footer = doc.getElement("foot");
                        String hStr = iStr
                                .replaceAll(BOLD_REG, BOLD_INJ)
                                .replaceAll(IT_REG, IT_INJ)
                                .replaceAll("\n", "<br>");
                                
                        String interpretation = String.format(I_TEMPLATE, "Interpretation", hStr);
                        try {
                            doc.insertBeforeStart(footer, interpretation);
                        } catch (IOException | BadLocationException ex) {
                            LOG.warn(I_WARNING, ex);
                        }
                    });
                    return null;
                }

                @Override
                protected void done() {
                    waitPnl.setVisible(false);
                }
            }.execute();
        }
    }

    String getPrompt() {
        if (spread == null) {
            return "Tell me a joke about a psychic who can't work without GenAI.";
        }
        final StringBuilder sb = new StringBuilder(100);
        sb.append(spread.getPrompt()).append("\n");

        sb.append("#")
                .append(spread.getTitle())
                .append("\n\n")
                .append(spread.getSubTitle())
                .append("\n\n");

        IntStream.range(0, spread.getPositionCount())
                .forEachOrdered(i -> {
                    sb.append("##")
                            .append(spread.getPositions().get(i))
                            .append("\n\n")
                            .append(images.get(i).getInterpretation())
                            .append("\n\n");
                });

        return sb.toString();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonPnl = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        ppfBtn = new javax.swing.JButton();
        concernBtn = new javax.swing.JButton();
        practicalBtn = new javax.swing.JButton();
        copyBtn = new javax.swing.JButton();
        closeBtn = new javax.swing.JButton();
        splitPnl = new javax.swing.JSplitPane();
        spreadPnl = new javax.swing.JPanel();
        layeredPane = new javax.swing.JLayeredPane();
        interpretationSrl = new javax.swing.JScrollPane();
        interpretationPnl = new javax.swing.JEditorPane();
        waitPnl = new javax.swing.JPanel();
        waitLbl = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Tarot Table");

        buttonPnl.setLayout(new javax.swing.BoxLayout(buttonPnl, javax.swing.BoxLayout.LINE_AXIS));
        buttonPnl.add(filler1);

        ppfBtn.setText("Past Present Future");
        ppfBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppfBtnActionPerformed(evt);
            }
        });
        buttonPnl.add(ppfBtn);

        concernBtn.setText("Concern");
        concernBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                concernBtnActionPerformed(evt);
            }
        });
        buttonPnl.add(concernBtn);

        practicalBtn.setText("Practical Advise");
        practicalBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practicalBtnActionPerformed(evt);
            }
        });
        buttonPnl.add(practicalBtn);

        copyBtn.setText("Copy Prompt");
        copyBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyBtnActionPerformed(evt);
            }
        });
        buttonPnl.add(copyBtn);

        closeBtn.setText("Close");
        closeBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeBtnActionPerformed(evt);
            }
        });
        buttonPnl.add(closeBtn);

        getContentPane().add(buttonPnl, java.awt.BorderLayout.SOUTH);

        splitPnl.setResizeWeight(0.667);

        spreadPnl.setBackground(new java.awt.Color(51, 0, 51));
        spreadPnl.setBorder(javax.swing.BorderFactory.createEmptyBorder(15, 5, 15, 5));
        splitPnl.setLeftComponent(spreadPnl);

        layeredPane.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                layeredPaneComponentResized(evt);
            }
        });

        interpretationSrl.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        interpretationSrl.setPreferredSize(new java.awt.Dimension(115, 200));

        interpretationPnl.setEditable(false);
        interpretationPnl.setContentType("text/html"); // NOI18N
        interpretationPnl.setMinimumSize(new java.awt.Dimension(62, 200));
        interpretationPnl.setPreferredSize(new java.awt.Dimension(62, 200));
        interpretationSrl.setViewportView(interpretationPnl);

        layeredPane.add(interpretationSrl);
        interpretationSrl.setBounds(0, 0, 115, 200);

        waitPnl.setOpaque(false);

        waitLbl.setFont(new java.awt.Font("Liberation Sans", 1, 24)); // NOI18N
        waitLbl.setForeground(new java.awt.Color(255, 0, 204));
        waitLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/waiting.gif"))); // NOI18N
        waitPnl.add(waitLbl);

        layeredPane.setLayer(waitPnl, javax.swing.JLayeredPane.PALETTE_LAYER);
        layeredPane.add(waitPnl);
        waitPnl.setBounds(0, 0, 210, 210);

        splitPnl.setRightComponent(layeredPane);

        getContentPane().add(splitPnl, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        closeFrame();
    }//GEN-LAST:event_closeBtnActionPerformed

    private void practicalBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practicalBtnActionPerformed
        prepareSpread(new PracticalAdvice());
    }//GEN-LAST:event_practicalBtnActionPerformed

    private void ppfBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppfBtnActionPerformed
        prepareSpread(new PastPresentFuture());
    }//GEN-LAST:event_ppfBtnActionPerformed

    private void concernBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_concernBtnActionPerformed
        prepareSpread(new Concern());
    }//GEN-LAST:event_concernBtnActionPerformed

    private void copyBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_copyBtnActionPerformed
        if (spread == null) {
            return;
        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(getPrompt()), null);
    }//GEN-LAST:event_copyBtnActionPerformed

    private void layeredPaneComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_layeredPaneComponentResized
        Dimension size = layeredPane.getSize();
        interpretationSrl.setBounds(0, 0, size.width, size.height);
        waitPnl.setBounds(0, 0, size.width, size.height);
    }//GEN-LAST:event_layeredPaneComponentResized

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPnl;
    private javax.swing.JButton closeBtn;
    private javax.swing.JButton concernBtn;
    private javax.swing.JButton copyBtn;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JEditorPane interpretationPnl;
    private javax.swing.JScrollPane interpretationSrl;
    private javax.swing.JLayeredPane layeredPane;
    private javax.swing.JButton ppfBtn;
    private javax.swing.JButton practicalBtn;
    private javax.swing.JSplitPane splitPnl;
    private javax.swing.JPanel spreadPnl;
    private javax.swing.JLabel waitLbl;
    private javax.swing.JPanel waitPnl;
    // End of variables declaration//GEN-END:variables
}
