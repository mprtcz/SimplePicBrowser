package com.simple.picBrowser;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
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
class ImageRotator {
    private Image image;
    private int width;
    private int height;
    private PixelReader imagePixelReader;
    private Map<Controller.ImageOrientation, Image> images = new EnumMap<>(Controller.ImageOrientation.class);

    ImageRotator(Image image) {
        this.image = image;
        this.height = (int) image.getHeight();
        this.width = (int) image.getWidth();
        this.imagePixelReader = image.getPixelReader();
    }

    Image getRotatedImage(Controller.ImageOrientation imageOrientation) {
        switch (imageOrientation) {
            case DEFAULT:
                return image;
            case DEG90:
                return rotate90Deg();
            case DEG180:
                return rotate180deg();
            case DEG270:
                return rotate270deg();
            default:
                return image;
        }
    }

    private Image rotate90Deg() {
        Image storedImage = images.get(DEG90);
        if (storedImage != null) {
            return storedImage;
        }

        WritableImage writableImage = new WritableImage(this.height, this.width);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                pixelWriter.setArgb(j, this.width - i - 1, imagePixelReader.getArgb(i, j));
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

        WritableImage writableImage = new WritableImage(width, this.height);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                pixelWriter.setArgb(width - i - 1, this.height - j - 1, imagePixelReader.getArgb(i, j));
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

        WritableImage writableImage = new WritableImage(this.height, this.width);
        PixelWriter pixelWriter = writableImage.getPixelWriter();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                pixelWriter.setArgb(this.height - j - 1, i, imagePixelReader.getArgb(i, j));
            }
        }
        images.put(DEG270, writableImage);
        return writableImage;
    }
}
