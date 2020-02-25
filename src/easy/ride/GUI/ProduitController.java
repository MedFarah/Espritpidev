/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;


import easy.ride.Utils.DataBase;
import easy.ride.entities.Produit;
import easy.ride.service.ServiceProduit;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
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
import javafx.print.PrinterJob;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author suare
 */
public class ProduitController implements Initializable {
    private Connection con=null;
   
    int idRow;
     JOptionPane jop1,jop2,jop3;
  
    @FXML
    private TextField tfnom;
   @FXML
    private TextField    recherche;
    @FXML
    private TextField tfdescription;
     @FXML
    private TextField tfcouleur;
    @FXML
    private TextField tfprix;
     @FXML
    private TextField tfremise;
    @FXML
    private Button ajouter;
    @FXML
    private TableView<Produit> table;
    @FXML
    private TableColumn<Produit,String> fnom;
    @FXML
    private TableColumn<Produit,String> fdescription;
     @FXML
    private TableColumn<Produit,String> fcouleur;
    @FXML
    private TableColumn<Produit,Integer> fprix;
     @FXML
    private TableColumn<Produit,Integer> fprixt;
          @FXML
    private TableColumn<Produit,Integer> fid;
             @FXML
    private TableColumn<Produit,Integer> fremise;
                @FXML
    private TableColumn<Produit,Integer> fprixr;
    ObservableList <Produit> arr= FXCollections.observableArrayList();
       
 
    @FXML
    private Button delete;
    @FXML
    private Button update;
 
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        tfprix.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(30));
          tfremise.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(10));
           tfnom.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(10));
       tfcouleur.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(10));
     
           
