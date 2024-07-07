package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.io.*;


public class SettingsFragment {

    @FXML
    public TextField StaffNameField;
    @FXML
    private AnchorPane AccountAnchor;

    @FXML
    private ToggleButton AccountBtn;

    @FXML
    private ImageView AccountImage;

    @FXML
    private ToggleButton CancelEmailBtn;

    @FXML
    private ToggleButton CancelNameBtn;

    @FXML
    private ToggleButton EditEmailBtn;

    @FXML
    private Button EditNameBtn;

    @FXML
    private Pane EmailEditPane;

    @FXML
    private TextField EmailField;

    @FXML
    private TextField HourField;

    @FXML
    private Circle ImageFrame;

    @FXML
    private AnchorPane InformationAnchor;

    @FXML
    private ToggleButton InformationBtn;

    @FXML
    private ImageView InformationImage;

    @FXML
    private AnchorPane IntegrationAnchor;

    @FXML
    private ToggleButton IntegrationBtn;

    @FXML
    private ImageView IntegrationImage;

    @FXML
    private Text LoginInfo;

    @FXML
    private TextField MinuteField;

    @FXML
    private ToggleButton SetTimeBtn;

    @FXML
    private ImageView Sidebar1;

    @FXML
    private Text StaffName;

    @FXML
    private Text StaffName1;

    @FXML
    private Pane StaffNameEditPane;

    @FXML
    private Button UpdateLoginBtn;

    @FXML
    private ToggleGroup SettingsSidebarToggle;

    @FXML
    private Pane UpdateLoginFrame;

    @FXML
    private TextField CurrentPasswordField;

    @FXML
    private TextField NewPasswordField;

    @FXML
    private TextField NewPasswordField1;

    @FXML
    private ToggleButton ConfirmLoginBtn;

    @FXML
    private ToggleButton CancelLoginBtn;

    private boolean isEditing = false;

    @FXML
    private void initialize() {
        // Ensure only one toggle button can be selected at a time
        SettingsSidebarToggle = new ToggleGroup();
        AccountBtn.setToggleGroup(SettingsSidebarToggle);
        InformationBtn.setToggleGroup(SettingsSidebarToggle);
        IntegrationBtn.setToggleGroup(SettingsSidebarToggle);

        // Set default selected button
        AccountBtn.setSelected(true);

        addPersistentToggleBehavior(AccountBtn, SettingsSidebarToggle);
        addPersistentToggleBehavior(InformationBtn, SettingsSidebarToggle);
        addPersistentToggleBehavior(IntegrationBtn, SettingsSidebarToggle);

        loadStaffName();
    }

    @FXML
    private void SettingsSidebarClicked() {
        ToggleButton selectedButton = (ToggleButton) SettingsSidebarToggle.getSelectedToggle();

        if (selectedButton != null) {
            if (selectedButton.equals(AccountBtn)) {
                showAccountSettings();
            } else if (selectedButton.equals(InformationBtn)) {
                showInformationSettings();
            } else if (selectedButton.equals(IntegrationBtn)) {
                showIntegrationSettings();
            }
        }
    }

