/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import easy.ride.entities.Commande;
import easy.ride.entities.Reclamation;
import easy.ride.service.ServiceCommande;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.shaded.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.shaded.apache.poi.ss.usermodel.Row;
import org.shaded.apache.poi.ss.usermodel.Sheet;
import org.shaded.apache.poi.ss.usermodel.Workbook;

/**
 * FXML Controller class
 *
 * @author Administrateur
 */
public class AfficheCommandeController implements Initializable {

     /**
     * Initializes the controller class.
     */
    @FXML
    private TableView<Commande> table;
    @FXML
    private TableColumn<Commande,String> ref;
    @FXML
    private TableColumn<Commande,String> date;
    @FXML
    private TableColumn<Commande,String> etat;
    @FXML
    private TableColumn<Commande,String> prix;
    
    ServiceCommande sc=new ServiceCommande();
    
    @FXML
    private TextField txt;
    @FXML
    private Button btndelete;
    @FXML
    private Button btnedit;
     @FXML
    private Label lbl;
    
     public void affiche(){
        ObservableList<Commande> observableList = null;
        try {
            observableList = FXCollections.observableArrayList(sc.getAll());
            table.setItems(observableList);
        } catch (SQLException ex) {
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(observableList);
        
        ref.setCellValueFactory(new PropertyValueFactory<>("ref"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
         //event row dbl click
         table.setRowFactory(t ->{
         TableRow<Commande> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Commande rowData = (Commande) row.getItem();
                    Notifications n = Notifications.create()
                            .title("Erreur")
                            .text("Choix invalide")
                            .graphic(null)
                            .position(Pos.TOP_CENTER)
                            .hideAfter(Duration.seconds(5));
                    n.showWarning();
                    try {
                        Parent root = FXMLLoader
        .load(getClass().getResource("EditCommande.fxml"));
            
            Scene scene = new Scene(root);
            Stage ps=new Stage();
            ps.setTitle("Liste Commande");
            ps.setScene(scene);
            ps.show();
            // fermer la stage
                    ((Node)event.getSource()).getScene().getWindow().hide();
                    } catch (Exception ex) {
                        System.out.println(ex.getMessage());
                    }

                }
            });
         
         return row;
         });
       
        
        
        
         
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        affiche();
        
        
       
      
    }    
    
   
    
