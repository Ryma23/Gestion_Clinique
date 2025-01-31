module com.example.gestion_clinique {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Export to javafx.graphics for the Main class
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    opens org.example to javafx.graphics;
    // Export and open for FXML
    opens org.example.GUI to javafx.fxml;
    exports com.example.gestion_clinique ;

}