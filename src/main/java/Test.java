
import java.sql.*;

public class Test {

    private static Connection con;
    private static String url = "jdbc:mysql://localhost:3306/esprit1al1";
    private static String user = "root";
    private static String pass = "";
    private static Statement stmt;

    public static void main(String[] args) {

        try {
            con = DriverManager.getConnection(url, user, pass);
            System.out.println(con);
            System.out.println("connexion établie");
        } catch (SQLException e) {
            System.out.println(e);
        }
        try {
            stmt = con.createStatement();
        } catch (SQLException e) {
            System.out.println(e);
        }

        String req = "INSERT INTO `personne` (`id`, `nom`, `prenom`, `age`) VALUES (NULL, 'hjiri', 'wiem', '20');";

        try {
          int res=  stmt.executeUpdate(req);
            System.out.println("personne ajoutée");
            System.out.println(res);
        } catch (SQLException e) {
            System.out.println(e);
        }

        try {
            ResultSet resultSet= stmt.executeQuery("select * from personne");

            while (resultSet.next()) {

                int id=resultSet.getInt(1);
                String nom=resultSet.getString("nom");
                String prenom=resultSet.getString(3);
                int age=resultSet.getInt("age");
                System.out.println("id :"+id+" nom :"+nom+" prenom :"+prenom+" age :"+age);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

    }
}