fid.setCellValueFactory(new PropertyValueFactory<>("id"));
fnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
fdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
fcouleur.setCellValueFactory(new PropertyValueFactory<>("couleur"));
fprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
fprixt.setCellValueFactory(new PropertyValueFactory<>("prixt"));
fremise.setCellValueFactory(new PropertyValueFactory<>("remise"));
fprixr.setCellValueFactory(new PropertyValueFactory<>("prixr"));
                         
        try { 
            ServiceProduit ser = new ServiceProduit();
            arr=ser.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems((ObservableList<Produit>)arr);
        
                        
            con = DataBase.getInstance().getConnection();
        
          setCellTbale();
     
      
       
       
     
       table.getSelectionModel().selectedItemProperty().addListener((ob,od,nv)->{
          
            try { 
       idRow=((Produit)nv).getId();
       
        
       }catch (Exception ex) {
           System.out.println("Done!"); }
       
       });
       
                   setCellTbale();
      
        
        
        FilteredList<Produit> filteredData = new FilteredList<>(arr, b -> true);
        recherche.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(E -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (E.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                }// Filter matches first name.
                else {
                    return false;
                }
               
            });
             refresh();
        });
        SortedList<Produit> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);
     
         
                        

    }    
    
    
       private void setCellTbale(){
fid.setCellValueFactory(new PropertyValueFactory<>("id"));
fnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
fdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
fcouleur.setCellValueFactory(new PropertyValueFactory<>("couleur"));
fprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
fprixt.setCellValueFactory(new PropertyValueFactory<>("prixt"));
fremise.setCellValueFactory(new PropertyValueFactory<>("remise"));
fprixr.setCellValueFactory(new PropertyValueFactory<>("prixr"));
        
    }
       
       public EventHandler<KeyEvent> numeric_Validation(final Integer max_Lengh) {
    return new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            TextField txt_TextField = (TextField) e.getSource();                
            if (txt_TextField.getText().length() >= max_Lengh) {                    
                e.consume();
            }
            if(e.getCharacter().matches("[0-9.]")){ 
                if(txt_TextField.getText().contains(".") && e.getCharacter().matches("[.]")){
                    e.consume();
                }else if(txt_TextField.getText().length() == 0 && e.getCharacter().matches("[.]")){
                    e.consume(); 
                }
            }else{
                e.consume();
            }
        }
    };
}     
   public EventHandler<KeyEvent> letter_Validation(final Integer max_Lengh) {
    return new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            TextField txt_TextField = (TextField) e.getSource();                
            if (txt_TextField.getText().length() >= max_Lengh) {                    
                e.consume();
            }
            if(e.getCharacter().matches("[A-Za-z]")){ 
            }else{
                e.consume();
            }
        }
    };
}
       
       

    public void refresh(){
fnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
fdescription.setCellValueFactory(new PropertyValueFactory<>("description"));
fcouleur.setCellValueFactory(new PropertyValueFactory<>("couleur"));
fprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
fprixt.setCellValueFactory(new PropertyValueFactory<>("prixt"));
fremise.setCellValueFactory(new PropertyValueFactory<>("remise"));
fprixr.setCellValueFactory(new PropertyValueFactory<>("prixr"));
                      
   try { 
            ServiceProduit ser = new ServiceProduit();
            arr=ser.readAll();
        } 
   catch (SQLException ex) {
            Logger.getLogger(ProduitController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems((ObservableList<Produit>)arr);                 
    }
    
    
    
    @FXML
     private void ajouter(ActionEvent event) {
          
 String nom = tfnom.getText();
 String description = tfdescription.getText();
 String couleur = tfcouleur.getText();
 String prixP = tfprix.getText();
 int prix=Integer.parseInt(tfprix.getText());
 String remiseP = tfremise.getText();
 int remise=Integer.parseInt(tfremise.getText());
            
 ServiceProduit sp = new ServiceProduit();
 Produit p = new Produit(nom,description,couleur,prix,remise);
 p.setId(idRow);
 p.setPrixt(prix*1.35);
 double x;
 double a;
 double b;
 x=prix*1.35;
 b=x*remise/100;
 a=x-b;
 p.setPrixr(a);
 System.out.println(a);
         
                
         try {
            sp.ajouter(p);   
       
            
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
       
    refresh();
    System.out.println("élément ajouter avec Success");
    
       
 
//Boîte du message d'information
jop1 = new JOptionPane();
jop1.showMessageDialog(null, "Elément ajouter avec succes", "", JOptionPane.INFORMATION_MESSAGE);
		

           
              
     }


    @FXML
    private void delete(ActionEvent Event)  {
        
        try {
            Produit a = table.getSelectionModel().getSelectedItem();
            ServiceProduit ser = new ServiceProduit();
            ser.delete(a);
            jop2 = new JOptionPane();
jop2.showMessageDialog(null, "voulez vous vraiment supprimer cet élément?", "Attention", JOptionPane.WARNING_MESSAGE);
            arr.clear();
            arr.addAll(ser.readAll());
        } catch (SQLException ex) {
           
        }
       refresh();
       System.out.println("élément supprimer avec Success");
  //Boîte du message préventif

		

    }

   
    @FXML
    private void update(ActionEvent event) {
        
        
        try {
            String nom = tfnom.getText();
            String description = tfdescription.getText();
            String couleur = tfcouleur.getText();
            String prixP = tfprix.getText();
            int prix=Integer.parseInt(tfprix.getText());
             String remiseP = tfremise.getText();
            int remise=Integer.parseInt(tfremise.getText());
            
            ServiceProduit sp = new ServiceProduit();
            Produit info1 = new Produit(nom,description,couleur,prix,remise);
            info1.setId(idRow);
            info1.setPrixt(prix*1.35);
           double x;
            double a;
            double b;
            x=prix*1.35;
            b=x*remise/100;
            a=x-b;
            info1.setPrixr(a);
            System.out.println(a);
            jop3 = new JOptionPane();
  
jop3.showMessageDialog(null, "voulez vous vraiment modifier cet élément?", "Attention", JOptionPane.WARNING_MESSAGE);
            
            sp.update(info1);
            refresh();
        } catch (SQLException ex) {
           
        }
            
        
    }       
    
    
    
  

   
    

    @FXML
    private void imprimer(ActionEvent event) {
       
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob.printPage(table)) {
            printerJob.endJob();
            System.out.println("printed");
        }
    }


   


   
    
  
    
    }