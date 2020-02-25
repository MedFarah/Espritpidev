/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.ride.GUI;


import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import static javafx.util.Duration.seconds;
import org.controlsfx.control.Notifications;
import easy.ride.entities.Maintenance;
import easy.ride.service.ServiceMaintenance;
import easy.ride.service.UserSession;

/**
 * FXML Controller class
 *
 * @author HP
 */
public class MaintenanceFXMLController implements Initializable {
    @FXML
    private TableColumn<Maintenance, Integer> col_id;
    @FXML
    private TableColumn<Maintenance, String> col_titre;
    @FXML
    private TableColumn<Maintenance, String> col_description;
    @FXML
    private TableColumn<Maintenance, String> col_date;
    @FXML
    private TableColumn<Maintenance, Integer> col_etat;
    @FXML
    private TableColumn<Maintenance, Integer> col_client;
    @FXML
    private TextField txt_titre1;
    @FXML
    private TextField txt_etat1;
    @FXML
    private TextField txt_client1;
    @FXML
    private DatePicker txt_date1;
    @FXML
    private TextArea txt_description1;
    @FXML
    private Label label_titre1;
    @FXML
    private Label label_description1;
    @FXML
    private Label label_date1;
    @FXML
    private Label label_etat1;
    @FXML
    private Label label_client1;
    @FXML
    private Label label_ID;
    @FXML
    private Button ajout;
    @FXML
    private TextField txt_titre;
    @FXML
    private TextField txt_etat;
    @FXML
    private TextField txt_client;
    @FXML
    private DatePicker txt_date;
    @FXML
    private TextArea txt_description;
    @FXML
    private Label label_titre;
    @FXML
    private Label label_description;
    @FXML
    private Label label_date;
    @FXML
    private Label label_etat;
    @FXML
    private Label label_client;
    @FXML
    private Label label_maintenance;
    @FXML
    private TableView<Maintenance> tableau;
    @FXML
    private Button Button_update;
    @FXML
    private Button Button_delete;
    

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button Button_refresh;
    @FXML
    private TextField txt_id;
    
   public ObservableList<Maintenance> oblist = FXCollections.observableArrayList();
    @FXML
    private TextField recherche1;
    @FXML
    private Label Velo1;
    @FXML
    private Button Button_update1;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        String client_id = String.valueOf(UserSession.getInstace("", "",0).getId());
        txt_client.setText(client_id);
        txt_client.setDisable(true);
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_client.setCellValueFactory(new PropertyValueFactory<>("client")); 
        try {
          
            ServiceMaintenance ser = new ServiceMaintenance();
            Maintenance M= new Maintenance();
            oblist = ser.afficher(M);
            //oblist.addAll(ser.afficher(M));
             tableau.setItems(oblist); 
            System.out.println("iciiiiiiiiiiiiiiiiiii"+oblist);
        } catch (SQLException ex) {
           
        }
        
        
        FilteredList<Maintenance> filteredData = new FilteredList<>(oblist, b -> true);
        recherche1.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(E -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (E.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }// Filter matches first name.
                else {
                    return false;
                }
            });
        });
        SortedList<Maintenance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableau.comparatorProperty());
        tableau.setItems(sortedData);
     
        
       
       // tableau.setItems(oblist);
      
       
    }   
    
    

    @FXML
    private void actualiser(MouseEvent event) {
    }


    @FXML
    private void ajouter(MouseEvent event) {
        Maintenance M = new Maintenance();

        M.setTitre(txt_titre.getText());
        M.setDescription(txt_description.getText());
        M.setDate(txt_date.getValue().format(DateTimeFormatter.ISO_DATE).toString());
        M.setEtat(Integer.parseInt(txt_etat.getText()));
        M.setClient(Integer.parseInt(txt_client.getText()));

        ServiceMaintenance ser = new ServiceMaintenance();
        try {
            ser.ajouter(M);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
             Notifications NotificationBuilder = Notifications.create()
                .title("Downloads complet")
                .text("Evenement ajouté ")
                .graphic(null)
                //.hideAfter()
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("hellooooooooo");
                    }

                });
    }

    @FXML
    private void Refresh(MouseEvent event) {
        col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        col_client.setCellValueFactory(new PropertyValueFactory<>("client")); 
        try {
          
            ServiceMaintenance ser = new ServiceMaintenance();
            Maintenance M= new Maintenance();
            oblist = ser.afficher(M);
            //oblist.addAll(ser.afficher(M));
             tableau.setItems(oblist); 
             FilteredList<Maintenance> filteredData = new FilteredList<>(oblist, b -> true);
        recherche1.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(E -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (E.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }// Filter matches first name.
                else {
                    return false;
                }
            });
        });
        SortedList<Maintenance> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableau.comparatorProperty());
        tableau.setItems(sortedData);
            System.out.println(oblist);
        } catch (SQLException ex) {
           
        }
        
        
        
    }

    @FXML
    private void Supprimer(MouseEvent event) {
        Maintenance M = new Maintenance();
        M.setId(Integer.parseInt(txt_id.getText()));
        
        
        try {
            M.setId(Integer.parseInt(txt_id.getText()));
            ServiceMaintenance ser = new ServiceMaintenance();
            ser.delete(M);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void Update(MouseEvent event) {
        Maintenance M = new Maintenance();
        M.setId(Integer.parseInt(txt_id.getText()));
        
        M.setTitre(txt_titre1.getText());
        M.setDescription(txt_description1.getText());
        M.setDate(txt_date1.getValue().format(DateTimeFormatter.ISO_DATE).toString());
        M.setEtat(Integer.parseInt(txt_etat1.getText()));
        M.setClient(Integer.parseInt(txt_client1.getText()));

        ServiceMaintenance ser = new ServiceMaintenance();
        try {
            ser.update(M);
            this.Refresh(event);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @FXML
    private void fill(MouseEvent event) {
        Maintenance mt = new Maintenance();
        col_id.getCellObservableValue(mt);
       txt_id.setText(col_id.getCellObservableValue(mt.getId()).toString() ); 
        txt_titre1.setText(mt.getTitre()); 
        txt_description1.setText(col_description.getText()); 
        txt_etat1.setText(col_etat.getText()); 
        txt_client1.setText(col_client.getText());
        
    }

    @FXML
    private void Confirmer(MouseEvent event) throws Exception {
        Maintenance M = new Maintenance();
        M.setId(Integer.parseInt(txt_id.getText()));
        
        M.setTitre(txt_titre1.getText());
        M.setDescription(txt_description1.getText());
        M.setDate(txt_date1.getValue().format(DateTimeFormatter.ISO_DATE).toString());
        M.setEtat(1);
        M.setClient(Integer.parseInt(txt_client1.getText()));

        ServiceMaintenance ser = new ServiceMaintenance();
        try {
            ser.update(M);
            String message = "hentatiyassine@gmail.com";
            Sendmail.sendmail(message);
            this.Refresh(event);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
        
    }

    
