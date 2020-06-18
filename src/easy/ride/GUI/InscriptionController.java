/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.Utils.DataBase;
import easy.ride.entities.Utilisateur;
import easy.ride.service.ServiceUtilisateur;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.StringConverter;
/**
 * FXML Controller class
 *
 * @author suare
 */
public class InscriptionController implements Initializable {
    @FXML
    private TextField nomComplet;
    @FXML
    private Label labelnomcomplet = new Label();
    @FXML
    private Hyperlink hyperc;
    @FXML
    private PasswordField Password;
    @FXML
    private Label labelpassword = new Label();
    @FXML
    private PasswordField Passwordrepeat;
    @FXML
    private Label labelpasswordrepeat = new Label();
    @FXML
    private TextField Mail;
    @FXML
    private TextField UserName;
    @FXML
    private Label labelmail = new Label();
    @FXML
    private TextField Adresse;
    @FXML
    private Label labeladresse = new Label();
    @FXML
    private DatePicker Datens = new DatePicker();;
    @FXML
    private Label labeldatens = new Label();
    @FXML
    private TextField Tel;
    @FXML
    private Label labeltel = new Label();
    @FXML
    private Button ajouter;
    private Pattern pattern;
    private Matcher matcher;

    private static final String EMAIL_PATTERN
            = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void initialize(URL url, ResourceBundle rb) {
        pattern = Pattern.compile(EMAIL_PATTERN);
    }

    public boolean validate(final String hex) {
        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

     public static boolean isStringOnlyAlphabet(String str) 
    { 
        return ((str != null) 
                && (!str.equals("")) 
                && (str.matches("^[\\p{L} .'-]+$"))); 
    } 
    
    @FXML
    public void ajouter(ActionEvent event) {
        String nomcomplet = nomComplet.getText();
         String username = UserName.getText();
        String password = Password.getText();
        String passwordrepeat = Passwordrepeat.getText();
        String mail = Mail.getText();
        LocalDate i = Datens.getValue();
        String datens = i.toString();
        String adresse = Adresse.getText();
        String tel = Tel.getText();
        labelnomcomplet.setText("");
        labelnomcomplet.setTextFill(Color.web("#ff0000"));
        labelpassword.setText("");
        labelpassword.setTextFill(Color.web("#ff0000"));
        labelpasswordrepeat.setText("");
        labelpasswordrepeat.setTextFill(Color.web("#ff0000"));
        labelmail.setText("");
        labelmail.setTextFill(Color.web("#ff0000"));
        labeldatens.setText("");
        labeldatens.setTextFill(Color.web("#ff0000"));
        labeladresse.setText("");
        labeladresse.setTextFill(Color.web("#ff0000"));
        labeltel.setText("");
        labeltel.setTextFill(Color.web("#ff0000"));
         Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Inscription");
        alert.setContentText("Inscription est terminée avec succés!");
        ServiceUtilisateur ser = new ServiceUtilisateur();
        if(nomcomplet.isEmpty() || !this.isStringOnlyAlphabet(nomcomplet) || nomcomplet.length()<6 || nomcomplet.length()>30 ){
            labelnomcomplet.setText("Champs Non Valide");
        }else if(username.isEmpty() || !this.isStringOnlyAlphabet(username) || username.length()<4 || username.length()>30 ){
            labelnomcomplet.setText("Champs Non Valide");
        }else if(password.isEmpty() || password.length()<6 || password.length()>30){
              labelpassword.setText("Champs Non Valide");
        }else if(!password.equals(passwordrepeat)){
              labelpasswordrepeat.setText("Mot de passes incompatibles");
        }else if( mail.isEmpty() || !this.validate(mail) ){
              labelmail.setText("Champs Non Valide");
        }else if(adresse.isEmpty() ||  adresse.length()<6 || adresse.length()>50){
              labeladresse.setText("Champs Non Valide");
        }else if(tel.isEmpty()  || !tel.matches("[0-9]+") || tel.length() != 8){
              labeltel.setText("Champs Non Valide");
        }else {
            Utilisateur u1 = new Utilisateur(username, "client", password, nomcomplet, mail, tel,datens, adresse);
            try {
             System.out.println("Erreur"+datens);
             boolean b = ser.testInscription(mail);
             System.out.println(b);
                 alert.showAndWait();
             ser.ajouter(u1);
             alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Inscription");
        alert.setContentText("Inscription est terminée avec succés!");
                alert.showAndWait();         
                ser.MailInscription(mail, "smtp.google@gmail.com");
            } catch (SQLException ex) {
                Logger.getLogger(InscriptionController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    }

    @FXML
    public void Rootc(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) hyperc.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    
}