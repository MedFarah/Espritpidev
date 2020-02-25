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
 * @author Administrateur
 */
public interface IServiceCommande<T> {
    public void Valider(T c)throws SQLException;
	public boolean Annuler(String ref)throws SQLException;
	public boolean update(T c)throws SQLException;
	public List<T>getAll ()throws SQLException ;
	public T findByReference(String ref)throws SQLException;
}
