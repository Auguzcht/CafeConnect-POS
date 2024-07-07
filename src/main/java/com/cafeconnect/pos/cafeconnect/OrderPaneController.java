package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class OrderPaneController {

    @FXML
    private Text SelectedDishName;

    @FXML
    private Text SelectedDishPrice;

    @FXML
    private TextField SelectedDishQty;

    @FXML
    private Button RemoveBtn;

    private OrderFragment parentController; // Reference to parent controller
    private String dishName;
    private double pricePerUnit; // Price per unit of the dish
    private int quantity; // Current quantity of the dish in the order

    // Inject parent controller
    public void setParentController(OrderFragment parentController) {
        this.parentController = parentController;
    }

    // Set dish details
    public void setDishDetails(String dishName, double price, int quantity) {
        if (this.dishName == null) {
            this.dishName = dishName;
            this.pricePerUnit = price / quantity; // Calculate price per unit
            this.quantity = quantity; // Initialize quantity
            SelectedDishName.setText(dishName);
            SelectedDishPrice.setText(String.format("%.2f", price));
            SelectedDishQty.setText(String.valueOf(quantity));

            // Log dish details
            System.out.println("setDishDetails - DishName: " + dishName + ", Price: " + price + ", Quantity: " + quantity);
        } else {
            // Log if dish details are already set
            System.out.println("setDishDetails - Dish already set: " + dishName);
        }
    }

    // Get current quantity of the dish
    public int getQuantity() {
        return quantity;
    }

    // Get dish name
    public String getDishName() {
        return dishName;
    }

    // Update quantity of the dish
    public void updateQuantity(int newQuantity) {
        this.quantity = newQuantity;
        SelectedDishQty.setText(String.valueOf(newQuantity));
        updatePrice();
        if (parentController != null) {
            parentController.updateSubtotal();
        }
    }

    // Update displayed price based on new quantity
    public void updatePrice() {
        double totalPrice = pricePerUnit * quantity;
        SelectedDishPrice.setText(String.format("%.2f", totalPrice));
        if (parentController != null) {
            parentController.updateSubtotal();
        }
    }

    // Overloaded updatePrice method to accept totalPrice directly
    public void updatePrice(double totalPrice) {
        SelectedDishPrice.setText(String.format("%.2f", totalPrice));

        // Log updated price
        System.out.println("updatePrice (overloaded) - DishName: " + dishName + ", TotalPrice: " + totalPrice);
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void updateSelectedQuantity(int quantity) {
        this.quantity = quantity; // Update the internal quantity
        SelectedDishQty.setText(String.valueOf(quantity));
        updatePrice();

        // Log updated selected quantity
        System.out.println("updateSelectedQuantity - DishName: " + dishName + ", Quantity: " + quantity);
    }

    @FXML
    private void handleQuantityChange() {
        try {
            int newQuantity = Integer.parseInt(SelectedDishQty.getText());
            if (newQuantity <= 0) {
                throw new NumberFormatException();
            }
            this.quantity = newQuantity; // Update internal quantity
            updatePrice();
            if (parentController != null) {
                parentController.updateDishQuantity(dishName, newQuantity);
            }

            // Log handle quantity change
            System.out.println("handleQuantityChange - DishName: " + dishName + ", NewQuantity: " + newQuantity);
        } catch (NumberFormatException e) {
            // Handle invalid input
            SelectedDishQty.setText(String.valueOf(quantity)); // Reset to previous valid quantity
            updatePrice(); // Reset price display

            // Log invalid quantity input
            System.out.println("handleQuantityChange - Invalid input, resetting to previous quantity: " + quantity);
        }
    }

    @FXML
    private void RemoveBtnClicked(MouseEvent event) {
        if (parentController != null) {
            parentController.removeDishFromOrder(dishName);

            // Log remove button click
            System.out.println("RemoveBtnClicked - DishName: " + dishName);
        }
    }
}