    private void addPersistentToggleBehavior(ToggleButton button, ToggleGroup group) {
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (button.isSelected() && group.getSelectedToggle() == button) {
                event.consume();
            }
        });
    }

    private void showAccountSettings() {
        AccountAnchor.setVisible(true);
        InformationAnchor.setVisible(false);
        IntegrationAnchor.setVisible(false);
    }

    private void showInformationSettings() {
        AccountAnchor.setVisible(false);
        InformationAnchor.setVisible(true);
        IntegrationAnchor.setVisible(false);
    }

    private void showIntegrationSettings() {
        AccountAnchor.setVisible(false);
        InformationAnchor.setVisible(false);
        IntegrationAnchor.setVisible(true);
    }

    @FXML
    private void EditAccountBtnClick(MouseEvent event) {
        System.out.println("EditAccountBtnClick: isEditing = " + isEditing);
        if (isEditing) {
            // Save changes
            saveStaffName();
            StaffNameField.setEditable(false);
            EditNameBtn.setText("Edit");
        } else {
            // Enable editing
            StaffNameField.setEditable(true);
            EditNameBtn.setText("Save");
        }
        isEditing = !isEditing;
    }

    private void loadStaffName() {
        System.out.println("Loading staff name...");
        try (BufferedReader br = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("Read line: " + line);
                String[] credentials = line.split(",");
                if (credentials.length >= 4) {
                    String staffName = credentials[2]; // Extract staff name
                    StaffNameField.setText(staffName);
                    System.out.println("Loaded staff name: " + staffName);
                    break; // Assuming only one relevant line; remove if there are multiple relevant lines
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void saveStaffName() {
        String newStaffName = StaffNameField.getText();
        StringBuilder updatedContent = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader("credentials.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] credentials = line.split(",", 4); // Limit split to 4 parts

                if (credentials.length >= 4) {
                    String accountType = credentials[0].trim(); // Assuming account type is the first field
                    String username = credentials[1].trim(); // Assuming username is the second field

                    // Check if it's an admin account
                    if ("admin".equals(accountType)) {
                        credentials[2] = newStaffName;  // Update the staff name
                    }

                    // Reconstruct the line with updated content
                    updatedContent.append(credentials[0]).append(",")
                                   .append(credentials[1]).append(",")
                                   .append(credentials[2]).append(",")
                                   .append(credentials[3]).append("\n");
                } else {
                    updatedContent.append(line).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated content back to the file
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("credentials.txt"))) {
            bw.write(updatedContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void EditNameBtnClick(MouseEvent event) {

    }

    @FXML
    void SearchKeyPressed(KeyEvent event) {

    }

    @FXML
    void SidebarSettingsBtnPressed(MouseEvent event) {

    }

    @FXML
    void SidebarSettingsBtnReleased(MouseEvent event) {

    }

    @FXML
    private void UpdateAccountBtnClick(MouseEvent event) {
        UpdateLoginFrame.setVisible(true); // Show the UpdateLoginFrame pane
    }

    @FXML
    private void ConfirmLoginBtnClick(MouseEvent event) {
        String currentPassword = CurrentPasswordField.getText();
        String newPassword = NewPasswordField.getText();
        String confirmPassword = NewPasswordField1.getText();

        // Validate if new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            // Show error message or handle mismatch case
            System.out.println("New password and confirm password do not match.");
            return;
        }

        // Update the admin account's password in credentials.txt
        updateAdminPassword(currentPassword, newPassword);
    }

    @FXML
    private void CancelLoginBtnClick(MouseEvent event) {
        UpdateLoginFrame.setVisible(false); // Hide the UpdateLoginFrame pane
        // Optionally clear text fields or reset UI state
        CurrentPasswordField.clear();
        NewPasswordField.clear();
        NewPasswordField1.clear();
    }

    private void updateAdminPassword(String currentPassword, String newPassword) {
        try {
            File file = new File("credentials.txt");
            File tempFile = new File("temp_credentials.txt");

            BufferedReader reader = new BufferedReader(new FileReader(file));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String line;
            boolean adminFound = false;

            while ((line = reader.readLine()) != null) {
                String[] credentials = line.split(",", -1); // Split line by comma, -1 to handle trailing empty strings

                if (credentials.length >= 4) {
                    String accountName = credentials[0].trim(); // Account name
                    String password = credentials[1].trim();    // Password
                    String staffName = credentials[2].trim();   // Staff Name
                    String schedule = credentials[3].trim();    // Schedule or other data

                    // Check and update the password
                    if ("admin".equals(accountName)) {
                        if (currentPassword.equals(password)) {
                            password = newPassword; // Update password
                            adminFound = true;
                        } else {
                            // Current password does not match
                            System.out.println("Current password does not match.");
                        }
                    }

                    // Reconstruct the line with updated password or as is
                    String updatedLine = accountName + "," + password + "," + staffName + "," + schedule;
                    writer.write(updatedLine);
                    writer.newLine();
                } else {
                    // If credentials format is incorrect, log an error or handle accordingly
                    System.out.println("Invalid credentials format.");
                }
            }

            reader.close();
            writer.close();

            // Delete original file and rename temp file to original file
            if (adminFound) {
                file.delete();
                tempFile.renameTo(file);
                System.out.println("Password updated successfully.");
            } else {
                System.out.println("Admin account not found in credentials file.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        UpdateLoginFrame.setVisible(false);
    }



    public void SetTimeBtnClick(MouseEvent event) {
    }

    public void EditEmailBtnClick(MouseEvent event) {
    }
}
