package Service;

import Entite.Prescription;
import Utils.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrescriptionService {
    private Connection conn;

    public PrescriptionService() {
        try {
            conn = DatabaseConnection.getInstance().getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addPrescription(Prescription prescription) {
        String query = "INSERT INTO prescriptions (patient_id, patient_name, doctor_id, doctor_name, " +
                "prescription_date, expiry_date, medications, dosage, frequency, instructions, status) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setLong(1, prescription.getPatientId());
            pstmt.setString(2, prescription.getPatientName());
            pstmt.setLong(3, prescription.getDoctorId());
            pstmt.setString(4, prescription.getDoctorName());
            pstmt.setDate(5, Date.valueOf(prescription.getPrescriptionDate()));
            pstmt.setDate(6, Date.valueOf(prescription.getExpiryDate()));
            pstmt.setString(7, prescription.getMedications());
            pstmt.setString(8, prescription.getDosage());
            pstmt.setString(9, prescription.getFrequency());
            pstmt.setString(10, prescription.getInstructions());
            pstmt.setString(11, prescription.getStatus());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        prescription.setId(generatedKeys.getLong(1));
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<Prescription> getAllPrescriptions() {
        List<Prescription> prescriptions = new ArrayList<>();
        String query = "SELECT * FROM prescriptions ORDER BY prescription_date DESC";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Prescription prescription = new Prescription();
                prescription.setId(rs.getLong("id"));
                prescription.setPatientId(rs.getLong("patient_id"));
                prescription.setPatientName(rs.getString("patient_name"));
                prescription.setDoctorId(rs.getLong("doctor_id"));
                prescription.setDoctorName(rs.getString("doctor_name"));
                prescription.setPrescriptionDate(rs.getDate("prescription_date").toLocalDate());
                prescription.setExpiryDate(rs.getDate("expiry_date").toLocalDate());
                prescription.setMedications(rs.getString("medications"));
                prescription.setDosage(rs.getString("dosage"));
                prescription.setFrequency(rs.getString("frequency"));
                prescription.setInstructions(rs.getString("instructions"));
                prescription.setStatus(rs.getString("status"));
                prescription.setNotes(rs.getString("notes"));

                prescriptions.add(prescription);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return prescriptions;
    }

    public Prescription getPrescriptionById(Long id) {
        String query = "SELECT * FROM prescriptions WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Prescription prescription = new Prescription();
                    prescription.setId(rs.getLong("id"));
                    prescription.setPatientId(rs.getLong("patient_id"));
                    prescription.setPatientName(rs.getString("patient_name"));
                    prescription.setDoctorId(rs.getLong("doctor_id"));
                    prescription.setDoctorName(rs.getString("doctor_name"));
                    prescription.setPrescriptionDate(rs.getDate("prescription_date").toLocalDate());
                    prescription.setExpiryDate(rs.getDate("expiry_date").toLocalDate());
                    prescription.setMedications(rs.getString("medications"));
                    prescription.setDosage(rs.getString("dosage"));
                    prescription.setFrequency(rs.getString("frequency"));
                    prescription.setInstructions(rs.getString("instructions"));
                    prescription.setStatus(rs.getString("status"));
                    prescription.setNotes(rs.getString("notes"));

                    return prescription;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updatePrescription(Prescription prescription) {
        String query = "UPDATE prescriptions SET patient_id=?, patient_name=?, doctor_id=?, doctor_name=?, " +
                "prescription_date=?, expiry_date=?, medications=?, dosage=?, frequency=?, " +
                "instructions=?, status=?, notes=? WHERE id=?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, prescription.getPatientId());
            pstmt.setString(2, prescription.getPatientName());
            pstmt.setLong(3, prescription.getDoctorId());
            pstmt.setString(4, prescription.getDoctorName());
            pstmt.setDate(5, Date.valueOf(prescription.getPrescriptionDate()));
            pstmt.setDate(6, Date.valueOf(prescription.getExpiryDate()));
            pstmt.setString(7, prescription.getMedications());
            pstmt.setString(8, prescription.getDosage());
            pstmt.setString(9, prescription.getFrequency());
            pstmt.setString(10, prescription.getInstructions());
            pstmt.setString(11, prescription.getStatus());
            pstmt.setString(12, prescription.getNotes());
            pstmt.setLong(13, prescription.getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePrescription(Long id) {
        String query = "DELETE FROM prescriptions WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setLong(1, id);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}