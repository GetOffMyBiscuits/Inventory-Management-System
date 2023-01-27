package com.example.myjavafxproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** <b>This class contains methods to edit and create Parts and Products to Inventory.</b> */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    //PARTS
    /**
     * This method adds a part to the Inventory.
     * @param part the part to add
     */
    public static void addPart(Part part) {
        allParts.add(part);
    }

    /**
     * This method deletes a part from the Inventory.
     * @param part the part to delete
     */
    public static boolean deletePart(Part part) {
        allParts.remove(part);
        return true;
    }

    /**
     * This method returns all parts from the Inventory.
     * @return all parts
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This method loops through the Inventory until it matches the partID,
     * then the matching part is updated.
     * @param part the part to update
     */
    public static void updatePart (Part part) {
        for (int i = 0; i<allParts.size(); i++) {
            if (allParts.get(i).getId() == part.getId()) {
                allParts.set(i, part);
            }
        }
    }

    /**
     * This method returns all parts with a matching partID.
     * @param partID the part ID to search
     */
    public static Part lookupPart(int partID) {
        for (Part allPart : allParts) {
            if (allPart.getId() == partID) {
                return allPart;
            }
        }
        return null;
    }

    /**
     * This method returns the part based on the provided part name.
     * @param partName the part to search by
     */
    public static ObservableList <Part> lookupPartName(String partName) {
        ObservableList<Part> resultingParts = FXCollections.observableArrayList();
        for (Part part: allParts) {
            if(part.getName().toLowerCase().contains(partName.toLowerCase())) {
                resultingParts.add(part);
            }
        }
        return resultingParts;
    }

    //PRODUCTS
    /**
     * This method adds a product to the Inventory
     * @param product - the product to add
     */
    public static void addProduct(Product product) {
        allProducts.add(product);
    }

    /**
     * This method deletes a product from the Inventory
     * @param product - the product to delete
     */
    public static boolean deleteProduct(Product product) {
        allProducts.remove(product);
        return true;
    }

    /**
     * This method returns all products from the Inventory
     * @return all products
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

    /**
     * This method loops through the Inventory until it matches the productID,
     * then the matching product is updated.
     * @param product the product to update
     */
    public static void updateProduct (Product product) {
        for (int i = 0; i<allProducts.size(); i++) {
            if (allProducts.get(i).getId() == product.getId()) {
                allProducts.set(i, product);
            }
        }
    }

    /**
     * This method returns the product based on the provided product ID.
     * @param productID the product ID to search by
     */
    public static Product lookupProduct(int productID) {
        for (Product allProduct : allProducts) {
            if (allProduct.getId() == productID) {
                return allProduct;
            }
        }
        return null;
    }

    /**
     * This method returns the product based on the provided product name.
     * @param productName the product name to search by
     */
    public static ObservableList <Product> lookupProductName(String productName) {
        ObservableList<Product> resultingParts = FXCollections.observableArrayList();
        for (Product product: allProducts) {
            if(product.getName().toLowerCase().contains(productName.toLowerCase())) {
                resultingParts.add(product);
            }
        }
        return resultingParts;
    }

}