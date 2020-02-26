/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.Utils.DataBase;
import easy.ride.entities.Utilisateur;
import easy.ride.service.ServiceUtilisateur;
import easy.ride.service.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
public class LoginController implements Initializable {

    @FXML
    private TextField tflogin;
    @FXML
    private Label labellogin = new Label();
    @FXML
    private Label labelpassword = new Label();
    @FXML
    private Hyperlink hyper;
    @FXML
    private Hyperlink hyperc;

    @FXML
    private PasswordField tfpassword;
    @FXML
    private Button ajouter;
     private Pattern pattern;
	private Matcher matcher;

	private static final String EMAIL_PATTERN = 
		"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public void initialize(URL url, ResourceBundle rb) {
     pattern = Pattern.compile(EMAIL_PATTERN);
    }

   public boolean validate(final String hex) {

		matcher = pattern.matcher(hex);
		return matcher.matches();

	}

	
    @FXML
    public void connecter(ActionEvent event) throws BackingStoreException, IOException {

        String login = tflogin.getText();
        String password = tfpassword.getText();
        ServiceUtilisateur ser = new ServiceUtilisateur();
        labellogin.setText("");
        labellogin.setTextFill(Color.web("#ff0000"));
        labelpassword.setText("");
        labelpassword.setTextFill(Color.web("#ff0000"));
        if (login.isEmpty() || !this.validate(login)) {
            labellogin.setText(" Champ Login Non Valide  !");
        } else if (password.isEmpty() || password.length()<6 || password.length() > 30) {
            labelpassword.setText(" Champ Password Non Valide !");
        } else {
            try {
                Utilisateur user = ser.connecter(login, password);
                if(user != null){
                    if(user.getRole().equals("Administrateur")){
                    UserSession userSession = UserSession.getInstace( user.getLogin(),user.getRole(),user.getId_user());
                    Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("AcceuilAdminFXML.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    // fermer la stage
                    ((Node)event.getSource()).getScene().getWindow().hide();}
                    else if (user.getRole().contains("Chef site")){
                    UserSession userSession =    UserSession.getInstace( user.getLogin(),user.getRole(),user.getId_user());
                    ChefSiteController.IdSite = String.valueOf(user.getId_user());
                        Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("ChefSite.fxml"));
                    
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    // fermer la stage
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    }
                    else{
                    UserSession userSession =    UserSession.getInstace( user.getLogin(),user.getRole(),user.getId_user());
                    
                        Stage stage = new Stage();
                    Parent root = FXMLLoader.load(getClass().getResource("AcceuilClientFXML.fxml"));
                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    // fermer la stage
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    }
                }
                
                System.out.println(""+ser.id);
            } catch (SQLException ex) {
                Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    @FXML
    public void Root(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) hyper.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Forgot.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void Rootc(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;

        stage = (Stage) hyperc.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("Inscription.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

}
