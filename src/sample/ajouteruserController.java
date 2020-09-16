package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ajouteruserController implements Initializable {

    @FXML
    private Label identifiant;

    @FXML
    private Label mot_de_passe;

    @FXML
    private Button menu;


    public void retourMenu(ActionEvent actionEvent) throws IOException {
        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("Listes.fxml"));
        Stage stage = new Stage();
        Scene scene4 = new Scene(parent);
        stage.setScene(scene4);
        stage.setTitle("INSCRIPTION TERMINER");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        identifiant.setText(Main.getMail());
        mot_de_passe.setText(Main.getMdp());
    }
}
