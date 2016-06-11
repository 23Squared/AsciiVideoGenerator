package com.twoThreeS.asciiVideoGenerator.imaging;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.inject.Inject;

import java.awt.image.BufferedImage;

public class VideoCapture {

    private Webcam webcam;

    @Inject
    public VideoCapture() {
        webcam = Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();
    }

    public BufferedImage captureImage() {
        return webcam.getImage();
    }

}
