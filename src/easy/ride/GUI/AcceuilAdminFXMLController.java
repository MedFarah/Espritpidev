/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.service.ServiceCommande;
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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class AcceuilAdminFXMLController implements Initializable {

    @FXML
    private Label lblAdmin;
    @FXML
    private Button btnReclamation;
    @FXML
    private Button btnEvents;
    @FXML
    private Label txtEvents;
    @FXML
    private Label txtCommande;
    @FXML
    private Label txtLocation;
    @FXML
    private Label txtReclamation;
    @FXML
    private AnchorPane anchorPaneRootAdmin;
    ServiceReclamation serviceReclamation = new ServiceReclamation();
    Serviceevenements se = new Serviceevenements();
    ServiceCommande sc = new ServiceCommande();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        int nbrReclamation = 0;
        int nbrEvents = 0;
        int nbrCommande = 0;
        //nbr reclamations
        nbrReclamation=serviceReclamation.get_Number_Reclamation();
        txtReclamation.setText(Integer.toString(nbrReclamation));
        //nbr events
        nbrEvents= se.get_Number_Reclamation();
        txtEvents.setText(Integer.toString(nbrEvents));
        //nbr commande
        nbrCommande= sc.get_Number_Reclamation();
        txtCommande.setText(Integer.toString(nbrEvents));

    }    

    @FXML
    private void OnClickBtnReclamation(ActionEvent event) throws IOException {
        anchorPaneRootAdmin.getChildren().clear();
        AnchorPane anchor = FXMLLoader.load(getClass().getResource("IndexReclamationAdminFXML.fxml"));
        anchorPaneRootAdmin.getChildren().addAll(anchor);
    }
    @FXML
    private void OnClickBtnEvenement(ActionEvent event) throws IOException {
        anchorPaneRootAdmin.getChildren().clear();
        AnchorPane anchor = FXMLLoader.load(getClass().getResource("event.fxml"));
        anchorPaneRootAdmin.getChildren().addAll(anchor);
    }
    @FXML
    private void OnClickBtnProduit(ActionEvent event) throws IOException {
        anchorPaneRootAdmin.getChildren().clear();
        AnchorPane anchor = FXMLLoader.load(getClass().getResource("produit.fxml"));
       
        anchorPaneRootAdmin.getChildren().addAll(anchor);
    }
    @FXML
    private void OnClickBtnCommande(ActionEvent event) throws IOException {
        anchorPaneRootAdmin.getChildren().clear();
        AnchorPane anchor = FXMLLoader.load(getClass().getResource("AfficheCommande.fxml"));
       
        anchorPaneRootAdmin.getChildren().addAll(anchor);
    }
    
    @FXML
    private void OnClickBtnSettings(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("Settings.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
