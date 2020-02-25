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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class EditCommandeController implements Initializable {

     @FXML
    private TextField tfRef;
   
    @FXML
    private TextField tfPrix;
    @FXML
    private Button btnEdit;
 @FXML
    private ComboBox<String> cbetat;

          ObservableList<String> dbTypeList = FXCollections.observableArrayList("en cours","valider","Annuler");
    /**
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cbetat.setItems(dbTypeList);
    }    
    
     @FXML
    private void edit(ActionEvent event) {
        try {
            String refC = tfRef.getText();
          //  String etatC = tfEtat.getText();
            String prixC = tfPrix.getText();
            String etatC=(String) cbetat.getValue();
            
            
            ServiceCommande sc = new ServiceCommande();
            Date currentDatetime = new Date(System.currentTimeMillis());
        java.sql.Date sqlDate = new java.sql.Date(currentDatetime.getTime());
            float prix = Float.parseFloat(prixC);
            Commande p = new Commande(refC, etatC, prix);
            System.out.println("succ");
            
            sc.update(p);
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
alert.setTitle("Felicitation");
alert.setHeaderText("     Success       ^_^");
alert.setContentText("votre commande a été modifier");

alert.showAndWait();
            
            try {
            Parent root = FXMLLoader
        .load(getClass().getResource("AfficheCommande.fxml"));
            
            Scene scene = new Scene(root);
            Stage ps=new Stage();
            ps.setTitle("Affiche");
            ps.setScene(scene);
            ps.show();
            // fermer la stage
                    ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
    }
    public void setResNom(String resNom) {
        this.tfRef.setText(resNom);
    }

    public void setprix(float resPrenom) {
        String str = String.valueOf(resPrenom);
        this.tfPrix.setText(str);
    }
   
    

  
    
}
