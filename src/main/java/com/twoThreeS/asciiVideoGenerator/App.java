package com.twoThreeS.asciiVideoGenerator;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.twoThreeS.asciiImage.processors.ImageConverter;
import com.twoThreeS.asciiVideoGenerator.guiceModules.AppModule;
import com.twoThreeS.asciiVideoGenerator.imaging.ImageViewer;
import com.twoThreeS.asciiVideoGenerator.imaging.VideoCapture;
import com.twoThreeS.asciiVideoGenerator.utils.Logger;
import org.slf4j.LoggerFactory;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class App {

    public static final String ANSI_WHITE = "\u001B[37m";
    public static final String ANSI_RESET = "\u001B[0m";

    private static final Logger LOGGER = new Logger(LoggerFactory.getLogger(App.class));

    private ImageConverter imageConverter;
    private VideoCapture videoCapture;
    private ImageViewer imageViewer;

    @Inject
    public App(ImageConverter imageConverter,
               VideoCapture videoCapture,
               ImageViewer imageViewer) {
        this.imageConverter = imageConverter;
        this.videoCapture = videoCapture;
        this.imageViewer = imageViewer;
    }

    public static void main(String[] args) {
        LOGGER.info("Stating application...");

        Injector appInjector = Guice.createInjector(new AppModule());
        App application = appInjector.getInstance(App.class);
        application.initialise();

        try {
            application.run();
        } catch (IOException e) {
            LOGGER.error("Caught IOException whilst rendering! " + e);
        }
    }

    public void initialise() {
        LOGGER.info("Initialising video context...");
        imageViewer.setVisible(true);
    }

    public void run() throws IOException {
        LOGGER.info("Beginning video capture and render...");

        while(true) {
            LOGGER.debug("Starting render cycle");

            BufferedImage image = videoCapture.captureImage();
            BufferedImage greyscaleImage = imageConverter.convertToGrayscale(image);

            String ascii = imageConverter.convertToAscii(greyscaleImage);

            imageViewer.updateImageViewer(greyscaleImage);

            LOGGER.debugNoColor(ANSI_WHITE + "Render cycle complete" + ANSI_RESET);
        }
    }
}
