module com.example.myjavafxproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.myjavafxproject to javafx.fxml;
    exports com.example.myjavafxproject;
}