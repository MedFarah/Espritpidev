/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.IService.IServiceCommande;
import easy.ride.Utils.DataBase;
import easy.ride.entities.Commande;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Administrateur
 */
public class ServiceCommande implements IServiceCommande<Commande> {
    private Connection con;
    private Statement ste;

    public ServiceCommande() {
        con = DataBase.getInstance().getConnection();
    }
   @Override
    public void Valider(Commande c) throws SQLException {
        ste = con.createStatement();
        String requeteInsert = "INSERT INTO `commande` (`ref_cmd`, `date_cmd`, `etat_cmd`, `prix_cmd`) VALUES ( '" + c.getRef() + "', '"+c.getDate()+"', '" + c.getEtat() + "', '" + c.getPrix() + "');";
        ste.executeUpdate(requeteInsert);
       
    }
    
    public void ajouter1(Commande c) throws SQLException
    {
        try{
    PreparedStatement pre=con.prepareStatement("INSERT INTO `pidev`.`commande` (`ref_cmd`, `date_cmd`, `etat_cmd`, `prix_cmd`, `id_user`) VALUES ( ?, ?, ?, ?,?);");
  
    Date currentDatetime = new Date(System.currentTimeMillis());
        java.sql.Date sqlDate = new java.sql.Date(currentDatetime.getTime());
    pre.setString(1, c.getRef());
    pre.setDate(2, sqlDate);
    pre.setString(3, c.getEtat());
    pre.setFloat(4, c.getPrix());
    pre.setInt(5, c.getId_user());
    pre.executeUpdate();
    }catch(Exception e)
    {
        System.out.println("nooooo");
    }
    
    }

    @Override
    public boolean Annuler(String ref) throws SQLException {
        ste = con.createStatement();
         ste.executeUpdate("delete from `commande` where ref_cmd = '"+ref+"';");
				return true ; 
     }
    public boolean delete(Commande t) throws SQLException {
        Date currentDatetime = new Date(System.currentTimeMillis());
        java.sql.Date sqlDate = new java.sql.Date(currentDatetime.getTime());
        PreparedStatement pre = null;
        try{
        if (sqlDate.getDate()-t.getDate().getDate()<3)
        {
         pre=con.prepareStatement("delete from commande where ref_cmd =? ");
        pre.setString(1, t.getRef());
        }
        else
        {System.out.println("noooooo");
        System.out.println(t.getDate().getDay());
        System.out.println(t.getDate().getDate());
        System.out.println(sqlDate.getDate());
        return false;
        }
        }catch(Exception e)
        { System.out.println("you can't");
            System.out.println(e);}
       if( pre.executeUpdate()==1) 
           return true; 
       else 
           return false;    }
    
    @Override
    public boolean update(Commande c) throws SQLException {
        ste = con.createStatement();
        ste.executeUpdate("update  commande set etat_cmd='"+c.getEtat()+"', prix_cmd ='"+c.getPrix()+"' where ref_cmd = '"+c.getRef()+"' ;");
				
   
        return true;
    }
    
    public boolean update2(Commande t) throws SQLException {
        PreparedStatement pre=con.prepareStatement("UPDATE commande SET date_cmd= ?,etat_cmd=?,prix_cmd ==?  where ref_cmd= ?");
        
        pre.setString(1, t.getRef());
        pre.setDate(2, (java.sql.Date) t.getDate());
        pre.setString(3, t.getEtat());
        pre.setFloat(4, t.getPrix());
        
       if( pre.executeUpdate()==1) 
           return true; 
       else 
           return false;  
    }

    @Override
    public List<Commande> getAll() throws SQLException {
        List<Commande> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from commande");
    
     while (rs.next()) {                
               String ref=rs.getString(1);
               Date date=rs.getDate(2);
               String etat=rs.getString(3);
               float prix=rs.getFloat(4);
               int id_user=rs.getInt(5);
               Commande  p=new Commande(ref, date, etat, prix,id_user);
     arr.add(p);
     }
    return arr;
  
    }

    @Override
    public Commande findByReference(String ref) throws SQLException {
        
            ste=con.createStatement();
			ResultSet rs = ste.executeQuery("select * from commande where ref_cmd= '"+ref+"';");
			if(rs.next()) {
				return new Commande(rs.getString(1),rs.getDate(2),rs.getString(3),rs.getFloat(4),rs.getInt(5));
			}
		
		return null ;
        }
    
    public List<Commande> tri() throws SQLException {
        List<Commande> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from commande order by date");
    
     while (rs.next()) {                
               String ref=rs.getString(1);
               Date date=rs.getDate(2);
               String etat=rs.getString(3);
               float prix=rs.getFloat(4);
               int id_user=rs.getInt(5);
               Commande  p=new Commande(ref, date, etat, prix,id_user);
     arr.add(p);
     }
    return arr;
  
    }
    
     public List<Commande> AfficherCommandeParClient(int id_user) throws SQLException {
        List<Commande> arr=new ArrayList<>();
    ste=con.createStatement();
    ResultSet rs=ste.executeQuery("select * from commande where id_user="+id_user);
      while (rs.next()) {                
               String ref=rs.getString(1);
               Date date=rs.getDate(2);
               String etat=rs.getString(3);
               float prix=rs.getFloat(4);
               int id=rs.getInt(5);
               Commande  p=new Commande(ref, date, etat, prix,id);
     arr.add(p);
     }
    return arr;
    }
}
