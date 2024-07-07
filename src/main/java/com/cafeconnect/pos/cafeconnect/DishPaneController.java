package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class DishPaneController implements Initializable {

    @FXML
    private Pane DishPane; // Example pane from DishPane.fxml

    private OrderFragment parentController; // Reference to parent controller

    // Inject parent controller
    public void setParentController(OrderFragment parentController) {
        this.parentController = parentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialization code here
    }

    @FXML
    private void DishPaneClicked(MouseEvent event) {
        // Access the details of the clicked dish pane
        Text dishNameText = (Text) DishPane.lookup("#DishName");
        Text priceText = (Text) DishPane.lookup("#DishPrice");
        Text stockText = (Text) DishPane.lookup("#DishStock");

        // Retrieve dish details
        String dishName = dishNameText.getText();
        double price = Double.parseDouble(priceText.getText());
        int availableStock = Integer.parseInt(stockText.getText().split(" ")[0]); // Set available stock

        // Log the dish details
        System.out.println("DishPaneClicked - DishName: " + dishName + ", Price: " + price + ", AvailableStock: " + availableStock);

        // Notify the parent controller (OrderFragment) to update selected dish details
        if (parentController != null) {
            parentController.incrementDishQuantity(dishName, price);

            // Update UI immediately after incrementing quantity
            parentController.updateSubtotal(); // Ensure this method updates your subtotal, total, etc.
        }
    }
}
