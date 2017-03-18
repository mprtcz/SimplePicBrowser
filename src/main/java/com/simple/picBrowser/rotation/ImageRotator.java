package com.simple.picBrowser.rotation;

import com.simple.picBrowser.Controller;
import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

import java.util.EnumMap;
import java.util.Map;

import static com.simple.picBrowser.Controller.ImageOrientation.DEG180;
import static com.simple.picBrowser.Controller.ImageOrientation.DEG270;
import static com.simple.picBrowser.Controller.ImageOrientation.DEG90;


/**
 * Created by mprtcz on 2017-03-17.
 */
public class ImageRotator {

    private RotatableImage basicImage;
    private RotatableImage tiltedImage;

    private Map<Controller.ImageOrientation, Image> images = new EnumMap<>(Controller.ImageOrientation.class);

    ImageRotator(Image basicImage) {
        this.basicImage = new RotatableImage(basicImage);
    }

    public ImageRotator(Image basicImage, Image tiltedImage) {
        this.basicImage = new RotatableImage(basicImage);
        this.tiltedImage = new RotatableImage(tiltedImage);
    }

    public ImageRotator(RotatableImage basicImage, RotatableImage tiltedImage) {
        this.basicImage = basicImage;
        this.tiltedImage = tiltedImage;
    }

    public Image getRotatedImage(Controller.ImageOrientation imageOrientation) {
        switch (imageOrientation) {
            case DEFAULT:
                return basicImage.image;
            case DEG90:
                return rotate90Deg();
            case DEG180:
                return rotate180deg();
            case DEG270:
                return rotate270deg();
            default:
                return basicImage.image;
        }
    }

    private Image rotate90Deg() {
        Image storedImage = images.get(DEG90);
        if (storedImage != null) {
            return storedImage;
        }

        RotatableImage sourceImage = chooseSourceImage();

        WritableImage writableImage = new WritableImage(sourceImage.height, sourceImage.width);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 0; i < sourceImage.width; i++) {
            for (int j = 0; j < sourceImage.height; j++) {
                pixelWriter.setArgb(j, sourceImage.width - i - 1, sourceImage.pixelReader.getArgb(i, j));
            }
        }
        images.put(DEG90, writableImage);
        return writableImage;
    }

    private Image rotate180deg() {
        Image storedImage = images.get(DEG180);
        if (storedImage != null) {
            return storedImage;
        }

        WritableImage writableImage = new WritableImage(this.basicImage.width, this.basicImage.height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 0; i < this.basicImage.width; i++) {
            for (int j = 0; j < this.basicImage.height; j++) {
                pixelWriter.setArgb(this.basicImage.width - i - 1, this.basicImage.height - j - 1, this.basicImage.pixelReader.getArgb(i, j));
            }
        }
        images.put(DEG180, writableImage);
        return writableImage;
    }

    private Image rotate270deg() {
        Image storedImage = images.get(DEG270);
        if (storedImage != null) {
            return storedImage;
        }

        RotatableImage sourceImage = chooseSourceImage();

        WritableImage writableImage = new WritableImage(sourceImage.height, sourceImage.width);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 0; i < sourceImage.width; i++) {
            for (int j = 0; j < sourceImage.height; j++) {
                pixelWriter.setArgb(sourceImage.height - j - 1, i, sourceImage.pixelReader.getArgb(i, j));
            }
        }
        images.put(DEG270, writableImage);
        return writableImage;
    }

    private RotatableImage chooseSourceImage() {
        RotatableImage sourceImage;
        if (this.tiltedImage == null) {
            sourceImage = this.basicImage;
        } else {
            sourceImage = this.tiltedImage;
        }
        return sourceImage;
    }


}
