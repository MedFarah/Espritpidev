/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.IService.IServiceReclamation;
import easy.ride.Utils.DataBase;
import easy.ride.entities.Reclamation;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author ASUS
 */
public class ServiceReclamation implements IServiceReclamation<Reclamation> {

    private Connection con;
    private Statement ste;

    public ServiceReclamation() {
        con = DataBase.getInstance().getConnection();

    }
    
    
    @Override
    public void ajouter(Reclamation t) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO reclamation VALUES (NULL, '" + t.getTypeReclamation()+ "', '" + t.getDateReclamation()+ "', '" + t.getImage()+ "', '"
                + t.getStatus()+ "', '" + t.getEmail()+ "','" + t.getObjet()+ "','" + t.getDescription()+ "','" + t.getId_user()+ "')";
        ste.executeUpdate(requeteInsert);    }

    @Override
    public boolean delete(Reclamation t) throws SQLException {
        PreparedStatement pre=con.prepareStatement("delete from reclamation where id=?");
        pre.setInt(1, t.getId());
       
       if( pre.executeUpdate()==1) 
           return true; 
       else 
           return false;    }

    @Override
    public boolean update(Reclamation t) throws SQLException {
        PreparedStatement pre=con.prepareStatement("UPDATE reclamation SET typeReclamation= ?,dateReclamation=?,image=?,status=?,email=?,objet=?,description=?,id_user=? where id= ?");
        pre.setInt(9, t.getId());
        pre.setString(1, t.getTypeReclamation());
        pre.setDate(2, (Date) t.getDateReclamation());
        pre.setString(3, t.getImage());
        pre.setString(4, t.getStatus());pre.setString(5, t.getEmail());pre.setString(6, t.getObjet());pre.setString(7, t.getDescription());
        pre.setInt(8, t.getId_user());
       if( pre.executeUpdate()==1) 
           return true; 
       else 
           return false;     }

    @Override
    public List<Reclamation> readAll() throws SQLException {
        List<Reclamation> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from reclamation");
     while (rs.next()) {                
               int id=rs.getInt("id");
               String typeReclamation=rs.getString("typeReclamation");
               Date dateReclamation=rs.getDate("dateReclamation");
               String image=rs.getString("image");
               String status=rs.getString("status");
               String email=rs.getString("email");
               String objet=rs.getString("objet");
               String description=rs.getString("description");
               int id_user=rs.getInt("id_user");
               Reclamation p=new Reclamation(id,typeReclamation ,dateReclamation,image,status,email,objet,description,id_user);
     arr.add(p);
     }
    return arr;
    }
    
    @Override
    public List<Reclamation> afficherReclamationParClient(int id_client) throws SQLException {
        List<Reclamation> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from reclamation where id_user="+id_client);
     while (rs.next()) {                
               int id=rs.getInt("id");
               String typeReclamation=rs.getString("typeReclamation");
               Date dateReclamation=rs.getDate("dateReclamation");
               String image=rs.getString("image");
               String status=rs.getString("status");
               String email=rs.getString("email");
               String objet=rs.getString("objet");
               String description=rs.getString("description");
               int id_user=rs.getInt("id_user");
               Reclamation p=new Reclamation(id,typeReclamation ,dateReclamation,image,status,email,objet,description,id_user);
               arr.add(p);
     }
    return arr;
    }
 
    
    public List<Reclamation> readAllByStatus(String s) throws SQLException {
        List<Reclamation> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from reclamation where status='"+s+"';");
     while (rs.next()) {                
               int id=rs.getInt("id");
               String typeReclamation=rs.getString("typeReclamation");
               Date dateReclamation=rs.getDate("dateReclamation");
               String image=rs.getString("image");
               String status=rs.getString("status");
               String email=rs.getString("email");
               String objet=rs.getString("objet");
               String description=rs.getString("description");
               int id_user=rs.getInt("id_user");
               Reclamation p=new Reclamation(id,typeReclamation ,dateReclamation,image,status,email,objet,description,id_user);
     arr.add(p);
     }
    return arr;
    }
    
    public List<Reclamation> readAllByStatusClient(String s,int idc) throws SQLException {
        List<Reclamation> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from reclamation where status='"+s+"' AND id_user='"+idc+"';");
     while (rs.next()) {                
               int id=rs.getInt("id");
               String typeReclamation=rs.getString("typeReclamation");
               Date dateReclamation=rs.getDate("dateReclamation");
               String image=rs.getString("image");
               String status=rs.getString("status");
               String email=rs.getString("email");
               String objet=rs.getString("objet");
               String description=rs.getString("description");
               int id_user=rs.getInt("id_user");
               Reclamation p=new Reclamation(id,typeReclamation ,dateReclamation,image,status,email,objet,description,id_user);
     arr.add(p);
     }
    return arr;
    }
    
    
    public int get_Number_Reclamation() {
        int Message_Number=0;
        try {
            ste=con.createStatement();
            ResultSet rs=ste.executeQuery("SELECT COUNT(*) FROM `reclamation`");
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
