/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.controller;

import easy.ride.GUI.LoginController;
import easy.ride.entities.Reclamation;
import easy.ride.service.ServiceReclamation;
import easy.ride.service.UserSession;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
//import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class IndexReclamationClientFXMLController implements Initializable {

    @FXML
    private TableView<Reclamation> table;
    @FXML
    private ComboBox<String> comboBox;
    @FXML
    private ImageView imageView;
    @FXML
    private TextField objet;
    @FXML
    private TextArea description;
    @FXML
    private Button Envoyerbtn;
    @FXML
    private TableColumn<Reclamation, String> id;
    @FXML
    private TableColumn<Reclamation, String> typeReclamation;
    @FXML
    private TableColumn<Reclamation, String> DateReclamation;
    @FXML
    private TableColumn<Reclamation, String> status;
    @FXML
    private TableColumn<Reclamation, String> ActionM;
    @FXML
    private TableColumn<Reclamation, String> ActionS;
    @FXML
    private TextField cherchertxt;
    @FXML
    CheckBox EnAttente;
    @FXML
    CheckBox EnTraitement;
    @FXML
    CheckBox traite;
    ServiceReclamation serviceReclamation = new ServiceReclamation();
    String imageGlobale = null;
    String imagePath;
    public final int rowsPerPage = 5;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList observableList = null;

        //TableView data
        try {
            observableList = FXCollections.observableArrayList(serviceReclamation.afficherReclamationParClient(UserSession.getInstace("", "",0).getId()));
            table.setItems(observableList);

            //filter search
            // Wrap the ObservableList in a FilteredList (initially display all data).
            FilteredList<Reclamation> filteredData = new FilteredList<>(observableList, b -> true);

            // 2. Set the filter Predicate whenever the filter changes.
            cherchertxt.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredData.setPredicate(r -> {
                    // If filter text is empty, display all reclamation.

                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }

                    // toUpperCase
                    String lowerCaseFilter = newValue.toUpperCase();
                    if (r.getTypeReclamation().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                        return true; // Filter matches 
                    } //else if (r.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    //	return true; 
                    //}
                    else if (r.getDescription().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (r.getStatus().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else if (String.valueOf(r.getDateReclamation()).indexOf(lowerCaseFilter) != -1) {
                        return true;
                    } else {
                        return false; // Does not match.
                    }
                });
            });

            // 3. Wrap the FilteredList in a SortedList. 
            SortedList<Reclamation> sortedData = new SortedList<>(filteredData);

            // 4. Bind the SortedList comparator to the TableView comparator.
            // 	  Otherwise, sorting the TableView would have no effect.
            sortedData.comparatorProperty().bind(table.comparatorProperty());

            // 5. Add sorted (and filtered) data to the table.
            table.setItems(sortedData);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        //binding
        id.setCellValueFactory(new PropertyValueFactory<>("id"));
        typeReclamation.setCellValueFactory(new PropertyValueFactory<>("typeReclamation"));
        DateReclamation.setCellValueFactory(new PropertyValueFactory<>("DateReclamation"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        ActionM.setCellValueFactory(new PropertyValueFactory<>("ActionM"));
        ActionS.setCellValueFactory(new PropertyValueFactory<>("ActionS"));
        //delete row
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactory
                = new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                    final Button btn = new Button("Supprimer");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

                                if (getTableView().getItems().get(getIndex()) == null) {
                                    Notifications n = Notifications.create()
                                            .title("Erreur")
                                            .text("Choix invalide")
                                            .graphic(null)
                                            .position(Pos.TOP_CENTER)
                                            .hideAfter(Duration.seconds(5));
                                    n.showWarning();
                                } else {
                                    //List<Reclamation> listReclamation = TableViewReclamation.getSelectionModel().getSelectedItems();

                                    Reclamation reclamationSelectione = getTableView().getItems().get(getIndex());
                                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                    alert.setTitle("Confirmation Dialog");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous voulez vraiment supprimer cette reclamation");
                                    Optional<ButtonType> action = alert.showAndWait();
                                    if (action.get() == ButtonType.OK) {
                                        try {
                                            serviceReclamation.delete(reclamationSelectione);//reload
                                            ObservableList observableList = FXCollections.observableArrayList(serviceReclamation.afficherReclamationParClient(UserSession.getInstace("", "",0).getId()));
                                            table.setItems(observableList);
                                            chercher(observableList);
                                        } catch (SQLException ex) {
                                            System.out.println(ex.getMessage());

                                        }
                                    }
                                }
                                // list();

                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        //affectation
        ActionS.setCellFactory(cellFactory);

        //update btn
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryU
                = new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                    final Button btn = new Button("Modifier");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

//                                Reclamation person = getTableView().getItems().get(getIndex());
////                                System.out.println(person.getId()
//                                        + "   " + person.getNom());
                                if (getTableView().getItems().get(getIndex()) == null) {
                                    Notifications n = Notifications.create()
                                            .title("Erreur")
                                            .text("Choix invalide")
                                            .graphic(null)
                                            .position(Pos.TOP_CENTER)
                                            .hideAfter(Duration.seconds(5));
                                    n.showWarning();
                                } else {
                                    //List<Reclamation> listReclamation = TableViewReclamation.getSelectionModel().getSelectedItems();

                                    Reclamation reclamationSelectione = getTableView().getItems().get(getIndex());
                                    System.out.println(reclamationSelectione.getDescription());
                                    loadUpdateWindow(reclamationSelectione);
                                }
                                // list();

                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        //btn update
        ActionM.setCellFactory(cellFactoryU);
        //event row dbl click
        table.setRowFactory(tv -> {
            TableRow<Reclamation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Reclamation rowData = (Reclamation) row.getItem();
                    Notifications n = Notifications.create()
                            .title("Erreur")
                            .text("Choix invalide")
                            .graphic(null)
                            .position(Pos.TOP_CENTER)
                            .hideAfter(Duration.seconds(5));
                    n.showWarning();
                    loadDblClick(rowData);
                    try {
                        //EtudiantController c = fxmlLoader.getController();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            });
            return row;
        });

        //comboBox 
        comboBox.getItems().add("Evenement");
        comboBox.getItems().add("Location");
        comboBox.getItems().add("Maintenance");
        comboBox.getItems().add("Commande");
        comboBox.setPromptText("Select type ");

        //Image
        imageView.setOnDragOver(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                if (db.hasFiles()) {
                    event.acceptTransferModes(TransferMode.COPY);
                } else {
                    event.consume();
                }
            }
        });

        // Dropping over surface
        imageView.setOnDragDropped(new EventHandler<DragEvent>() {
            @Override
            public void handle(DragEvent event) {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasFiles()) {
                    success = true;
                    String path = null;
                    for (File file : db.getFiles()) {
                        path = file.getName();
                        File selectedFile = new File(file.getAbsolutePath());
                        imageView.setImage(new Image("file:" + file.getAbsolutePath()));
                        imageGlobale = file.getAbsolutePath();
                        try {
                            InputStream inputImage = new FileInputStream(file);

                        } catch (FileNotFoundException ex) {
                            System.out.println(ex.getMessage());
                        }
                    }
                }
                event.setDropCompleted(success);
                event.consume();
            }
        });       //image par defaut
        imageView.setImage(new Image("file:C:\\Users\\ASUS\\Desktop\\drop.jpg"));

        description.setPromptText("Voulez vous saisir votre text ici");

        //btn envoyer
        /* Envoyerbtn.setOnAction(e->{
        String obj = "";
        });*/
        CheckSearch();

    }

    @FXML
    private void AjouterReclamation(ActionEvent event) throws SQLException {
        if ((objet.getText().trim().length() == 0) || (description.getText().trim().length() == 0)) {
            /* Alert alert = new Alert(Alert.AlertType.WARNING);
                                    alert.setTitle("Erreur Dialog");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Vous devez remplir tous les champs");
                                    Optional<ButtonType> action = alert.showAndWait();*/
            ObservableList observableList = FXCollections.observableArrayList(serviceReclamation.afficherReclamationParClient(UserSession.getInstace("", "",0).getId()));
            Notifications n = Notifications.create()
                    .title("Erreur")
                    .text("Vous devez remplir tous les champs")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(5));
            n.showError();
            table.setItems(observableList);
            chercher(observableList);
            CheckSearch();
        } else {
            String typeReclamation = (String) comboBox.getValue();
            String obj = objet.getText();
            Date currentDatetime = new Date(System.currentTimeMillis());
            java.sql.Date sqlDate = new java.sql.Date(currentDatetime.getTime());
            String status = "En attente";

            String descrip = description.getText();
            String email = "test@";
            String im = null;
            if (imageGlobale == null) {
                im = "No picture";
            } else {
                im = imageGlobale.replace('\\', '+');
            }
            serviceReclamation.ajouter(new Reclamation(typeReclamation, sqlDate, im, status, UserSession.getInstace("", "",0).getUserName(), obj, descrip, UserSession.getInstace("", "",0).getId()));
            /* Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("Confirmation Dialog");
                                    alert.setHeaderText(null);
                                    alert.setContentText("Operation effectue aves success");
                                    Optional<ButtonType> action = alert.showAndWait();*/
            Notifications n = Notifications.create()
                    .title("Confirmation")
                    .text("Operation effectue aves success")
                    .graphic(null)
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(5));
            n.showConfirm();

            table.refresh();

            ObservableList observableList = FXCollections.observableArrayList(serviceReclamation.afficherReclamationParClient(UserSession.getInstace("", "",0).getId()));
            table.setItems(observableList);
            chercher(observableList);
            CheckSearch();
            objet.setText("");
            description.setText("");
            //image par defaut
        imageView.setImage(new Image("file:C:\\Users\\ASUS\\Desktop\\drop.jpg"));

        }
        //clear fields
    }

    /*private boolean check_control_saisie(){
            boolean x = true;
            String typeReclamation = (String) comboBox.getValue();
            System.out.println("************** "+typeReclamation);
        if((typeReclamation.equals("null")) || (description.getText().trim().length()==0) || (objet.getText().trim().length()==0));
        {x=false;}
        return x;
        }*/
    private void loadUpdateWindow(Reclamation reclamationSelectione) {
        // create sample content

        VBox vbox = new VBox();
        HBox hbox1 = new HBox();
        HBox hbox2 = new HBox();
        HBox hbox3 = new HBox();
        HBox hbox4 = new HBox();
        HBox hbox5 = new HBox();
        Label comboLabel = new Label("Type Reclamation:");
        Label objLabel = new Label("Objet:");
        Label desLabel = new Label("Description");
        Label imgLabel = new Label("Image");
        Label btnLabel = new Label("");
        ComboBox comboBox = new ComboBox();
        comboBox.getItems().add("Evenement");
        comboBox.getItems().add("Location");
        comboBox.getItems().add("Maintenance");
        comboBox.getItems().add("Commande");
        comboBox.setValue(reclamationSelectione.getTypeReclamation());
        TextField textField = new TextField();
        textField.setText(reclamationSelectione.getObjet());
        // TextField textFielddes = new TextField();
        TextArea txtDes = new TextArea();
        txtDes.setText(reclamationSelectione.getDescription());
        Button btn = new Button("Confirmer");
        String im;
        if (reclamationSelectione.getImage().equals("No picture")) {
            im = "C:\\Users\\ASUS\\Desktop\\drop.jpg";
        } else {
            im = reclamationSelectione.getImage().replace("+", "/");
        }

        FileInputStream input;

        try {
            input = new FileInputStream(im);
            Image image7 = new Image(input, 150, 150, false, false);
            ImageView imageView2 = new ImageView(image7);
            //test image est vide
            //  if(im.equals("No picture"))
            //{imageView2.setImage(new Image("file:C:\\Users\\ASUS\\Desktop\\drop.jpg"));}
            //else
            //{imageView2.setImage(image7);}

            //Image
            imageView2.setOnDragOver(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    Dragboard db = event.getDragboard();
                    if (db.hasFiles()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    } else {
                        event.consume();
                    }
                }
            });

            // Dropping over surface
            imageView2.setOnDragDropped(new EventHandler<DragEvent>() {
                @Override
                public void handle(DragEvent event) {
                    Dragboard db = event.getDragboard();
                    boolean success = false;
                    if (db.hasFiles()) {
                        success = true;
                        String path = null;
                        for (File file : db.getFiles()) {
                            path = file.getName();
                            File selectedFile = new File(file.getAbsolutePath());
                            imageView2.setImage(new Image("file:" + file.getAbsolutePath(), 100, 100, false, false));
                            imagePath = file.getAbsolutePath();
                            try {
                                InputStream inputImage = new FileInputStream(file);

                            } catch (FileNotFoundException ex) {
                                System.out.println(ex.getMessage());
                            }
                        }
                    }
                    event.setDropCompleted(success);
                    event.consume();
                }
            });       //image par defaut

            hbox1.getChildren().add(comboLabel);
            hbox1.getChildren().add(comboBox);
            vbox.getChildren().add(hbox1);
            hbox2.getChildren().add(objLabel);
            hbox2.getChildren().add(textField);
            //hbox3.getChildren().add(desLabel);
            hbox3.getChildren().add(txtDes);
            hbox4.getChildren().add(imgLabel);
            hbox4.getChildren().add(imageView2);
            hbox5.getChildren().add(btnLabel);
            hbox5.getChildren().add(btn);
            vbox.getChildren().add(hbox2);
            vbox.getChildren().add(hbox3);
            vbox.getChildren().add(hbox4);
            vbox.getChildren().add(hbox5);

            //Date currentDatetime = new Date(System.currentTimeMillis());
            //  java.sql.Date sqlDate = new java.sql.Date(currentDatetime.getTime());
            //  if(!(imagePath.contains("\\")))
            imagePath = reclamationSelectione.getImage();
            System.out.println(imagePath);
            //  Reclamation r = new Reclamation(reclamationSelectione.getId(),(String) comboBox.getValue(),sqlDate,imagePath.replace('\\','+'),"","","","",0);

            btn.setOnAction(e -> {

                try {
                    serviceReclamation.update(new Reclamation(reclamationSelectione.getId(), (String) comboBox.getValue(), reclamationSelectione.getDateReclamation(), imagePath.replace('\\', '+'), reclamationSelectione.getStatus(), UserSession.getInstace("", "",0).getUserName(), textField.getText(), txtDes.getText(), UserSession.getInstace("", "",0).getId()));
                   
                    Notifications n = Notifications.create()
                            .title("Confirmation")
                            .text("Operation effectue aves success")
                            .graphic(null)
                            .position(Pos.TOP_CENTER)
                            .hideAfter(Duration.seconds(5));
                    n.showConfirm();
                    //  if (action.get() == ButtonType.OK) {
                    Stage stage = (Stage) btn.getScene().getWindow();
                    stage.close();
                    ObservableList observableList = FXCollections.observableArrayList(serviceReclamation.afficherReclamationParClient(UserSession.getInstace("", "",0).getId()));
                    table.setItems(observableList);
                    chercher(observableList);
                    CheckSearch();
                    //  }     
                } catch (SQLException ex) {
                    System.out.println("iciiiiiiii" + ex.getMessage());
                }

            });
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IndexReclamationClientFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        Pane root = new Pane(vbox);
        root.setPrefSize(500, 500);

        Parent content = root;

        // create scene containing the content
        Scene scene = new Scene(content);

        Stage window = new Stage();
        window.setScene(scene);
        //resize window
        window.setResizable(false);
        // make window visible
        window.show();

    }

    private void loadDblClick(Reclamation rowData) {

    }

    

    //chercher
    private void chercher(ObservableList<Reclamation> observableList) {
        //filter search
        // Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Reclamation> filteredData = new FilteredList<>(observableList, b -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        cherchertxt.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(r -> {
                // If filter text is empty, display all reclamation.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // toUpperCase
                String lowerCaseFilter = newValue.toUpperCase();
                if (r.getTypeReclamation().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches 
                } //else if (r.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                //	return true; 
                //}
                else if (r.getDescription().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (r.getStatus().toUpperCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (String.valueOf(r.getDateReclamation()).indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false; // Does not match.
                }
            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Reclamation> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.
        table.setItems(sortedData);
    }

    public void loadTabByStatus(String ch) {
        ObservableList observableList = null;
        try {
            observableList = FXCollections.observableArrayList(serviceReclamation.readAllByStatusClient(ch,UserSession.getInstace("", "",0).getId()));
            table.setItems(observableList);
            chercher(observableList);
        } catch (SQLException ex) {
            Logger.getLogger(IndexReclamationAdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void CheckSearch() {

// create a event handler 
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {

            public void handle(ActionEvent e) {
                if (EnAttente.isSelected()) {
                    EnTraitement.setSelected(false);
                    traite.setSelected(false);
                    loadTabByStatus("En attente");
                } else if (EnTraitement.isSelected()) {
                    EnAttente.setSelected(false);
                    traite.setSelected(false);
                    loadTabByStatus("En traitement");
                } else if (traite.isSelected()) {
                    EnAttente.setSelected(false);
                    EnTraitement.setSelected(false);
                    loadTabByStatus("Traité");
                } else if ((!(EnAttente.isSelected())) && (!(EnTraitement.isSelected())) && (!(traite.isSelected()))) {
                    ObservableList observableList;
                    try {
                        observableList = FXCollections.observableArrayList(serviceReclamation.afficherReclamationParClient(UserSession.getInstace("", "",0).getId()));
                        table.setItems(observableList);
                        chercher(observableList);
                    } catch (SQLException ex) {
                        Logger.getLogger(IndexReclamationClientFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }

            }

        };

        // set event to checkbox 
        EnAttente.setOnAction(event);
        EnTraitement.setOnAction(event);
        traite.setOnAction(event);

    }

}
