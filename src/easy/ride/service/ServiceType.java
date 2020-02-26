/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.service;

import easy.ride.entities.Type;
import easy.ride.IService.IType;
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

public class ServiceType implements IType<Type> {
    private Connection con;
    private Statement ste;
    public PreparedStatement pre;
    public String req;
    public ResultSet resultat;

    public ServiceType() {
        con = DataBase.getInstance().getConnection();
    }

    //ajout type admin
    @Override
    public void ajouter(Type s) throws SQLException {
        req = "INSERT INTO `type`( `type`, `image`) VALUES (?,?);";
        pre=con.prepareStatement(req);
        pre.setString(1, s.getType());
        pre.setString(2,s.getImage());
        pre.executeUpdate();
        System.out.println("type ajouté");
    }

    //supprimer type admin
    @Override
    public void delete(int id) throws SQLException {
        req = "delete from site where id =?";
        pre = con.prepareStatement(req);
        pre.setInt(1,id);
        pre.executeUpdate();
        System.out.println("type supprimé");
    }
    
    //modifier type admin
    @Override
    public void update(Type s,int id) throws SQLException {
        req = "UPDATE `type` SET`type`=?,`image`=? WHERE id=?";
        pre = con.prepareStatement(req);
        pre.setString(1, s.getType());
        pre.setString(2, s.getImage());
        pre.setInt(3,id);
        pre.executeUpdate();    
        System.out.println("type modifié");
   }

    //voir liste type admin
    @Override
    public List<Type> readAll() throws SQLException {
        List<Type> arr=new ArrayList<>();
        req = "select * from type";
        pre = con.prepareStatement(req);
        resultat = pre.executeQuery(req);
        while (resultat.next()) {                
               int id=resultat.getInt(1);
               String type=resultat.getString("type");
               String image=resultat.getString("image");
               Type s=new Type(id,type,image);
               arr.add(s);
        }
        return arr;    
    }

    //get all type nom (ajout location user)
    @Override
    public List<String> getAllTypeName() throws SQLException {
        List<String> arr=new ArrayList<>();
        ste=con.createStatement();
        ResultSet rs=ste.executeQuery("select type from type");
        while (rs.next()) {
               arr.add(rs.getString(1));
        }
        return arr;    
    }
    
    //get type id (ajout location user)
    @Override
    public int getTypeId(String type) throws SQLException {
        req = "select id from type where `type`=?";
        pre = con.prepareStatement(req);
        pre.setString(1, type);
        resultat = pre.executeQuery();
        int data = 0;
        while (resultat.next()) {
            data = resultat.getInt("id");
        }
        return data;
    }
    
}
