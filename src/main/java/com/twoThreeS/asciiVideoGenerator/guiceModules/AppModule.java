package com.twoThreeS.asciiVideoGenerator.guiceModules;

import com.google.inject.AbstractModule;
import com.twoThreeS.asciiImage.processors.ImageConverter;
import com.twoThreeS.asciiVideoGenerator.imaging.ImageViewer;
import com.twoThreeS.asciiVideoGenerator.imaging.VideoCapture;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(ImageConverter.class);
        bind(VideoCapture.class);
        bind(ImageViewer.class);
    }
}
