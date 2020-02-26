/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.entities.Site;
import easy.ride.IService.ISite;
import easy.ride.Utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Dali
 */
public class ServiceSite implements ISite<Site> {
    private Connection con;
    private Statement ste;
    public PreparedStatement pre;
    public String req;
    public ResultSet resultat;

    public ServiceSite() {
        con = DataBase.getInstance().getConnection();
    }

    //ajout site admin
    @Override
    public void ajouter(Site t) throws SQLException {
        req = "INSERT INTO `site`( `emplacement`, `longitude`, `latitude`) VALUES (?,?,?);";
        pre=con.prepareStatement(req);
        pre.setString(1, t.getEmplacement());
        pre.setDouble(2, t.getX());
        pre.setDouble(3, t.getY());
        pre.executeUpdate();
        System.out.println("site ajouté");
    }

    //supprimer site admin
    @Override
    public void delete(int id) throws SQLException {
        req = "delete from site where id =?";
        pre = con.prepareStatement(req);
        pre.setInt(1,id);
        pre.executeUpdate();
        System.out.println("site supprimé");
         
    }

    //modifier site admin
    @Override
    public void update(Site s,int id) throws SQLException {
        req = "UPDATE `site` SET `emplacement`=?,`x`=?,`y`=? WHERE `id`=?";
        pre = con.prepareStatement(req);
        pre.setString(1, s.getEmplacement());
        pre.setDouble(2,s.getX());
        pre.setDouble(3,s.getY());
        pre.setInt(4,id);
        pre.executeUpdate();
        System.out.println("site modifié");
    }

    //get all site admin
    @Override
    public List<Site> readAll() throws SQLException {
        List<Site> arr=new ArrayList<>();
        req = "select * from site";
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery(req);
        while (rs.next()) {                
               int id=rs.getInt(1);
               String emplacement=rs.getString("emplacement");
               Double x=rs.getDouble(3);
               Double y=rs.getDouble(4);
               Site s=new Site(id,emplacement,x,y);
               arr.add(s);
        }
        return arr;
    }

    //get all sites nom (ajout location user)
    @Override
    public List<String> getAllSiteNoms() throws SQLException {
        List<String> arr=new ArrayList<>();
        req = "select emplacement from site";
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery(req);
        while (rs.next()) {
               arr.add(rs.getString(1));
        }
        return arr;
    }

    //get site id (ajout location user)
    @Override
    public int getSiteId(String emplacement) throws SQLException {
        req = "select id from site where `emplacement`=?";
        pre = con.prepareStatement(req);
        pre.setString(1, emplacement);
        resultat = pre.executeQuery();
        int data = 0;
        while (resultat.next()) {
            data = resultat.getInt("id");
        }
        return data;
    }
   
    }

    
    
