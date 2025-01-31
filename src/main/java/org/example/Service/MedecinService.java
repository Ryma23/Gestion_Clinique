package org.example.Service;

import org.example.Model.Medecin;
import org.example.Database.db_connexion;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MedecinService implements IService<Medecin> {

    private Statement ste;
    private static MedecinService ser;

    private MedecinService() {
        try {
            Connection con1 = db_connexion.getInstance().getCon();
            ste = con1.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static MedecinService getInstance() {
        if (ser == null) ser = new MedecinService();
        return ser;
    }

    @Override
    public void add(Medecin medecin) throws SQLException {
        String req = "INSERT INTO medecin (nom, prenom, age, genre, adresse, etat_civil, region, email, telephone) VALUES ('"
                + medecin.getNom() + "', '"
                + medecin.getPrenom() + "', "
                + medecin.getAge() + ", '"
                + medecin.getGenre() + "', '"
                + medecin.getAdresse() + "', '"
                + medecin.getEtatCivil() + "', '"
                + medecin.getRegion() + "', '"
                + medecin.getEmail() + "', '"
                + medecin.getTelephone() + "');";
        ste.executeUpdate(req);
    }

    @Override
    public boolean delete(Medecin medecin) throws SQLException {
        String req = "DELETE FROM medecin WHERE id_medecin = " + medecin.getId_medecin() + ";";
        int rowsDeleted = ste.executeUpdate(req);
        return rowsDeleted > 0;
    }

    @Override
    public boolean update(Medecin medecin) throws SQLException {
        String req = "UPDATE medecin SET nom = '" + medecin.getNom()
                + "', prenom = '" + medecin.getPrenom()
                + "', age = " + medecin.getAge()
                + ", genre = '" + medecin.getGenre()
                + "', adresse = '" + medecin.getAdresse()
                + "', etat_civil = '" + medecin.getEtatCivil()
                + "', region = '" + medecin.getRegion()
                + "', email = '" + medecin.getEmail()
                + "', telephone = '" + medecin.getTelephone()
                + "' WHERE id_medecin = " + medecin.getId_medecin() + ";";
        int rowsUpdated = ste.executeUpdate(req);
        return rowsUpdated > 0;
    }

    @Override
    public Medecin findById(Medecin medecin) throws SQLException {
        return null;
    }

    @Override
    public Medecin findById(int id) throws SQLException {
        String req = "SELECT * FROM medecin WHERE id_medecin = " + id + ";";
        ResultSet res = ste.executeQuery(req);
        if (res.next()) {
            return new Medecin(
                    res.getInt("id_medecin"),
                    res.getString("nom"),
                    res.getString("prenom"),
                    res.getInt("age"),
                    res.getString("genre"),
                    res.getString("adresse"),
                    res.getString("etat_civil"),
                    res.getString("region"),
                    res.getString("email"),
                    res.getString("telephone")
            );
        }
        return null;
    }

    @Override
    public List<Medecin> findAll() throws SQLException {
        List<Medecin> medecins = new ArrayList<>();
        String req = "SELECT * FROM medecin;";
        ResultSet res = ste.executeQuery(req);
        while (res.next()) {
            Medecin medecin = new Medecin(
                    res.getInt("id_medecin"),
                    res.getString("nom"),
                    res.getString("prenom"),
                    res.getInt("age"),
                    res.getString("genre"),
                    res.getString("adresse"),
                    res.getString("etat_civil"),
                    res.getString("region"),
                    res.getString("email"),
                    res.getString("telephone")
            );
            medecins.add(medecin);
        }
        return medecins;
    }
}