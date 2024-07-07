package com.cafeconnect.pos.cafeconnect;

public class Dish {
    private String name;
    private double price;
    private int stock;
    private String imagePath;

    public Dish(String name, double price, int stock, String imagePath) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.imagePath = imagePath;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public String getImagePath() {
        return imagePath;
    }

    public String toFileString() {
        return name + "," + price + "," + stock + "," + (imagePath == null ? "" : imagePath);
    }

    public static Dish fromFileString(String fileString) {
        String[] parts = fileString.split(",");

        // Check if the parts array has at least 3 elements (Name, Price, Stock)
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid format: " + fileString);
        }

        String name = parts[0];
        double price = Double.parseDouble(parts[1]);
        int stock = Integer.parseInt(parts[2]);

        // Set imagePath to empty string if not provided
        String imagePath = "";
        if (parts.length > 3) {
            imagePath = parts[3];
        }

        return new Dish(name, price, stock, imagePath);
    }
}
