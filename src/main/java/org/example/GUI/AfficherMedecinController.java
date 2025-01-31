package org.example.GUI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.Model.Medecin;
import org.example.Service.MedecinService;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AfficherMedecinController implements Initializable {
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
    private TableColumn<Medecin, String> adresseColumn;
    @FXML
    private TableColumn<Medecin, String> etatCivilColumn;
    @FXML
    private TableColumn<Medecin, String> regionColumn;
    @FXML
    private TableColumn<Medecin, String> emailColumn;
    @FXML
    private TableColumn<Medecin, String> telephoneColumn;

    @FXML
    private TextField searchField;
    @FXML
    private ComboBox<String> filterRegion;

    private ObservableList<Medecin> medecinList;
    private FilteredList<Medecin> filteredMedecins;
    private MedecinService medecinService;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        medecinService = MedecinService.getInstance();
        initializeColumns();
        loadMedecins();
        setupSearch();
        setupRegionFilter();
    }

    private void initializeColumns() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_medecin"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomColumn.setCellValueFactory(new PropertyValueFactory<>("specialite")); // Using specialite as prenom for now
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
        adresseColumn.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        etatCivilColumn.setCellValueFactory(new PropertyValueFactory<>("etatCivil"));
        regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        telephoneColumn.setCellValueFactory(new PropertyValueFactory<>("telephone"));
    }

    private void loadMedecins() {
        try {
            List<Medecin> medecins = medecinService.findAll();
            medecinList = FXCollections.observableArrayList(medecins);
            filteredMedecins = new FilteredList<>(medecinList, p -> true);
            medecinTable.setItems(filteredMedecins);

            // Load regions into ComboBox
            List<String> regions = medecins.stream()
                    .map(Medecin::getRegion)
                    .distinct()
                    .sorted()
                    .collect(Collectors.toList());
            filterRegion.getItems().clear();
            filterRegion.getItems().add("Toutes les régions");
            filterRegion.getItems().addAll(regions);
            filterRegion.setValue("Toutes les régions");
        } catch (SQLException e) {
            showAlert("Erreur", "Erreur lors du chargement des médecins", e.getMessage());
        }
    }

    private void setupSearch() {
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredMedecins.setPredicate(medecin -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String lowerCaseFilter = newValue.toLowerCase();
                String regionFilter = filterRegion.getValue();

                boolean matchesRegion = regionFilter.equals("Toutes les régions") ||
                        medecin.getRegion().equals(regionFilter);

                return (medecin.getNom().toLowerCase().contains(lowerCaseFilter) ||
                        medecin.getEmail().toLowerCase().contains(lowerCaseFilter) ||
                        medecin.getTelephone().contains(lowerCaseFilter)) && matchesRegion;
            });
        });
    }

    private void setupRegionFilter() {
        filterRegion.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredMedecins.setPredicate(medecin -> {
                if (newValue == null || newValue.equals("Toutes les régions")) {
                    return true;
                }

                String searchText = searchField.getText();
                boolean matchesSearch = searchText.isEmpty() ||
                        medecin.getNom().toLowerCase().contains(searchText.toLowerCase()) ||
                        medecin.getEmail().toLowerCase().contains(searchText.toLowerCase()) ||
                        medecin.getTelephone().contains(searchText);

                return medecin.getRegion().equals(newValue) && matchesSearch;
            });
        });
    }

    @FXML
    private void refreshTable() {
        loadMedecins();
        searchField.clear();
        filterRegion.setValue("Toutes les régions");
    }

    private void showAlert(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }
}