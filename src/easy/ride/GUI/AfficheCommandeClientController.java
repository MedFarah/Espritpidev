/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Commande;
import easy.ride.service.ServiceCommande;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AfficheCommandeClientController implements Initializable {

   @FXML
    private TableView<Commande> table;
    @FXML
    private TableColumn<Commande,String> ref;
    @FXML
    private TableColumn<Commande,String> date;
    @FXML
    private TableColumn<Commande,String> etat;
    @FXML
    private TableColumn<Commande,String> prix;
    @FXML
    private Label lbl;
    
    ServiceCommande sc=new ServiceCommande();
    
    
    
     public void affiche(){
        ObservableList<Commande> observableList = null;
        try {
            observableList = FXCollections.observableArrayList(sc.AfficherCommandeParClient(1));
            table.setItems(observableList);
        } catch (SQLException ex) {
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(observableList);
        
        ref.setCellValueFactory(new PropertyValueFactory<>("ref"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        
         
        
        
        
        
         
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        affiche();
        
        
       
      
    }    
    
     @FXML
    private void delete(ActionEvent event) {
        
            
           
            
            
        try {
            float prix = 12;
            Commande t2 = table.getSelectionModel().getSelectedItem();
             
            
            if (t2==null)
            {
                 
               
            lbl.setText("Sélectionner la commande qui va etre annulé");
            
            }
          
           if( sc.delete(t2)==false)
           {
           lbl.setText("Tu ne peut pas annuler cette commande");
           }
           else 
           {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous sur de vouloir supprimer cet  Commande ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
alert.showAndWait();

if (alert.getResult() == ButtonType.NO || alert.getResult() == ButtonType.CANCEL) {
    sc.ajouter1(t2);
}
               
               
               lbl.setText("");
           }
            affiche();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    
}
    
}

