/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.IService.IServiceProduit;
import easy.ride.Utils.DataBase;
import easy.ride.entities.Produit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author suare
 */
public class ServiceProduit implements IServiceProduit<Produit>{
    private final Connection con;
    private Statement ste;
    public ServiceProduit() {
        con = DataBase.getInstance().getConnection();

    }

    @Override
    public void ajouter(Produit t) throws SQLException
    {
    PreparedStatement pre=con.prepareStatement("INSERT INTO `produit` (`id`, `nom`,`description`,`couleur`,`prix`,`prixt`,`remise`,`prixr`)"
                  + "VALUES ( Null,?,?, ?,?, ?,?, ?);");
    pre.setString(1, t.getNom());
    pre.setString(2, t.getDescription());
    pre.setString(3, t.getCouleur());
    pre.setInt(4, t.getPrix());
    pre.setDouble(5, t.getPrixt());
    pre.setInt(6, t.getRemise());
    pre.setDouble(7, t.getPrixr());
    pre.executeUpdate();
    }
            
@Override
    public void delete(Produit a) throws SQLException {
        ste = con.createStatement();
        String requeteDelete = "DELETE FROM produit WHERE nom = ? ;";
        PreparedStatement pst = con.prepareStatement(requeteDelete);
        pst.setString(1, a.getNom());
        pst.executeUpdate();
    }

    @Override
    public void update(Produit t) throws SQLException {
          
             ste=con.createStatement();
          String requeteUpdate="UPDATE `produit` SET `nom`='"+t.getNom()+"' ,`description`='"+t.getDescription()+"',`couleur`= '"+t.getCouleur()+"'  ,`prix`='"+t.getPrix()+"',`prixt`='"+t.getPrixt()+"' ,`remise`='"+t.getRemise()+"' ,`prixr`='"+t.getPrixr()+"'WHERE id= '"+t.getId()+"'";
          ste.executeUpdate(requeteUpdate);
    
    }
           
 
      
    
   



    public ObservableList<Produit> readAll() throws SQLException {
    ObservableList<Produit> arr=FXCollections.observableArrayList();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from produit");
     while (rs.next()) {                
               int id=rs.getInt(1);
               String nom=rs.getString(2);
              String description=rs.getString(3);
              String couleur= rs.getString(4);
               int prix=rs.getInt(5);
                  double prixt=rs.getDouble(6);
                    int remise=rs.getInt(7);
                  double prixr=rs.getDouble(8);
               Produit p=new Produit(id, nom, description,couleur,prix,prixt,remise,prixr);
     arr.add(p);
     }
    return arr;
    }

   //To change body of generated methods, choose Tools | Templates.
    
    
}


