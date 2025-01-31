package com.example.gestion_clinique.Controller;

import com.example.gestion_clinique.Entite.Patient;

import com.example.gestion_clinique.Service.PatientService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import java.io.IOException;

public class PatientController {
    @FXML private TextField lastNameField;
    @FXML private TextField firstNameField;
    @FXML private TextField ageField;
    @FXML private TextField emailField;
    @FXML private TextField phoneNumberField;
    @FXML private TextField addressField;
    @FXML private TextField numeroSecSocialeField;
    @FXML private TextField assuranceField;
    @FXML private ComboBox<String> regionComboBox;
    @FXML private ToggleGroup genreGroup;
    @FXML private ToggleGroup etatCivilGroup;
    @FXML private RadioButton hommeRadio;
    @FXML private RadioButton femmeRadio;
    @FXML private RadioButton marieRadio;
    @FXML private RadioButton celibataireRadio;
    @FXML private Button suivantButton;

    @FXML private TableView<Patient> patientTable;
    @FXML private TableColumn<Patient, String> nomCol;
    @FXML private TableColumn<Patient, String> prenomCol;
    @FXML private TableColumn<Patient, Integer> ageCol;
    @FXML private TableColumn<Patient, String> emailCol;
    @FXML private TableColumn<Patient, String> phoneCol;
    @FXML private TableColumn<Patient, String> regionCol;
    @FXML private TableColumn<Patient, Void> actionsCol;

    private PatientService patientService;
    private ObservableList<Patient> patientList;

    @FXML
    public void initialize() {
        patientService = new PatientService();

        if (patientTable != null) {
            setupTable();
            loadPatients();
        }
    }

    private void setupTable() {
        nomCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        regionCol.setCellValueFactory(new PropertyValueFactory<>("region"));

        setupActionsColumn();
    }

    private void setupActionsColumn() {
        actionsCol.setCellFactory(param -> new TableCell<>() {
            private final Button editButton = new Button();
            private final Button deleteButton = new Button();
            private final HBox actionButtons = new HBox(10, editButton, deleteButton);

            {
                editButton.getStyleClass().addAll("action-button", "edit-button");
                deleteButton.getStyleClass().addAll("action-button", "delete-button");

                editButton.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    openEditDialog(patient);
                });

                deleteButton.setOnAction(event -> {
                    Patient patient = getTableView().getItems().get(getIndex());
                    handleDelete(patient);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : actionButtons);
            }
        });
    }

    private void openEditDialog(Patient patient) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/addPatient.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();

            PatientController controller = loader.getController();
            controller.populateFields(patient);

            stage.setTitle("Modifier Patient");
            stage.setScene(scene);
            stage.showAndWait();

            loadPatients(); // Refresh table after edit
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleDelete(Patient patient) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setContentText("Voulez-vous vraiment supprimer ce patient ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                patientService.deletePatient(patient.getId());
                loadPatients();
            }
        });
    }

    private void loadPatients() {
        patientList = FXCollections.observableArrayList(patientService.getAllPatients());
        patientTable.setItems(patientList);
    }

    @FXML
    private void handleAddPatient() {
        try {
            Patient patient = new Patient(
                    null, // id will be generated
                    lastNameField.getText(),
                    firstNameField.getText(),
                    Integer.parseInt(ageField.getText()),
                    emailField.getText(),
                    phoneNumberField.getText(),
                    addressField.getText(),
                    regionComboBox.getValue(),
                    numeroSecSocialeField.getText(),
                    ((RadioButton) genreGroup.getSelectedToggle()).getText(),
                    ((RadioButton) etatCivilGroup.getSelectedToggle()).getText(),
                    assuranceField.getText()
            );

            patientService.addPatient(patient);
            clearFields();

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Patient ajouté avec succès");
            alert.showAndWait();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("L'âge doit être un nombre valide");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Une erreur est survenue lors de l'ajout du patient");
            alert.showAndWait();
        }
    }

    private void populateFields(Patient patient) {
        lastNameField.setText(patient.getLastName());
        firstNameField.setText(patient.getFirstName());
        ageField.setText(String.valueOf(patient.getAge()));
        emailField.setText(patient.getEmail());
        phoneNumberField.setText(patient.getPhoneNumber());
        addressField.setText(patient.getAddress());
        regionComboBox.setValue(patient.getRegion());
        numeroSecSocialeField.setText(patient.getNumero_sec_sociale());
        assuranceField.setText(patient.getAssurance());

        // Set radio buttons
        if (patient.getGenre().equals("Homme")) {
            hommeRadio.setSelected(true);
        } else {
            femmeRadio.setSelected(true);
        }

        if (patient.getEtat_civil().equals("Marié(e)")) {
            marieRadio.setSelected(true);
        } else {
            celibataireRadio.setSelected(true);
        }
    }

    private void clearFields() {
        lastNameField.clear();
        firstNameField.clear();
        ageField.clear();
        emailField.clear();
        phoneNumberField.clear();
        addressField.clear();
        regionComboBox.setValue(null);
        numeroSecSocialeField.clear();
        assuranceField.clear();
        genreGroup.selectToggle(null);
        etatCivilGroup.selectToggle(null);
    }
    public void handleAddPatient(ActionEvent actionEvent) {
    }

    public void handleSearch(ActionEvent actionEvent) {
    }

    public void handleModifyPatient(ActionEvent actionEvent) {
    }

    public void handleViewPatients(ActionEvent actionEvent) {
    }

    public void handleDeletePatient(ActionEvent actionEvent) {
    }
}
