package org.example.GUI;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Model.Medecin;
import org.example.Service.MedecinService;

import java.sql.SQLException;
import java.util.List;

public class SupprimerMedecinController {

    @FXML
    private TableView<Medecin> medecinTable;
    @FXML
    private TableColumn<Medecin, Integer> idColumn;
    @FXML
    private TableColumn<Medecin, String> nomColumn;
    @FXML
    private TableColumn<Medecin, String> prenomColumn;
    @FXML
    private TableColumn<Medecin, Integer> ageColumn;
    @FXML
    private TableColumn<Medecin, String> genreColumn;
    @FXML
    private TableColumn<Medecin, String> emailColumn;
    @FXML
    private TableColumn<Medecin, String> telephoneColumn;
    @FXML
    private TableColumn<Medecin, String> regionColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Label statusLabel;

    private MedecinService medecinService;

    @FXML
    public void initialize() {
        medecinService = MedecinService.getInstance();

        // Initialize columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));

        loadMedecins();

        // Add search functionality
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterMedecins(newValue);
        });
    }

    private void loadMedecins() {
        try {
            List<Medecin> medecins = medecinService.findAll();
            medecinTable.getItems().setAll(medecins);
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors du chargement des médecins");
        }
    }

    private void filterMedecins(String searchText) {
        // Implement search functionality here
    }

    @FXML
    private void supprimerMedecin() {
        Medecin selectedMedecin = medecinTable.getSelectionModel().getSelectedItem();
        if (selectedMedecin == null) {
            showAlert(Alert.AlertType.WARNING, "Sélection", "Veuillez sélectionner un médecin à supprimer");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Confirmation de suppression");
        confirmation.setHeaderText(null);
        confirmation.setContentText("Êtes-vous sûr de vouloir supprimer ce médecin ?");

        if (confirmation.showAndWait().get() == ButtonType.OK) {
            try {
                medecinService.delete(selectedMedecin);
                loadMedecins();
                statusLabel.setText("Médecin supprimé avec succès");
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la suppression du médecin");
            }
        }
    }

    @FXML
    private void annuler() {
        medecinTable.getSelectionModel().clearSelection();
        searchField.clear();
        statusLabel.setText("");
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
