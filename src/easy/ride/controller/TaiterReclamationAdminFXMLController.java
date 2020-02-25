/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.controller;

import easy.ride.EasyRide;
import easy.ride.entities.Reclamation;
import easy.ride.service.Mailing;
import easy.ride.service.ServiceReclamation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class TaiterReclamationAdminFXMLController implements Initializable {

    @FXML
    private TextField typeReclamationtxt;
    @FXML
    private TextArea descriptiontxt;
    @FXML
    private TextField objettxt;
    @FXML
    private TextField DateReclamationtxt;
    @FXML
    private TextField statustxt;
    @FXML
    private TextField mailTxt;
    @FXML
    private TextField nomClientTxt;
    @FXML
    private ImageView imageView;
    @FXML
    private Button repondre;
    @FXML
    private Button traiter;
    
    Reclamation reclamation;
    ServiceReclamation serviceReclamation = new ServiceReclamation();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
    }    

    
    @FXML
    private void OnRepondre(ActionEvent event) throws IOException {
         Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(EasyRide.class.getResource("GUI/MailFXML.fxml"));
        // fermer la stage
//        ((Node)event.getSource()).getScene().getWindow().hide();

    Parent root1 = (Parent) fxmlLoader.load();
    stage.setScene(new Scene(root1));  
    MailFXMLController c = fxmlLoader.getController();
    c.setReclamation(getReclamation());
    stage.setTitle("E-mail");
    stage.show();
    }
    
 
    @FXML
    private void OnTraite(ActionEvent event) throws SQLException {
        getReclamation().setStatus("Traité");
        serviceReclamation.update(reclamation);
        //Notif Client
        Mailing.send(getReclamation().getEmail(), "Reclamation Traité", "Votre reclamation de type "+getReclamation().getTypeReclamation()+" a été traité avec success", "", "");
        Notifications n = Notifications.create()
                                            .title("Success")
                                            .text("Operation effectue")
                                            .graphic(null)
                                            .position(Pos.CENTER)
                                            .hideAfter(Duration.seconds(5));
                                    n.showConfirm();
     
    Stage stage = new Stage();
    stage.close();
    }

    public Reclamation getReclamation() {
        return reclamation;
    }

    public void setReclamation(Reclamation reclamation) {
        this.reclamation = reclamation;
        objettxt.setText(reclamation.getObjet()); 
        objettxt.setDisable(true);
        typeReclamationtxt.setText(reclamation.getTypeReclamation());
        typeReclamationtxt.setDisable(true);
        descriptiontxt.setText(reclamation.getDescription());
        descriptiontxt.setDisable(true);
        mailTxt.setText(reclamation.getEmail());
        mailTxt.setDisable(true);
        DateReclamationtxt.setText(reclamation.getDateReclamation().toString());
        DateReclamationtxt.setDisable(true);
        statustxt.setText(reclamation.getStatus());
        statustxt.setDisable(true);
        String im;
         if(reclamation.getImage().equals("No picture"))
             { im ="C:\\Users\\ASUS\\Desktop\\images.jpg";}
         else
             {  im = reclamation.getImage().replace("+","\\");}
                                              
         FileInputStream input=null;
                                    
                       //  System.out.println(im);
        try {
            input = new FileInputStream(im);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TaiterReclamationAdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
                        Image image7 = new Image(input);
                            imageView.setImage(image7);
    }
    
    
}
