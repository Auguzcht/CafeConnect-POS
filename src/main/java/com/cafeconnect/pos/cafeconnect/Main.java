package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    private OrderFragment orderFragmentController;
    private AdminFragment adminFragmentController;

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cafeconnect/pos/cafeconnect/view/LoginFragment.fxml"));
            Parent root = loader.load();

            // Create the scene
            Scene scene = new Scene(root);

            // Load the CSS stylesheet
            scene.getStylesheets().add(getClass().getResource("/com/cafeconnect/pos/cafeconnect/view/styles.css").toExternalForm());

            // Set the primary stage
            primaryStage.setTitle("CafeConnect");
            primaryStage.setFullScreen(true);
            primaryStage.setScene(scene);
            primaryStage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
