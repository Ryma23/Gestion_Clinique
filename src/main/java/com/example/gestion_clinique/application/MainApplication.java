package com.example.gestion_clinique.application;

import com.example.gestion_clinique.HelloApplication;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class MainApplication extends Application {
    private BorderPane mainLayout;
    private VBox sideMenu;

    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            mainLayout = new BorderPane();
            createSideMenu();
            mainLayout.setLeft(sideMenu);

            // Load initial content using Class.getResource
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("add_patient.fxml"));

            Scene scene = new Scene(mainLayout, 1200, 800);

            // Instead of loading from the file system, let's add the styles directly
            scene.getRoot().setStyle(
                    ".root {\n" +
                            "    -fx-background-color: white;\n" +
                            "}\n" +
                            ".side-menu {" +
                            "-fx-background-color: #f5f5f5;" +  // Light gray background
                            "-fx-pref-width: 250px;" +
                            "}" +
                            ".header-button {" +
                            "-fx-background-color: #0078D4;" +  // Microsoft blue"-fx-text-fill: white;" +
                            "-fx-font-size: 16px;" +
                            "-fx-padding: 10px;" +
                            "-fx-border-width: 0 0 1 0;" +
                            "-fx-border-color: #005a9e;" +
                            "-fx-background-radius: 0;" +
                            "}" +
                            ".menu-button {" +
                            "-fx-background-color: #e8e8e8;" +  // Light gray for buttons
                            "-fx-text-fill: black;" +
                            "-fx-font-size: 14px;" +
                            "-fx-alignment: center-left;" +
                            "-fx-padding: 8px 15px;" +
                            "-fx-min-width: 250px;" +
                            "-fx-background-radius: 3;" +
                            "-fx-border-color: #d0d0d0;" +  // Slight border
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 3;" +
                            "-fx-margin: 2 0;" +  // Small vertical margin
                            "}" +
                            ".menu-button:hover {" +
                            "-fx-background-color: #d8d8d8;" +  // Darker on hover
                            "}" +
                            ".submenu {" +
                            "-fx-background-color: transparent;" +
                            "-fx-padding: 0 0 0 20px;" +  // Left padding for indent
                            "}" +
                            ".submenu-button {" +
                            "-fx-background-color: #e8e8e8;" +
                            "-fx-text-fill: black;" +
                            "-fx-font-size: 14px;" +
                            "-fx-alignment: center-left;" +
                            "-fx-padding: 8px 15px;" +
                            "-fx-min-width: 230px;" +  // Slightly smaller for indent effect
                            "-fx-background-radius: 3;" +
                            "-fx-border-color: #d0d0d0;" +
                            "-fx-border-width: 1;" +
                            "-fx-border-radius: 3;" +
                            "-fx-margin: 2 0;" +
                            "}" +
                            ".submenu-button:hover {" +
                            "-fx-background-color: #d8d8d8;" +
                            "}"
            );
            primaryStage.setTitle("Gestion des Patients");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            showError("Error", "Failed to start application", e.getMessage());
        }}



    private void createSideMenu() {
        sideMenu = new VBox(5);  // 5px spacing between elements
        sideMenu.getStyleClass().add("side-menu");
        sideMenu.setStyle("-fx-padding: 0;");  // Remove padding
        sideMenu.setSpacing(0);
        sideMenu.setPadding(new Insets(0));
        sideMenu.setAlignment(Pos.TOP_LEFT);



        // Header as a button
        Button headerButton = new Button("Gestion des Patients");
        headerButton.getStyleClass().addAll("menu-button", "header-button");
        headerButton.setMaxWidth(Double.MAX_VALUE);

        Button gererPatientBtn = createMenuButton("Gérer Patient");
        gererPatientBtn.setMaxWidth(Double.MAX_VALUE);


        VBox patientSubmenu = new VBox(3);  // 3px spacing for submenu items
        patientSubmenu.getStyleClass().add("submenu");
        patientSubmenu.setVisible(false);
        patientSubmenu.setManaged(false);
        patientSubmenu.setAlignment(Pos.TOP_LEFT);
        patientSubmenu.setSpacing(0);
        patientSubmenu.setStyle("-fx-border-color: blue;");

        gererPatientBtn.setOnAction(e -> loadFXML("gerer_patient.fxml"));
        Button ajouterPatientBtn = createSubmenuButton("Ajouter Patient");
        Button consulterPatientBtn = createSubmenuButton("Consulter Patient");
        Button supprimerPatientBtn = createSubmenuButton("Supprimer Patient");
        Button modifierPatientBtn = createSubmenuButton("Modifier Patient");

        patientSubmenu.getChildren().addAll(
                ajouterPatientBtn,
                consulterPatientBtn,
                supprimerPatientBtn,
                modifierPatientBtn
        );
ajouterPatientBtn.setOnAction(e -> loadFXML("add_patient.fxml"));
        // Toggle submenu visibility when clicking on Gérer Patient


        Button gererRdvBtn = createMenuButton("Gérer Rendez-vous");
        Button consulterInfoBtn = createMenuButton("Consulter informations\npersonnelles");
        Button helpBtn = createMenuButton("Help");
        Button logoutBtn = createMenuButton("Logout Account");

        sideMenu.getChildren().addAll(
                headerButton,
                gererPatientBtn,
                patientSubmenu,
                gererRdvBtn,
                consulterInfoBtn,
                helpBtn,
                logoutBtn
        );
    }

    private Button createMenuButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("menu-button");
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private Button createSubmenuButton(String text) {
        Button button = new Button(text);
        button.getStyleClass().add("submenu-button");
        button.setMaxWidth(Double.MAX_VALUE);
        return button;
    }

    private void trySetButtonIcon(Button button, String iconPath) {
        try {
            URL iconUrl = MainApplication.class.getResource(iconPath);
            if (iconUrl != null) {
                Image icon = new Image(((URL) iconUrl).toExternalForm());
                ImageView imageView = new ImageView(icon);
                imageView.setFitHeight(20);
                imageView.setFitWidth(20);
                button.setGraphic(imageView);
            }
        } catch (Exception e) {
            System.err.println("Failed to load icon: " + iconPath);
        }
    }

    private void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void loadFXML(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource(fxmlFile));
            Parent newView = loader.load();
            mainLayout.setCenter(newView); // Replace the center content with new FXML content
        } catch (IOException e) {
            e.printStackTrace();
            showError("Error", "Failed to load " + fxmlFile, e.getMessage());
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}