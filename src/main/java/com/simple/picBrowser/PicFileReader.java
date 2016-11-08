package com.simple.picBrowser;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Azet on 2015-10-17.
 */
class PicFileReader {

    static File chooseFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("wtf");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Picture Only", "*.jpg", "*.png", "*.bmp", "*.gif", "*.tiff"
        ));
        return fileChooser.showOpenDialog(stage);
    }
}
