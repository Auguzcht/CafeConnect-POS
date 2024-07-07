package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Color;

import javafx.scene.Parent;

public class AdminFragment {

    @FXML
    private ImageView AddProductImage;

    @FXML
    private Pane AddProductPane;

    @FXML
    private AnchorPane AdminAnchor;

    @FXML
    private ToggleButton CloserBtn;

    @FXML
    private ToggleButton DateBtn;

    @FXML
    private ScrollPane DateScrollPane;

    @FXML
    private ImageView DateSidebar;

    @FXML
    private ImageView DateSidebar1;

    @FXML
    private TilePane DateTilePane;

    @FXML
    private Circle DishFrame;

    @FXML
    private Text DishName;

    @FXML
    private Text DishPrice;

    @FXML
    private Text DishStock;

    @FXML
    private Button EmailStaffBtn;

    @FXML
    private ImageView EmailStaffImage;

    @FXML
    private Pane ExistingProductPane;

    @FXML
    private Circle ImageFrame;

    @FXML
    private ToggleButton OpenerBtn;

    @FXML
    private AnchorPane ProductAnchor;

    @FXML
    private ImageView ProductImage;

    @FXML
    private ToggleButton ProductManagementBtn;

    @FXML
    private ImageView ProductManagementImage;

    @FXML
    private ScrollPane ProductScrollPane;

    @FXML
    private Text ProductText1;

    @FXML
    private TilePane ProductTilePane;

    @FXML
    private ImageView Sidebar1;

    @FXML
    private Text StaffName;

    @FXML
    private Pane StaffPane;

    @FXML
    private AnchorPane StaffRecordAnchor;

    @FXML
    private ToggleButton StaffRecordBtn;

    @FXML
    private ImageView StaffRecordImage;

    @FXML
    private ScrollPane StaffScrollPane;

    @FXML
    private TilePane StaffTilePane;

    @FXML
    private Text TimeInText;

    @FXML
    private Text TimeInVal;

    @FXML
    private Text TimeOutText;

    @FXML
    private Text TimeOutVal;

    @FXML
    private Text TotalWorkTime;

    @FXML
    private ToggleGroup dateToggle;

    @FXML
    private ToggleGroup scheduleToggle;

    @FXML
    private ToggleGroup sidebarAdminToggle;

    @FXML
    private AnchorPane ProductPane;

    private ProductPaneController productPaneController;

    private OrderFragment orderFragmentController;

    @FXML
    private Pane AddDishFrame;

    @FXML
    public TextField DishNameField, DishPriceField, DishStockField;

    @FXML
    private Button AddBtn, CancelBtn;

    private Dish selectedDish;
    private List<Dish> dishes;

    private Dish currentDish = null;
    private static final String DISHES_FILE_PATH = "dishes.txt";
    private static final String PLACEHOLDER_IMAGE_PATH = "/path/to/placeholder.png";

