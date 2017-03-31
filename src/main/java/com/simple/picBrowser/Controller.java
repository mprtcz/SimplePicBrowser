package com.simple.picBrowser;

import com.simple.picBrowser.rotation.ImageRotator;
import com.simple.picBrowser.rotation.RotatableImagesBuilder;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
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
    public ListView<String> folderFilesListView;
    public Button addToFolderButton;
    public TextField fileStatusTextField;
    public Button rotateButton;
    public Button showSideMenusButton;
    public TextArea propTextArea;
    public ListView<CheckBox> extensionsListView;
    public Button enterFolderNameButton;
    public Label folderNameLabel;
    public BorderPane borderPane;
    public VBox sidePanelVBox;
    public HBox topPanelHBox;
    public Button hideTopPanelButton;

    private Stage stage;
    private File currentFile;
    private List<File> files;
    private ImageOrientation imageOrientation = ImageOrientation.DEFAULT;
    private ImageRotator imageRotator;

    public void onOpenButtonClicked() throws MalformedURLException {
        stage = (Stage) openButton.getScene().getWindow();
        setListeners();
        currentFile = FolderChooser.chooseFile(new Stage());
        if (currentFile != null) {
            files = FilesListCreator.getAllFilesPathsList(currentFile, getListOfSelectedExtensions());
            onSelectedFileChanged();
            populateFilesListView();
        }
        openButton.requestFocus();
    }

    public void onShowSideMenusButtonClicked() {
        this.sidePanelVBox.setVisible(!this.sidePanelVBox.isVisible());
    }

    public void onHideTopPanelButtonClicked() {
        this.topPanelHBox.setVisible(!this.topPanelHBox.isVisible());
    }

    public void onEnterFolderNameButtonClicked() {
        folderNameLabel.setText(FolderChooser.enterSubfolderName());
    }

    public void onNextButtonClicked() throws MalformedURLException {
        if (currentFile != null) {
            selectNextIndex();
            onSelectedFileChanged();
        }
    }

    private void selectNextIndex() {
        if (files.indexOf(currentFile) < (files.size() - 1)) {
            currentFile = files.get(files.indexOf(currentFile) + 1);
        } else {
            currentFile = files.get(0);
        }
    }

    public void onPrevButtonClicked() throws MalformedURLException {
        if (currentFile != null) {
            selectPreviousIndex();
            onSelectedFileChanged();
        }
    }

    private void selectPreviousIndex() {
        if (files.indexOf(currentFile) > 0) {
            currentFile = files.get(files.indexOf(currentFile) - 1);
        } else {
            currentFile = files.get(files.size() - 1);
        }
    }

    private void onSelectedFileChanged() throws MalformedURLException {
        setProgressBar();
        createImageAndItsRotator();
        displayFIleAsBackgroundPicture();
        setTextField(currentFile);
    }

    private void createImageAndItsRotator() throws MalformedURLException {
        this.imageOrientation = ImageOrientation.DEFAULT;
        String img = currentFile.toURI().toURL().toString();
        RotatableImagesBuilder rotatableImagesBuilder =
                new RotatableImagesBuilder(img, this.borderPane.getWidth(), this.borderPane.getHeight());
        this.imageRotator = new ImageRotator(rotatableImagesBuilder.getBasicRotatableImage(),
                rotatableImagesBuilder.getTiltedRotatableImage());
    }

    private void setTextField(File file) throws MalformedURLException {
        textField.setText(file.getAbsolutePath());
        stylizeFileStatusTextField(FileAddStatus.CLEARED);
        setListFocus();
        propTextArea.setText(ImagePropertiesParser.parseImageProperties(currentFile));
        propTextArea.setWrapText(true);
    }

    private void populateFilesListView() {
        List<String> filenameList = files.stream().map(File::getName).collect(Collectors.toList());
        ObservableList<String> filenames = FXCollections.observableArrayList(filenameList);
        folderFilesListView.setItems(filenames);
    }

    private void setListFocus() {
        if (currentFile != null) {
            try {
                int index = files.indexOf(currentFile);
                folderFilesListView.getSelectionModel().select(index);
                folderFilesListView.scrollTo(index);
            } catch (Exception e) {
                System.out.println("error: " + e);
            }
        }
    }

    private void setProgressBar() {
        double index = ((double) files.indexOf(currentFile) + 1) / ((double) files.size());
        progressBar.setProgress(index);
        progressTextField.setText(files.indexOf(currentFile) + 1 + " of " + files.size());
    }

    public void onListViewClicked() throws MalformedURLException {
        String clickedFileName = folderFilesListView.getSelectionModel().getSelectedItem();
        if (clickedFileName != null) {
            findSelectedFile(clickedFileName);
            onSelectedFileChanged();
        }
    }

    private void findSelectedFile(String clickedFileName) {
        for (File f : files) {
            if (clickedFileName.equals(f.getName())) {
                currentFile = f;
                break;
            }
        }
    }

    public void onAddToFolderButtonClicked() {
        if (currentFile != null) {
            String folderPath = NewFolderCreator.createSubFolder(currentFile, folderNameLabel.getText());
            String copiedFilePath = folderPath + "\\" + currentFile.getName();
            copyFileAndDisplayResult(copiedFilePath);
        }
    }

    private void copyFileAndDisplayResult(String copiedFilePath) {
        try {
            Path path = Paths.get(copiedFilePath);
            Files.copy(currentFile.toPath(), path);
            stylizeFileStatusTextField(FileAddStatus.ADDED);
        } catch (IOException e) {
            stylizeFileStatusTextField(FileAddStatus.ALREADY_EXISTS);
        }
    }

    private void stylizeFileStatusTextField(FileAddStatus status) {
        fileStatusTextField.setStyle(status.getStyle());
        fileStatusTextField.setText(status.getText());
    }

    public void onRotateButtonClicked() throws MalformedURLException {
        imageOrientation = ImageOrientation.next(this.imageOrientation);
        if(currentFile != null) {
            displayFIleAsBackgroundPicture();
        }
    }

    private void displayFIleAsBackgroundPicture() throws MalformedURLException {
        BackgroundImage backgroundImage = new BackgroundImage(
                this.imageRotator.getRotatedImage(this.imageOrientation),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        List<BackgroundImage> backgroundImages = new ArrayList<>();
        backgroundImages.add(backgroundImage);
        BackgroundFill backgroundFill = new BackgroundFill(Paint.valueOf("#000000"), new CornerRadii(1), new Insets(0));
        List<BackgroundFill> backgroundFills = new ArrayList<>();
        backgroundFills.add(backgroundFill);
        this.borderPane.setBackground(new Background(backgroundFills, backgroundImages));
    }

    private void setListeners() {
        openButton.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.SPACE) {
                event.consume();
            }
        });

        stage.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            try {
                if (event.getCode() == KeyCode.LEFT) {
                    onPrevButtonClicked();
                } else if (event.getCode() == KeyCode.RIGHT) {
                    onNextButtonClicked();
                } else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN) {
                    onRotateButtonClicked();
//                } else if (event.getCode() == KeyCode.SPACE) {
//                    onRotateButtonClicked();
                } else if (event.getCode() == KeyCode.ENTER) {
                    onAddToFolderButtonClicked();
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

    private enum FileAddStatus {
        ADDED("Added to folder!", "-fx-background-color: lightgreen"),
        CLEARED("", "-fx-background-color: transparent"),
        ALREADY_EXISTS("File already exists", "-fx-background-color: #F00000");

        private String text, style;

        FileAddStatus(String text, String style) {
            this.text = text;
            this.style = style;
        }

        public String getText() {
            return text;
        }

        public String getStyle() {
            return style;
        }
    }

    public enum ImageOrientation {
        DEFAULT,
        DEG90,
        DEG180,
        DEG270;

        public static ImageOrientation next(ImageOrientation imageOrientation) {
            switch (imageOrientation) {
                case DEFAULT: return DEG90;
                case DEG90: return DEG180;
                case DEG180: return DEG270;
                case DEG270: return DEFAULT;
                default: return DEFAULT;
            }
        }
    }
}