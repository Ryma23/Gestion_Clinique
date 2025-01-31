package Models;

import java.time.LocalDateTime;

public class RendezVous {

    private long id;
    private LocalDateTime dateHeure;
    private String status;
    private int patientId;
    private int doctorId;

    // Constructeur
    public RendezVous(long id, LocalDateTime dateHeure, String status, int patientId, int doctorId) {
        this.id = id;
        this.dateHeure = dateHeure;
        this.status = status;
        this.patientId = patientId;
        this.doctorId = doctorId;
    }

    // Getters et Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDateTime getDateHeure() {
        return dateHeure;
    }

    public void setDateHeure(LocalDateTime dateHeure) {
        this.dateHeure = dateHeure;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }
}
