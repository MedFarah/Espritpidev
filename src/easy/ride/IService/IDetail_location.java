/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.IService;

import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Dali
 */
public interface IDetail_location<Detail_location> {
    void ajouter(Detail_location d) throws SQLException;
    ObservableList<Detail_location> readAllChefSite(int id) throws SQLException;
    List<Integer> GetListIdEnCours(int id) throws SQLException;
    void SetEtatTermine(int id) throws SQLException;
    
    Boolean getValidLocation(int id) throws SQLException;
    Detail_location getLocationEnCours(int id) throws SQLException;
    
    ObservableList<Detail_location> getAllHistoryUser(int id) throws SQLException;
    void AnnulerLocation(int id) throws SQLException;
    int GetCountRetour(int site) throws SQLException;
    
    ObservableList<Detail_location> getAllAdmin() throws SQLException;
    int getNbrLocationSelonStatus(String status) throws SQLException;
    
    
}
