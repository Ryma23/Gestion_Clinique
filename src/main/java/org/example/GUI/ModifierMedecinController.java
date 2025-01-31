package org.example.GUI;


import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Model.Medecin;
import org.example.Service.MedecinService;

import java.sql.SQLException;
import java.util.Arrays;

public class ModifierMedecinController {

    @FXML private TextField nomField;
    @FXML private TextField prenomField;
    @FXML private TextField ageField;
    @FXML private TextField adresseField;
    @FXML private TextField emailField;
    @FXML private TextField telephoneField;
    @FXML private RadioButton masculinRadio;
    @FXML private RadioButton femininRadio;
    @FXML private RadioButton marieRadio;
    @FXML private RadioButton celibataireRadio;
    @FXML private ComboBox<String> regionComboBox;

    private ToggleGroup genreGroup;
    private ToggleGroup etatCivilGroup;
    private MedecinService medecinService;
    private Medecin currentMedecin;

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
        regionComboBox.getItems().addAll(Arrays.asList(
                "Tunis", "Ariana", "Ben Arous", "Manouba", "Nabeul", "Zaghouan",
                "Bizerte", "Béja", "Jendouba", "Kef", "Siliana", "Sousse",
                "Monastir", "Mahdia", "Sfax", "Kairouan", "Kasserine", "Sidi Bouzid",
                "Gabès", "Medenine", "Tataouine", "Gafsa", "Tozeur", "Kebili"
        ));

        // Add input validation
        setupInputValidation();
    }

    public void setMedecin(Medecin medecin) {
        this.currentMedecin = medecin;
        populateFields();
    }

    private void populateFields() {
        if (currentMedecin != null) {
            nomField.setText(currentMedecin.getNom());
            prenomField.setText(currentMedecin.getPrenom());
            ageField.setText(String.valueOf(currentMedecin.getAge()));
            adresseField.setText(currentMedecin.getAdresse());
            emailField.setText(currentMedecin.getEmail());
            telephoneField.setText(currentMedecin.getTelephone());
            regionComboBox.setValue(currentMedecin.getRegion());

            // Set genre
            if ("Masculine".equals(currentMedecin.getGenre())) {
                masculinRadio.setSelected(true);
            } else {
                femininRadio.setSelected(true);
            }

            // Set état civil
            if ("Marié(e)".equals(currentMedecin.getEtatCivil())) {
                marieRadio.setSelected(true);
            } else {
                celibataireRadio.setSelected(true);
            }
        }
    }

    private void setupInputValidation() {
        // Age validation - only numbers
        ageField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                ageField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        // Phone validation - only numbers and limited length
        telephoneField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                telephoneField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (newValue.length() > 8) {
                telephoneField.setText(oldValue);
            }
        });

        // Email validation visual feedback
        emailField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
                emailField.setStyle("-fx-border-color: red;");
            } else {
                emailField.setStyle("");
            }
        });
    }

    @FXML
    private void enregistrerModifications() {
        if (!validateInputs()) {
            return;
        }

        try {
            updateMedecinFromFields();
            medecinService.update(currentMedecin);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Les modifications ont été enregistrées avec succès.");
            closeWindow();
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur",
                    "Erreur lors de la modification du médecin: " + e.getMessage());
        }
    }

    private boolean validateInputs() {
        StringBuilder errorMessage = new StringBuilder();

        if (nomField.getText().trim().isEmpty()) {
            errorMessage.append("Le nom est requis.\n");
        }
        if (prenomField.getText().trim().isEmpty()) {
            errorMessage.append("Le prénom est requis.\n");
        }
        if (ageField.getText().trim().isEmpty()) {
            errorMessage.append("L'âge est requis.\n");
        }
        if (emailField.getText().trim().isEmpty() ||
                !emailField.getText().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            errorMessage.append("Email invalide.\n");
        }
        if (telephoneField.getText().trim().isEmpty() ||
                telephoneField.getText().length() != 8) {
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
            showAlert(Alert.AlertType.ERROR, "Erreur de validation", errorMessage.toString());
            return false;
        }

        return true;
    }

    private void updateMedecinFromFields() {
        currentMedecin.setNom(nomField.getText().trim());
        currentMedecin.setPrenom(prenomField.getText().trim());
        currentMedecin.setAge(Integer.parseInt(ageField.getText().trim()));
        currentMedecin.setAdresse(adresseField.getText().trim());
        currentMedecin.setEmail(emailField.getText().trim());
        currentMedecin.setTelephone(telephoneField.getText().trim());
        currentMedecin.setRegion(regionComboBox.getValue());
        currentMedecin.setGenre(masculinRadio.isSelected() ? "Masculine" : "Feminine");
        currentMedecin.setEtatCivil(marieRadio.isSelected() ? "Marié(e)" : "Célibataire");
    }

    @FXML
    private void annuler() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) nomField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }}