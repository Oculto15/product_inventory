package Products;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main {

    String url = "jdbc:mysql://localhost:3306/javasql";
    String user = "owner";
    String password = "Pillan!904";

    private Connection connect() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Database");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        return connection;
    }
}