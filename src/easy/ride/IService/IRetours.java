/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.IService;

import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Dali
 */
public interface IRetours<Retour> {
    void ajouter(Retour r) throws SQLException;
    void delete(int id) throws SQLException;
    void update(Retour r,int id) throws SQLException;
    List<Retour> readAll() throws SQLException;
    
}
