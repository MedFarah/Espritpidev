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
 * @author ASUS
 */
public interface IServiceUtilisateur<T> {
    void ajouter(T t) throws SQLException;

    boolean delete(T t) throws SQLException;

    T connecter(String e, String s) throws SQLException;
    void Mail(String e,String from);
    boolean update(String password, int id) throws SQLException;

    void afficher() throws SQLException;

    void trier() throws SQLException;
    void trierRole() throws SQLException;

    List rechercher(String t, Utilisateur u) throws SQLException;
}
