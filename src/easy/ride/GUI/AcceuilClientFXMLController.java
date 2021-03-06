/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.EasyRide;
import easy.ride.controller.MailFXMLController;
import easy.ride.service.ServiceCommande;
import easy.ride.service.ServiceMaintenance;
import easy.ride.service.ServiceReclamation;
import easy.ride.service.Serviceevenements;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AcceuilClientFXMLController implements Initializable {

    @FXML
    private Button btnReclamation;
    @FXML
    private Text txtEvents;
    @FXML
    private Text txtCommande;
    @FXML
    private Text txtLocation;
    @FXML
    private Text txtReclamation;
    ServiceReclamation serviceReclamation = new ServiceReclamation();
    Serviceevenements se = new Serviceevenements();
    ServiceCommande sc = new ServiceCommande();
    ServiceMaintenance sm = new ServiceMaintenance();
    @FXML
    private Label txtClient;
    @FXML
    private Button btnCommande;
    @FXML
    private Button btnEvents;
    @FXML
    private Button btnLocation;



    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int nbrReclamation = 0;
        int nbrEvents = 0;
        int nbrCommande = 0;
         int nbrMaintenance = 0;
        //nbr reclamations
        nbrReclamation=serviceReclamation.get_Number_Reclamation();
        txtReclamation.setText(Integer.toString(nbrReclamation));
        //nbr events
        nbrEvents= se.get_Number_Reclamation();
        txtEvents.setText(Integer.toString(nbrEvents));
        //nbr commande
        nbrCommande= sc.get_Number_Reclamation();
        txtCommande.setText(Integer.toString(nbrCommande));
        //nbr commande
        nbrMaintenance= sm.get_Number_Maintenance();
        txtLocation.setText(Integer.toString(nbrMaintenance));
    }    

    @FXML
    private void OnClickBtnReclamation(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("IndexReclamationClientFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Reclamation");
        stage.setScene(scene);
        stage.show();
        
        
        
    }
     @FXML
    private void OnClickBtnEvenement(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("gestioneventclient.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void OnClickBtnCommande(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("AfficheCommandeClient.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void OnClickBtnSettings(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("Settings.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML
    private void OnClickBtnMaintenance(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("MaintenanceFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void OnClickBtnLocation(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("UserAcceuil.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
