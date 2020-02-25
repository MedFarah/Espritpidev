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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

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
                    PdfPTable my_report_table = new PdfPTable(4);
                    //create a cell object
                    PdfPCell table_cell;

                    while (query_set.next()) {                
                                    String dept_id = query_set.getString(1);
                                    table_cell=new PdfPCell(new Phrase(dept_id));
                                    my_report_table.addCell(table_cell);
                                    
                                    String dept_name=query_set.getString(2);
                                  //  DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");  
                                  //  String strDate = dateFormat.format(dept_name);
                                    
                                    table_cell=new PdfPCell(new Phrase(dept_name));
                                    my_report_table.addCell(table_cell);
                                    String manager_id=query_set.getString(3);
                                    table_cell=new PdfPCell(new Phrase(manager_id));
                                    my_report_table.addCell(table_cell);
                                    float location_id=query_set.getFloat(4);
                                    table_cell=new PdfPCell(new Phrase(location_id));
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

       
      
}
