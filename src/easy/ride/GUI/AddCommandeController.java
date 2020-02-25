/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Commande;
import easy.ride.service.ServiceCommande;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AddCommandeController implements Initializable {

    @FXML
    private TextField tfRef;
  //  @FXML
  //  private TextField tfEtat;
    @FXML
    private TextField tfPrix;
    @FXML
    private Button bValider;
    @FXML
    private ComboBox<String> cbetat;
    @FXML
    private Label lbl;

          ObservableList<String> dbTypeList = FXCollections.observableArrayList("en cours","valider","Annuler");
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        cbetat.setItems(dbTypeList);
    }    
    
     @FXML
    private void ajouter(ActionEvent event) {
        String refC = tfRef.getText();
        //    String etatC = tfEtat.getText();
        String prixC = tfPrix.getText();
        String etatC=(String) cbetat.getValue();
        ServiceCommande sc = new ServiceCommande();
        float prix = Float.parseFloat(prixC);
        Date currentDatetime = new Date(System.currentTimeMillis());
        java.sql.Date sqlDate = new java.sql.Date(currentDatetime.getTime());
        Commande p = new Commande(refC,sqlDate, etatC, prix,1);
        try {
            sc.ajouter1(p);
        } catch (SQLException ex) {
            Logger.getLogger(AddCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Felicitation");
            alert.setHeaderText("     Success       ^_^");
            alert.setContentText("votre commande a été ajoutée");
            
            alert.showAndWait();
           
        try {
            Parent root = FXMLLoader
                    .load(getClass().getResource("AfficheCommande.fxml"));
            
            Scene scene = new Scene(root);
            Stage ps=new Stage();
            ps.setTitle("Liste Commande");
            ps.setScene(scene);
            ps.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
    
}
