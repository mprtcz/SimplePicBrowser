package com.simple.picBrowser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Azet on 2015-10-17.
 */
public class Controller {

    public TextField textField;
    public Button openButton;
    public ImageView mainImage, prevImageView, nextImageView;
    public ProgressBar progressBar;
    public TextField progressTextField;
    public Button nextButton, prevButton;
    public ListView<String> listViewControl;
    public Button addToFolderButton;
    public TextField fileStatusTextField;
    public Button rotateButton;
    public TextArea propTextArea;
    public BorderPane borderPane;
    public Pane imageViewPane, progressBarPane;


    File currentFile;
    List<File> fileList;
    public double rotation = 0;

    public void onOpenButtonClicked(ActionEvent actionEvent) throws MalformedURLException {
        currentFile = PicFileReader.chooseFile(new Stage());

        if (currentFile != null) {
            fileList = FilesListCreator.getImagePathsList(currentFile.getAbsolutePath());
            setTextField(currentFile);
            displayPictures(currentFile);
            setProgressBar();
            setListViewControl();
        }

        openButton.requestFocus();
    }


    public void onNextButtonClicked() throws MalformedURLException {
        if (currentFile != null) {
            if (fileList.indexOf(currentFile) < (fileList.size() - 1)) {
                currentFile = fileList.get(fileList.indexOf(currentFile) + 1);

            } else {
                currentFile = fileList.get(0);
            }
            setProgressBar();
            displayPictures(currentFile);
            setTextField(currentFile);
        }
    }

    public void onPrevButtonClicked() throws MalformedURLException {
        if (currentFile != null) {
            if (fileList.indexOf(currentFile) > 0) {
                currentFile = fileList.get(fileList.indexOf(currentFile) - 1);
            } else {
                currentFile = fileList.get(fileList.size() - 1);
            }
            setProgressBar();
            displayPictures(currentFile);
            setTextField(currentFile);
        }
    }

    public void setTextField(File file) throws MalformedURLException {
        textField.setText(file.getAbsolutePath());
        fileStatusTextField.setText("");
        fileStatusTextField.setStyle("-fx-background-color: transparent");
        setListFocus();
        propTextArea.setText(ImagePropertiesParser.parseImageProperties(currentFile));

    }

    public void setListViewControl() {
        List<String> filenameList = new ArrayList<String>();
        for (File c : fileList) {
            filenameList.add(c.getName());
        }

        ObservableList<String> filenames = FXCollections.observableArrayList(filenameList);
        listViewControl.setItems(filenames);
    }

    private void setListFocus() {
        if (currentFile != null) {
            try {
                int index = fileList.indexOf(currentFile);
                listViewControl.getSelectionModel().select(index);
                listViewControl.scrollTo(index);
            } catch (Exception e) {
                System.out.println("error: " + e);
            }
        }
    }


    private void setProgressBar() {
        double index = ((double) fileList.indexOf(currentFile) + 1) / ((double) fileList.size());
        progressBar.setProgress(index);
        progressTextField.setText(fileList.indexOf(currentFile) + 1 + " z " + fileList.size());
        rotation = 0;
        mainImage.setRotate(rotation);
    }

    public void onListViewClicked() throws MalformedURLException {
        String clickedFileName = listViewControl.getSelectionModel().getSelectedItem();
        if (clickedFileName != null) {
            for (File f : fileList) {
                if (clickedFileName.equals(f.getName())) {
                    currentFile = f;
                    break;
                }
            }
            setProgressBar();
            displayPictures(currentFile);
            setTextField(currentFile);
        }
    }

    public void onAddToFolderButtonClicked() {
        if (currentFile != null) {
            String folderPath = NewFolderCreator.createNewFolder(currentFile.getPath());
            String copiedFilePath = folderPath + "\\" + currentFile.getName();
            try {
                Path path = Paths.get(copiedFilePath);
                System.out.println("path: " + path);
                Files.copy(currentFile.toPath(), path);
                fileStatusTextField.setStyle("-fx-background-color: lightgreen");
                fileStatusTextField.setText("Added to folder!");
            } catch (IOException e) {
                fileStatusTextField.setStyle("-fx-background-color: #F00000");
                fileStatusTextField.setText("File already exists");
            }
        }
    }

    public void onRotateButtonClicked() {
        if (currentFile != null) {
            rotation += 90;
            if (rotation > 270) {
                rotation = 0;
            }
            mainImage.setRotate(rotation);
        }
    }

    private void displayAddidtionalPictures() throws MalformedURLException {
        int index = fileList.indexOf(currentFile);
        int previousFileIndex = index - 1;
        int nextFileIndex = index + 1;

        if (previousFileIndex < 0) {
            previousFileIndex = fileList.size() - 1;
        }

        if(nextFileIndex >= fileList.size()){
            nextFileIndex = 0;
        }

        displayPictureOnImageArea(fileList.get(previousFileIndex), prevImageView);
        displayPictureOnImageArea(fileList.get(nextFileIndex), nextImageView);
    }

    private void displayPictureOnImageArea(File file, ImageView imageArea) throws MalformedURLException {
        String imageData = file.toURI().toURL().toString();
        if(file == null){
            File defaultFile = new File(getClass().getResource("/2.bmp").toString());
            imageData = defaultFile.toURI().toURL().toString();
        }
        Image image = new Image(imageData);
        imageArea.setImage(image);
    }

    private void displayPictures(File file) throws MalformedURLException {
        displayPictureOnImageArea(file, mainImage);
        displayAddidtionalPictures();
    }

    public void initialize() {

        imageViewPane.setMaxHeight(0);
        mainImage.fitWidthProperty().bind(imageViewPane.widthProperty());

        openButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.SPACE){
                event.consume();
            }
        });

        openButton.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                try {
                    if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP) {
                        onPrevButtonClicked();
                    } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.DOWN) {
                        onNextButtonClicked();
                    } else if (event.getCode() == KeyCode.SPACE) {
                        onRotateButtonClicked();
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

