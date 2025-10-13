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
import us.albertzb.tarot.spreads.Affirmation;
import us.albertzb.tarot.spreads.Balance;
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
    private static final int ROW_HEIGHT = 32;

    private transient final DeckStack stack;
    private transient Spreadable spread;
    private transient List<CardImage> images;
    private final boolean isAISupported;

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
        if (isAISupported) {
            actionBtn.setText("Lay The Cards");
        } else {
            actionBtn.setText("Copy Prompt");
        }
        SwingUtilities.invokeLater(() -> {
            prepareSpread(new PastPresentFuture());
        });
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

    private void clearTable() {
        spreadPnl.removeAll();
        interpretationPnl.setText("");
        SwingUtilities.invokeLater(() -> {
            spreadPnl.revalidate();
            spreadPnl.repaint();
        });
    }

    private void prepareSpread(Spreadable spread) {
        clearTable();
        this.spread = spread;

        questionLbl.setVisible(spread.hasInput());
        questionTxt.setVisible(spread.hasInput());

        questionLbl.setText(spread.getQuestion());
    }

    private void doTheCards() {
        layCards();
        if (isAISupported) {
            runInterpretation();
        } else {
            copyPrompt();
        }
    }

    private void copyPrompt() {
        if (spread == null) {
            return;
        }

        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(new StringSelection(getPrompt("a  person")), null);

    }

    private void runInterpretation() {
        waitPnl.setVisible(true);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                HTMLDocument doc = (HTMLDocument) interpretationPnl.getDocument();
                GenerativeAiClient client = GeminiClientImpl.create();

                StringBuilder sb = new StringBuilder("");
                String question = spread.getConversionPrompt() + "\n" + questionTxt.getText();
                if (spread.hasInput()) {
                    client.generateText(question, null).ifPresent(iStr -> {
                        sb.append(iStr);
                    });
                    if (sb.isEmpty()) {
                        sb.append("a person");
                    }
                }
                client.generateText(getPrompt(sb.toString()), null).ifPresent(iStr -> {
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

    private void layCards() {
        //show the image in the middle of the table
        clearTable();

        images = stack.drawImages(spread.getCardCount());
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

    }

    String getPrompt(String personalizedText) {
        if (spread == null) {
            return "Tell me a joke about a psychic who can't work without GenAI.";
        }
        final StringBuilder sb = new StringBuilder(100);

        sb.append(spread.getPrompt().replace("a person", personalizedText)).append("\n");

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

    private int[] getColumnWidths() {

        return new int[]{controlPnl.getWidth()};
    }

    private int[] getRowHeights() {
        return new int[]{ROW_HEIGHT, ROW_HEIGHT, controlPnl.getHeight() - 2 * ROW_HEIGHT};
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonPnl = new javax.swing.JPanel();
        filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
        actionBtn = new javax.swing.JButton();
        closeBtn = new javax.swing.JButton();
        splitPnl = new javax.swing.JSplitPane();
        spreadPnl = new javax.swing.JPanel();
        controlPnl = new javax.swing.JPanel();
        questionLbl = new javax.swing.JLabel();
        questionTxt = new javax.swing.JTextField();
        layeredPane = new javax.swing.JLayeredPane();
        interpretationSrl = new javax.swing.JScrollPane();
        interpretationPnl = new javax.swing.JEditorPane();
        waitPnl = new javax.swing.JPanel();
        waitLbl = new javax.swing.JLabel();
        menuBar = new javax.swing.JMenuBar();
        spreadMnu = new javax.swing.JMenu();
        affirmMni = new javax.swing.JMenuItem();
        balanceMni = new javax.swing.JMenuItem();
        pppMni = new javax.swing.JMenuItem();
        concernMni = new javax.swing.JMenuItem();
        practicalMni = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("The Tarot Table");

        buttonPnl.setLayout(new javax.swing.BoxLayout(buttonPnl, javax.swing.BoxLayout.LINE_AXIS));
        buttonPnl.add(filler1);

        actionBtn.setText("Action");
        actionBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionBtnActionPerformed(evt);
            }
        });
        buttonPnl.add(actionBtn);

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

        java.awt.GridBagLayout controlPnlLayout = new java.awt.GridBagLayout();
        controlPnlLayout.columnWidths = getColumnWidths();
        controlPnlLayout.rowHeights = getRowHeights();
        controlPnl.setLayout(controlPnlLayout);

        questionLbl.setAlignmentX(0.5F);
        questionLbl.setPreferredSize(new java.awt.Dimension(64, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        controlPnl.add(questionLbl, gridBagConstraints);

        questionTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                questionTxtActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        controlPnl.add(questionTxt, gridBagConstraints);

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
        waitLbl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/waiting_t.gif"))); // NOI18N
        waitPnl.add(waitLbl);

        layeredPane.setLayer(waitPnl, javax.swing.JLayeredPane.PALETTE_LAYER);
        layeredPane.add(waitPnl);
        waitPnl.setBounds(0, 0, 210, 210);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        controlPnl.add(layeredPane, gridBagConstraints);

        splitPnl.setRightComponent(controlPnl);

        getContentPane().add(splitPnl, java.awt.BorderLayout.CENTER);

        spreadMnu.setText("Spread");

        affirmMni.setText("Affirmation");
        affirmMni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                affirmMniActionPerformed(evt);
            }
        });
        spreadMnu.add(affirmMni);

        balanceMni.setText("The Balance");
        balanceMni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                balanceMniActionPerformed(evt);
            }
        });
        spreadMnu.add(balanceMni);

        pppMni.setText("Past - Present - Future");
        pppMni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pppMniActionPerformed(evt);
            }
        });
        spreadMnu.add(pppMni);

        concernMni.setText("Concern");
        concernMni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                concernMniActionPerformed(evt);
            }
        });
        spreadMnu.add(concernMni);

        practicalMni.setText("Practical Advice");
        practicalMni.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                practicalMniActionPerformed(evt);
            }
        });
        spreadMnu.add(practicalMni);

        menuBar.add(spreadMnu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeBtnActionPerformed
        closeFrame();
    }//GEN-LAST:event_closeBtnActionPerformed

    private void layeredPaneComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_layeredPaneComponentResized
        Dimension size = layeredPane.getSize();
        interpretationSrl.setBounds(0, 0, size.width, size.height);
        waitPnl.setBounds(0, 0, size.width, size.height);
    }//GEN-LAST:event_layeredPaneComponentResized

    private void pppMniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pppMniActionPerformed
        prepareSpread(new PastPresentFuture());
    }//GEN-LAST:event_pppMniActionPerformed

    private void concernMniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_concernMniActionPerformed
        prepareSpread(new Concern());
    }//GEN-LAST:event_concernMniActionPerformed

    private void practicalMniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_practicalMniActionPerformed
        prepareSpread(new PracticalAdvice());
    }//GEN-LAST:event_practicalMniActionPerformed

    private void questionTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_questionTxtActionPerformed
        doTheCards();
    }//GEN-LAST:event_questionTxtActionPerformed

    private void actionBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionBtnActionPerformed
        doTheCards();
    }//GEN-LAST:event_actionBtnActionPerformed

    private void affirmMniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_affirmMniActionPerformed
        prepareSpread(new Affirmation());
    }//GEN-LAST:event_affirmMniActionPerformed

    private void balanceMniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_balanceMniActionPerformed
        prepareSpread(new Balance());
    }//GEN-LAST:event_balanceMniActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton actionBtn;
    private javax.swing.JMenuItem affirmMni;
    private javax.swing.JMenuItem balanceMni;
    private javax.swing.JPanel buttonPnl;
    private javax.swing.JButton closeBtn;
    private javax.swing.JMenuItem concernMni;
    private javax.swing.JPanel controlPnl;
    private javax.swing.Box.Filler filler1;
    private javax.swing.JEditorPane interpretationPnl;
    private javax.swing.JScrollPane interpretationSrl;
    private javax.swing.JLayeredPane layeredPane;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem pppMni;
    private javax.swing.JMenuItem practicalMni;
    private javax.swing.JLabel questionLbl;
    private javax.swing.JTextField questionTxt;
    private javax.swing.JSplitPane splitPnl;
    private javax.swing.JMenu spreadMnu;
    private javax.swing.JPanel spreadPnl;
    private javax.swing.JLabel waitLbl;
    private javax.swing.JPanel waitPnl;
    // End of variables declaration//GEN-END:variables
}
