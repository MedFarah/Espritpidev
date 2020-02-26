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
public class Site {
    private int id;
    private String emplacement;
    private double langitude;
    private double latitude;

    public Site() {
    }

    
    public Site(int id, String emplacement, double langitude, double latitude) {
        this.id=id;
        this.emplacement = emplacement;
        this.langitude = langitude;
        this.latitude = latitude;
    }

    public Site(String emplacement, double langitude, double latitude) {
        this.emplacement = emplacement;
        this.langitude = langitude;
        this.latitude = latitude;
    }
    
    

    public int getId() {
        return id;
    }

    public String getEmplacement() {
        return emplacement;
    }

    public double getX() {
        return langitude;
    }

    public double getY() {
        return latitude;
    }

    public void setEmplacement(String emplacement) {
        this.emplacement = emplacement;
    }

    public void setX(double langitude) {
        this.langitude = langitude;
    }

    public void setY(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Site{" + "id=" + id + ", emplacement=" + emplacement + ", langitude=" + langitude + ", latitude=" + latitude + '}';
    }
    
    
    
    
    
}
