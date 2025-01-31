module com.example.mediconnect {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.mediconnect to javafx.fxml;
    exports com.example.mediconnect;
}