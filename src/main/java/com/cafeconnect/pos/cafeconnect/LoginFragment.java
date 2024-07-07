package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;

public class LoginFragment {

    @FXML
    private ImageView OrDivider;
    @FXML
    private ImageView AccountExistError;
    @FXML
    private ImageView IncorrectUserPassError;
    @FXML
    private ImageView EmptyFieldError;
    @FXML
    private ImageView NoAccountFoundError;

    @FXML
    private TextField UsernameField;
    @FXML
    private TextField PasswordField;

    @FXML
    private Text StaffName;

    @FXML
    private Text DateTimeAndSchedule;

    @FXML
    private Button SignInBtn;
    @FXML
    private Button CreateBtn;

    private final AuthenticationHandler authHandler = new AuthenticationHandler();

    @FXML
    private void initialize() {
        // Initialize or configure any components if needed
        hideErrorImages(); // Initially hide all error images
    }

    @FXML
    private void SignInBtnClicked() {
        hideErrorImages();

        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            EmptyFieldError.setVisible(true);
            return;
        }

        if (authHandler.authenticate(username, password)) {
            navigateToOrderFragment(username);
        } else {
            IncorrectUserPassError.setVisible(true);
        }
    }

    @FXML
    private void CreateBtnClicked() {
        hideErrorImages();

        String username = UsernameField.getText();
        String password = PasswordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            EmptyFieldError.setVisible(true);
            return;
        }

        if (authHandler.checkAccountExists(username)) {
            AccountExistError.setVisible(true);
            return;
        }

        // For simplicity, using dummy staff name and schedule for account creation
        String staffName = "Staff";
        String schedule = "Schedule";
        boolean accountCreated = authHandler.createAccount(username, password, staffName, schedule);

        if (accountCreated) {
            navigateToOrderFragment(username);
        } else {
            System.err.println("Failed to create account.");
        }
    }

    private void hideErrorImages() {
        // Hide all error images
        AccountExistError.setVisible(false);
        IncorrectUserPassError.setVisible(false);
        EmptyFieldError.setVisible(false);
        NoAccountFoundError.setVisible(false);
    }

    private void navigateToOrderFragment(String username) {
        try {
             System.out.println("Navigating to OrderFragment for user: " + username);
            // Load OrderFragment.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cafeconnect/pos/cafeconnect/view/OrderFragment.fxml"));
            Parent root = loader.load();

            // Get the controller for OrderFragment
            OrderFragment orderFragment = loader.getController();

            // Set the staff name and schedule in the OrderFragment
            AuthenticationHandler.User user = authHandler.getUser(username);
            if (user != null) {
                orderFragment.setStaffName(user.getStaffName());
                orderFragment.setDateTimeAndSchedule(user.getSchedule());
            }

            // Get the current stage and set the scene
            Stage stage = (Stage) SignInBtn.getScene().getWindow();
            Scene scene = new Scene(root);

            // Set scene and show stage
            stage.setTitle("Order Fragment");
            stage.setScene(scene);

            // Load the CSS stylesheet
            scene.getStylesheets().add(getClass().getResource("/com/cafeconnect/pos/cafeconnect/view/styles.css").toExternalForm());

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}