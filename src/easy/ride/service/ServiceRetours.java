/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.entities.Retour;
import easy.ride.IService.IRetours;
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
public class ServiceRetours implements IRetours<Retour>{
    private Connection con;
    private Statement ste;
    public PreparedStatement pre;
    public String req;
    public ResultSet resultat;

    public ServiceRetours() {
        con = DataBase.getInstance().getConnection();
    }

    //supprimer retour
    @Override
    public void delete(int id) throws SQLException {
        req = "delete from retours where id =?";
        pre = con.prepareStatement(req);
        pre.setInt(1,id);
        pre.executeUpdate();
    }

    //ajout retour chef site
    @Override
    public void ajouter(Retour r) throws SQLException {
        req = "INSERT INTO `retours`( `id_location`, `etat`, `retard`) VALUES (?,?,?);";
        pre=con.prepareStatement(req);
        pre.setInt(1, r.getId_location());
        pre.setInt(2, r.getEtat());
        pre.setInt(3, r.getRetard());
        pre.executeUpdate();
        System.out.println("retour ajouté");
    }

    //modifer retour chef site
    @Override
    public void update(Retour r, int id) throws SQLException {
        req = "UPDATE `retours` SET `id_location`=?,`etat`=?,`retard`=? WHERE `id`=?";
        pre = con.prepareStatement(req);            
        pre.setInt(1,r.getId_location());
        pre.setInt(2,r.getEtat());
        pre.setInt(3,r.getRetard());
        pre.setInt(4,id);
        pre.executeUpdate();
    }

    //get all retours chef site
    @Override
    public List<Retour> readAll() throws SQLException {
        List<Retour> arr=new ArrayList<>();
        req = "select * from retours";
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery(req);
        while (rs.next()) {                
               int id=rs.getInt(1);
               int id_location=rs.getInt(2);
               int etat=rs.getInt(3);
               int retard=rs.getInt(4);
               Retour s=new Retour(id,id_location,etat,retard);
               arr.add(s);
        }
        return arr;
    }
}
