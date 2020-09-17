package sample;
import com.sun.glass.ui.CommonDialogs;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
public class infospersoController implements Initializable {

    public TextField imagePhoto;
    public TextField imagePiece;
    public TextField imageDiplome;
    private List<String> lstFile;
    private String pathPhoto,pathPiece,pathDiplome;
    @FXML
    private TextField contatc;

    @FXML
    private TextField nom;

    @FXML
    private TextField lieu;

    @FXML
    private TextField email;

    @FXML
    private TextField num_piece;

    @FXML
    private TextField numDiplome;

    @FXML
    private TextField prenoms;

    @FXML
    private TextField nationalite;

    @FXML
    private Button quitter;

    @FXML
    private Button suivant;

    @FXML
    private DatePicker date_naiss;

    @FXML
    private ComboBox type_piece;

    @FXML
    private ComboBox typeDiplome;

    @FXML
    private Button photo;

    @FXML
    private Button image_piece;

    @FXML
    private Button image_diplome;


    private void getPiece() {
        try{
            String query1 = "SELECT * FROM TYPE_PIECES";
            Statement statement =ChristConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res1 = statement.executeQuery(query1);
            type_piece.getItems().add("");
            while (res1.next()){
                type_piece.getItems().add(res1.getString("libelle_piece"));
            }
            type_piece.getSelectionModel().select(14);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }


    }

    private void getDiplome() {
        try{
            String query2 = "SELECT * FROM TYPE_DIPLOMES";
            Statement statement =ChristConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            ResultSet res2 = statement.executeQuery(query2);
            typeDiplome.getItems().add("");
            while (res2.next()){
                typeDiplome.getItems().add(res2.getString("libelle_type_diplomes"));
            }
            typeDiplome.getSelectionModel().select(14);
        } catch (SQLException exception) {
            JOptionPane.showMessageDialog(null, exception.getMessage());
        }


    }



    public void FilePhoto(ActionEvent actionEvent) {
        FileChooser photo = new FileChooser();
        photo.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers images",lstFile));
        File fphoto = photo.showOpenDialog(null);
        pathPhoto=fphoto.getAbsolutePath();
        if (fphoto !=null){
            imagePhoto.setText("Fichier selectionner:" +pathPhoto);
        }

    }

    public void Filepiece(ActionEvent actionEvent) {
        FileChooser piece = new FileChooser();
        piece.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers images",lstFile));
        File fpiece = piece.showOpenDialog(null);
        pathPiece=fpiece.getAbsolutePath();
        if (fpiece !=null){
            imagePiece.setText("Fichier selectionner:" +pathPiece);
        }

    }

    public void FileDiplome(ActionEvent actionEvent) {
        FileChooser diplome = new FileChooser();
        diplome.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers images",lstFile));
        File fdiplome = diplome.showOpenDialog(null);
        pathDiplome=fdiplome.getAbsolutePath();
        if (fdiplome !=null){
            imageDiplome.setText("Fichier selectionner:" +pathDiplome);
        }
    }

