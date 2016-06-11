package com.twoThreeS.asciiVideoGenerator;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.twoThreeS.asciiVideoGenerator.guiceModules.AppModule;
import com.twoThreeS.asciiVideoGenerator.imaging.ImageViewer;
import com.twoThreeS.asciiVideoGenerator.imaging.VideoCapture;
import com.twoThreeS.asciiVideoGenerator.utils.Logger;
import io.korhner.asciimg.image.AsciiImgCache;
import io.korhner.asciimg.image.character_fit_strategy.StructuralSimilarityFitStrategy;
import io.korhner.asciimg.image.converter.AsciiToImageConverter;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.image.BufferedImage;

public class App {

    private static final Logger LOGGER = new Logger(LoggerFactory.getLogger(App.class));

    private VideoCapture videoCapture;
    private ImageViewer imageViewer;

    private AsciiImgCache cache;
    private AsciiToImageConverter imageConverter;

    @Inject
    public App(VideoCapture videoCapture,
               ImageViewer imageViewer) {
        this.videoCapture = videoCapture;
        this.imageViewer = imageViewer;
    }

    public static void main(String[] args) {
        LOGGER.info("Stating application...");

        Injector appInjector = Guice.createInjector(new AppModule());
        App application = appInjector.getInstance(App.class);
        application.initialise();
        application.run();
    }

    public void initialise() {
        LOGGER.info("Initialising video context...");
        cache = AsciiImgCache.create(new Font("Courier", Font.BOLD, 6));
        imageConverter = new AsciiToImageConverter(cache, new StructuralSimilarityFitStrategy());
        imageViewer.setVisible(true);
    }

    public void run() {
        while(true) {
            LOGGER.debug("Starting render cycle");
            BufferedImage image = videoCapture.captureImage();
            BufferedImage asciiImage = imageConverter.convertImage(image);
            imageViewer.updateImageViewer(asciiImage);
            LOGGER.debug("Finished render cycle");
        }
    }
}
