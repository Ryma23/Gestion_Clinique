package org.example.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class db_connexion {
    private static final Logger LOGGER = Logger.getLogger(db_connexion.class.getName());

    private Connection con;
    private static db_connexion data;

    private final String url = "jdbc:mysql://localhost:3306/gestion_clinique";
    private final String user = "root";
    private final String pwd = "";

    private db_connexion() {
        try {
            // Ensure the driver is registered
            con = DriverManager.getConnection(url, user, pwd);
            System.out.println("Connexion établie");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to establish connection", e);
        }
    }

    public Connection getCon() {
        try {
            // Check if the connection is closed or null, and if so, re-establish it
            if (con == null || con.isClosed()) {
                con = DriverManager.getConnection(url, user, pwd);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to re-establish connection", e);
        }
        return con;
    }

    public static db_connexion getInstance() {
        if (data == null) {
            data = new db_connexion();
        }
        return data;
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to close connection", e);
        }
    }
}
