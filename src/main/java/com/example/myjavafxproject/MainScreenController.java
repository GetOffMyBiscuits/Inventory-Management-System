package com.example.myjavafxproject;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/** <b>This class is the controller for the main screen.</b> */
public class MainScreenController implements Initializable {
    @FXML private TableView <Part> Parts;
    @FXML private TableColumn <Part, Integer> PartID, PartInventory, PartPrice;
    @FXML private TableColumn <Part, String> PartName;

    @FXML private TableView <Product> Products;
    @FXML private TableColumn <Product, Integer> ProductID, ProductInventory, ProductPrice;
    @FXML private TableColumn <Product, String> ProductName;

    @FXML private Button addPartButton, modifyPartButton, addProductButton, modifyProductButton;
    @FXML TextField PartSearch, ProductSearch;

    static boolean exists;

    /**
     *This method can be used to return to the main screen from any other screen.
     * @param button - a button to get the current Window from
     * @throws IOException exception
     */
    public static void mainScreen (Button button) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();

        FXMLLoader mainLoad =
                new FXMLLoader(MainScreenController.class.getResource("main-view.fxml"));
        Pane mainPane = mainLoad.load();
        stage.setScene(new Scene(mainPane));
    }

    /**
     *This method can be used to return to the main screen from any other screen
     * @param title - set the title of the error window
     * @param text - set the display text of the error message
     */
    public static void ErrorMessage (String title, String text) {
        Alert input = new Alert(Alert.AlertType.ERROR);
        input.setTitle(title);
        input.setContentText(text);
        input.showAndWait();
    }

    /**
     *This method can be used to exit the program after a confirmation.
     */
    @FXML
    protected void handleExitButton() {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.setTitle("Exit");
        exit.setContentText("Would you like to exit the program?");
        exit.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                Platform.exit();
            }
        });
    }

    /**
     *This method can be used to switch to either the add product, modify product, or
     * add part, modify part screens.
     * @param form string to pick which scene
     * @param button button to get the current scene window
     */
    public void ChangeScene(String form, Button button) throws IOException {
        Stage stage = (Stage) button.getScene().getWindow();

        //part
        FXMLLoader partLoad =  new FXMLLoader(getClass().getResource("addpart-view.fxml"));
        Pane partPane = partLoad.load();
        PartController partC = partLoad.getController();

        //product
        FXMLLoader productLoad =  new FXMLLoader(getClass().getResource("addproduct-view.fxml"));
        Pane productPane = productLoad.load();
        ProductController productC = productLoad.getController();

        switch (form) {
            case "addPart" -> {
                partC.updateLabel("Add Part");
                PartController.modify = false;
                stage.setScene(new Scene(partPane));
            }
            case "modifyPart" -> {
                Part partSelected = Parts.getSelectionModel().getSelectedItem();
                partC.modifyPart(partSelected);
                partC.updateLabel("Modify Part");
                PartController.modify = true;
                stage.setScene(new Scene(partPane));
            }
            case "addProduct" -> {
                productC.modify = false;
                productC.updateLabel("Add Product");
                stage.setScene(new Scene(productPane));
            }
            case "modifyProduct" -> {
                Product productSelected = Products.getSelectionModel().getSelectedItem();
                productC.modify = true;
                productC.modifyProduct(productSelected);
                productC.updateLabel("Modify Part");
                stage.setScene(new Scene(productPane));
            }
        }
    }

    /**
     *This method switches to the add-part screen.
     */
    @FXML
    public void handleAddPartButton() throws IOException{
        ChangeScene("addPart", addPartButton);
    }
    /**
     *This method switches to the modify-part screen.
     */
    @FXML
    public void handleModifyPartButton() throws IOException{
        Part currentlySelected = Parts.getSelectionModel().getSelectedItem();

        if (currentlySelected != null) {
            ChangeScene("modifyPart", modifyPartButton);
        }
    }
    /**
     *This method deletes the currently selected part from Inventory.
     */
    @FXML
    public void handleDeletePartButton() {
        Part currentlySelected = Parts.getSelectionModel().getSelectedItem();

        if (currentlySelected != null) {
            Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
            delete.setTitle("Delete?");
            delete.setContentText("Would you like to delete this part?");
            delete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    Inventory.deletePart(currentlySelected);
                }
            });
        }
    }
    /**
     *This method parses the text box entry to search for a matching part in the table by name or ID.
     */
    @FXML
    public void handlePartSearch() {
        String search = PartSearch.getText();
        ObservableList<Part> resultingPart = Inventory.lookupPartName(search);

        if (!Objects.equals(search, "") && search.matches("[0-9]+")) {
            int searchID = Integer.parseInt(PartSearch.getText());
            Part foundID = Inventory.lookupPart(searchID);

            resultingPart.add(foundID);
            Parts.setItems(resultingPart);

            if (resultingPart.get(0) == null)
                ErrorMessage("No Results",
                        "There are no results for that search");
        }

        else {
            Parts.setItems(resultingPart);
            if (resultingPart.size() == 0) ErrorMessage
                    ("No Results",
                     "There are no results for that search");
        }
    }

    /**
     *This method switches to the add product screen.
     */
    @FXML
    public void handleAddProductButton() throws IOException{
        ChangeScene("addProduct", addProductButton);
    }
    /**
     *This method switches to the modify-product screen.
     */
    @FXML
    public void handleModifyProductButton() throws IOException {
        Product currentlySelected = Products.getSelectionModel().getSelectedItem();

        if (currentlySelected != null) {
        ChangeScene("modifyProduct", modifyProductButton);
        }
    }

    /**
     *This method deletes the currently selected product from Inventory.
     */
    @FXML
    public void handleDeleteProductButton() {
        Product currentlySelected = Products.getSelectionModel().getSelectedItem();

        if (currentlySelected != null) {
            Alert delete = new Alert(Alert.AlertType.CONFIRMATION);
            delete.setTitle("Delete?");
            delete.setContentText("Would you like to delete this product?");
            delete.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    //delete
                    if(currentlySelected.getAllAssociatedParts().size() == 0){
                        Inventory.deleteProduct(currentlySelected);
                    }
                    else{
                      Alert parts = new Alert(Alert.AlertType.ERROR);
                      parts.setTitle("Associated Parts");
                      parts.setContentText("This Product has associated parts that must be removed!");
                      parts.showAndWait();
                    }
                }
            });
        }
    }

    /**
     *This method parses the text box entry to search for a matching product in the table by ID or name.
     */
    @FXML
    public void handleProductSearch() {
        String search = ProductSearch.getText();
        ObservableList<Product> resultingProduct = Inventory.lookupProductName(search);

        if (!Objects.equals(search, "") && search.matches("[0-9]+")) {
            int     searchID = Integer.parseInt(ProductSearch.getText());
            Product foundID  = Inventory.lookupProduct(searchID);

            resultingProduct.add(foundID);
            Products.setItems(resultingProduct);

            if (resultingProduct.get(0) == null)
                ErrorMessage("No Results",
                             "There are no results for that search");
        }

        else {
            Products.setItems(resultingProduct);
            if (resultingProduct.size() == 0) ErrorMessage
                    ("No Results",
                     "There are no results for that search");
        }
    }

    /**
     * This method initializes the tables and adds any preliminary Inventory data.
     * @param url url
     * @param resourceBundle resource bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!exists) {
            Inventory.addPart    (new    InHouse    (1, "Groot",  100, 10, 1, 10, 122));
            Inventory.addPart    (new    Outsourced (2, "Monkey", 200, 20, 1, 20, "Nevanders"));
            Inventory.addProduct (new    Product    (1, "Donkey", 200, 2,  1, 2));
            Inventory.addProduct (new    Product    (2, "Grimbo", 300, 2,  1, 2));

            exists = true;
        }

        PartID.setCellValueFactory           (new PropertyValueFactory<>("id"));
        PartName.setCellValueFactory         (new PropertyValueFactory<>("name"));
        PartPrice.setCellValueFactory        (new PropertyValueFactory<>("price"));
        PartInventory.setCellValueFactory    (new PropertyValueFactory<>("stock"));

        Parts.setItems(Inventory.getAllParts());

        ProductID.setCellValueFactory        (new PropertyValueFactory<>("id"));
        ProductName.setCellValueFactory      (new PropertyValueFactory<>("name"));
        ProductPrice.setCellValueFactory     (new PropertyValueFactory<>("price"));
        ProductInventory.setCellValueFactory (new PropertyValueFactory<>("stock"));

        Products.setItems(Inventory.getAllProducts());
    }
}

