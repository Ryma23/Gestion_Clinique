package Utils;

import java.sql.*;

public class DataSource {
    private Connection con;
    private String url = "jdbc:mysql://localhost:3306/esprit1al1";
    private String user = "root";
    private String pass = "";
    private static DataSource ds;

    private DataSource() {

        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("connexion établie");
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public Connection getCon() {
        return con;
    }

    public static DataSource getInstance() {
        if (ds == null) {
            ds = new DataSource();
        }
        return ds;
    }

}
