package com.twoThreeS.asciiVideoGenerator;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.twoThreeS.asciiImage.processors.ImageConverter;
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
    private ImageConverter imageConverter;
    private AsciiImgCache imageCache;
    private AsciiToImageConverter asciiImageConverter;

    private boolean grayscaling = false;

    @Inject
    public App(VideoCapture videoCapture,
               ImageViewer imageViewer,
               ImageConverter imageConverter) {
        this.videoCapture = videoCapture;
        this.imageViewer = imageViewer;
        this.imageConverter = imageConverter;
    }

    public static void main(String[] args) {
        LOGGER.info("Stating application...");

        Injector appInjector = Guice.createInjector(new AppModule());
        App application = appInjector.getInstance(App.class);
        application.initialise();

        if(args.length == 1 && Boolean.parseBoolean(args[0]) == true) {
            LOGGER.info("Enabling grayscaling");
            application.setGrayscaling(true);
        }

        application.run();
    }

    public void initialise() {
        LOGGER.info("Building font cache...");
        imageCache = AsciiImgCache.create(new Font("Courier", Font.BOLD, 6));

        LOGGER.info("Initialising image converter...");
        asciiImageConverter = new AsciiToImageConverter(imageCache, new StructuralSimilarityFitStrategy());
        imageViewer.setVisible(true);
    }

    public void run() {
        while(true) {
            LOGGER.debug("Starting render cycle");
            BufferedImage capture = videoCapture.captureImage();
            BufferedImage image = capture;

            if(grayscaling) {
                image = imageConverter.convertToGrayscale(capture);
            }

            BufferedImage asciiImage = asciiImageConverter.convertImage(image);
            imageViewer.updateImageViewer(asciiImage);
            LOGGER.debug("Finished render cycle");
        }
    }

    public void setGrayscaling(boolean grayscaling) {
        this.grayscaling = grayscaling;
    }
}
