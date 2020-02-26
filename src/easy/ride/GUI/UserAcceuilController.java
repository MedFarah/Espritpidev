/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Detail_location;
import easy.ride.service.ServiceDetail_location;
import easy.ride.service.ServiceType;
import easy.ride.service.ServiceUser;
        
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class UserAcceuilController implements Initializable {

    public static int idUser;
    @FXML
    private Label nom;
    @FXML
    private TableView<Detail_location> historiqueuser;
    @FXML
    private TableColumn<Detail_location, Number> type;
    @FXML
    private TableColumn<Detail_location, Date> date_debut;
    @FXML
    private TableColumn<Detail_location, Date> date_fin;
    @FXML
    private TableColumn<Detail_location, String> site;
    @FXML
    private Button annulerLcation;
    @FXML
    private Label datedebut;
    @FXML
    private Label datefin;
    @FXML
    private GridPane locationencours;
    @FXML
    private Label labelsite;
    @FXML
    private Label labeltype;
    
    ServiceUser su = new ServiceUser();
    ServiceDetail_location sdl = new ServiceDetail_location();
    ServiceType st = new ServiceType();
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            if (sdl.getValidLocation(idUser)==false){
                Detail_location dl = new Detail_location();
                datedebut.setText(sdl.getLocationEnCours(idUser).getDate_debut().toString());
                datefin.setText(sdl.getLocationEnCours(idUser).getDate_fin().toString());
                labelsite.setText(sdl.getLocationEnCours(idUser).getId_site());
                labeltype.setText(sdl.getLocationEnCours(idUser).getId_type());
                       
            }else{
                locationencours.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserAcceuilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            nom.setText(su.GetUserNomPrenon(idUser));
        } catch (SQLException ex) {
            Logger.getLogger(UserAcceuilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            if (sdl.getValidLocation(idUser) == true){
                annulerLcation.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserAcceuilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ObservableList listdl = FXCollections.observableArrayList();
        
        try {
            listdl = sdl.getAllHistoryUser(idUser);
        } catch (SQLException ex) {
            Logger.getLogger(UserAcceuilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        historiqueuser.setItems(null);
        historiqueuser.setItems(listdl);
        site.setCellValueFactory(new PropertyValueFactory<>("id_site"));
        type.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        date_fin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
    }    

    @FXML
    private void LouerVelo(ActionEvent event) throws IOException, SQLException {
        if (sdl.getValidLocation(idUser) == true){
            UserchoixController.idClient = idUser;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Userchoix.fxml"));
            Parent root = loader.load();
            historiqueuser.getScene().setRoot(root);
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR, "Vous ne pouvez pas louer plus qu'un vélo à la fois", ButtonType.OK);
            a.showAndWait(); 
        }
        
    }

    @FXML
    private void annulerLocation(ActionEvent event) throws SQLException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        if (sdl.getLocationEnCours(idUser).getDate_debut().toString().compareTo(dateFormat.format(date)) == 1 ){
            sdl.AnnulerLocation(idUser);
        }else{
            Alert a = new Alert(Alert.AlertType.ERROR, "Vous ne pouvez plus annuler cetter location", ButtonType.OK);
            a.showAndWait();
        }
        
        
        
        
    }
    
}
