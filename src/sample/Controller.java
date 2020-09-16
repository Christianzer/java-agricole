package sample;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import javax.swing.*;


public class Controller implements Initializable {


    @FXML
    private TextField textusername;

    private String query;

    @FXML
    private Button fermerbouton;

    @FXML
    private PasswordField textpaswd;

    @FXML
    private Button btnlogin ;

    @FXML
    private Label lblMessage;

    @FXML
    private ProgressBar progress;

    private Timeline timeline;
    private static final Integer STARTTIME = 2;
    private IntegerProperty timeSeconds = new SimpleIntegerProperty(STARTTIME*100);

    @FXML
    private void btnloginaction (ActionEvent event) throws IOException, SQLException {
        if(!"".equals(textusername.getText()) && !"".equals(textpaswd.getText())) {
            query = "SELECT * FROM USERS WHERE IDENTIFIANT ='" + textusername.getText() + "' AND MOT_DE_PASSE='" + textpaswd.getText() + "'";
            try {
                Statement state = ChristConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet res = state.executeQuery(query);
                if (res.next()){
                    progress.progressProperty().bind(timeSeconds.divide(STARTTIME*100.0).subtract(1).multiply(-1));
                    ((Node) (event.getSource())).getScene().getWindow().hide();
                    Parent parent = FXMLLoader.load(getClass().getResource("/sample/Listes.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(parent);
                    stage.setScene(scene);
                    stage.setTitle("MENU");
                    stage.show();
                }else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Identifiant ou Mot de Passe incorrecte");
                    alert.showAndWait();
                }
            } catch (SQLException sqlException) {
                JOptionPane.showMessageDialog(null, sqlException.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Veuillez renseignez tous les champs");
            alert.showAndWait();
        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }




}
