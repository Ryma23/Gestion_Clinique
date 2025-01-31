package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args); // Lancer l'application
    }

    @Override
    public void start(Stage stage) {
        try {
            // Charger uniquement RendezVous.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RendezVous.fxml"));

            // Charger le fichier FXML et le mettre dans la scène
            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setTitle("Réserver Rendez-vous");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Gestion de l'exception si le fichier FXML ne peut pas être chargé
            System.err.println("Erreur lors du chargement de l'interface utilisateur : " + e.getMessage());
            e.printStackTrace();
        }
    }
}

