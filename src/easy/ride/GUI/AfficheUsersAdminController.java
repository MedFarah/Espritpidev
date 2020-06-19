        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Utilisateur;
import easy.ride.service.ServiceUtilisateur;
import easy.ride.service.UserSession;
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
public class AfficheUsersAdminController implements Initializable {

   @FXML
    private TableView<Utilisateur> table;
    @FXML
    private TableColumn<Utilisateur,String> username;
    @FXML
    private TableColumn<Utilisateur,String> nomprenom;
    @FXML
    private TableColumn<Utilisateur,String> mail;
    @FXML
    private TableColumn<Utilisateur,String> Tel;
    @FXML
    private Label lbl;
    
    ServiceUtilisateur sc=new ServiceUtilisateur();
    
    
    
     public void affiche(){
        ObservableList<Utilisateur> observableList = null;
        try {
            System.out.println(" le service a realise "+sc.AfficherUsersParAdmin(UserSession.getInstace("", "",0).getId()));
            observableList = FXCollections.observableArrayList(sc.AfficherUsersParAdmin(UserSession.getInstace("", "",0).getId()));
            table.setItems(observableList);
        } catch (SQLException ex) {
           System.out.println("exception");
             Logger.getLogger(AfficheUsersAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(observableList);
        
        username.setCellValueFactory(new PropertyValueFactory<>("login"));
        nomprenom.setCellValueFactory(new PropertyValueFactory<>("nomcomplet"));
        Tel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        mail.setCellValueFactory(new PropertyValueFactory<>("mail"));
       
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
            Utilisateur t2 = table.getSelectionModel().getSelectedItem();
             
            if (t2==null)
            {
                      
            lbl.setText("Sélectionner l'utilisateur qui va etre annulé");
            
            }
         
            if( sc.delete(t2)==false)
           {
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous sur de vouloir supprimer cet  Utilisateur ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
alert.showAndWait();

if (alert.getResult() == ButtonType.NO || alert.getResult() == ButtonType.CANCEL) {
    sc.delete(t2);
}
               
               
               lbl.setText("");
           }
            affiche();
            
        } catch (SQLException ex) {
            
            Logger.getLogger(AfficheUsersAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
}
    
}

