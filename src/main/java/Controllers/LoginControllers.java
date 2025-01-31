package Controllers;
import DAO.AdminDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
public class LoginControllers {

        @FXML
        private TextField usernameField;
        @FXML
        private PasswordField passwordField;

        public void login() {
            String username = usernameField.getText();
            String password = passwordField.getText();

            if (AdminDAO.authenticate(username, password)) {
                showAlert("Connexion réussie !", Alert.AlertType.INFORMATION);
                openDashboard(); // Ouvre la page des rendez-vous
                ((Stage) usernameField.getScene().getWindow()).close(); // Ferme la fenêtre login
            } else {
                showAlert("Échec de la connexion. Vérifiez vos identifiants.", Alert.AlertType.ERROR);
            }
        }

        private void openDashboard() {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dashboard.fxml"));
                Stage stage = new Stage();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle("Gestion des Rendez-vous");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
                showAlert("Erreur lors de l'ouverture du tableau de bord.", Alert.AlertType.ERROR);
            }
        }

        private void showAlert(String message, Alert.AlertType type) {
            Alert alert = new Alert(type);
            alert.setContentText(message);
            alert.show();
        }
    }

