package DAO;

import Models.RendezVous;
import Utiles.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAO {

    // Méthode pour récupérer tous les rendez-vous de la base de données
    public static List<RendezVous> getAllRendezVous() {
        List<RendezVous> rendezVousList = new ArrayList<>();
        String query = "SELECT * FROM rendezvous";

        try (Connection connection = DataSource.getInstance().getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");  // Récupère l'ID comme long
                LocalDateTime dateHeure = resultSet.getTimestamp("date_heure").toLocalDateTime();
                String status = resultSet.getString("status");
                int patientId = resultSet.getInt("patient_id");
                int doctorId = resultSet.getInt("doctor_id");

                RendezVous rendezVous = new RendezVous(id, dateHeure, status, patientId, doctorId);  // Utilisez long pour ID
                rendezVousList.add(rendezVous);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rendezVousList;
    }

    // Méthode pour ajouter un rendez-vous dans la base de données
    public static boolean ajouterRendezVous(RendezVous rendezVous) {
        String query = "INSERT INTO rendezvous (date_heure, status, patient_id, doctor_id) VALUES (?, ?, ?, ?)";

        try (Connection connection = DataSource.getInstance().getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setTimestamp(1, Timestamp.valueOf(rendezVous.getDateHeure()));
            preparedStatement.setString(2, rendezVous.getStatus());
            preparedStatement.setInt(3, rendezVous.getPatientId()); // ID du patient
            preparedStatement.setInt(4, rendezVous.getDoctorId()); // ID du médecin

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Méthode pour modifier un rendez-vous
    public static boolean modifierRendezVous(RendezVous rendezVous) {
        String query = "UPDATE rendezvous SET status = ? WHERE id = ?";

        try (Connection connection = DataSource.getInstance().getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, rendezVous.getStatus());
            preparedStatement.setLong(2, rendezVous.getId());  // Utiliser setLong pour l'ID

            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Méthode pour supprimer un rendez-vous
    public static boolean supprimerRendezVous(long id) {  // Utiliser long pour l'ID
        String query = "DELETE FROM rendezvous WHERE id = ?";

        try (Connection connection = DataSource.getInstance().getCon();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setLong(1, id);  // Utiliser setLong pour l'ID
            int result = preparedStatement.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

