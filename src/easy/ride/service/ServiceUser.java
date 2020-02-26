/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.entities.User;
import easy.ride.entities.Utilisateur;
import easy.ride.IService.IUser;
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
public class ServiceUser implements IUser<User>{
    
    private Connection con;
    private Statement ste;
    public PreparedStatement pre;
    public String req;
    public ResultSet resultat;
    
    public ServiceUser() {
        con = DataBase.getInstance().getConnection();
    }
    
    //retourner user
    @Override
    public User SeConnecter(User u) throws SQLException {
        List<User> arr=new ArrayList<>();
        req = "select * from user where nom=? and pwd=?";
        pre=con.prepareStatement(req);
        pre.setString(1, u.getNom());
        pre.setString(2, u.getPwd());
        resultat= pre.executeQuery();
        User u1 = new User();
        while (resultat.next()) {
               u1.setId(resultat.getInt(1));
               u1.setRole(resultat.getString(5));
        }
        return u1;   
    }

    //retourner nom prenom du user
    @Override
    public String GetUserNomPrenon(int id) throws SQLException {
        String data = "";
        req = "select nomComplet from utilisateur where `id_user`=?";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        resultat = pre.executeQuery();
        while (resultat.next()) {
            data = resultat.getString(1);
        }
        return data;
    }
    
    //ajout chef site admin
    @Override
    public List<Integer> GetAllUserId() throws SQLException {
        List<Integer> arr=new ArrayList<>();
        req = "select id_user from utilisateur where `role`=?";
        pre = con.prepareStatement(req);
        pre.setString(1, "Client");
        resultat = pre.executeQuery();
        while (resultat.next()) {
            arr.add(resultat.getInt(1));
        }
        return arr;
    }
    
    @Override
    public Utilisateur GetAllInfoUser(int id) throws SQLException {
        req = "select * from utilisateur where `id_user`=?";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        //User u1 = new User();
        Utilisateur u = new Utilisateur();
        resultat = pre.executeQuery();
        while (resultat.next()) {
            u.setNomcomplet(resultat.getString(5));
            u.setMail(resultat.getString(6));
            u.setAdresse(resultat.getString(7));
            u.setTel(resultat.getString(8));
        }
        return u;
    }

    @Override
    public void UpdateRoleUser(int idsite,int id) throws SQLException {
        req = "Update utilisateur set role=? where `id_user`=?";
        pre = con.prepareStatement(req);            
        pre.setString(1,"Chef site "+String.valueOf(idsite));
        pre.setInt(2,id);
        pre.executeUpdate();
    }

    @Override
    public String getUserNomComplet(int id) throws SQLException {
        req = "select nomComplet from utilisateur where `id_user`=?";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        String data = "";
        resultat = pre.executeQuery();
        while (resultat.next()) {
            data = resultat.getString(1);
        }
        return data;
    }

    @Override
    public String getUserMail(int id) throws SQLException {
        req = "select mail from utilisateur where `id_user`=?";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        String data = "";
        resultat = pre.executeQuery();
        while (resultat.next()) {
            data = resultat.getString(1);
        }
        return data;
    }

    @Override
    public String getUserMailFromLocation(int id) throws SQLException {
        req = "select mail from utilisateur inner join detail_location "
                + "on detail_location.id_user = utilisateur.id_user inner join retours "
                + "on retours.id_location = detail_location.id"
                + " where detail_location.id=?";
        pre = con.prepareStatement(req);
        pre.setInt(1, id);
        String data = "";
        resultat = pre.executeQuery();
        while (resultat.next()) {
            data = resultat.getString(1);
        }
        return data;
    }
    
    
}
