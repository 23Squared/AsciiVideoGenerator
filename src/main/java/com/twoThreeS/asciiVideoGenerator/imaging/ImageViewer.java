package com.twoThreeS.asciiVideoGenerator.imaging;

import com.google.inject.Inject;
import com.twoThreeS.asciiVideoGenerator.utils.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class ImageViewer extends JFrame implements ActionListener {

    private static final Logger LOGGER = new Logger(LoggerFactory.getLogger(ImageViewer.class));

    private JLabel imageLabel;

    @Inject
    public ImageViewer() {
        setTitle("Dicky's Magical ASCII Video App!");
        setSize(640, 480);
        imageLabel = new JLabel();
        add(imageLabel);
    }

    public void updateImageViewer(BufferedImage image) {
        ImageIcon imageIcon = new ImageIcon(image);
        imageLabel.setIcon(imageIcon);
        repaint();
    }

    public void updateImageViewer(String image) {
        imageLabel.setText(image);
        repaint();
    }

    public void actionPerformed(ActionEvent e) {
        // do nothing, for now
        LOGGER.debug("ImageViewer received event: " + e);
    }
}
