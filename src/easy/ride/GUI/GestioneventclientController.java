/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.GUI;



import easy.ride.entities.Evenements;
import com.lowagie.text.DocumentException;
import easy.ride.Utils.DataBase;
import easy.ride.service.Serviceevenements;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author suare
 */
public class GestioneventclientController implements Initializable {
private Connection con=null;
    private PreparedStatement ste=null;
    private ResultSet rs=null;
    @FXML
    private TextField tfnom;
    private TextField tfdate;
    int idRow;
     
 @FXML
    private DatePicker tfdateeve;  
     @FXML
    private TextField tfnombre;
    @FXML
    private Button ajouter;
    @FXML
    private TableView<Evenements> table;
    @FXML
    private TableColumn<Evenements,String> fnom;
    @FXML 
    private TableColumn<Evenements,Integer> fnombre;
    @FXML
     private TableColumn<Evenements,String> fdate;
    @FXML
    private TableColumn<Evenements,String> flieux;
    @FXML
    private TableColumn<Evenements,String> fdescreption;
    ObservableList <Evenements> arr= FXCollections.observableArrayList();
    @FXML
    private TextField SearchNewsfx;
    @FXML
    private TextArea tfdescreption;
    @FXML
    private TextField tflieux;
    @FXML
    private TableColumn<Evenements,String> fid;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        Evenements e= new Evenements();
         fid.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnom.setCellValueFactory(new PropertyValueFactory<>("nom_evenements"));
        fdate.setCellValueFactory(new PropertyValueFactory<>("dateeve"));
       fnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        flieux.setCellValueFactory(new PropertyValueFactory<>("lieuxeve"));
       fdescreption.setCellValueFactory(new PropertyValueFactory<>("descreptioneve"));
                        //Serviceevenements ser= new Serviceevenements();
        try { 
            Serviceevenements ser = new Serviceevenements();
            arr=ser.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems((ObservableList<Evenements>)arr);
        
                        
            con = DataBase.getInstance().getConnection();
         searchNews();
          setCellTbale();
       LoadDataFromDB();
      
       
     
       table.getSelectionModel().selectedItemProperty().addListener((ob,od,nv)->{
          // System.out.println(((Evenements)nv).getId());
            try { 
       idRow=((Evenements)nv).getId();
       
        
       }catch (Exception ex) {
           System.out.println("hellp"); }
       
       });
        tfnombre.addEventFilter(KeyEvent.KEY_TYPED , numeric_Validation(10));
           tflieux.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(10));
     tfnom.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation(10));
    tfdescreption.addEventFilter(KeyEvent.KEY_TYPED , letter_Validation1(10));

       

    }  
    private Boolean checkValidInputs() {

       if ((tfnom.getText().toString().compareTo("") == 0) ||
    
    (tflieux.getText().toString().compareTo("") == 0) ||
    (tfnombre.getText().toString().compareTo("") == 0) ||
    (tfdescreption.getText().toString().compareTo("") == 0)) {

    JOptionPane.showMessageDialog(null, "champs vide");
return false;
}
       
       else {  JOptionPane.showMessageDialog(null, "Enregistrement a été  ajouter");

       
       }
return true;
    }
    private void setCellTbale(){
     fid.setCellValueFactory(new PropertyValueFactory<>("id"));
     fnom.setCellValueFactory(new PropertyValueFactory<>("nom_evenements"));
     fnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
     fdate.setCellValueFactory(new PropertyValueFactory<>("dateeve"));
     flieux.setCellValueFactory(new PropertyValueFactory<>("lieuxeve"));
     fdescreption.setCellValueFactory(new PropertyValueFactory<>("descreptioneve"));
        
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
      public EventHandler<KeyEvent> letter_Validation1(final Integer max_Lengh) {
    return new EventHandler<KeyEvent>() {
        @Override
        public void handle(KeyEvent e) {
            TextArea txt_TextField = (TextArea) e.getSource();                
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
         Evenements e= new Evenements();
        fnom.setCellValueFactory(new PropertyValueFactory<>("nom_evenements"));
        fnombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));

        fdate.setCellValueFactory(new PropertyValueFactory<>("dateeve"));
         flieux.setCellValueFactory(new PropertyValueFactory<>("lieuxeve"));
        fdescreption.setCellValueFactory(new PropertyValueFactory<>("descreptioneve"));
        
        

                        //Serviceevenements ser= new Serviceevenements();
        try { 
            Serviceevenements ser = new Serviceevenements();
            arr=ser.readAll();
        } catch (SQLException ex) {
            Logger.getLogger(EventController.class.getName()).log(Level.SEVERE, null, ex);
        }
        table.setItems((ObservableList<Evenements>)arr);
        
                        
                        
    }
    
    
    @FXML
     private void ajouter(ActionEvent event) {
         System.out.println("hehheheheh");
            String nomE = tfnom.getText();
            String nombreE = tfnombre.getText();
            int nbr=Integer.parseInt(tfnombre.getText());
            String dateE = tfdateeve.getValue().toString();
            String lieuxE = tflieux.getText();
            String descreptionE = tfdescreption.getText();
        
            Serviceevenements sp = new Serviceevenements();
            Evenements p = new Evenements();
            p.setNom_evenements(nomE);
            p.setNombre(nbr);
            p.setDateeve(dateE);
            p.setDescreptioneve(descreptionE);
            p.setLieuxeve(lieuxE);

            
         try {
             if(checkValidInputs()){
                           sp.ajouter(p);   
  
             }
           


            
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        
    refresh();
     }
   /*   public List<Evenements> afficher(Evenements E) throws SQLException {/*
       ObservableList<Evenements> arr =FXCollections.observableArrayList();
        ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from evenements");
        while (rs.next()) {
    arr.add(new Evenements(rs.getInt("nombre participant") ,rs.getString("nom"),rs.getString("date evenements"),rs.getInt("id")));
            System.out.println("helloooooooooooooo");
            Evenements p = new Evenements();
            arr.add(p);
        }
        return arr;
} /
    } */

    private void delete(ActionEvent Event) throws SQLException {
         Evenements a = table.getSelectionModel().getSelectedItem();
       
         Serviceevenements ser = new Serviceevenements();
        Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
         alert.setTitle("confirmation de suppression");
         alert.setHeaderText(null);
         alert.setContentText("Vous etes sure de supprimer cet evenements");
         Optional <ButtonType> action=alert.showAndWait();
         
       if(action.get()==ButtonType.OK)
       {
        ser.delete(a);
        arr.clear();
        arr.addAll(ser.readAll());
   
       }
     //  E.Integer.parseInt(t_nombreMax.getText());
       // table.getItems().removeAll(table.getSelectionModel().getSelectedItem());
    }

    private void update(ActionEvent event) {
          String nomE = tfnom.getText();
           
            String nombreE = tfnombre.getText();
        int nbr=Integer.parseInt(tfnombre.getText());
            String dateE = tfdateeve.getValue().toString();
            String lieuxE = tflieux.getText();
            String descreptionE = tfdescreption.getText();
         Serviceevenements sp = new Serviceevenements();
             Evenements info1 = new Evenements(nomE,nbr,dateE,lieuxE,descreptionE);
             info1.setId(idRow);
            System.out.println(info1);
            //int max=Integer.parseInt(tfid.getText());
         try {
            if(checkValidInputs()){
                           sp.ajouter(info1);   
  
             }    
        refresh();
            
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }        /*int idd=Integer.parseInt(tfid.getText());

       String nomE = tfnom.getText();
            String dateE = tfdate.getText();
             String nombreE = tfnombre.getText();
        int nbr=Integer.parseInt(tfnombre.getText());

        java.sql.Timestamp tm = java.sql.Timestamp.valueOf(dateE);
        
        try 
        { 
        Serviceevenements srv = new Serviceevenements();
        Evenements info1 = new Evenements(idd,nomE,tm,nbr);
        srv.update(info1);
        
        } 
        catch (SQLException e){ 
    
         }*/
   
  /* private void setCellValueFromTableToTextFiled(){
        table.setOnMouseClicked( e -> {
            Evenements inf1 = table.getItems().get(table.getSelectionModel().getSelectedIndex());
            tfnom.setText(inf1.getNom_evenements());
            tfnombre.setText(""+inf1.getNombre());
            tfdate.setText(inf1.getDateeve());
            tflieux.setText(inf1.getLieuxeve());
            tfdescreption.setText(inf1.getDescreptioneve());
        });
    }
   */
    private void  LoadDataFromDB(){
        arr.clear();
        
        try {
            ste = con.prepareStatement("Select * from evenements");
            rs= ste.executeQuery();
            while(rs.next()){
            arr.add(new Evenements(rs.getInt(1),rs.getString(2), rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6)));
            }
            
        } catch (SQLException e) {
        }
        table.setItems(arr);
    }
    private void searchNews(){
        SearchNewsfx.setOnKeyReleased(e->{
            if(SearchNewsfx.getText().equals("")){
                LoadDataFromDB();
            }
            else{
                arr.clear();
                String sql = "SELECT * FROM `evenements` where nom_evenements LIKE '%"+SearchNewsfx.getText()+"%'";
                try {
                    ste = con.prepareStatement(sql);
                    rs = ste.executeQuery();
                    while(rs.next()){
            arr.add(new Evenements(rs.getString(2), rs.getInt(3),rs.getString(4),rs.getString(5),rs.getString(6)));
                        
                    }   
                    table.setItems(arr);
                } catch (SQLException r) {
                }
                
            }
        });
    }
      @FXML
    private void imprimerpdf(ActionEvent event) throws SQLException, DocumentException, IOException {
             PDFutil p = new PDFutil();
      p.listevenements();
    }

    }
   
    
  
    
