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
public interface ISite<Site> {
    void ajouter(Site t) throws SQLException;
    void delete(int id) throws SQLException;
    void update(Site t,int id) throws SQLException;
    List<Site> readAll() throws SQLException;
    
    List<String> getAllSiteNoms() throws SQLException;
    int getSiteId(String t) throws SQLException;
    
}
