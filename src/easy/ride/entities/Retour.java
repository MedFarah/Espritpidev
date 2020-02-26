/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.entities;

/**
 *
 * @author Dali
 */
public class Retour {
        private int id;
        private int id_location;
        private int etat;
        private int retard;

    public Retour(int id, int id_location, int etat, int retard) {
        this.id = id;
        this.id_location = id_location;
        this.etat = etat;
        this.retard = retard;
    }

    public Retour(int id_location, int etat, int retard) {
        this.id_location = id_location;
        this.etat = etat;
        this.retard = retard;
    }
    
    

    public int getId() {
        return id;
    }

    public int getId_location() {
        return id_location;
    }

    public int getEtat() {
        return etat;
    }

    public int getRetard() {
        return retard;
    }

   

    public void setId_location(int id_location) {
        this.id_location = id_location;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public void setRetard(int retard) {
        this.retard = retard;
    }

    @Override
    public String toString() {
        return "Retours{" + "id=" + id + ", id_location=" + id_location + ", etat=" + etat + ", retard=" + retard + '}';
    }
    
        
        
    
}
