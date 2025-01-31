package DAO;
import Utiles.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class AdminDAO {
    public static boolean authenticate(String username, String password) {
        String query = "SELECT * FROM admin WHERE username = ? AND password = ?";

        try (Connection conn = DataSource.getInstance().getCon();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            return rs.next(); // Si un résultat existe, l'authentification est réussie

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }}
