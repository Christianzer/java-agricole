package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListesController implements Initializable {

    @FXML
    private Button listesCand;

    @FXML
    private Button ajouterCand;

    @FXML
    private Button quitter;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void liste(ActionEvent actionEvent) {
    }

    public void ajouter(ActionEvent actionEvent) throws IOException {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
        Parent parent = FXMLLoader.load(getClass().getResource("infosperso.fxml"));
        Stage stage = new Stage();
        Scene scene1 = new Scene(parent);
        stage.setScene(scene1);
        stage.setTitle("INFORMATION CANDIDAT");
        stage.show();
    }

    public void quitter(ActionEvent actionEvent) {
    }
}
