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
public class User {
    private int id;
    private String nom;
    private String prenom;
    private String pwd;
    private String role;

    public User(int id, String nom, String prenom, String pwd, String role) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.pwd = pwd;
        this.role = role;
    }

    public User(String nom, String pwd) {
        this.nom = nom;
        this.pwd = pwd;
    }

    public User(int id, String role) {
        this.id = id;
        this.role = role;
    }

    public User() {
        
    }
    
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", pwd=" + pwd + ", role=" + role + '}';
    }
    
    
    
    
}
