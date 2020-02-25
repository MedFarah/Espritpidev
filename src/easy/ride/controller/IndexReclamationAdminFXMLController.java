/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.controller;


import com.mysql.jdbc.Statement;
import com.pdfjet.Letter;
import com.pdfjet.PDF;
import com.pdfjet.Page;
import easy.ride.EasyRide;
import easy.ride.entities.Reclamation;
import easy.ride.service.ServiceReclamation;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import static javax.print.DocFlavor.BYTE_ARRAY.PDF;
import static javax.print.DocFlavor.INPUT_STREAM.PDF;
import static javax.print.DocFlavor.URL.PDF;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.controlsfx.control.Notifications;

/**
 * FXML Controller class
 *
 * @author ASUS
 */
public class IndexReclamationAdminFXMLController implements Initializable {

    @FXML
    private TableView<Reclamation> table;
    @FXML
    private TableColumn<Reclamation,String> TypeReclamation;
    @FXML
    private TableColumn<Reclamation,String> DateReclamation;
    @FXML
    private TableColumn<Reclamation,String> Status;
    @FXML
    private TableColumn<Reclamation,String> objet;
    @FXML
    private TableColumn<Reclamation,String> email;
    @FXML
    private TableColumn<Reclamation,String> Editer;
    @FXML
    private Button statsBtn;
    @FXML
    private TextField cherchertxt;
    @FXML
    private Button reload;
    @FXML
    CheckBox EnAttente;
    @FXML
    CheckBox EnTraitement;
    @FXML
    CheckBox traite;
    ServiceReclamation serviceReclamation = new ServiceReclamation();


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        ObservableList observableList=null;

