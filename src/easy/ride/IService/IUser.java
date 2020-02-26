/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.IService;

import easy.ride.entities.Utilisateur;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dali
 */
public interface IUser<User> {
    User SeConnecter(User u) throws SQLException;
    String GetUserNomPrenon(int id) throws SQLException;
    List<Integer> GetAllUserId() throws SQLException;
    Utilisateur GetAllInfoUser(int id) throws SQLException;
    void UpdateRoleUser(int idsite,int id) throws SQLException;
    String getUserNomComplet(int id) throws SQLException;
    String getUserMail(int id) throws SQLException;
    String getUserMailFromLocation(int id) throws SQLException;
    /*void delete(int id) throws SQLException;
    void update(User u,int id) throws SQLException;
    List<User> readAll() throws SQLException;*/
}
