/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entites;

/**
 *
 * @author Aymen
 */
public class produit {
 int id;
 int prix;
 String type;
 String labelle;
 int quantite;
 String image;

    public produit() {
    }

    public produit(int prix, String type, String labelle, int quantite, String image) {
        this.prix = prix;
        this.type = type;
        this.labelle = labelle;
        this.quantite = quantite;
        this.image = image;
    }

    public produit(int id, int prix, String type, String labelle, int quantite, String image) {
        this.id = id;
        this.prix = prix;
        this.type = type;
        this.labelle = labelle;
        this.quantite = quantite;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLabelle() {
        return labelle;
    }

    public void setLabelle(String labelle) {
        this.labelle = labelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
 
}
