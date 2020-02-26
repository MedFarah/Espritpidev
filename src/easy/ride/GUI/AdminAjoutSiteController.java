/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Site;
import easy.ride.entities.User;
import easy.ride.entities.Utilisateur;
import easy.ride.service.ServiceRetours;
import easy.ride.service.ServiceSite;
import easy.ride.service.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class AdminAjoutSiteController implements Initializable {

    @FXML
    private ChoiceBox<String> ListId;
    @FXML
    private TextField Emplacement;
    @FXML
    private TextField Long;
    @FXML
    private TextField Lat;
    @FXML
    private Label NomComplet;
    @FXML
    private Label Mail;
    @FXML
    private Label Address;
    @FXML
    private Label tel;
    
    ServiceUser su = new ServiceUser();
    ServiceSite ss = new ServiceSite();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            List<Integer> arr = new ArrayList<>(su.GetAllUserId());
            ArrayList<String> Lid = new ArrayList<>();
            for(int i=0; i<arr.size(); i++){
                Lid.add(String.valueOf(arr.get(i)));
            }
            ObservableList<String> OLid = FXCollections.observableArrayList(Lid);
            ListId.setItems(OLid);
            ListId.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                Utilisateur u1 = new Utilisateur();
                User u = new User();
                try {
                    u1 = su.GetAllInfoUser(Integer.valueOf(ListId.getItems().get((Integer) number2)));
                } catch (SQLException ex) {
                    Logger.getLogger(AdminAjoutSiteController.class.getName()).log(Level.SEVERE, null, ex);
                }
                NomComplet.setText(u1.getNomcomplet());
                Mail.setText(u1.getMail());
                Address.setText(u1.getAdresse());
                tel.setText(u1.getTel());
            }
            });
        } catch (SQLException ex) {
            Logger.getLogger(AdminAjoutSiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    private void Confirmer(ActionEvent event) throws SQLException, IOException {
        Site site = new Site();
        site.setEmplacement(Emplacement.getText());
        site.setX(Double.valueOf(Long.getText()));
        site.setY(Double.valueOf(Lat.getText()));
        ss.ajouter(site);
        su.UpdateRoleUser(ss.getSiteId(Emplacement.getText()),Integer.valueOf(ListId.getValue()));
        System.out.println("Site ajouté + Role modifié");
    }

    
}
