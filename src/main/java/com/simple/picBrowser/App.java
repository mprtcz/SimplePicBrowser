package com.simple.picBrowser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Created by Azet on 2015-10-17.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mainMenu1.fxml"));
        System.out.println(loader.getClass());
        Parent root = loader.load();

        Scene scene = new Scene (root, 1200 , 800);

        primaryStage.setTitle("Picture Browser");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
