/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Site;
import easy.ride.service.ServiceDetail_location;
import easy.ride.service.ServiceLogs;
import easy.ride.service.ServiceType;
import easy.ride.service.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class ChefSiteController implements Initializable {

    public static String IdChef;
    public static String role;
    @FXML
    private TableColumn<Site, Number> coloneid;
    @FXML
    private TableColumn<Site, Number> coloneiduser;
    @FXML
    private TableColumn<Site, Number> colonetype;
    @FXML
    private TableColumn<Site, Date> colonedatedebut;
    @FXML
    private TableColumn<Site, Date> colonedatefin;
    @FXML
    private TableColumn<Site, String> colonestatut;
    @FXML
    private TableView<Site> affichage;
    @FXML
    private Label Site;
    
    ServiceDetail_location sdl = new ServiceDetail_location();
    ServiceType st = new ServiceType();
    ServiceUser su = new ServiceUser();
    ServiceLogs sl = new ServiceLogs();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO

            Site.setText(su.getUserNomComplet(Integer.valueOf(IdChef)));
        } catch (SQLException ex) {
            Logger.getLogger(ChefSiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ObservableList listdl = FXCollections.observableArrayList();
        
        try {
            listdl = sdl.readAllChefSite(Integer.valueOf(role.substring(role.lastIndexOf(" "), role.length()).trim()));
        } catch (SQLException ex) {
            Logger.getLogger(ChefSiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        try {
            sl.writelogs("Chef site "+role.substring(role.lastIndexOf(" "), role.length()).trim()
                    +" a affiché ses données");
        } catch (IOException ex) {
            Logger.getLogger(ChefSiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        affichage.setItems(null);
        affichage.setItems(listdl);
        coloneid.setCellValueFactory(new PropertyValueFactory<>("id"));
        coloneiduser.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        colonetype.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        colonedatedebut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        colonedatefin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        colonestatut.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        String msg = "";
        try {
            msg = "vous avez "+sdl.GetCountRetour(Integer.valueOf(IdChef))+" velo(s) à retourner aujourd'hui";
        } catch (SQLException ex) {
            Logger.getLogger(ChefSiteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Notifications notificationBuilder = Notifications.create()
                .title("          Hello")
                .text(msg)
                .graphic(null)
                .hideAfter(javafx.util.Duration.seconds(5))
                .position(Pos.TOP_RIGHT);
        notificationBuilder.showConfirm();
        
    }    

    @FXML
    private void affichageretours(ActionEvent event) throws IOException {
        RetoursController.idSite = Integer.valueOf(role.substring(role.lastIndexOf(" "), role.length()).trim());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Retours.fxml"));
        Parent root = loader.load();
        affichage.getScene().setRoot(root);
    }
    
}
