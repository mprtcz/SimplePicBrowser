package com.simple.picBrowser.rotation;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;

/**
 * Created by mprtcz on 2017-03-18.
 */
class RotatableImage {
    Image image;
    int width;
    int height;
    PixelReader pixelReader;

    RotatableImage(Image image) {
        this.image = image;
        this.height = (int) image.getHeight();
        this.width = (int) image.getWidth();
        this.pixelReader = image.getPixelReader();
    }
}
