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

public interface IType<Type> {
    void ajouter(Type s) throws SQLException;
    void delete(int id) throws SQLException;
    void update(Type s,int id) throws SQLException;
    List<Type> readAll() throws SQLException;
    
    int getTypeId(String id) throws SQLException;
    public List<String> getAllTypeName() throws SQLException;
    
    //List<int> getAllTypeId() throws SQLException;
    
}