    public void enrePerso(ActionEvent actionEvent) throws SQLException, IOException {
        if (!"".equals(nom.getText()) && !"".equals(prenoms.getText()) && !"".equals(date_naiss.getValue()) && !"".equals(lieu.getText()) && !"".equals(contatc.getText()) && !"".equals(email.getText())&& !"".equals(num_piece.getText()) && !"".equals(numDiplome.getText())){
            try {
                Main.setMail(email.getText());
                String query = "INSERT INTO CANDIDATS(PHOTO_CANDIDAT, NOM_CANDIDAT, PRENOM_CANDIDAT, DATE_NAISS_CANDIDAT, LIEU_NAIS_CANDIDAT, NATIONALITE_CANDIDAT, CONTACT_CANDIDAT, MAIL_CANDIDAT) VALUES (?,?,?,?,?,?,?,?)";
                PreparedStatement preparedStatement = ChristConnexion.getInstance().prepareStatement(query);
                if(!"0".equals(type_piece.getSelectionModel().getSelectedIndex()) && !"0".equals(typeDiplome.getSelectionModel().getSelectedIndex())) {
                    preparedStatement.setString(1,pathPhoto);
                    preparedStatement.setString(2, nom.getText());
                    preparedStatement.setString(3,prenoms.getText());
                    preparedStatement.setString(4,date_naiss.getValue().toString());
                    preparedStatement.setString(5,lieu.getText());
                    preparedStatement.setString(6,nationalite.getText());
                    preparedStatement.setString(7,contatc.getText());
                    preparedStatement.setString(8,email.getText());
                    int resultatCand = preparedStatement.executeUpdate();
                    if (resultatCand==1){
                        String query1 = "SELECT MAX(id_cand) as identifiant_cand FROM CANDIDATS";
                        try {
                            Statement statement = ChristConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                            ResultSet resultSet = statement.executeQuery(query1);
                            resultSet.next();
                            int id_cand = Integer.parseInt(resultSet.getString("identifiant_cand"));
                            Main.setIdentifiant_cand(id_cand);
                            try{
                                String queryPiece = "INSERT INTO AVOIR_PIECES(PIECES, ID_CAND, NUM_PI, IMG_PI) VALUES (?,?,?,?)";
                                PreparedStatement preparedStatement1 = ChristConnexion.getInstance().prepareStatement(queryPiece);
                                preparedStatement1.setInt(1,type_piece.getSelectionModel().getSelectedIndex());
                                preparedStatement1.setInt(2,id_cand);
                                preparedStatement1.setString(3,num_piece.getText());
                                preparedStatement1.setString(4,pathPiece);
                                int resultatPiece = preparedStatement1.executeUpdate();
                                if (resultatPiece == 1){
                                        String queryDiplome = "INSERT INTO AVOIR_DIPLOMES (DIPLOMES, ID_CAND, NUM_DIP, IMG_DIP) VALUES (?,?,?,?)";
                                        PreparedStatement preparedStatement2 = ChristConnexion.getInstance().prepareStatement(queryDiplome);
                                        preparedStatement2.setInt(1,typeDiplome.getSelectionModel().getSelectedIndex());
                                        preparedStatement2.setInt(2,id_cand);
                                        preparedStatement2.setString(3,numDiplome.getText());
                                        preparedStatement2.setString(4,pathDiplome);
                                        int resultatDiplome = preparedStatement2.executeUpdate();
                                        if (resultatDiplome == 1){
                                            ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                                            Parent parent = FXMLLoader.load(getClass().getResource("infoplantation.fxml"));
                                            Stage stage = new Stage();
                                            Scene scene2 = new Scene(parent);
                                            stage.setScene(scene2);
                                            stage.setTitle("INFORMATION PLANTATION CANDIDAT");
                                            stage.show();
                                        }
                                }

                            } catch (SQLException sqlException) {
                                JOptionPane.showMessageDialog(null, sqlException.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
                            }

                        }catch (SQLException sqlException){
                            JOptionPane.showMessageDialog(null, sqlException.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
                        }

                    }else {
                        JOptionPane.showMessageDialog(null,"Erreur insertion candidat");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Veuillez renseignez les types");
                }
            } catch (SQLException exception) {
                JOptionPane.showMessageDialog(null, exception.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);
            } catch (HeadlessException ehe) {
                JOptionPane.showMessageDialog(null, ehe.getMessage(), "ERREUR ! ", JOptionPane.ERROR_MESSAGE);

            }

        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();
        }


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getPiece();
        getDiplome();
        lstFile = new ArrayList<>();
        lstFile.add("*.img");
        lstFile.add("*.png");
        lstFile.add("*.jpeg");
        lstFile.add("*.jpg");

    }
    private void actualiser(){



    }


}
