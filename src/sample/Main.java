package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    private static int identifiant_cand;
    private static int identifiant_plant;
    private static String mail;
    private static String mdp;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Connexion");
        primaryStage.setResizable(false);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }

    public static String getMdp() {
        return mdp;
    }

    public static void setMdp(String mdp) {
        Main.mdp = mdp;
    }

    public static String getMail() {
        return mail;
    }

    public static void setMail(String mail) {
        Main.mail = mail;
    }

    public static int getIdentifiant_cand() {
        return identifiant_cand;
    }

    public static int getIdentifiant_plant() {
        return identifiant_plant;
    }

    public static void setIdentifiant_cand(int identifiant_cand) {
        Main.identifiant_cand = identifiant_cand;
    }

    public static void setIdentifiant_plant(int identifiant_plant) {
        Main.identifiant_plant = identifiant_plant;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
