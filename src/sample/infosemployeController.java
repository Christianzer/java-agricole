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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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

public class infosemployeController implements Initializable {
    @FXML
    private TextField nbrEmploy;
    public String pathEmploye;
    private List<String> lstFileCertiEmpl;
    private int id_candi;

    private int id_plant;

    private String email;

    @FXML
    private TextField salaire;

    @FXML
    private TextField nbreFemme;

    @FXML
    private ChoiceBox mineur;

    @FXML
    private Button enre;

    @FXML
    private Button kitter;

    @FXML
    private Button fichier;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        mineur.getItems().add("");
        mineur.getItems().add("OUI");
        mineur.getItems().add("NON");
        id_candi = Main.getIdentifiant_cand();
        id_plant = Main.getIdentifiant_plant();
        email = Main.getMail();
        System.out.println(id_candi);
        System.out.println(id_plant);
        System.out.println(email);
        lstFileCertiEmpl = new ArrayList<>();
        lstFileCertiEmpl.add("*.img");
        lstFileCertiEmpl.add("*.png");
        lstFileCertiEmpl.add("*.jpeg");
        lstFileCertiEmpl.add("*.jpg");
        lstFileCertiEmpl.add("*.pdf");
    }

    public static String getRandomStr(int n)
    {
        //choisissez un caractére au hasard à partir de cette chaîne
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvxyz";

        StringBuilder s = new StringBuilder(n);

        for (int i = 0; i < n; i++) {
            int index = (int)(str.length() * Math.random());
            s.append(str.charAt(index));
        }
        return s.toString();
    }

    public void sauvegarder(ActionEvent actionEvent) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String datainscription = format.format(date);
        if (!"".equals(nbrEmploy.getText()) && !"".equals(nbreFemme.getText()) && !"".equals(salaire.getText()) && !"0".equals(mineur.getSelectionModel().getSelectedIndex())) {
            String query = "INSERT INTO EMPLOYE_CANDIDATS(NOMBRE_EMPLOYE, NOMBRE_FEMME, SALAIRE_MOYEN, CERTIFICAT_EMPLOYE) VALUES (?,?,?,?)";
            int nbreEmp = Integer.parseInt(nbrEmploy.getText());
            int nbreFem = Integer.parseInt(nbreFemme.getText());
            int sal = Integer.parseInt(salaire.getText());
            String password = getRandomStr(10);
            Main.setMdp(password);
            try {
                PreparedStatement preparedStatement = ChristConnexion.getInstance().prepareStatement(query);
                preparedStatement.setInt(1,nbreEmp);
                preparedStatement.setInt(2,nbreFem);
                preparedStatement.setInt(3,sal);
                preparedStatement.setString(4,pathEmploye);
                int resultat = preparedStatement.executeUpdate();
                String query2 = "SELECT MAX(ID_EMPL_CAND) AS identifiant_empl FROM EMPLOYE_CANDIDATS";
                Statement statement = ChristConnexion.getInstance().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = statement.executeQuery(query2);
                resultSet.next();
                int id_empl = Integer.parseInt(resultSet.getString("identifiant_empl"));
                if(resultat == 1){
                    String query1 = "INSERT INTO DOSSIER_INSCRIPTIONS (IDENTIFIANT_CANDIDAT, MOT_DE_PASSE, ID_PLANT, ID_CAND, ID_EMPL_CAND, DATE_INSCRIPTION) VALUES (?,?,?,?,?,?)";
                    PreparedStatement preparedStatement1 = ChristConnexion.getInstance().prepareStatement(query1);
                    preparedStatement1.setString(1,email);
                    preparedStatement1.setString(2,password);
                    preparedStatement1.setInt(3,id_plant);
                    preparedStatement1.setInt(4,id_candi);
                    preparedStatement1.setInt(5,id_empl);
                    preparedStatement1.setString(6,datainscription);
                    int resultat1 = preparedStatement1.executeUpdate();
                    if (resultat1 ==1){
                        ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                        Parent parent = FXMLLoader.load(getClass().getResource("ajouteruser.fxml"));
                        Stage stage = new Stage();
                        Scene scene3 = new Scene(parent);
                        stage.setScene(scene3);
                        stage.setTitle("INSCRIPTION TERMINER");
                        stage.show();
                    }

                }
            } catch (SQLException | IOException sqlException) {
                sqlException.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText("Veuillez remplir tous les champs");
            alert.showAndWait();

        }
    }

        public void exit(ActionEvent actionEvent) {
        }

        public void fichierImage(ActionEvent actionEvent) {
            FileChooser certifiEmpl = new FileChooser();
            certifiEmpl.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers images",lstFileCertiEmpl));
            File fceriEmpl = certifiEmpl.showOpenDialog(null);
            pathEmploye=fceriEmpl.getAbsolutePath();
        }
    }
