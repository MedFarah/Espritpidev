/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;

import easy.ride.entities.Retour;
import easy.ride.service.ServiceDetail_location;
import easy.ride.service.ServiceRetours;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;

/**
 * FXML Controller class
 *
 * @author Dali
 */
public class RetoursController implements Initializable {

    public static int idSite;
    @FXML
    private Button retour;
    @FXML
    private ChoiceBox<String> ListeIDLocation;
    @FXML
    private CheckBox CheckBoxRetard;
    @FXML
    private CheckBox CheckboxEndom;
    
    ServiceDetail_location sdl = new ServiceDetail_location();
    ServiceRetours sr = new ServiceRetours();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        System.out.println(idSite);
        try {
            List<Integer> intidLocation = new ArrayList<>(sdl.GetListIdEnCours(idSite));
            ArrayList<String> idLocation = new ArrayList();
            for (int a:intidLocation)
                idLocation.add(String.valueOf(a));
            ObservableList<String> list = FXCollections.observableArrayList(idLocation);
            ListeIDLocation.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(RetoursController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void retourdetail(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChefSite.fxml"));
        Parent root = loader.load();
        retour.getScene().setRoot(root);
    }

    @FXML
    private void Ajouter(ActionEvent event) throws SQLException, IOException {
        int retard;
        retard = 0;
        int endom;
        endom = 0;
        if (CheckBoxRetard.isSelected())
            retard = 1;
        if (CheckboxEndom.isSelected())
            endom = 1;
            
        sr.ajouter(new Retour(Integer.valueOf(ListeIDLocation.getValue()),retard,endom));
        sdl.SetEtatTermine(Integer.valueOf(ListeIDLocation.getValue()));
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ChefSite.fxml"));
        Parent root = loader.load();
        retour.getScene().setRoot(root);
    }
    
}
