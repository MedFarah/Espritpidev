/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package easy.ride.entities;




import java.sql.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class Maintenance {
    int id;
    String titre;
    String description;
    String date;
    int etat;
    int client;
    
    
    public Maintenance(){
        
    }
    
    public Maintenance(int id, String titre, String descript, String date, int etat, int client) {
        this.id = id;
        this.titre = titre;
        this.description = descript;
        this.date = date;
        this.etat = etat;
        this.client = client;
    }

    public Maintenance(String titre, String descript, String date, int etat, int client) {
        this.titre = titre;
        this.description = descript;
        this.date = date;
        this.etat = etat;
        this.client = client;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public int getClient() {
        return client;
    }

    public void setClient(int client) {
        this.client = client;
    }

   

  
    @Override
    public String toString() {
        return "Maintenance{" + "id=" + id + ", titre=" + titre + ", description=" + description + ", date=" + date + "id client= " + client +'}';
    }
}


