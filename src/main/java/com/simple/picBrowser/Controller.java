package com.simple.picBrowser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Azet on 2015-10-17.
 */
public class Controller {

    public TextField textField;
    public Button openButton;
    public ImageView imageArea;
    public ProgressBar progressBar;
    public TextField progressTextField;
    public Button nextButton, prevButton;
    public ListView<String> listViewControl;
    public Button addToFolderButton;
    public TextField fileStatusTextField;
    public Button rotateButton;
    public TextArea propTextArea;
    public ListView<CheckBox> extensionsListView;
    public TextField folderNameTextField;

    private Stage stage;
    private File currentFile;
    private List<File> fileList;
    private double rotation = 0;

    public void onOpenButtonClicked() throws MalformedURLException {
        stage = (Stage) openButton.getScene().getWindow();
        setListeners();
        currentFile = PicFileReader.chooseFile(new Stage());
        if (currentFile != null) {
            fileList = FilesListCreator.getAllFilesPathsList(currentFile, getListOfSelectedExtensions());
            setTextField(currentFile);
            displayPicture(currentFile);
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
            displayPicture(currentFile);
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
            displayPicture(currentFile);
            setTextField(currentFile);
        }
    }

    private void setTextField(File file) throws MalformedURLException {
        textField.setText(file.getAbsolutePath());
        fileStatusTextField.setText("");
        fileStatusTextField.setStyle("-fx-background-color: transparent");
        setListFocus();
        propTextArea.setText(ImagePropertiesParser.parseImageProperties(currentFile));

    }

    private void setListViewControl() {
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
        progressTextField.setText(fileList.indexOf(currentFile) + 1 + " of " + fileList.size());
        rotation = 0;
        imageArea.setRotate(rotation);
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
            displayPicture(currentFile);
            setTextField(currentFile);
        }
    }

    public void onAddToFolderButtonClicked() {
        if (currentFile != null) {
            String folderPath = NewFolderCreator.createSubFolder(currentFile, folderNameTextField.getText());
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
            imageArea.setRotate(rotation);
        }
    }

    private void displayPicture(File file) throws MalformedURLException {
        String img = file.toURI().toURL().toString();
        Image image = new Image(img);
        imageArea.setImage(image);
    }

    private void setListeners(){
        openButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if(event.getCode() == KeyCode.SPACE){
                event.consume();
            }
        });

        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            try {
                if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.UP) {
                    event.consume();
                    onPrevButtonClicked();
                } else if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.DOWN) {
                    onNextButtonClicked();
                } else if (event.getCode() == KeyCode.SPACE) {
                    event.consume();
                    onRotateButtonClicked();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        });

    }

    public void initialize() {
        ObservableList<CheckBox> items = FXCollections.observableArrayList(
                checkBoxBuilder("png", true),
                checkBoxBuilder("jpg", true),
                checkBoxBuilder("bmp", true),
                checkBoxBuilder("gif", true),
                checkBoxBuilder("tiff", false));
        extensionsListView.setItems(items);
    }

    private CheckBox checkBoxBuilder(String name, boolean isSelected) {
        CheckBox checkBox = new CheckBox(name);
        checkBox.setSelected(isSelected);
        return checkBox;
    }

    private List<String> getListOfSelectedExtensions() {
        List<String> extensionStrings = new ArrayList<>();
        ObservableList<CheckBox> items = extensionsListView.getItems();
        extensionStrings.addAll(items.stream().filter(CheckBox::isSelected)
                .map(Labeled::getText).collect(Collectors.toList()));
        return extensionStrings;
    }
}

