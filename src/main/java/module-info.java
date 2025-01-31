module org.example.gestion_clinique_f {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.sql;
    exports Controllers;
    exports Models;
    exports DAO;
    exports Utiles;
    exports main;

    opens Controllers to javafx.fxml;  // Permet à javafx.fxml de charger les classes de Controllers
}


