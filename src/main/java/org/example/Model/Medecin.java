package org.example.Model;

public class Medecin {
    private int id_medecin;
    private String nom;
    private String prenom;
    private int age;
    private String adresse;
    private String telephone;
    private String email;
    private String region;
    private String genre;
    private String etatCivil;

    // Constructor with ID (for retrieval and updates)
    public Medecin(int id_medecin, String nom, String prenom, int age, String adresse,
                   String telephone, String email, String region, String genre, String etatCivil) {
        this.id_medecin = id_medecin;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.region = region;
        this.genre = genre;
        this.etatCivil = etatCivil;
    }

    // Constructor without ID (for new entries)
    public Medecin(String nom, String prenom, int age, String adresse,
                   String telephone, String email, String region, String genre, String etatCivil) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.region = region;
        this.genre = genre;
        this.etatCivil = etatCivil;
    }

    // Getters and Setters
    public int getId_medecin() {
        return id_medecin;
    }

    public void setId_medecin(int id_medecin) {
        this.id_medecin = id_medecin;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(String etatCivil) {
        this.etatCivil = etatCivil;
    }

    @Override
    public String toString() {
        return "Medecin{" +
                "id_medecin=" + id_medecin +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", adresse='" + adresse + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", region='" + region + '\'' +
                ", genre='" + genre + '\'' +
                ", etatCivil='" + etatCivil + '\'' +
                '}';
    }
}