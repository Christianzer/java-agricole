package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class infoplantationController implements Initializable {
    private List<String> lstFileCertificat;
    private String pathCertificat;

    @FXML
    private TextField localisation;

    @FXML
    private TextField superficie;

    @FXML
    private Button suivantPl;

    @FXML
    private Button quitter;

    @FXML
    private Button certificat;

    @FXML
    private ComboBox methode;

    @FXML
    private ComboBox culture;




    public void getCulture(){
        try{
            String query1 = "SELECT * FROM TYPE_CULTURES";
            Statement statement =ChristConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res1 = statement.executeQuery(query1);
            culture.getItems().add("");
            while (res1.next()){
                culture.getItems().add(res1.getString("libelle_type_cultures"));
            }
            culture.getSelectionModel().select(14);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }

    public void getMethod(){
        try{
            String query1 = "SELECT * FROM METHODE_CULTURES ";
            Statement statement =ChristConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res1 = statement.executeQuery(query1);
            methode.getItems().add("");
            while (res1.next()){
                methode.getItems().add(res1.getString("libelle_methodes_cultures"));
            }
            methode.getSelectionModel().select(14);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCulture();
        getMethod();
        lstFileCertificat = new ArrayList<>();
        lstFileCertificat.add("*.img");
        lstFileCertificat.add("*.png");
        lstFileCertificat.add("*.jpeg");
        lstFileCertificat.add("*.jpg");
    }

    public void suivantPlant(ActionEvent actionEvent) {
        if (!"".equals(localisation.getText()) && !"".equals(superficie.getText()) && !"".equals(localisation.getText()) && !"0".equals(culture.getSelectionModel().getSelectedIndex()) && !"0".equals(methode.getSelectionModel().getSelectedIndex())){
            try {
                String query = "INSERT INTO PLANTATION_CANDIDATS(LOCALISATION_PLANTATION, SUPERFICIE_PLANTATION, CERTIFICAT_PROPRIETE) VALUES (?,?,?)";
                PreparedStatement preparedStatement = ChristConnexion.getInstance().prepareStatement(query);
                preparedStatement.setString(1,localisation.getText());
                preparedStatement.setString(2,superficie.getText());
                preparedStatement.setString(3,pathCertificat);
                int resultat = preparedStatement.executeUpdate();
                if (resultat==1){
                    String query1 = "SELECT MAX(ID_PLANT) AS IDENTIFIANT_PLANT FROM PLANTATION_CANDIDATS";
                    Statement statement = ChristConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                    ResultSet resultSet = statement.executeQuery(query1);
                    resultSet.next();
                    int id_plant = Integer.parseInt(resultSet.getString("identifiant_plant"));
                    Main.setIdentifiant_plant(id_plant);
                    String query2 = "INSERT INTO AVOIR_CULTURES(ID_PLANT, ID_TYPE_CULT) VALUES (?,?)";
                    PreparedStatement preparedStatement1 = ChristConnexion.getInstance().prepareStatement(query2);
                    preparedStatement1.setInt(1,id_plant);
                    preparedStatement1.setInt(2,culture.getSelectionModel().getSelectedIndex());
                    int resultat1 = preparedStatement1.executeUpdate();
                    if (resultat1 == 1){
                        String query3 = "INSERT INTO AVOIR_METHODES(ID_PLANT, ID_METHODE) VALUES (?,?)";
                        PreparedStatement preparedStatement2 = ChristConnexion.getInstance().prepareStatement(query3);
                        preparedStatement2.setInt(1,id_plant);
                        preparedStatement2.setInt(2,methode.getSelectionModel().getSelectedIndex());
                        int resultat2 = preparedStatement2.executeUpdate();
                        if (resultat2 == 1){
                            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                            Parent parent = FXMLLoader.load(getClass().getResource("infosemploye.fxml"));
                            Stage stage = new Stage();
                            Scene scene3 = new Scene(parent);
                            stage.setScene(scene3);
                            stage.setTitle("INFORMATION EMPLOYE CANDIDAT");
                            stage.show();
                        }else {
                            JOptionPane.showMessageDialog(null,"Erreur insertion methode");
                        }
                    }else {
                        JOptionPane.showMessageDialog(null,"Erreur insertion culture");
                    }
                }else {
                    JOptionPane.showMessageDialog(null,"Erreur insertion plantation");
                }
            } catch (SQLException | IOException sqlException) {
                JOptionPane.showMessageDialog(null, sqlException.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
            }

        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();
        }
    }

    public void quitter(ActionEvent actionEvent) {
    }

    public void certificatImg(ActionEvent actionEvent) {
        FileChooser certifi = new FileChooser();
        certifi.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers images",lstFileCertificat));
        File fcerif = certifi.showOpenDialog(null);
        pathCertificat=fcerif.getAbsolutePath();
    }
}
