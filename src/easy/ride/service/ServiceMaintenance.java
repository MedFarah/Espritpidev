/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.ride.service;


import easy.ride.IService.IServiceMaintenance;
import easy.ride.Utils.DataBase;
import easy.ride.entities.Maintenance;
import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author HP
 */
public class ServiceMaintenance implements IServiceMaintenance<Maintenance> {
    
    
    
    private Connection con;
    private Statement ste;

    public ServiceMaintenance() {
        con = DataBase.getInstance().getConnection();

    }
    
    public  ObservableList<Maintenance> afficher(Maintenance M) throws SQLException {
           
       ObservableList<Maintenance> arr =FXCollections.observableArrayList() ; 
   ste = con.createStatement();
        ResultSet rs = ste.executeQuery("select * from maintenance");
        while (rs.next()) {
    arr.add(new Maintenance(rs.getInt("id") ,rs.getString("titre"),rs.getString("description"),rs.getString("date"),rs.getInt("etat"),rs.getInt("client")));
         System.out.println("*********************************");
        }
        return arr;

    }

    @Override
    public void ajouter(Maintenance t) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO `maintenance` (`id`, `titre`, `description`, `date`, `etat`, `client`) VALUES (NULL, '" + t.getTitre()+ "', '" + t.getDescription()+ "', '" + t.getDate()+ "', '" + t.getEtat()+ "', '" + t.getClient()+ "');";
        System.out.println(requeteInsert);
        ste.executeUpdate(requeteInsert);
    }

    @Override
    public boolean delete(Maintenance t) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "DELETE FROM `maintenance` WHERE `maintenance`.`id` = '"+ t.getId()+"' ";
        ste.executeUpdate(requeteInsert);
        return false;
    }

    @Override
    public boolean update(Maintenance t) throws SQLException {
       ste = con.createStatement();
        String requeteInsert = "UPDATE `maintenance` SET `titre` = '"+t.getTitre()+"', `description` = '"+t.getDescription()+"' , `date` = '"+t.getDate()+"', `etat` = '"+t.getEtat()+"', `client` = '"+t.getClient()+"' WHERE `maintenance`.`id` = '"+t.getId()+"';";
        ste.executeUpdate(requeteInsert); 
        return false;
    }

    @Override
    public List<Maintenance> readAll() throws SQLException {
        List<Maintenance> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from maintenance");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String titre=rs.getString("titre");
               String description=rs.getString(3);
               String date=rs.getString(4);
               int etat=rs.getInt(5);
               int id_client=rs.getInt(6);
               Maintenance m=new Maintenance(id, titre, description, date, etat, id_client);
     arr.add(m);
     }
    return arr;
    }
    
    public List<Maintenance> sorted() throws SQLException {
        List<Maintenance> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from maintenance order by titre");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String titre=rs.getString("titre");
               String description=rs.getString(3);
               String date=rs.getString(4);
               int etat=rs.getInt(5);
               int id_client=rs.getInt(6);
               Maintenance m=new Maintenance(id, titre, description, date, etat, id_client);
     arr.add(m);
     }
    return arr;
    }
    
    public List<Maintenance> recherche(int recherche) throws SQLException {
        List<Maintenance> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from maintenance where client like '"+recherche+"'");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String titre=rs.getString("titre");
               String description=rs.getString(3);
               String date=rs.getString(4);
               int etat=rs.getInt(5);
               int id_client=rs.getInt(6);
               Maintenance m=new Maintenance(id, titre, description, date, etat, id_client);
     arr.add(m);
     }
    return arr;
    }
    
    public int total() throws SQLException {
        List<Maintenance> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from maintenance");
    int total=0;
     while (rs.next()) {          
               total++;
     }
    return total;
        
    }
    
    public int get_Number_Maintenance() {
        int Message_Number=0;
        try {
            ste=con.createStatement();
            ResultSet rs=ste.executeQuery("SELECT COUNT(*) FROM `maintenance`");
            while (rs.next()) {
                Message_Number = rs.getInt(1);
            }
            return Message_Number;
        } catch (SQLException ex) {
            Logger.getLogger(ServiceReclamation.class.getName()).log(Level.SEVERE, null, ex);
        }
            

        return Message_Number;
        
    }
    
}
