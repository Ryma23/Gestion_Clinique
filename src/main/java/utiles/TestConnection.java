package utiles;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class TestConnection {



        public static void main(String[] args) {
            String url = "jdbc:mysql://localhost:3306/gest_clinique";
            String user = "root";
            String pass = "";

            try {
                Connection con = DriverManager.getConnection(url, user, pass);
                if (con != null) {
                    System.out.println("Connexion réussie !");
                }
            } catch (SQLException e) {
                System.out.println("Erreur de connexion : " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

