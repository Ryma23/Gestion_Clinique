package com.example.gestion_clinique.Entite;
public class Patient {
    private int id;
    private String username;
    private String password;
    private String email;
    private int age;
    private String region;
    private String numero_sec_sociale;
    private String genre;
    private String etat_civil;
    private String assurance;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    // Default constructor
    public Patient() {
    }

    // Full constructor
    public Patient(int id, String username, String password, String email,
                   int age, String region, String numero_sec_sociale,
                   String genre, String etat_civil, String assurance,
                   String firstName, String lastName, String phoneNumber,
                   String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.region = region;
        this.numero_sec_sociale = numero_sec_sociale;
        this.genre = genre;
        this.etat_civil = etat_civil;
        this.assurance = assurance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Constructor without ID (for new patients)
    public Patient(String username, String password, String email,
                   int age, String region, String numero_sec_sociale,
                   String genre, String etat_civil, String assurance,
                   String firstName, String lastName, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.age = age;
        this.region = region;
        this.numero_sec_sociale = numero_sec_sociale;
        this.genre = genre;
        this.etat_civil = etat_civil;
        this.assurance = assurance;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public int getAge() {
        return age;
    }

    public String getRegion() {
        return region;
    }

    public String getNumero_sec_sociale() {
        return numero_sec_sociale;
    }

    public String getGenre() {
        return genre;
    }

    public String getEtat_civil() {
        return etat_civil;
    }

    public String getAssurance() {
        return assurance;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    // Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setNumero_sec_sociale(String numero_sec_sociale) {
        this.numero_sec_sociale = numero_sec_sociale;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setEtat_civil(String etat_civil) {
        this.etat_civil = etat_civil;
    }

    public void setAssurance(String assurance) {
        this.assurance = assurance;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // toString method
    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", age=" + age +
                ", region='" + region + '\'' +
                ", numero_sec_sociale='" + numero_sec_sociale + '\'' +
                ", genre='" + genre + '\'' +
                ", etat_civil='" + etat_civil + '\'' +
                ", assurance='" + assurance + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}