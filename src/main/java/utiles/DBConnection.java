package utiles;

import java.sql.*;

public class DBConnection {
    private static DBConnection ds;  // Singleton
    private Connection con;
    private String url = "jdbc:mysql://localhost:3306/gest_clinique";
    private String user = "root";
    private String pass = "";

    // Constructeur privé
    private DBConnection() {
        try {
            // Enregistrement du driver MySQL (si nécessaire)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion à la base de données
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connexion établie");
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Erreur de connexion à la base de données : " + e.getMessage());
        }
    }

    // Méthode pour obtenir l'instance du singleton
    public static DBConnection getInstance() {
        if (ds == null) {
            ds = new DBConnection();
        }
        return ds;
    }

    // Méthode pour récupérer la connexion
    public Connection getConnection() {
        return con;
    }
}