        //TableView data
        try {
            observableList = FXCollections.observableArrayList(serviceReclamation.readAll());
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
				
				// lowercase.
				String lowerCaseFilter = newValue.toUpperCase();
				
				if (r.getObjet().toUpperCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches 
				} else if (r.getTypeReclamation().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                               else if (r.getStatus().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else if (r.getEmail().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                               else if (r.getDescription().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                                else if (String.valueOf(r.getDateReclamation()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                
                                
				     else  
				    	 return false; // Does not match.
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Reclamation> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(table.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		table.setItems(sortedData);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());        }
        
        //update btn
        Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>> cellFactoryU
                = 
                new Callback<TableColumn<Reclamation, String>, TableCell<Reclamation, String>>() {
            @Override
            public TableCell call(final TableColumn<Reclamation, String> param) {
                final TableCell<Reclamation, String> cell = new TableCell<Reclamation, String>() {

                    final Button btn = new Button("Traiter");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {

                                if (getTableView().getItems().get(getIndex()) == null) {
                                   /* Notifications n = Notifications.create()
                                            .title("Erreur")
                                            .text("Choix invalide")
                                            .graphic(null)
                                            .position(Pos.TOP_CENTER)
                                            .hideAfter(Duration.seconds(5));
                                    n.showWarning();*/
                                } else {
                                    //List<Reclamation> listReclamation = TableViewReclamation.getSelectionModel().getSelectedItems();

                                    
                                    try {
                                        Reclamation reclamationSelectione = getTableView().getItems().get(getIndex());
                                        reclamationSelectione.setStatus("En traitement");//reload
                                        serviceReclamation.update(reclamationSelectione);
                                        ObservableList observableList =FXCollections.observableArrayList(serviceReclamation.readAll());
                                        table.setItems(observableList); 
                                    System.out.println(reclamationSelectione.getDescription());
                                        loadUpdateWindow(reclamationSelectione);
                                    } catch (Exception ex) {
                                        Logger.getLogger(IndexReclamationAdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                                        System.out.println(ex.getMessage()+"**************************************************");
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
         //editer update
         Editer.setCellFactory(cellFactoryU);
        
        //binding
         TypeReclamation.setCellValueFactory(new PropertyValueFactory<>("TypeReclamation"));
         DateReclamation.setCellValueFactory(new PropertyValueFactory<>("DateReclamation"));
         Status.setCellValueFactory(new PropertyValueFactory<>("Status"));
         objet.setCellValueFactory(new PropertyValueFactory<>("objet"));
         email.setCellValueFactory(new PropertyValueFactory<>("email"));
         Editer.setCellValueFactory(new PropertyValueFactory<>("Editer"));
         
         //event row dbl click
         table.setRowFactory( tv -> {
        TableRow<Reclamation> row = new TableRow<>();
        row.setOnMouseClicked(event -> {
        if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
            Reclamation rowData = (Reclamation)row.getItem();
            System.out.println(rowData.getImage());
           loadDblClick(rowData);
            try {
                //EtudiantController c = fxmlLoader.getController();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
           
            
  
     
    
        }
    });
    return row ;
});
         //more than 100 reclamation
         
CheckSearch();
        

    }    
    public void nrbReclamation() throws SQLException{
         ArrayList<Reclamation> list =(ArrayList<Reclamation>) serviceReclamation.readAll();
         final long limit = 100;
         long nbrReMaintenance = list.stream().filter(e->e.getTypeReclamation().equals("Maintenance")).map(e->e.getTypeReclamation()).count();
        if (nbrReMaintenance>100){
            if( (nbrReMaintenance-limit) ==0 ){}
            nbrReMaintenance=0;
        }
    }
    
    @FXML
    private void OnClick(ActionEvent event) {
        try {
            
            ArrayList<Reclamation> list =(ArrayList<Reclamation>) serviceReclamation.readAll();
         //   PDFViewer m_PDFViewer = new PDFViewer();
        //    BorderPane borderPane = new BorderPane(m_PDFViewer);
            Scene scene = new Scene(new Group());
       Stage stage = new Stage();
        stage.setTitle("Type Reclamation stats");
        stage.setWidth(500);
        stage.setHeight(500);
 
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                new PieChart.Data("Maintenance", list.stream().filter(e->e.getTypeReclamation().equals("Maintenance")).map(e->e.getTypeReclamation()).count()),
                new PieChart.Data("Location", list.stream().filter(e->e.getTypeReclamation().equals("Location")).map(e->e.getTypeReclamation()).count()),
                new PieChart.Data("Evenement", list.stream().filter(e->e.getTypeReclamation().equals("Evenement")).map(e->e.getTypeReclamation()).count()),
                new PieChart.Data("Commande", list.stream().filter(e->e.getTypeReclamation().equals("Commande")).map(e->e.getTypeReclamation()).count())
                );
        
        
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Type Reclamation");
        //label
        final Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (final PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                caption.setTranslateX(e.getSceneX());
                caption.setTranslateY(e.getSceneY());
                caption.setText(String.valueOf(data.getPieValue())+" Reclamations" );
             }
        });
                                                           }
            

        ((Group) scene.getRoot()).getChildren().add(chart);
        ((Group) scene.getRoot()).getChildren().add(caption);
        Button btn = new Button();
   
           ((Group) scene.getRoot()).getChildren().add(btn);
     FileChooser fc = new FileChooser();
     fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF FILES", "*.pdf"));
     fc.setTitle("save to pdf");
     fc.setInitialFileName("Statistique Reclamation");
    btn.setText("save PDF ");
    
  
  btn.setOnAction(new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            WritableImage nodeshot = chart.snapshot(new SnapshotParameters(), null);
            File file = new File("chart.png");

            try {
                ImageIO.write(SwingFXUtils.fromFXImage(nodeshot, null), "png", file);
            } catch (IOException e) {

            }

            PDDocument doc    = new PDDocument();
            PDPage page = new PDPage();
            PDImageXObject pdimage;
            PDPageContentStream content;
            System.out.println("icicic");
            try {
                pdimage = PDImageXObject.createFromFile("chart.png",doc);
                content = new PDPageContentStream(doc, page);
                content.drawImage(pdimage, 100, 100);
                content.close();
                doc.addPage(page);
                doc.save("pdf_file.pdf");
                doc.close();
                file.delete();
                
                 Notifications n = Notifications.create()
                                            .title("Success")
                                            .text("Operation effectue aves success")
                                            .graphic(null)
                                            .position(Pos.CENTER)
                                            .hideAfter(Duration.seconds(5));
                                    n.showConfirm();
            } catch (IOException ex) {
                //Logger.getLogger(NodeToPdf.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    });
  
  
   stage.setScene(scene);
    stage.show();
    /*
    btn.setOnAction(new EventHandler<ActionEvent>() {
            
        @Override
        public void handle(ActionEvent event) {
            File file = fc.showSaveDialog(stage);
            //System.out.println("To Printer!");
            //PrinterJob job = PrinterJob.createPrinterJob();
            //if(job != null){
            //job.showPrintDialog(stage); 
            //job.printPage(((Group) scene.getRoot()));
            //job.endJob();
    //}
             if(file !=null){
                 String pa = file.getAbsolutePath();
                try {
                    FileOutputStream fos = new FileOutputStream(pa);
                    PDF pdf = new PDF(fos);
                    Page page = new Page (pdf,Letter.LANDSCAPE);
                    pdf.close();
                    fos.flush();
                } catch (Exception ex) {
                    Logger.getLogger(IndexReclamationAdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
        }
    });
       
       */
        } catch (SQLException ex) {
            Logger.getLogger(IndexReclamationAdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    private void loadDblClick(Reclamation rowData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    private void loadUpdateWindow(Reclamation reclamationSelectione) throws IOException, SQLException {
         Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(EasyRide.class.getResource("GUI/TaiterReclamationAdminFXML.fxml"));
        // fermer la stage
//        ((Node)event.getSource()).getScene().getWindow().hide();
    stage.setTitle("Traitement Reclamation");
    Parent root1 = (Parent) fxmlLoader.load();
    stage.setScene(new Scene(root1));  
    TaiterReclamationAdminFXMLController c = fxmlLoader.getController();
    c.setReclamation(reclamationSelectione);
    stage.show();
    }
    
    
    @FXML
    private void Reload(ActionEvent event) {
        ObservableList observableList=null;
        try {
            observableList = FXCollections.observableArrayList(serviceReclamation.readAll());
            table.setItems(observableList);
            chercher(observableList);
            EnAttente.setSelected(false);
            EnTraitement.setSelected(false);
            traite.setSelected(false);
        } catch (SQLException ex) {
            Logger.getLogger(IndexReclamationAdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }
    //chercher
        private void chercher(ObservableList<Reclamation>observableList){
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
				
				// lowercase.
				String lowerCaseFilter = newValue.toUpperCase();
				
				if (r.getObjet().toUpperCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; // Filter matches 
				} else if (r.getTypeReclamation().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                               else if (r.getStatus().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
				else if (r.getEmail().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                               else if (r.getDescription().toUpperCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                                else if (String.valueOf(r.getDateReclamation()).indexOf(lowerCaseFilter)!=-1)
				     return true;
                                
                                
				     else  
				    	 return false; // Does not match.
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

        
        public void loadTabByStatus(String ch){
             ObservableList observableList=null;
        try {
            observableList = FXCollections.observableArrayList(serviceReclamation.readAllByStatus(ch));
            table.setItems(observableList);
            chercher(observableList);
        } catch (SQLException ex) {
            Logger.getLogger(IndexReclamationAdminFXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
        }
        
public void CheckSearch(){
    
// create a event handler 
            EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
  
                public void handle(ActionEvent e) 
                { 
                    if (EnAttente.isSelected()) 
                    {EnTraitement.setSelected(false);
            traite.setSelected(false);
            loadTabByStatus("En attente");} 
                  else  if(EnTraitement.isSelected())
                    {EnAttente.setSelected(false); traite.setSelected(false); loadTabByStatus("En traitement");
                } 
                  else  if(traite.isSelected()){
                        EnAttente.setSelected(false);
                        EnTraitement.setSelected(false);
                        loadTabByStatus("Traité");
                    }
                    
                }
                    
            }; 
  
            // set event to checkbox 
            EnAttente.setOnAction(event); 
            EnTraitement.setOnAction(event);
            traite.setOnAction(event);

}

}

