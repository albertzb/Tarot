/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package us.albertzb.tarot;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.Frame;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.albertzb.tarot.ui.TarotTable;

/**
 *
 * @author albertzb [albertzb42@gmail.com]
 */
public class Tarot {

    private static final Logger LOG = LoggerFactory.getLogger(Tarot.class);

    public static void main(String args[]) {
        FlatDarkLaf.setup();
        LOG.info("Starting Tarot application");

        SwingUtilities.invokeLater(() -> {
            /* Create and display the form */
            final JFrame frame = new TarotTable();
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);

            frame.setVisible(true);
        });
    }
}
