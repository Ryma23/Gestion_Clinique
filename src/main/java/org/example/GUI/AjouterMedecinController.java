package org.example.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.Model.Medecin;
import org.example.Service.MedecinService;

import java.sql.SQLException;

public class AjouterMedecinController {
    @FXML private TextField nomMedecinTf;
    @FXML private TextField prenomTf;
    @FXML private TextField ageTf;
    @FXML private TextField adresseTf;
    @FXML private TextField specialiteTf;
    @FXML private TextField telephoneTf;
    @FXML private TextField emailTf;
    @FXML private ComboBox<String> regionComboBox;
    @FXML private RadioButton masculinRadio;
    @FXML private RadioButton femininRadio;
    @FXML private RadioButton marieRadio;
    @FXML private RadioButton celibataireRadio;

    private ToggleGroup genreGroup;
    private ToggleGroup etatCivilGroup;
    private MedecinService medecinService;

    @FXML
    public void initialize() {
        medecinService = MedecinService.getInstance();

        // Initialize ToggleGroups
        genreGroup = new ToggleGroup();
        masculinRadio.setToggleGroup(genreGroup);
        femininRadio.setToggleGroup(genreGroup);

        etatCivilGroup = new ToggleGroup();
        marieRadio.setToggleGroup(etatCivilGroup);
        celibataireRadio.setToggleGroup(etatCivilGroup);

        // Initialize regions
        regionComboBox.getItems().addAll(
                "Tunis", "Ariana", "Ben Arous", "Manouba", "Nabeul", "Zaghouan",
                "Bizerte", "Béja", "Jendouba", "Kef", "Siliana", "Sousse",
                "Monastir", "Mahdia", "Sfax", "Kairouan", "Kasserine", "Sidi Bouzid",
                "Gabès", "Medenine", "Tataouine", "Gafsa", "Tozeur", "Kebili"
        );

        // Add listeners for real-time validation
        setupInputValidation();
    }

    private void setupInputValidation() {
        // Age validation - only numbers
        ageTf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageTf.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Phone validation - only numbers and length limit
        telephoneTf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telephoneTf.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 8) {
                telephoneTf.setText(oldValue);
            }
        });
    }

    @FXML
    private void enregistrer() {
        if (validateInputs()) {
            try {
                Medecin nouveauMedecin = new Medecin(
                        nomMedecinTf.getText().trim(),
                        prenomTf.getText().trim(),
                        Integer.parseInt(ageTf.getText().trim()),
                        adresseTf.getText().trim(),
                        telephoneTf.getText().trim(),
                        emailTf.getText().trim(),
                        regionComboBox.getValue(),
                        getSelectedGenre(),
                        getSelectedEtatCivil()
                );

                medecinService.add(nouveauMedecin);
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Le médecin a été ajouté avec succès!");
                clearFields();

            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout du médecin: " + e.getMessage());
            }
        }
    }

    private String getSelectedGenre() {
        RadioButton selectedGenre = (RadioButton) genreGroup.getSelectedToggle();
        return selectedGenre != null ? selectedGenre.getText() : "Non spécifié";
    }

    private String getSelectedEtatCivil() {
        RadioButton selectedEtatCivil = (RadioButton) etatCivilGroup.getSelectedToggle();
        return selectedEtatCivil != null ? selectedEtatCivil.getText() : "Non spécifié";
    }

    private boolean validateInputs() {
        StringBuilder errorMessage = new StringBuilder();

        if (nomMedecinTf.getText().trim().isEmpty()) {
            errorMessage.append("Le nom est requis.\n");
        }
        if (prenomTf.getText().trim().isEmpty()) {
            errorMessage.append("Le prénom est requis.\n");
        }
        if (ageTf.getText().trim().isEmpty()) {
            errorMessage.append("L'âge est requis.\n");
        }
        if (specialiteTf.getText().trim().isEmpty()) {
            errorMessage.append("La spécialité est requise.\n");
        }
        if (emailTf.getText().trim().isEmpty() ||
                !emailTf.getText().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            errorMessage.append("Email invalide.\n");
        }
        if (telephoneTf.getText().trim().isEmpty() ||
                !telephoneTf.getText().matches("\\d{8}")) {
            errorMessage.append("Le numéro de téléphone doit contenir 8 chiffres.\n");
        }
        if (regionComboBox.getValue() == null) {
            errorMessage.append("La région est requise.\n");
        }
        if (genreGroup.getSelectedToggle() == null) {
            errorMessage.append("Le genre est requis.\n");
        }
        if (etatCivilGroup.getSelectedToggle() == null) {
            errorMessage.append("L'état civil est requis.\n");
        }

        if (errorMessage.length() > 0) {
            showAlert(Alert.AlertType.WARNING, "Validation", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void clearFields() {
        nomMedecinTf.clear();
        prenomTf.clear();
        ageTf.clear();
        adresseTf.clear();
        specialiteTf.clear();
        telephoneTf.clear();
        emailTf.clear();
        regionComboBox.setValue(null);
        genreGroup.selectToggle(null);
        etatCivilGroup.selectToggle(null);
    }

    @FXML
    private void annuler() {
        clearFields();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}