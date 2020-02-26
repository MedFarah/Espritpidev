/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Detail_location;
import easy.ride.service.ServiceDetail_location;
import easy.ride.service.ServiceLogs;
import easy.ride.service.ServiceSite;
import easy.ride.service.ServiceType;
import easy.ride.service.ServiceUser;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class UserchoixController implements Initializable {

    public static int idClient;
    @FXML
    private ChoiceBox<String> type;
    @FXML
    private DatePicker datedebut;
    @FXML
    private DatePicker datefin;
    @FXML
    private ChoiceBox<String> site;
    @FXML
    private Label Nomclient;
    
    ServiceType st = new ServiceType();
    ServiceSite ss = new ServiceSite();
    ServiceDetail_location sdl = new ServiceDetail_location();
    ServiceUser su = new ServiceUser();
    ServiceLogs sl = new ServiceLogs();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        try {
            ArrayList<String> LtypeNom = new ArrayList<>(st.getAllTypeName());
            ObservableList<String> OLtypeNom = FXCollections.observableArrayList(LtypeNom);
            type.setItems(OLtypeNom);
        } catch (SQLException ex) {
            Logger.getLogger(UserchoixController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            ArrayList<String> LsiteNom = new ArrayList<>(ss.getAllSiteNoms());
            ObservableList<String> OLsiteNom = FXCollections.observableArrayList(LsiteNom);
            site.setItems(OLsiteNom);
        } catch (SQLException ex) {
            Logger.getLogger(UserchoixController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        datedebut.valueProperty().addListener((e) -> {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date();
            if (datedebut.getValue().toString().compareTo(dateFormat.format(date)) < 0) {
                Alert a = new Alert(Alert.AlertType.ERROR, "Entrer une date de début valide", ButtonType.OK);
                a.showAndWait();
            }
        });
        
        datefin.valueProperty().addListener((e) -> {
            if (datedebut.getValue() == null) {
                Alert a = new Alert(Alert.AlertType.WARNING, "Entrer une date de début", ButtonType.OK);
                a.showAndWait();
            } else {
                if (datefin.getValue().toString().compareTo(datedebut.getValue().toString()) < 0) {
                    Alert a = new Alert(Alert.AlertType.ERROR, "Entrer une date de fin valide", ButtonType.OK);
                    a.showAndWait();
                }
            }

        });
    }

    @FXML
    private void ConfirmerLocation(ActionEvent event) throws SQLException, IOException {
        if (datedebut.getValue() == null || datefin.getValue() == null || type.getValue() == null || site.getValue() == null) {
            Alert a = new Alert(Alert.AlertType.ERROR, "Veuillez remplir tous les champs", ButtonType.OK);
            a.showAndWait();
        } else {
            
            LocalDate localDate = datedebut.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            java.util.Date date = Date.from(instant);
            java.sql.Date dtSql = new java.sql.Date(date.getTime());

            localDate = datefin.getValue();
            instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            date = Date.from(instant);
            java.sql.Date dtSql1 = new java.sql.Date(date.getTime());

            ServiceType st = new ServiceType();
            String id_type ;
            id_type = String.valueOf(st.getTypeId(type.getValue()));
            String id_site;
            id_site = String.valueOf(ss.getSiteId(site.getValue()));
            Detail_location d = new Detail_location(idClient, id_type, id_site,dtSql, dtSql1);
            sdl.ajouter(d);

            String message = "Vous venez de louer un vélo";
            sl.send("Felicitaion", message, su.getUserMail(idClient));
            ((Node)event.getSource()).getScene().getWindow().hide();
            
            /*FXMLLoader loader = new FXMLLoader(getClass().getResource("UserAcceuil.fxml"));
            Parent root = loader.load();
            datedebut.getScene().setRoot(root);*/
        }
    }

    @FXML
    private void ViewMap(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("map.fxml"));
        Parent root = loader.load();
        datedebut.getScene().setRoot(root);
    }
    
}