     @FXML
    private void delete(ActionEvent event) {
        
            Commande t2 = table.getSelectionModel().getSelectedItem();
           
            if (t2==null)
            {
                 
               
            lbl.setText("Sélectionner la commande qui va etre annulé");
            
            }
            
        try {
          if(  sc.Annuler(t2.getRef()))
          {
          Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Etes-vous sur de vouloir supprimer cet  Commande ?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
alert.showAndWait();

if (alert.getResult() == ButtonType.NO || alert.getResult() == ButtonType.CANCEL) {
    sc.ajouter1(t2);
}
          }
            affiche();
        } catch (SQLException ex) {
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    
}
    
      @FXML
    private void recherche(ActionEvent event) {
        
            String refC = txt.getText();
           
            
            
        ObservableList<Commande> observableList = null;
        try {
            observableList = FXCollections.observableArrayList(sc.findByReference(refC));
            table.setItems(observableList);
        } catch (SQLException ex) {
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems(observableList);
        
        ref.setCellValueFactory(new PropertyValueFactory<>("ref"));
        date.setCellValueFactory(new PropertyValueFactory<>("date"));
        etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
        prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
            
           
    
}

    
    @FXML
    private void edit(ActionEvent event){
          try {
            Parent root = FXMLLoader
        .load(getClass().getResource("EditCommande.fxml"));
            
            Scene scene = new Scene(root);
            Stage ps=new Stage();
            ps.setTitle("Liste Commande");
            ps.setScene(scene);
            ps.show();
            // fermer la stage
                    ((Node)event.getSource()).getScene().getWindow().hide();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
       
    }
    
  @FXML
       private void pdfs() 
          throws Exception{
        try {
           //   Class.forName("com.mysql.jdbc.Driver");
               Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev", "root", "");
  Statement stmt = con.createStatement();
          
                    /* Define the SQL query */
                    ResultSet query_set = stmt.executeQuery("SELECT * from commande");
                    /* Step-2: Initialize PDF documents - logical objects */
                    Document my_pdf_report = new Document();
                    PdfWriter.getInstance(my_pdf_report, new FileOutputStream("ListeCommande.pdf"));
                    my_pdf_report.open();            
                    //we have four columns in our table
                    PdfPTable my_report_table = new PdfPTable(5);
                    //create a cell object
                    PdfPCell table_cell;
                    
                    table_cell=new PdfPCell(new Phrase("Reference"));
                                    my_report_table.addCell(table_cell);
                                    table_cell=new PdfPCell(new Phrase("Date"));
                                    my_report_table.addCell(table_cell);
                                    table_cell=new PdfPCell(new Phrase("Etat"));
                                    my_report_table.addCell(table_cell);
                                    table_cell=new PdfPCell(new Phrase("Prix"));
                                    my_report_table.addCell(table_cell);
                                    table_cell=new PdfPCell(new Phrase("user_id"));
                                    my_report_table.addCell(table_cell);

                    while (query_set.next()) {                
                                    String refer = query_set.getString(1);
                                    table_cell=new PdfPCell(new Phrase(refer));
                                    my_report_table.addCell(table_cell);
                                    
                                    String date=query_set.getString(2);
                                  //  DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
                                  //  String strDate = dateFormat.format(dept_name);
                                    
                                    table_cell=new PdfPCell(new Phrase(date));
                                    my_report_table.addCell(table_cell);
                                    String etat=query_set.getString(3);
                                    table_cell=new PdfPCell(new Phrase(etat));
                                    my_report_table.addCell(table_cell);
                                    String prix=query_set.getString(4);
                                    table_cell=new PdfPCell(new Phrase(prix));
                                    my_report_table.addCell(table_cell);
                                    String user=query_set.getString(5);
                                    table_cell=new PdfPCell(new Phrase(user));
                                    my_report_table.addCell(table_cell);
                                    }
                    /* Attach report table to PDF */
                    my_pdf_report.add(my_report_table);                       
                    my_pdf_report.close();
                    Desktop.getDesktop().open(new File ("./ListeCommande.pdf"));

                    /* Close all DB related objects */
                    query_set.close();
                    stmt.close(); 
                    con.close();               



    } catch (FileNotFoundException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    } catch (DocumentException e) {
    // TODO Auto-generated catch block
    e.printStackTrace();
    }
        
       }
       
       @FXML
    private void add(ActionEvent event){
       try {
           
           
            Parent root = FXMLLoader
        .load(getClass().getResource("AddCommande.fxml"));
            
            Scene scene = new Scene(root);
            Stage ps=new Stage();
            ps.setTitle("Add Commande");
            ps.setScene(scene);
            ps.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
     @FXML
    private void excel(ActionEvent event){
        Workbook workbook = new HSSFWorkbook();
        Sheet spreadsheet = workbook.createSheet("sample");

        Row row = spreadsheet.createRow(0);

        for (int j = 0; j < table.getColumns().size(); j++) {
            row.createCell(j).setCellValue(table.getColumns().get(j).getText());
        }

        for (int i = 0; i < table.getItems().size(); i++) {
            row = spreadsheet.createRow(i + 1);
            for (int j = 0; j < table.getColumns().size(); j++) {
                if(table.getColumns().get(j).getCellData(i) != null) { 
                    row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString()); 
                }
                else {
                    row.createCell(j).setCellValue("");
                }   
            }
        }

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream("ListeCommande.xls");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            workbook.write(fileOut);
        } catch (IOException ex) {
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            fileOut.close();
        } catch (IOException ex) {
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            Desktop.getDesktop().open(new File ("./ListeCommande.xls"));
        } catch (IOException ex) {
            Logger.getLogger(AfficheCommandeController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

       
      
}

