package com.simple.picBrowser;

import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Optional;

/**
 * Created by Azet on 2015-10-17.
 */
class FolderChooser {

    static File chooseFile(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("wtf");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Picture Only", "*.jpg", "*.png", "*.bmp", "*.gif", "*.tiff"
        ));
        return fileChooser.showOpenDialog(stage);
    }

    static String enterSubfolderName() {
        TextInputDialog dialog = new TextInputDialog("ChosenFiles");
        dialog.setTitle("Enter Subfolder Name");
        dialog.setContentText("Subfolder Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            return result.get();
        }
        return "ChosenFiles";
    }
}