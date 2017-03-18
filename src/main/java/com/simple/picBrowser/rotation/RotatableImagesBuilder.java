package com.simple.picBrowser.rotation;

import javafx.scene.image.Image;

/**
 * Created by mprtcz on 2017-03-18.
 */
public class RotatableImagesBuilder {
    private RotatableImage basicRotatableImage;
    private RotatableImage tiltedRotatableImage;

    public RotatableImagesBuilder(String sourceImagePath, double backgroundWidth, double backgroundHeight) {
        this.basicRotatableImage = new RotatableImage(new Image(sourceImagePath, backgroundWidth, backgroundHeight, true, true));
        this.tiltedRotatableImage = new RotatableImage(new Image(sourceImagePath, backgroundHeight, backgroundWidth, true, true));
    }

    public RotatableImage getBasicRotatableImage() {
        return basicRotatableImage;
    }

    public RotatableImage getTiltedRotatableImage() {
        return tiltedRotatableImage;
    }
}