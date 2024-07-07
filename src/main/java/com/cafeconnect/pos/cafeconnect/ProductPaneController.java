package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;

public class ProductPaneController {

    @FXML
    private Text DishName;

    @FXML
    private Text DishPrice;

    @FXML
    private Text DishStock;

    private AdminFragment parentController; // Reference to parent controller

    private Dish dish; // Current dish instance

    // Method to set the parent controller (AdminFragment)
    public void setParentController(AdminFragment parentController) {
        this.parentController = parentController;
        System.out.println("Parent controller set."); // Debug statement
    }

    public void setDishName(String name) {
        if (DishName != null) {
            DishName.setText(name);
            System.out.println("Dish name set to: " + name); // Debug statement
        } else {
            System.out.println("DishName Text is null.");
        }
    }

    public void setPrice(double price) {
        if (DishPrice != null) {
            DishPrice.setText(String.format("%.2f ₱", price));
            System.out.println("Dish price set to: " + price); // Debug statement
        } else {
            System.out.println("DishPrice Text is null.");
        }
    }

    public void setQuantity(int quantity) {
        if (DishStock != null) {
            DishStock.setText(quantity + " Available");
            System.out.println("Dish quantity set to: " + quantity); // Debug statement
        } else {
            System.out.println("DishStock Text is null.");
        }
    }

    @FXML
    void EditDishClick(MouseEvent event) {
        if (parentController != null && dish != null) {
            System.out.println("Clicked on Dish: " + dish.getName()); // Debug statement
            parentController.handleExistingDishClick(dish);
        } else {
            System.out.println("Parent controller or dish is null.");
        }
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }
}
