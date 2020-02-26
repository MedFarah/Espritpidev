/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.entities.Detail_location;
import easy.ride.IService.IDetail_location;
import easy.ride.Utils.DataBase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Dali
 */
public class ServiceDetail_location implements IDetail_location<Detail_location>{
    
    private Connection con;
    private Statement ste;
    public ResultSet resultat;
    public PreparedStatement pre;
    public String req;

    public ServiceDetail_location() {
        con = DataBase.getInstance().getConnection();
    }
    
    //ajout location user
    @Override
    public void ajouter(Detail_location d) throws SQLException {
        req = "INSERT INTO `detail_location`(`id_user`, `id_type`, `id_site`,`date_debut`, `date_fin`, `status`) VALUES (?,?,?,?,?,?);";
        pre=con.prepareStatement(req);
        pre.setInt(1, d.getId_user());
        pre.setString(2, d.getId_type());
        pre.setString(3, d.getId_site());
        pre.setDate(4, d.getDate_debut());
        pre.setDate(5, d.getDate_fin());
        pre.setString(6, "En cours");
        pre.executeUpdate();
        System.out.println("location ajouté");
    }

    //supprimer location user
    @Override
    public void AnnulerLocation(int id) throws SQLException {
        req = "delete from detail_location where id_user =? and status=?";
        pre = con.prepareStatement(req);
        pre.setInt(1,id);
        pre.setString(2, "En cours");
        pre.executeUpdate();
        System.out.println("location supprimés");
    }

    //affichage detail location admin
    @Override
    public ObservableList<Detail_location> getAllAdmin() throws SQLException {
        req = "SELECT * FROM `detail_location`";
        pre = con.prepareStatement(req);
        resultat = pre.executeQuery();
        ObservableList<Detail_location> data = FXCollections.observableArrayList();
        while (resultat.next()) {
            Detail_location dl = new Detail_location();
            dl.setId(resultat.getInt("id"));
            dl.setId_site(resultat.getString("id_site"));
            dl.setId_type(resultat.getString("id_site"));
            dl.setId_user(resultat.getInt("id_user"));
            dl.setDate_debut(resultat.getDate("date_debut"));
            dl.setDate_fin(resultat.getDate("date_fin"));
            dl.setStatus(resultat.getString("status"));
            data.add(dl);
        }
        return data;
    }
    
    //affichage detail location chef site
    @Override
    public ObservableList<Detail_location> readAllChefSite(int id) throws SQLException {
        ObservableList<Detail_location> data = FXCollections.observableArrayList();
        req = "SELECT detail_location.id, `id_user`, `type`, `date_debut`, `date_fin`, `status` FROM `detail_location` inner join `type` on detail_location.id_type=type.id where id_site = ?";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        resultat = pre.executeQuery();
        while (resultat.next()) {
            Detail_location dl = new Detail_location(resultat.getInt("id"), resultat.getInt("id_user"), resultat.getString("type"), resultat.getDate("date_debut"), resultat.getDate("date_fin"), resultat.getString("status"));
            data.add(dl);
        }
        return data;
    }
    
    //affichage detail location user
    @Override
    public ObservableList<Detail_location> getAllHistoryUser(int id) throws SQLException {
        ObservableList<Detail_location> data = FXCollections.observableArrayList();
        req = "SELECT `type`, `date_debut`, `date_fin`, `emplacement` FROM `detail_location` "
                + "inner join `type` on detail_location.id_type=type.id "
                + "inner join `site` on detail_location.id_site=site.id "
                + "where id_user = ? and status=?";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        pre.setString(2, "Terminée");
        resultat = pre.executeQuery();
        while (resultat.next()) {
            Detail_location dl = new Detail_location(resultat.getString("type"),resultat.getString("emplacement"), resultat.getDate("date_debut"), resultat.getDate("date_fin"));
            data.add(dl);
        }
        return data;
    }

    //liste des id des retours chef site
    @Override
    public List<Integer> GetListIdEnCours(int id) throws SQLException {
        List<Integer> l1 = new ArrayList<>();
        req = "SELECT id FROM `detail_location` where status=? and id_site=?";
        pre = con.prepareStatement(req);
        pre.setString(1, "En cours");
        pre.setInt(2, id);
        resultat = pre.executeQuery();
        while (resultat.next()) {
            l1.add(resultat.getInt("id"));
        }
        return l1;
    }

    //confirmation retour chef site
    @Override
    public void SetEtatTermine(int id) throws SQLException {
        req = "UPDATE `detail_location` SET`status`=? WHERE id=?";
        pre = con.prepareStatement(req);
        pre.setString(1, "Terminée");
        pre.setInt(2, id);
        pre.executeUpdate();  
    }

    //nbr location en cours user
    @Override
    public Boolean getValidLocation(int id) throws SQLException {
        req = "SELECT count(*) from `detail_location` where id_user=? and status=? ";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        pre.setString(2, "En cours");
        resultat = pre.executeQuery();
        int count = 0;
        while (resultat.next()) {
            count = resultat.getInt(1);
        }
        return count == 0;
    }
    
    //nbr retour aujourd'hui chef site
    @Override
    public int GetCountRetour(int site) throws SQLException {
        req = "SELECT count(*) from `detail_location` where id_site=? and date_fin=? and status=? ";
        pre = con.prepareStatement(req);
        pre.setInt(1, site);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date();
        pre.setString(2, dateFormat.format(date).toString());
        pre.setString(3, "En cours");
        resultat = pre.executeQuery();
        int count = 0;
        while (resultat.next()) {
            count = resultat.getInt(1);
        }
        return count ;
    }

    //location en cours user
    @Override
    public Detail_location getLocationEnCours(int id) throws SQLException {
        req = "SELECT date_debut, date_fin, type, emplacement from `detail_location` "
                + "inner join `type` on detail_location.id_type=type.id "
                + "inner join `site` on detail_location.id_site=site.id "
                + "where id_user=? and status=? ";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        pre.setString(2, "En cours");
        resultat = pre.executeQuery();
        Detail_location dl = new Detail_location();
        while (resultat.next()) {
            dl.setDate_debut(resultat.getDate("date_debut"));
            dl.setDate_fin(resultat.getDate("date_fin"));
            dl.setId_type(resultat.getString("type"));
            dl.setId_site(resultat.getString("emplacement"));
        }
        return dl;
    }
    
    
    
    
    
}
