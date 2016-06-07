package com.simple.picBrowser;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Created by Azet on 2015-10-17.
 */
public class PicFileReader {

    public static File chooseFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("wtf");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Picture Only", "*.jpg", "*.png", "*.bmp", "*.gif"
        ));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if(selectedFile != null){
            //System.out.println("Sciezka pliku: " +selectedFile.getAbsolutePath());
        }
        return selectedFile;
    }
}
