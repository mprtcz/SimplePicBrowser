package com.simple.picBrowser.rotation;

import javafx.scene.image.Image;

/**
 * Created by mprtcz on 2017-03-18.
 */
public class RotatableImagesBuilder {
    private RotatableImage basicRotatableImage;
    private RotatableImage tiltedRotatableImage;

    public RotatableImagesBuilder(String sourceImagePath, double backgroundWidth, double backgroundHeight) {
        Image basicImage = new Image(sourceImagePath, backgroundWidth, backgroundHeight, true, true);
        Image tiltedImage = new Image(sourceImagePath, backgroundHeight, backgroundWidth, true, true);
        this.basicRotatableImage = new RotatableImage(basicImage);
        this.tiltedRotatableImage = new RotatableImage(tiltedImage);
    }

    public RotatableImage getBasicRotatableImage() {
        return basicRotatableImage;
    }

    public RotatableImage getTiltedRotatableImage() {
        return tiltedRotatableImage;
    }
}