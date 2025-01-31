package com.example.gestion_clinique.Service;

import com.example.gestion_clinique.Entite.Patient;
import utiles.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientService  {
    private final DBConnection dataSource;

    public PatientService() {
        this.dataSource = DBConnection.getInstance();
    }


    public void addPatient(Patient patient) {
        String query = "INSERT INTO patients (username, password, email, age, region, " +
                "numero_sec_sociale, genre, etat_civil, assurance, firstName, " +
                "lastName, phoneNumber, address) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = dataSource.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pst.setString(1, patient.getUsername());
            pst.setString(2, patient.getPassword());
            pst.setString(3, patient.getEmail());
            pst.setInt(4, patient.getAge());
            pst.setString(5, patient.getRegion());
            pst.setString(6, patient.getNumero_sec_sociale());
            pst.setString(7, patient.getGenre());
            pst.setString(8, patient.getEtat_civil());
            pst.setString(9, patient.getAssurance());
            pst.setString(10, patient.getFirstName());
            pst.setString(11, patient.getLastName());
            pst.setString(12, patient.getPhoneNumber());
            pst.setString(13, patient.getAddress());

            pst.executeUpdate();

            // Get the generated ID and set it to the patient object
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                patient.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            System.err.println("Error adding patient: " + e.getMessage());
            throw new RuntimeException("Error adding patient", e);
        }
    }

    public void updatePatient(Patient patient) {
        String query = "UPDATE patients SET username=?, password=?, email=?, age=?, " +
                "region=?, numero_sec_sociale=?, genre=?, etat_civil=?, assurance=?, " +
                "firstName=?, lastName=?, phoneNumber=?, address=? WHERE id=?";

        try (PreparedStatement pst = dataSource.getConnection().prepareStatement(query)) {
            pst.setString(1, patient.getUsername());
            pst.setString(2, patient.getPassword());
            pst.setString(3, patient.getEmail());
            pst.setInt(4, patient.getAge());
            pst.setString(5, patient.getRegion());
            pst.setString(6, patient.getNumero_sec_sociale());
            pst.setString(7, patient.getGenre());
            pst.setString(8, patient.getEtat_civil());
            pst.setString(9, patient.getAssurance());
            pst.setString(10, patient.getFirstName());
            pst.setString(11, patient.getLastName());
            pst.setString(12, patient.getPhoneNumber());
            pst.setString(13, patient.getAddress());
            pst.setInt(14, patient.getId());

            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating patient: " + e.getMessage());
            throw new RuntimeException("Error updating patient", e);
        }
    }


    public void deletePatient(int id) {
        String query = "DELETE FROM patients WHERE id=?";
        try (PreparedStatement pst = dataSource.getConnection().prepareStatement(query)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting patient: " + e.getMessage());
            throw new RuntimeException("Error deleting patient", e);
        }
    }


    public Patient getPatient(int id) {
        String query = "SELECT * FROM patients WHERE id=?";
        try (PreparedStatement pst = dataSource.getConnection().prepareStatement(query)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return extractPatientFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving patient: " + e.getMessage());
            throw new RuntimeException("Error retrieving patient", e);
        }
        return null;
    }

    public List<Patient> getAllPatients() {
        List<Patient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients";

        try (Statement st = dataSource.getConnection().createStatement();
             ResultSet rs = st.executeQuery(query)) {

            while (rs.next()) {
                patients.add(extractPatientFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving patients: " + e.getMessage());
            throw new RuntimeException("Error retrieving patients", e);
        }
        return patients;
    }

    private Patient extractPatientFromResultSet(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getInt("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("email"),
                rs.getInt("age"),
                rs.getString("region"),
                rs.getString("numero_sec_sociale"),
                rs.getString("genre"),
                rs.getString("etat_civil"),
                rs.getString("assurance"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("phoneNumber"),
                rs.getString("address")
        );
    }
}