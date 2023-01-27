package com.example.myjavafxproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * <b>This class runs the application.</b><br>FUTURE ENHANCEMENT: To extend this application I would connect
 * it to a database such as PostgreSQL and then I would create a reporting page that shows different statistics
 * based on the current Inventory.&nbsp;There could be a report which shows totals number of parts and products,
 * total number of outsourced parts and in-house.&nbsp;It could also display averages, or even which parts are most
 * common among products.
 */
public class IMSApplication extends Application {
    /**
     * Start method: this method loads the main screen of the application.
     * @param stage - the stage
     * @throws IOException - IOExceptions
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(IMSApplication.class.getResource("main-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 340);
        stage.setResizable(false);
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Main method: this method launches the application.
     * Javadocs location:
     * @param args - args
     */
    public static void main(String[] args) {
        launch();
    }
}