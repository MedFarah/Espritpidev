/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easyride.Entite;

/**
 *
 * @author suare
 */
public class Evenements {
     private int id;
    private String nom_evenements; 
    private int nombre;
 private String lieuxeve;
 private String descreptioneve;
    private String dateeve;
    
    public Evenements() {
    }

    public Evenements(int id, String nom_evenements, int nombre,String dateeve, String lieuxeve, String descreptioneve) {
        this.id = id;
        this.nom_evenements = nom_evenements;
        this.nombre = nombre;
        this.dateeve = dateeve;
        this.lieuxeve = lieuxeve;
        
        this.descreptioneve = descreptioneve;
        
    }

  

   
   
    public Evenements(String nom_evenements, int nombre, String dateeve,String lieuxeve,String descreptioneve) {
        this.nom_evenements = nom_evenements;
        this.nombre = nombre;
        this.dateeve = dateeve;
         this.lieuxeve = lieuxeve;
           this.descreptioneve = descreptioneve;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_evenements() {
        return nom_evenements;
    }

    public void setNom_evenements(String nom_evenements) {
        this.nom_evenements = nom_evenements;
    }

    public int getNombre() {
        return nombre;
    }

    public void setNombre(int nombre) {
        this.nombre = nombre;
    }

    public String getLieuxeve() {
        return lieuxeve;
    }

    public void setLieuxeve(String lieuxeve) {
        this.lieuxeve = lieuxeve;
    }

    public String getDescreptioneve() {
        return descreptioneve;
    }

    public void setDescreptioneve(String descreptioneve) {
        this.descreptioneve = descreptioneve;
    }

    public String getDateeve() {
        return dateeve;
    }

    public void setDateeve(String dateeve) {
        this.dateeve = dateeve;
    }


    
}
