package com.twoThreeS.asciiVideoGenerator.guiceModules;

import com.google.inject.AbstractModule;
import com.twoThreeS.asciiVideoGenerator.imaging.ImageViewer;
import com.twoThreeS.asciiVideoGenerator.imaging.VideoCapture;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(VideoCapture.class);
        bind(ImageViewer.class);
    }
}
