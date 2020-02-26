/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Detail_location;
import easy.ride.service.ServiceDetail_location;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class AdminGuiController implements Initializable {

    private VBox Historique;

    ServiceDetail_location sdl = new ServiceDetail_location();
    @FXML
    private TableView<Detail_location> tableHistorique;
    @FXML
    private TableColumn<Detail_location, Number> id;
    @FXML
    private TableColumn<Detail_location, Number> id_user;
    @FXML
    private TableColumn<Detail_location, String> id_type;
    @FXML
    private TableColumn<Detail_location, String> id_site;
    @FXML
    private TableColumn<Detail_location, Date> date_debut;
    @FXML
    private TableColumn<Detail_location, Date> date_fin;
    @FXML
    private TableColumn<Detail_location, String> status;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList Details = FXCollections.observableArrayList();
        try {
            Details = sdl.getAllAdmin();
        } catch (SQLException ex) {
            Logger.getLogger(AdminGuiController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        tableHistorique.setItems(null);
        tableHistorique.setItems(Details);
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        id_user.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        id_type.setCellValueFactory(new PropertyValueFactory<>("id_type"));
        id_site.setCellValueFactory(new PropertyValueFactory<>("id_site"));
        date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
        date_fin.setCellValueFactory(new PropertyValueFactory<>("date_fin"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }    

    @FXML
    private void AjouterSite(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("AdminAjoutSite.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Ajout Site");
        stage.show();
    }

    @FXML
    private void VoirLog(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(AcceuilClientFXMLController.class.getResource("Logs.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Logs");
        stage.show();
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("Logs.fxml"));
        Parent root = loader.load();
        Historique.getScene().setRoot(root);*/
    }
    
}
