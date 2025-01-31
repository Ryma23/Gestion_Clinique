package Controllers;

import Entite.Prescription;
import Service.PrescriptionService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PrescriptionController implements Initializable {

    @FXML private ComboBox<String> patientComboBox;
    @FXML private ComboBox<String> doctorComboBox;
    @FXML private DatePicker prescriptionDatePicker;
    @FXML private DatePicker expiryDatePicker;
    @FXML private TextArea medicationsTextArea;
    @FXML private TextField dosageTextField;
    @FXML private TextField frequencyTextField;
    @FXML private TextArea instructionsTextArea;

    @FXML private TableView<Prescription> prescriptionTable;
    @FXML private TableColumn<Prescription, Long> idColumn;
    @FXML private TableColumn<Prescription, String> patientColumn;
    @FXML private TableColumn<Prescription, String> doctorColumn;
    @FXML private TableColumn<Prescription, LocalDate> dateColumn;
    @FXML private TableColumn<Prescription, String> medicationsColumn;
    @FXML private TableColumn<Prescription, String> dosageColumn;
    @FXML private TableColumn<Prescription, String> statusColumn;

    private PrescriptionService prescriptionService;
    private ObservableList<Prescription> prescriptionList;
    private Prescription selectedPrescription;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prescriptionService = new PrescriptionService();
        prescriptionList = FXCollections.observableArrayList();

        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        patientColumn.setCellValueFactory(new PropertyValueFactory<>("patientName"));
        doctorColumn.setCellValueFactory(new PropertyValueFactory<>("doctorName"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("prescriptionDate"));
        medicationsColumn.setCellValueFactory(new PropertyValueFactory<>("medications"));
        dosageColumn.setCellValueFactory(new PropertyValueFactory<>("dosage"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));

        // Set default dates
        prescriptionDatePicker.setValue(LocalDate.now());
        expiryDatePicker.setValue(LocalDate.now().plusMonths(1));

        // Load data
        loadPrescriptions();

        // Add table selection listener
        prescriptionTable.getSelectionModel().selectedItemProperty().addListener(
                (obs, oldSelection, newSelection) -> {
                    if (newSelection != null) {
                        selectedPrescription = newSelection;
                        showPrescriptionDetails();
                    }
                }
        );
    }

    private void loadPrescriptions() {
        prescriptionList.clear();
        prescriptionList.addAll(prescriptionService.getAllPrescriptions());
        prescriptionTable.setItems(prescriptionList);
    }

    private void showPrescriptionDetails() {
        if (selectedPrescription != null) {
            patientComboBox.setValue(selectedPrescription.getPatientName());
            doctorComboBox.setValue(selectedPrescription.getDoctorName());
            prescriptionDatePicker.setValue(selectedPrescription.getPrescriptionDate());
            expiryDatePicker.setValue(selectedPrescription.getExpiryDate());
            medicationsTextArea.setText(selectedPrescription.getMedications());
            dosageTextField.setText(selectedPrescription.getDosage());
            frequencyTextField.setText(selectedPrescription.getFrequency());
            instructionsTextArea.setText(selectedPrescription.getInstructions());
        }
    }

    @FXML
    private void handleAdd() {
        try {
            Prescription prescription = new Prescription(
                    1L, // You'll need to get actual patient ID
                    patientComboBox.getValue(),
                    1L, // You'll need to get actual doctor ID
                    doctorComboBox.getValue(),
                    prescriptionDatePicker.getValue(),
                    expiryDatePicker.getValue(),
                    medicationsTextArea.getText(),
                    dosageTextField.getText(),
                    frequencyTextField.getText(),
                    instructionsTextArea.getText()
            );

            if (prescriptionService.addPrescription(prescription)) {
                loadPrescriptions();
                clearFields();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Prescription ajoutée avec succès!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ajout: " + e.getMessage());
        }
    }

    @FXML
    private void handleUpdate() {
        if (selectedPrescription == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une prescription à modifier");
            return;
        }

        try {
            selectedPrescription.setPatientName(patientComboBox.getValue());
            selectedPrescription.setDoctorName(doctorComboBox.getValue());
            selectedPrescription.setPrescriptionDate(prescriptionDatePicker.getValue());
            selectedPrescription.setExpiryDate(expiryDatePicker.getValue());
            selectedPrescription.setMedications(medicationsTextArea.getText());
            selectedPrescription.setDosage(dosageTextField.getText());
            selectedPrescription.setFrequency(frequencyTextField.getText());
            selectedPrescription.setInstructions(instructionsTextArea.getText());

            if (prescriptionService.updatePrescription(selectedPrescription)) {
                loadPrescriptions();
                showAlert(Alert.AlertType.INFORMATION, "Succès", "Prescription mise à jour avec succès!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de la modification: " + e.getMessage());
        }
    }

    @FXML
    private void handleDelete() {
        if (selectedPrescription == null) {
            showAlert(Alert.AlertType.WARNING, "Attention", "Veuillez sélectionner une prescription à supprimer");
            return;
        }

        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION,
                "Êtes-vous sûr de vouloir supprimer cette prescription?",
                ButtonType.YES, ButtonType.NO);

        confirmDialog.showAndWait().ifPresent(response -> {
            if (response == ButtonType.YES) {
                if (prescriptionService.deletePrescription(selectedPrescription.getId())) {
                    loadPrescriptions();
                    clearFields();
                    showAlert(Alert.AlertType.INFORMATION, "Succès", "Prescription supprimée avec succès!");
                }
            }
        });
    }

    @FXML
    private void handleClear() {
        clearFields();
        selectedPrescription = null;
        prescriptionTable.getSelectionModel().clearSelection();
    }

    private void clearFields() {
        patientComboBox.setValue(null);
        doctorComboBox.setValue(null);
        prescriptionDatePicker.setValue(LocalDate.now());
        expiryDatePicker.setValue(LocalDate.now().plusMonths(1));
        medicationsTextArea.clear();
        dosageTextField.clear();
        frequencyTextField.clear();
        instructionsTextArea.clear();
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}