    @FXML
    private void initialize() {
        // Ensure ProductAnchor is visible by default
        StaffRecordAnchor.setVisible(false);
        ProductManagementBtn.setSelected(true);

        // ToggleGroup listener to switch between Product and Staff views
        ToggleGroup sidebarAdminToggle = new ToggleGroup();
        ProductManagementBtn.setToggleGroup(sidebarAdminToggle);
        StaffRecordBtn.setToggleGroup(sidebarAdminToggle);

        // Add listeners to the toggle buttons to ensure they stay selected when clicked
        addPersistentToggleBehavior(ProductManagementBtn, sidebarAdminToggle);
        addPersistentToggleBehavior(StaffRecordBtn, sidebarAdminToggle);

        sidebarAdminToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == ProductManagementBtn) {
                ProductAnchor.setVisible(true);
                StaffRecordAnchor.setVisible(false);
            } else if (newValue == StaffRecordBtn) {
                ProductAnchor.setVisible(false);
                StaffRecordAnchor.setVisible(true);
            }
        });

        loadProductPane();

        dishes = loadDishes();
    }

    private void addPersistentToggleBehavior(ToggleButton button, ToggleGroup group) {
        button.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
            if (button.isSelected() && group.getSelectedToggle() == button) {
                event.consume();
            }
        });
    }

    private void loadProductPane() {
        try (BufferedReader br = new BufferedReader(new FileReader("dishes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String dishName = parts[0].trim();
                    double price = Double.parseDouble(parts[1].trim());
                    int quantity = Integer.parseInt(parts[2].trim());

                    // Debug print statements
                    System.out.println("Dish: " + dishName + ", Price: " + price + ", Quantity: " + quantity);

                    // Load ExistingProductPane FXML and set properties
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cafeconnect/pos/cafeconnect/view/ProductPane.fxml"));
                    Pane existingProductPane = loader.load();
                    ProductPaneController controller = loader.getController();
                    controller.setParentController(this);

                    // Create a new Dish instance
                    Dish dish = new Dish(dishName, price, quantity, null); // null for image path, update as needed

                    // Set values directly in ProductPaneController
                    controller.setDish(dish);
                    controller.setDishName(dishName);
                    controller.setPrice(price);
                    controller.setQuantity(quantity);

                    // Add ProductPane to ProductTilePane
                    ProductTilePane.getChildren().add(existingProductPane);

                    System.out.println("Adding ExistingProductPane for: " + dishName);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Example method to reload existing products from ProductPaneController
    public void reloadProducts() {
        // Remove existing ProductPanes, excluding AddProductPane if present
        List<Node> toRemove = new ArrayList<>();
        for (Node node : ProductTilePane.getChildren()) {
            if (node instanceof Pane) {
                ProductPaneController controller = (ProductPaneController) node.getProperties().get("controller");
                if (controller != null && node != AddProductPane) {
                    toRemove.add(node);
                }
            }
        }

        ProductTilePane.getChildren().removeAll(toRemove);

        // Load dishes from dishes.txt
        loadProductPane();
    }

    @FXML
    void DishFrameClicked(MouseEvent event) {
        // Handle add new dish click event
    }

    @FXML
    void AdminBtnClicked(MouseEvent event) {
        // Handle admin button click event
    }

    @FXML
    void handleRemoveLastDishClicked(MouseEvent event) {
        // Implement logic to remove the last dish from dishes.txt
        removeLastDishFromTextFile();
        
        // Optionally, update UI or provide feedback
        System.out.println("Remove Last Dish clicked");
    }

    private void removeLastDishFromTextFile() {
        try {
            // Open the dishes.txt file for reading and writing
            File file = new File("dishes.txt");
            List<String> lines = Files.readAllLines(file.toPath());

            // Remove the last line (last dish)
            if (!lines.isEmpty()) {
                lines.remove(lines.size() - 1);

                // Write the updated lines back to dishes.txt
                Files.write(file.toPath(), lines, StandardOpenOption.TRUNCATE_EXISTING);
            }

            // Optionally, update UI to reflect the removal

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void ProductBtnClicked(MouseEvent event) {
        // Handle product button click event
    }

    @FXML
    void StaffDateBtnClicked(MouseEvent event) {
        // Handle staff date button click event
    }

    @FXML
    void StaffSchedBtnClicked(MouseEvent event) {
        // Handle staff schedule button click event
    }

    @FXML
    public void AddNewDishClicked(MouseEvent event) {
        AddDishFrame.setVisible(true);
    }

    @FXML
    private void addBtnClicked() {
        String name = DishNameField.getText();
        double price = Double.parseDouble(DishPriceField.getText());
        int stock = Integer.parseInt(DishStockField.getText());
        String imagePath = currentDish == null ? "" : currentDish.getImagePath();

        Dish dish = new Dish(name, price, stock, imagePath);

        if (currentDish != null) {
            updateDish(dish);
        } else {
            addNewDish(dish);
        }

        reloadProducts();

        clearAddDishFrame();
        AddDishFrame.setVisible(false);
    }

    @FXML
    private void cancelBtnClicked() {
        clearAddDishFrame();
        AddDishFrame.setVisible(false);
    }

    private void clearAddDishFrame() {
        DishNameField.clear();
        DishPriceField.clear();
        DishStockField.clear();
        DishFrame.setFill(Color.WHITE);
        currentDish = null;
    }

    private void populateAddDishFrame(Dish dish) {
        DishNameField.setText(dish.getName());
        DishPriceField.setText(String.valueOf(dish.getPrice()));
        DishStockField.setText(String.valueOf(dish.getStock()));
        if (dish.getImagePath() == null || dish.getImagePath().isEmpty()) {
            DishFrame.setFill(Color.WHITE); // No image, show default circle
        } else {
            DishFrame.setFill(new ImagePattern(new Image(dish.getImagePath())));
        }
        currentDish = dish;
    }

    private void addNewDish(Dish dish) {
        try (FileWriter writer = new FileWriter(DISHES_FILE_PATH, true)) {
            writer.write(dish.toFileString() + System.lineSeparator());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateDish(Dish dish) {
        List<Dish> dishes = loadDishes();
        try (FileWriter writer = new FileWriter(DISHES_FILE_PATH)) {
            for (Dish d : dishes) {
                if (d.getName().equals(currentDish.getName())) {
                    writer.write(dish.toFileString() + System.lineSeparator());
                } else {
                    writer.write(d.toFileString() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Dish> loadDishes() {
        List<Dish> dishes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(DISHES_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                dishes.add(Dish.fromFileString(line));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dishes;
    }

    public void handleExistingDishClick(Dish dish) {
        if (dish != null) {
            System.out.println("Handling click for dish: " + dish.getName());
            populateAddDishFrame(dish);
            if (AddDishFrame != null) {
                AddDishFrame.setVisible(true);
            } else {
                System.out.println("AddDishFrame is null.");
            }
        } else {
            System.out.println("Dish is null.");
        }
    }
}