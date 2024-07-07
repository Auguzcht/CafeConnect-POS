package com.cafeconnect.pos.cafeconnect;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


import java.io.*;
import java.net.URL;
import java.util.*;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderFragment implements Initializable {

    @FXML
    public Pane AddDishFrame;

    @FXML
    public ImageView DishFrameImage;

    @FXML
    public TextField DishNameField;

    @FXML
    public TextField DishPriceField;

    @FXML
    public TextField DishStockField;
    @FXML
    public Button CancelBtn;

    @FXML
    public Button AddBtn;

    @FXML
    private ToggleButton AdminBtn;

    @FXML
    private Text ChangeVal;

    @FXML
    private Button ConfirmBtn;

    @FXML
    private ToggleButton DashboardBtn;

    @FXML
    private Text DateTimeAndSchedule;

    @FXML
    private Button DeleteBtn;

    @FXML
    private ToggleButton DeliveryBtn;

    @FXML
    private ToggleButton DineInBtn;

    @FXML
    private ToggleButton DiscountBtn;

    @FXML
    private Text DiscountVal;

    @FXML
    private ImageView DishCard;

    @FXML
    private Circle DishFrame;

    @FXML
    private TilePane OrderTilePane;

    @FXML
    private Text DishName;

    @FXML
    private Text DishPrice;

    @FXML
    private ScrollPane DishScrollPane;

    @FXML
    private Text DishStock;

    @FXML
    private Circle ImageFrame;

    @FXML
    private Button LogoutBtn;

    @FXML
    private ToggleButton OrderBtn;

    @FXML
    private ImageView OrderHeader;

    @FXML
    private Text OrderNumberVal;

    @FXML
    private Pane OrderPane;

    @FXML
    private ScrollPane OrderScrollPane;

    @FXML
    private ImageView OrderSidebar;

    @FXML
    private ImageView Remove;

    @FXML
    private Button RemoveBtn;

    @FXML
    private ImageView Search;

    @FXML
    private TextField SearchField;

    @FXML
    private Text SearchVal;

    @FXML
    private Text SelectedDishName;

    @FXML
    private Text SelectedDishPrice;

    @FXML
    private TextField SelectedDishQty;

    @FXML
    private ToggleButton SettingsBtn;

    @FXML
    private ImageView SideEdges;

    @FXML
    private ImageView SideEdges1;

    @FXML
    private ImageView SideEdges2;

    @FXML
    private ImageView SideEdges3;

    @FXML
    private ImageView Sidebar;

    @FXML
    private Text StaffName;

    @FXML
    private Text SubtotalVal;

    @FXML
    private TextField TenderedVal;

    @FXML
    private ToggleButton ToGoBtn;

    @FXML
    private Text TotalVal;

    private ToggleButton lastSelectedButton;

    @FXML
    private AnchorPane mainContent;

    @FXML
    private Rectangle contentBGColor;

     @FXML
    private TilePane DishTilePane;

     @FXML
    private ToggleGroup orderToggleGroup;

     @FXML
    private ToggleGroup toggleGroup;

     @FXML
    private Pane DishPane;

     @FXML
     private AnchorPane OrderAnchor;

     @FXML
    private Pane addDishPlaceholder;

    private Parent addDishFrame;

    private int availableStock = 0;
    private double pricePerItem;
    private int currentOrderNumber;
    private String currentOrderType;
    private double discountRate = 0.10;
    private boolean isDiscountApplied = false;

    // To keep track of order panes by dish name
    private final Map<String, Pane> orderPaneMap = new HashMap<>();


     @FXML
     public void initialize(URL location, ResourceBundle resources) {
         System.out.println("Initializing OrderFragment");

         ToggleGroup toggleGroup = new ToggleGroup();

         System.out.println("OrderBtn: " + OrderBtn);
         System.out.println("DashboardBtn: " + DashboardBtn);
         System.out.println("AdminBtn: " + AdminBtn);
         System.out.println("SettingsBtn: " + SettingsBtn);

         OrderBtn.setToggleGroup(toggleGroup);
         DashboardBtn.setToggleGroup(toggleGroup);
         AdminBtn.setToggleGroup(toggleGroup);
         SettingsBtn.setToggleGroup(toggleGroup);

         orderToggleGroup = new ToggleGroup();

         System.out.println("DinInBtn: " + DineInBtn);
         System.out.println("ToGoBtn: " + ToGoBtn);
         System.out.println("DeliveryBtn: " + DeliveryBtn);

         DineInBtn.setToggleGroup(orderToggleGroup);
         ToGoBtn.setToggleGroup(orderToggleGroup);
         DeliveryBtn.setToggleGroup(orderToggleGroup);

         // Set default selected toggle (optional)
         DineInBtn.setSelected(true);
         currentOrderType = "Dine In";

         toggleGroup.selectToggle(OrderBtn);
         lastSelectedButton = OrderBtn;

         loadDishes();

         currentOrderNumber = readOrderNumber();
         updateOrderNumberDisplay();
    }

    @FXML
    void ConfirmBtnClicked(MouseEvent event) {
        // Implement logic for Confirm button click
    }

    @FXML
    void SearchKeyPressed(KeyEvent event) {
        // Implement logic for search field key press
    }

    @FXML
    void SidebarBtnPressed(MouseEvent event) {
    }

    @FXML
    void toggleButtonClicked(MouseEvent event) {
        ToggleButton clickedButton = (ToggleButton) event.getSource();

        try {
            if (clickedButton.isSelected()) {
                if (lastSelectedButton != null && clickedButton != lastSelectedButton) {
                    lastSelectedButton.setSelected(false);
                }
                lastSelectedButton = clickedButton;

                if (clickedButton == OrderBtn) {
                    mainContent.setVisible(false); // Hide mainContent for OrderBtn
                } else {
                    mainContent.setVisible(true); // Show mainContent for other buttons

                    // Load corresponding fragment based on the clicked button
                    if (clickedButton == DashboardBtn) {
                        loadFragment("DashboardFragment.fxml");
                    } else if (clickedButton == AdminBtn) {
                        loadFragment("AdminFragment.fxml");
                    } else if (clickedButton == SettingsBtn) {
                        loadFragment("SettingsFragment.fxml");
                    } else {
                    }
                }
            } else {
                // Prevent button from being unselected
                clickedButton.setSelected(true);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void SidebarBtnReleased(MouseEvent event) {
        // Implement logic for sidebar button release if needed
    }

    @FXML
    void TogglePressed(MouseEvent event) {
        // Implement logic for toggle button press if needed
    }

    @FXML
    void ToggleReleased(MouseEvent event) {
        // Implement logic for toggle button release if needed
    }

    private void initializeDateTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            LocalDateTime now = LocalDateTime.now();
            String formattedDateTime = now.format(formatter);
            DateTimeAndSchedule.setText(formattedDateTime + " | Schedule");
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateDateTimeAndSchedule(String dateTime) {
        String schedule = loadSchedule();
        DateTimeAndSchedule.setText(dateTime + ", " + schedule);
    }

    public void setStaffName(String staffName) {
        StaffName.setText(staffName);
    }

    public void setDateTimeAndSchedule(String schedule) {
        updateDateTimeAndSchedule(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")) + ", " + schedule);
    }

    private String loadSchedule() {
        // Implement logic to load schedule from a file
        try (BufferedReader br = new BufferedReader(new FileReader("credentials.txt"))) {
            StringBuilder scheduleBuilder = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                scheduleBuilder.append(line).append(" ");
            }
            return scheduleBuilder.toString().trim();
        } catch (IOException e) {
            System.err.println("Error reading schedule.txt: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private void loadFragment(String fxmlFile) throws IOException {
        String resourcePath = "/com/cafeconnect/pos/cafeconnect/view/" + fxmlFile;
        URL resourceUrl = getClass().getResource(resourcePath);

        if (resourceUrl == null) {
            System.err.println("FXML file not found: " + resourcePath);
            throw new IOException("FXML file not found: " + resourcePath);
        }

        FXMLLoader loader = new FXMLLoader(resourceUrl);
        Node fragment = loader.load();

        // Clear existing content in mainContent
        mainContent.getChildren().clear();

        // Add the background rectangle and the loaded fragment to mainContent
        mainContent.getChildren().add(contentBGColor);
        mainContent.getChildren().add(fragment);
    }

    private void loadDishes() {
        try (BufferedReader br = new BufferedReader(new FileReader("dishes.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    String dishName = parts[0];
                    double price = Double.parseDouble(parts[1]);
                    int quantity = Integer.parseInt(parts[2]);

                    // Load DishPane FXML and set properties
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cafeconnect/pos/cafeconnect/view/DishPane.fxml"));
                    Pane dishPane = loader.load();
                    DishPaneController controller = loader.getController(); // Get the DishPaneController instance
                    controller.setParentController(this); // Pass reference to parent controller

                    // Access components directly in DishPane.fxml
                    Text dishNameText = (Text) dishPane.lookup("#DishName");
                    Text priceText = (Text) dishPane.lookup("#DishPrice");
                    Text stockText = (Text) dishPane.lookup("#DishStock");

                    // Set values directly
                    dishNameText.setText(dishName);
                    priceText.setText(String.format("%.2f", price));
                    stockText.setText(quantity + " Available");

                    // Add DishPane to DishGridPane (TilePane)
                    DishTilePane.getChildren().add(dishPane);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void updateSelectedDish(String dishName, double price, int quantity, int availableStock) {
        // Iterate through orderPaneMap to find the correct OrderPaneController
        for (Pane orderPane : orderPaneMap.values()) {
            OrderPaneController controller = (OrderPaneController) orderPane.getUserData();
            if (controller != null && controller.getDishName().equals(dishName)) {
                controller.setDishDetails(dishName, price, quantity); // Update details
                break;
            }
        }
    }

    @FXML
    private void handleQuantityChange() {
        try {
            int enteredQty = Integer.parseInt(SelectedDishQty.getText());
            if (enteredQty > availableStock) {
                SelectedDishQty.setText(String.valueOf(availableStock)); // Limit quantity to available stock
            } else if (enteredQty < 1) {
                SelectedDishQty.setText("1"); // Ensure quantity is at least 1
            }
        } catch (NumberFormatException e) {
            // Handle non-numeric input if needed
            SelectedDishQty.setText("1"); // Reset to 1 if invalid input
        }
        updatePrice(); // Update price whenever quantity changes
    }

    public void updateSelectedQuantity(String dishName, int quantity) {
        // Iterate through orderPaneMap to find the correct OrderPaneController
        for (Pane orderPane : orderPaneMap.values()) {
            OrderPaneController controller = (OrderPaneController) orderPane.getUserData();
            if (controller != null && controller.getDishName().equals(dishName)) {
                controller.updateQuantity(quantity);
                break;
            }
        }
    }

    private void updatePrice() {
        try {
            int quantity = Integer.parseInt(SelectedDishQty.getText());
            double totalPrice = pricePerItem * quantity; // Use pricePerItem for total price calculation
            SelectedDishPrice.setText(String.format("%.2f", totalPrice));
        } catch (NumberFormatException e) {
            // Handle parsing errors if necessary
            SelectedDishPrice.setText(String.format("%.2f", pricePerItem)); // Reset to price per item if there's an error
        }
    }


    //Order number methods
    private int readOrderNumber() {
        try (BufferedReader reader = new BufferedReader(new FileReader("order_number.txt"))) {
            String line = reader.readLine();
            if (line != null) {
                return Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return 1; // Start from 1 if file doesn't exist or is empty
    }

    private void writeOrderNumber(int orderNumber) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("order_number.txt"))) {
            writer.write(String.valueOf(orderNumber));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateOrderNumberDisplay() {
        OrderNumberVal.setText(String.format("Order #%05d", currentOrderNumber));
    }

    public void newTransaction() {
         System.out.println("Processing order with type: " + currentOrderType);
        currentOrderNumber++;
        writeOrderNumber(currentOrderNumber);
        updateOrderNumberDisplay();

        //Clear current order
        OrderTilePane.getChildren().clear();
        orderPaneMap.clear();
    }

    @FXML
    private void handleCompleteOrder(MouseEvent event) {
        // Get the current order number
        String OrderNumber = String.valueOf(currentOrderNumber);
        System.out.println("OrderNumber: " + OrderNumber);

        // Get all selected dishes
        StringBuilder orderItems = new StringBuilder();
        for (Pane orderPane : orderPaneMap.values()) {
            OrderPaneController controller = (OrderPaneController) orderPane.getUserData();
            String dishName = controller.getDishName();
            int quantity = controller.getQuantity();
            double price = controller.getPricePerUnit() * quantity;

            // Debugging statements
            System.out.println("DishName: " + dishName);
            System.out.println("Quantity: " + quantity);
            System.out.println("Price: " + price);

            // Append dish details to orderItems string
            orderItems.append(dishName).append(" ").append(quantity).append(" ").append(price).append(";");
        }

        // Get the total payment from totalVal, remove Peso sign if present
        String totalValStr = TotalVal.getText().replace("₱", "");
        double totalPayment = Double.parseDouble(totalValStr);
        System.out.println("Total Payment: " + totalPayment);

        // Get the current date/time
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String orderDateTime = now.format(formatter);
        System.out.println("Order DateTime: " + orderDateTime);

        // Get the current order type
        String OrderType = currentOrderType;
        System.out.println("OrderType: " + OrderType);

        // Prepare the order details string
        String orderDetails = String.format("%s,%s,%.2f,%s,%s", OrderNumber, orderItems.toString(), totalPayment, orderDateTime, OrderType);
        System.out.println("OrderDetails: " + orderDetails);

        // Write order details to a file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("order_transactions.txt", true))) {
            writer.write(orderDetails);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
            // Handle file writing errors
        }

        // Reset for a new transaction
        newTransaction();
    }


    //Order type handler
    @FXML
    private void handleToggleClick(MouseEvent event) {
        ToggleButton selectedButton = (ToggleButton) orderToggleGroup.getSelectedToggle();
        if (selectedButton != null) {
            currentOrderType = selectedButton.getText();
            System.out.println("Order Type: " + currentOrderType); // For testing
        }
    }

    //Order pane methods
    public void addDishToOrder(String dishName, double price, int quantity) {
        if (orderPaneMap.containsKey(dishName)) {
            // Update existing order pane
            Pane orderPane = orderPaneMap.get(dishName);
            OrderPaneController controller = (OrderPaneController) orderPane.getUserData();
            int newQty = controller.getQuantity() + quantity;
            System.out.println("addDishToOrder - Updating existing dish: " + dishName + ", New Quantity: " + newQty);
            controller.updateQuantity(newQty);
            double pricePerUnit = controller.getPricePerUnit();
            controller.updatePrice(pricePerUnit * newQty);
        } else {
            // Create new order pane
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/cafeconnect/pos/cafeconnect/view/OrderPane.fxml"));
                Pane orderPane = loader.load();
                OrderPaneController controller = loader.getController();
                controller.setParentController(this);
                controller.setDishDetails(dishName, price, quantity);

                orderPane.setUserData(controller); // Store controller reference in user data

                OrderTilePane.getChildren().add(orderPane);
                orderPaneMap.put(dishName, orderPane);

                // Log new dish addition
                System.out.println("addDishToOrder - Adding new dish: " + dishName + ", Price: " + price + ", Quantity: " + quantity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void incrementDishQuantity(String dishName, double price) {
        Pane orderPane = orderPaneMap.get(dishName);
        if (orderPane != null) {
            OrderPaneController controller = (OrderPaneController) orderPane.getUserData();
            if (controller != null) {
                int newQuantity = controller.getQuantity() + 1;
                System.out.println("incrementDishQuantity - DishName: " + dishName + ", NewQuantity: " + newQuantity);
                controller.updateQuantity(newQuantity);
                double pricePerUnit = controller.getPricePerUnit();
                controller.updatePrice(pricePerUnit * newQuantity);
            }
        } else {
            // Log if the dish is not found in the order
            System.out.println("incrementDishQuantity - DishName not found in order, adding new: " + dishName);
            // Add the dish to the order if it doesn't exist yet
            addDishToOrder(dishName, price, 1);
        }
    }

    public void updateDishQuantity(String dishName, int newQty) {
        Pane orderPane = orderPaneMap.get(dishName);
        if (orderPane != null) {
            OrderPaneController controller = (OrderPaneController) orderPane.getUserData();
            if (controller != null) {
                controller.updateQuantity(newQty);
                double pricePerUnit = controller.getPricePerUnit();
                controller.updatePrice(pricePerUnit * newQty);
            }
        }
    }

    public void removeDishFromOrder(String dishName) {
        if (orderPaneMap.containsKey(dishName)) {
            Pane orderPane = orderPaneMap.remove(dishName);
            OrderTilePane.getChildren().remove(orderPane);
        }
    }

    public void updateSubtotal() {
        double subtotal = orderPaneMap.values().stream()
                .mapToDouble(pane -> {
                    OrderPaneController controller = (OrderPaneController) pane.getUserData();
                    return controller.getPricePerUnit() * controller.getQuantity();
                })
                .sum();

        SubtotalVal.setText(String.format("₱%.2f", subtotal));
        updateTotal();
    }

    public void updateTotal() {
        double subtotal = Double.parseDouble(SubtotalVal.getText().replace("₱", ""));
        double discount = isDiscountApplied ? subtotal * discountRate : 0.0;
        double total = subtotal - discount;

        DiscountVal.setText(String.format("₱%.2f", discount));
        TotalVal.setText(String.format("₱%.2f", total));
    }

    public void updateChange() {
        double total = Double.parseDouble(TotalVal.getText().replace("₱", ""));
        double tendered = Double.parseDouble(TenderedVal.getText().replace("₱", ""));
        double change = tendered - total;

        if (change < 0) {
            ChangeVal.setText("Insufficient Amount");
        } else {
            ChangeVal.setText(String.format("₱%.2f", change));
        }
    }

    @FXML
    private void DiscountBtnClicked(MouseEvent event) {
        isDiscountApplied = !isDiscountApplied;
        updateTotal();
    }

    @FXML
    private void handleTenderedValueChange() {
        try {
            double tendered = Double.parseDouble(TenderedVal.getText().replace("₱", ""));
            updateChange();
        } catch (NumberFormatException e) {
            // Handle invalid input
            TenderedVal.setText("₱00.00");
            updateChange();
        }
    }

    @FXML
    private void DeleteBtnClicked() {
    }

    public void cancelBtnClicked(MouseEvent event) {

    }

    public void addBtnClicked(MouseEvent event) {
    }

    public void DishFrameClicked(MouseEvent event) {
    }
}

