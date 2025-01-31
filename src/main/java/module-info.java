module com.example.gestion_clinique {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.controlsfx.controls;

    opens com.example.gestion_clinique to javafx.graphics, javafx.fxml;
    opens com.example.gestion_clinique.application to javafx.graphics, javafx.fxml;
    opens com.example.gestion_clinique.Controller to javafx.fxml;
    opens com.example.gestion_clinique.Entite to javafx.base;
    opens com.example.gestion_clinique.resources.view to javafx.fxml;

    exports com.example.gestion_clinique;
    exports com.example.gestion_clinique.application;
    exports com.example.gestion_clinique.Controller;
    exports com.example.gestion_clinique.Entite;
    exports com.example.gestion_clinique.Service;
    exports com.example.gestion_clinique.resources.view;

}