package sample;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ChristConnexion {
    private String url = "jdbc:oracle:thin:@localhost:1521:bdchris";
    private String user ="christian";
    private String password = "1234";
    private static Connection connect;

    private ChristConnexion(){
        try {
            Class.forName("oracle.jdbc.OracleDriver");
            connect = DriverManager.getConnection(url, user, password);
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "ERREUR DE LA CONNEXION AU SERVEUR DE LA BASE DE DONNEE", "ERREUR DE CONNEXION ! ", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static Connection getInstance(){
        if(connect == null){
            new ChristConnexion();
        }
        return connect;
    }
}




