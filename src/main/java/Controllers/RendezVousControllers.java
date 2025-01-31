package Controllers;

import DAO.RendezVousDAO;
import Models.RendezVous;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RendezVousControllers {

    // Définition des composants FXML
    @FXML private TableView<RendezVous> tableView;
    @FXML private TableColumn<RendezVous, String> colNom;
    @FXML private TableColumn<RendezVous, String> colPrenom;
    @FXML private TableColumn<RendezVous, String> colDate;
    @FXML private TableColumn<RendezVous, String> colHeure;
    @FXML private TableColumn<RendezVous, String> colStatus;

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private DatePicker datePicker;
    @FXML private TextField heureField;
    @FXML private TextField statusField;

    @FXML private Button ajouterButton;
    @FXML private Button modifierButton;
    @FXML private Button supprimerButton;

    private ObservableList<RendezVous> listeRendezVous;

    // Méthode pour initialiser la table avec les données des rendez-vous
    public void initialize() {
        // Initialisation des colonnes de la table
        colNom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientId() + "23"));
        colPrenom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPatientId() + "Ryma")); // Remplacer par le vrai nom
        colDate.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateHeure().toString()));
        colHeure.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateHeure().toLocalTime().toString()));
        colStatus.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getStatus()));

        // Charger les rendez-vous dans la table
        loadRendezVous();
    }

    // Méthode pour charger les rendez-vous depuis la base de données
    private void loadRendezVous() {
        listeRendezVous = FXCollections.observableArrayList(RendezVousDAO.getAllRendezVous());
        tableView.setItems(listeRendezVous);
    }

    // Méthode pour ajouter un rendez-vous
    @FXML
    public void ajouterRendezVous(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        LocalDateTime date = datePicker.getValue().atStartOfDay(); // Utiliser la date du DatePicker
        String heure = heureField.getText();
        String statut = statusField.getText();

        if (nom.isEmpty() || prenom.isEmpty() || date == null || heure.isEmpty() || statut.isEmpty()) {
            showAlert("Erreur", "Tous les champs doivent être remplis.");
            return;
        }

        try {
            // Formater l'heure
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalDateTime dateHeure = date.plusHours(Integer.parseInt(heure.split(":")[0]))
                    .plusMinutes(Integer.parseInt(heure.split(":")[1]));

            // Remplacez les IDs ici avec les vraies valeurs, par exemple, du médecin et du patient
            RendezVous rdv = new RendezVous(0, dateHeure, statut, 1, 1);


            boolean success = RendezVousDAO.ajouterRendezVous(rdv);

            if (success) {
                showAlert("Succès", "Rendez-vous ajouté avec succès.");
                loadRendezVous();
            } else {
                showAlert("Erreur", "Échec de l'ajout du rendez-vous.");
            }
        } catch (Exception e) {
            showAlert("Erreur", "Format de l'heure invalide.");
        }
    }

    // Méthode pour modifier un rendez-vous
    @FXML
    public void modifierRendezVous(ActionEvent event) {
        RendezVous selectedRdv = tableView.getSelectionModel().getSelectedItem();
        if (selectedRdv == null) {
            showAlert("Erreur", "Sélectionnez un rendez-vous à modifier.");
            return;
        }

        String statut = statusField.getText();
        if (statut.isEmpty()) {
            showAlert("Erreur", "Le statut du rendez-vous est requis.");
            return;
        }

        selectedRdv.setStatus(statut);
        boolean success = RendezVousDAO.modifierRendezVous(selectedRdv);

        if (success) {
            showAlert("Succès", "Rendez-vous modifié avec succès.");
            loadRendezVous(); // Recharger les rendez-vous après modification
        } else {
            showAlert("Erreur", "Échec de la modification du rendez-vous.");
        }
    }

    // Méthode pour supprimer un rendez-vous
    @FXML
    public void supprimerRendezVous(ActionEvent event) {
        RendezVous selectedRdv = tableView.getSelectionModel().getSelectedItem();
        if (selectedRdv == null) {
            showAlert("Erreur", "Sélectionnez un rendez-vous à supprimer.");
            return;
        }

        boolean success = RendezVousDAO.supprimerRendezVous(selectedRdv.getId());

        if (success) {
            showAlert("Succès", "Rendez-vous supprimé avec succès.");
            loadRendezVous(); // Recharger les rendez-vous après suppression
        } else {
            showAlert("Erreur", "Échec de la suppression du rendez-vous.");
        }
    }

    // Méthode pour afficher les alertes
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
