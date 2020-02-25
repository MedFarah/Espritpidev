/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package easy.ride.entities;

/**
 *
 * @author suare
 */
public class Produit {
     private int id;
    private String nom;
    private String description;
    private String couleur;
   private int prix;
   private double prixt;
      private int remise;
   private double prixr;

    public Produit() {
    }

    public Produit(int id, String nom, String description, String couleur, int prix, double prixt, int remise, double prixr) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.couleur = couleur;
        this.prix = prix;
        this.prixt = prixt;
        this.remise = remise;
        this.prixr = prixr;
    }

    public Produit(String nom, String description, String couleur, int prix, int remise) {
        this.nom = nom;
        this.description = description;
        this.couleur = couleur;
        this.prix = prix;
        this.remise = remise;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return description;
    }

    public String getCouleur() {
        return couleur;
    }

    public int getPrix() {
        return prix;
    }

    public double getPrixt() {
        return prixt;
    }

    public int getRemise() {
        return remise;
    }

    public double getPrixr() {
        return prixr;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public void setPrixt(double prixt) {
        this.prixt = prixt;
    }

    public void setRemise(int remise) {
        this.remise = remise;
    }

    public void setPrixr(double prixr) {
        this.prixr = prixr;
    }

    @Override
    public String toString() {
        return "Produit{" + "id=" + id + ", nom=" + nom + ", description=" + description + ", couleur=" + couleur + ", prix=" + prix + ", prixt=" + prixt + ", remise=" + remise + ", prixr=" + prixr + '}';
    }

}