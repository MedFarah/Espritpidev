/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.entities;

import java.sql.Date;

/**
 *
 * @author Dali
 */
public class Detail_location {
    private int id;
    private int id_user;
    private String id_type;
    private String id_site;
    private Date date_debut;
    private Date date_fin;
    private String status;

    public Detail_location(int id, int id_user, String id_type, String id_site, Date date_debut, Date date_fin, String status) {
        this.id = id;
        this.id_user = id_user;
        this.id_type = id_type;
        this.id_site = id_site;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.status = status;
    }

    public Detail_location(int id, int id_user, String id_type, Date date_debut, Date date_fin, String status) {
        this.id = id;
        this.id_user = id_user;
        this.id_type = id_type;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.status = status;
    }

    public Detail_location(int id_user, String id_type, String id_site,Date date_debut, Date date_fin) {
        this.id_user = id_user;
        this.id_type = id_type;
        this.id_site = id_site;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    //Historique user
    public Detail_location(String id_type, String id_site, Date date_debut, Date date_fin) {
        this.id_type = id_type;
        this.id_site = id_site;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }

    public Detail_location() {
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public int getId_user() {
        return id_user;
    }

    public String getId_type() {
        return id_type;
    }

    public String getId_site() {
        return id_site;
    }

    public Date getDate_debut() {
        return date_debut;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public String getStatus() {
        return status;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
    }

    public void setId_site(String id_site) {
        this.id_site = id_site;
    }

    public void setDate_debut(Date date_debut) {
        this.date_debut = date_debut;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Detail_location{" + "id=" + id + ", id_user=" + id_user + ", id_type=" + id_type + ", id_site=" + id_site + ", date_debut=" + date_debut + ", date_fin=" + date_fin + ", status=" + status + '}';
    }
    
   
    
}
