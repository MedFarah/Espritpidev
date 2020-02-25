/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.IService.IService;
import easy.ride.Utils.DataBase;
import easy.ride.entities.Evenements;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.DatePicker;

/**
 *
 * @author suare
 */
public class Serviceevenements implements IService<Evenements>{
    private final Connection con;
    private Statement ste;
    public Serviceevenements() {
        con = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Evenements t) throws SQLException
    {
    PreparedStatement pre=con.prepareStatement("INSERT INTO `evenements` (`id`, `Nom_evenements`,`nombre`,`dateeve`,`lieuxeve`,`descreptioneve`)"
                  + "VALUES ( Null,?, ?, ?,?,?);");
    pre.setString(1, t.getNom_evenements());
    
    pre.setInt(2, t.getNombre());
   pre.setString(3, t.getDateeve());
   pre.setString(4, t.getLieuxeve());
   pre.setString(5, t.getDescreptioneve());
    pre.executeUpdate();
    }
            
@Override
    public void delete(Evenements a) throws SQLException {
        ste = con.createStatement();
        String requeteDelete = "DELETE FROM Evenements WHERE nom_evenements = ? ;";
        PreparedStatement pst = con.prepareStatement(requeteDelete);
        pst.setString(1, a.getNom_evenements());
        pst.executeUpdate();
    }

  
 /*   
  public boolean update(Evenements t) throws SQLException {
            boolean test=false;

    PreparedStatement pre= con.prepareStatement(" UPDATE Evenements SET `nom_evenements`= ?,`nombre`= ? ,`dateeve`= ? WHERE `nom_evenements`=?");
    pre.setString(1, t.getNom_evenements());                
    pre.setInt(2, t.getNombre());
    pre.setString(3, t.getDateeve());
    pre.setString(4, t.getNom_evenements());

       System.out.println(pre);
       pre.executeUpdate();
       
            return true;
            
    } 
*/
  public boolean update(Evenements t) throws SQLException {
            boolean test=false;

            PreparedStatement pre= con.prepareStatement(" UPDATE Evenements SET `nom_evenements`= ?, `nombre`= ?,`dateeve`= ?, `lieuxeve`= ?, `descreptioneve`= ? WHERE `id`=?");
           pre.setString(1, t.getNom_evenements());
           pre.setInt(2, t.getNombre());
           pre.setString(3, t.getDateeve());
          pre.setString(4, t.getLieuxeve());
   pre.setString(5, t.getDescreptioneve());
           pre.setInt(6, t.getId());
                    System.out.println(pre);
           pre.executeUpdate();
            return true;
    }
  /*
public boolean update1(Evenements t) throws SQLException {
            boolean test=false;

            PreparedStatement ste= con.prepareStatement(" UPDATE `news` SET Subject`= ?,Text`= ?, Date`= ?,ImageNews`= ? WHERE `Subject`=?");
            ste.setString(1,t.getSubject() );
              ste.setString(2,t.getText() );
                ste.setTimestamp(3,  t.getDate() );
                  ste.setString(4, t.getPhoto() );
                    //ste.setInt(5,t.getId() );
                    ste.setString(5,t.getSubject() );
                    System.out.println(ste);
                        ste.executeUpdate();
            return true;
    }

*/
    public ObservableList<Evenements> readAll() throws SQLException {
    ObservableList<Evenements> arr=FXCollections.observableArrayList();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from evenements");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String nom_evenements=rs.getString(2);
               int nombre=rs.getInt(3);
               String dateeve=rs.getString(4);
               String lieuxeve=rs.getString(5);
               String descreptioneve =rs.getString(6);

               Evenements p=new Evenements(id,nom_evenements,nombre,dateeve,lieuxeve,descreptioneve);
     arr.add(p);
     }
    return arr;
    }

    public int get_Number_Reclamation() {
        int Message_Number=0;
        try {
            ste=con.createStatement();
            ResultSet rs=ste.executeQuery("SELECT COUNT(*) FROM `evenements`");
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


