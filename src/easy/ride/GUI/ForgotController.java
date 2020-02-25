/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;


import easy.ride.Utils.DataBase;
import easy.ride.service.ServiceUtilisateur;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 *
 * @author Fares
 */
public class ForgotController {

    @FXML
    private TextField Mail;
    @FXML
    private Button ajouter;
    @FXML
    private Label labellogin = new Label();
    @FXML
    private Hyperlink hyperc;
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
    public void Forgot(ActionEvent event) throws SQLException {

        String mail = Mail.getText();
        labellogin.setText("");
        labellogin.setTextFill(Color.web("#ff0000"));
        ServiceUtilisateur ser = new ServiceUtilisateur();
  ser.Recuperer(2);
        if ( mail.isEmpty() || !this.validate(mail) ) {
         labellogin.setText("Email Non Valide !");
        }else{
             ser.Mail(mail, "smtp.gmail.com");
            
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
