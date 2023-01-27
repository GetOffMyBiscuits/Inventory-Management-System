package com.example.myjavafxproject;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/** <b>This class is the controller for the Add Part and Modify Part screen.</b> */
public class PartController {
    @FXML private Label mainLabel, changeLabel;
    @FXML private Button cancelButton;
    @FXML private RadioButton inHouseRadio, outsourcedRadio;
    @FXML private TextField idTxt, nameTxt, invTxt, priceTxt, maxTxt, minTxt, machineTxt;
    private static AtomicInteger updateID = new AtomicInteger(3);
    private boolean inHouse;
    private boolean outsourced;
    public static Boolean modify = false;

    /**
     *This method can be used to change the label text.
     * @param label string
     */
    public void updateLabel(String label) {
        mainLabel.setText(label);
    }

    /**
     *This method can be used to cancel what you are doing and return to the main screen.
     */
    @FXML
    public void handleCancelButton() {
        Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
        exit.setTitle("Return to Main Page");
        exit.setContentText("Return to the main page? You will lose all entered data");
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
     *This method can be used to switch to InHouse when the radio button is clicked.
     */
    @FXML
    public void handleInHouseRadio() {
        inHouse = inHouseRadio.isSelected();
        outsourcedRadio.setSelected(false);
        outsourced = outsourcedRadio.isSelected();
        inHouseRadio.setDisable(true);
        outsourcedRadio.setDisable(false);

        changeLabel.setText("Machine ID");
    }

    /**
     *This method can be used to switch to Outsourced when the radio button is clicked.
     */
    @FXML
    public void handleOutsourcedRadio() {
        outsourced = outsourcedRadio.isSelected();
        inHouseRadio.setSelected(false);
        inHouse = inHouseRadio.isSelected();
        outsourcedRadio.setDisable(true);
        inHouseRadio.setDisable(false);

        changeLabel.setText("Company Name");
    }

    /**
     * This method is used to validate, save, or modify a new Part on button click.
     * LOGICAL ERROR: I ran into several errors relating to whether a part was valid or not.
     * Sometimes it would save despite the error popups, or not save when all inputs were valid,
     * so I had to create a 'successful' boolean to clearly tell the program when to save and
     * when not to save. Then I had to reorganize my code so that all validation checks occur
     * in the right order.
     * @throws IOException ioexception
     * @throws NumberFormatException when a number is not given
     */
    @FXML
    public void handleSaveButton() throws IOException, NumberFormatException {
        boolean success;

        //First checks to see if the inputted values are not null
        if(!Objects.equals(priceTxt.getText(), "") ||
           !Objects.equals(invTxt.getText(),   "") ||
           !Objects.equals(maxTxt.getText(),   "") ||
           !Objects.equals(minTxt.getText(),   "")) {

            int id;
            String name = nameTxt.getText();
            int stock = 0;
            double price = 0;
            int max = 0;
            int min = 0;


            //check that the input is of the correct data type
            try { stock = Integer.parseInt(invTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'integer'");}

            try{ price = Double.parseDouble(priceTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'double'");}

            try {max = Integer.parseInt(maxTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error", "Input must be of value 'integer'");}

            try {min = Integer.parseInt(minTxt.getText());}
            catch (NumberFormatException e) {
                MainScreenController.ErrorMessage("Input Error","Input must be of value 'Integer'");}

            //Mechanism to ADD a new Part
            if (!modify) {
                id = updateID.getAndIncrement();

                //Handles the outsourced Parts
                if (outsourced) {
                    String companyName = machineTxt.getText();

                    if (stock <= max && stock >= min && price > 0.0) {
                        Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                        success = true;
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
                //Handles the InHouse Parts
                else {
                    String text = machineTxt.getText();

                    if (stock <= max && stock >= min
                            && text.matches("[0-9]+") && price > 0.0) {
                        int machineID = Integer.parseInt(text);
                        Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                        success = true;
                    }
                    else if (min > max) {
                        MainScreenController.ErrorMessage
                                ("Min Max Error",
                                 "The Minimum value exceeds the maximum!");
                        success = false;
                    }
                    else if (price <= 0.0) success = false;

                    else if (!text.matches("[0-9]+")) {
                            MainScreenController.ErrorMessage
                                    ("Type Error",
                                     "Machine ID must be a number!");
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
            //Mechanism to modify and update a Part
            else {
                id = Integer.parseInt(idTxt.getText());

                //Handles the Outsourced Parts
                if (outsourced) {
                    String companyName = machineTxt.getText();

                    if (stock <= max && stock >= min && price > 0.0) {
                        Inventory.updatePart(new Outsourced(id, name, price, stock, min, max, companyName));
                        success = true;
                    }
                    else if (min > max) {
                        MainScreenController.ErrorMessage
                                ("Min Max Error",
                                 "The Minimum value exceeds the maximum!");
                        success = false;
                    }
                    else if (price <= 0.0){
                        success = false;
                    }
                    else {
                        MainScreenController.ErrorMessage
                                ("Inventory error",
                                 "The inventory number exceeds min/max boundaries!");
                        success = false;
                    }
                }

                //Handles the InHouse Parts
                else {
                    String text = machineTxt.getText();

                    if (stock >= min && stock <= max && text.matches("[0-9]+") && price > 0.0) {
                        int machineID = Integer.parseInt(text);
                        Inventory.updatePart(new InHouse(id, name, price, stock, min, max, machineID));
                        success = true;
                    }
                    else if (min > max) {
                        MainScreenController.ErrorMessage
                                ("Min Max Error",
                                 "The Minimum value exceeds the maximum!");
                        success = false;
                    }
                    else if (!text.matches("[0-9]+")) {
                            MainScreenController.ErrorMessage
                                    ("Type Error",
                                     "Machine ID must be a number!");
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
        }
        //Error message if there are null values
        else {
            MainScreenController.ErrorMessage
                    ("Null values",
                     "Some values were left blank!");
            success = false;
        }
        //Go back to main menu if Saved or Updated successfully
        if (success) MainScreenController.mainScreen(cancelButton);
    }

    /**
     * This method is used to handle swapping between InHouse and Outsourced,
     * so that no two are selected at the same time.
     * @param selected part selected
     */
    public void modifyPart(Part selected) {
        idTxt.setText(String.valueOf(selected.getId()));
        nameTxt.setText(selected.getName());
        priceTxt.setText(String.valueOf(selected.getPrice()));
        invTxt.setText(String.valueOf(selected.getStock()));
        minTxt.setText(String.valueOf(selected.getMin()));
        maxTxt.setText(String.valueOf(selected.getMax()));
        if(selected instanceof InHouse ih) {
            inHouseRadio.setSelected(true);
            outsourcedRadio.setSelected(false);
            outsourced = outsourcedRadio.isSelected();
            inHouse = inHouseRadio.isSelected();
            inHouseRadio.setDisable(true);
            outsourcedRadio.setDisable(false);

            changeLabel.setText("Machine ID");
            machineTxt.setText(String.valueOf(ih.getMachineID()));
        }
        else if(selected instanceof Outsourced os) {
            outsourcedRadio.setSelected(true);
            inHouseRadio.setSelected(false);
            outsourced = outsourcedRadio.isSelected();
            inHouse = inHouseRadio.isSelected();
            outsourcedRadio.setDisable(true);
            inHouseRadio.setDisable(false);

            changeLabel.setText("Company Name");
            machineTxt.setText(String.valueOf(os.getCompanyName()));
        }

    }
}