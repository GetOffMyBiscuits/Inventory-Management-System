package com.example.myjavafxproject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** <b>This class creates the Product objects as well as our getters and setters.</b> */
public class Product {
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * This is the Product constructor.
     * @param id - Product ID
     * @param name - Product Name
     * @param price - Product Price
     * @param stock - Product Inventory
     * @param min - Product Minimum Inventory
     * @param max - Product Maximum Inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }
    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /**
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * @return the stock
     */
    public int getStock() {
        return stock;
    }
    /**
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @return the min
     */
    public int getMin() {
        return min;
    }
    /**
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * @return the max
     */
    public int getMax() {
        return max;
    }
    /**
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * @param part adds the associated part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * @param selectedPart removes the selected part
     */
    public boolean deleteAssociatedPart(int selectedPart) {
        associatedParts.remove(selectedPart);
        return true;
    }

    /**
     * @return the associatedParts - this will return a Product's associated Parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts; //remove later
    }

}