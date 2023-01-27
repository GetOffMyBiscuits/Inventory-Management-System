package com.example.myjavafxproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

/** <b>This class is the controller for the Add Product and Modify Product screen.</b> */
public class ProductController implements Initializable {

    @FXML private Label mainLabel;
    @FXML private Button cancelButton;
    @FXML private TextField idTxt;
    @FXML private TextField nameTxt;
    @FXML private TextField invTxt;
    @FXML private TextField priceTxt;
    @FXML private TextField maxTxt;
    @FXML private TextField minTxt;
    @FXML private TextField partSearch;
    @FXML TableView<Part> PartsToAdd, PartsToRemove;
    @FXML TableColumn<Part, String> partNameAdd, partNameRemove;
    @FXML TableColumn<Part, Integer> partIDAdd, partStockAdd, partIDRemove, partStockRemove;
    @FXML TableColumn<Part, Double> partPriceAdd, partPriceRemove;
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private static AtomicInteger updateID = new AtomicInteger(3);
    boolean modify = false;

    /**
     * This method sets the text for the label: modify or add.
     * @param label string
     */
    public void updateLabel(String label) {
        mainLabel.setText(label);
    }

    /**
     * This void method discards the current form and
     * returns the user to the main menu.
     */
    @FXML
    public void handleCancelButton() {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.setTitle("Return to Main Page");
        exit.setContentText("Return to the main page? You will lose all entered data?");
        exit.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    MainScreenController.mainScreen(cancelButton);
                    }catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    /**
     * This void method handles saving a new product and modifying a new product,
     * and ensuring that the correct data type is inputted before saving and
     * throwing the appropriate error message.
     * @throws IOException exception
     * @throws NumberFormatException when input is not a number
     */
    public void handleSaveButton() throws NumberFormatException, IOException {
        boolean success;

        //Save a new Product
    if (!Objects.equals(priceTxt.getText(), "") ||
        !Objects.equals(invTxt.getText(),   "") ||
        !Objects.equals(maxTxt.getText(),   "") ||
        !Objects.equals(minTxt.getText(),   "")) {

        if(!modify) {
            int id = updateID.getAndIncrement();
            String name = nameTxt.getText();
            double price = 0.0;
            int stock = 0;
            int max = 0;
            int min = 0;

            try { stock = Integer.parseInt(invTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'integer'");}

            try{ price = Double.parseDouble(priceTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'double'");}

            try { max = Integer.parseInt(maxTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error", "Input must be of value 'integer'");}

            try { min = Integer.parseInt(minTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'Integer'");}

            Product prod = new Product(id, name, price, stock, min, max);
            for (Part parts : associatedParts) prod.addAssociatedPart(parts);

            if (stock <= max && stock >= min && price > 0.0) {
                success = true;
                Inventory.addProduct(prod);
            }

            else if (min > max) {
                MainScreenController.ErrorMessage
                        ("Min Max Error",
                         "The Minimum value exceeds the maximum!");
                success = false;
            }
            else if (price <= 0.0) success = false;

            else {
                MainScreenController.ErrorMessage
                        ("Inventory error",
                         "The inventory number exceeds min/max boundaries!");
                success = false;
            }
        }

        // Save a modified product
        else {
                int id = Integer.parseInt(idTxt.getText());
                String name = nameTxt.getText();
                double price = 0.0;
                int stock = 0;
                int max = 0;
                int min = 0;

            try { stock = Integer.parseInt(invTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'integer'");}

            try{ price = Double.parseDouble(priceTxt.getText());}
            catch (NumberFormatException e) {
                success = false;
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'double'");}

            try {max = Integer.parseInt(maxTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error", "Input must be of value 'integer'");}

            try {min = Integer.parseInt(minTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'Integer'");}

                Product prod = new Product(id, name, price, stock, min, max);
                for (Part parts : associatedParts) prod.addAssociatedPart(parts);

                if (stock <= max && stock >= min && price > 0.0) {
                    Inventory.updateProduct(prod);
                    success = true;
                }
                else if (min > max) {
                    MainScreenController.ErrorMessage
                                   ("Min Max Error",
                                    "The Minimum value exceeds the maximum!");
                    success = false;
                }
                else if (price <= 0.0) {
                    success = false;
                }
                else {
                    MainScreenController.ErrorMessage
                            ("Inventory error",
                             "The inventory number exceeds min/max boundaries!");
                    success = false;
                }
            }
        }
        else {
        MainScreenController.ErrorMessage
                ("Null values",
                 "Some values were left blank!");
        success = false;
    }

    if (success) MainScreenController.mainScreen(cancelButton);
    }

    /**
     * This is a void method that gets the currently selected part and
     * adds it to <b><i>associated parts</i></b> on button click.
     */
    public void handleAddPartButton() {
        Part parts = PartsToAdd.getSelectionModel().getSelectedItem();
        associatedParts.add(parts);
    }

    /**
     * This is a void method that gets the currently selected part and
     * removes it from <b><i>associated parts</i></b> on button click.
     */
    public void handleRemovePartButton() {
        Part parts = PartsToRemove.getSelectionModel().getSelectedItem();
        Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
        delete.setTitle("Remove?");
        delete.setContentText("Would you like to remove this part?");
        delete.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                associatedParts.remove(parts);
            }
        });
    }

    /**
     * This void method allows the user to modify a product.
     * @param selected currently selected Product
     */
    public void modifyProduct(Product selected) {
        idTxt.setText(String.valueOf(selected.getId()));
        nameTxt.setText(selected.getName());
        priceTxt.setText(String.valueOf(selected.getPrice()));
        invTxt.setText(String.valueOf(selected.getStock()));
        minTxt.setText(String.valueOf(selected.getMin()));
        maxTxt.setText(String.valueOf(selected.getMax()));

        associatedParts = selected.getAllAssociatedParts();
        PartsToRemove.setItems(associatedParts);
    }

    /**
     * This is a void method that allows a user to search for parts with the search text box.
     */
    @FXML
    public void handlePartSearch() {
        String search = partSearch.getText();
        ObservableList<Part> resultingPart = Inventory.lookupPartName(search);

        if (!Objects.equals(search, "") && search.matches("[0-9]+")) {
            int searchID = Integer.parseInt(partSearch.getText());
            Part foundID = Inventory.lookupPart(searchID);

            resultingPart.add(foundID);
            PartsToAdd.setItems(resultingPart);

            if (resultingPart.get(0) == null)
                MainScreenController.ErrorMessage
                        ("No Results",
                         "There are no results for that search");
        }

        else {
            PartsToAdd.setItems(resultingPart);
            if (resultingPart.size() == 0)
                MainScreenController.ErrorMessage
                    ("No Results",
                     "There are no results for that search");
        }
    }

    /**
     * Initializes the Part Table and its columns.
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //list of all parts
        partIDAdd.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameAdd.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceAdd.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockAdd.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsToAdd.setItems(Inventory.getAllParts());

        //list of parts attached to product
        partIDRemove.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameRemove.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceRemove.setCellValueFactory(new PropertyValueFactory<>("price"));
        partStockRemove.setCellValueFactory(new PropertyValueFactory<>("stock"));
        PartsToRemove.setItems(associatedParts);
    }
}