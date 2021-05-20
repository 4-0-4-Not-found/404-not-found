/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.entities;

/**
 *
 * @author momen
 */
public class article {
    int id;
    String nom,Description,image;

    public article() {
    }

    public article(String nom, String Description, String image) {
        this.nom = nom;
        this.Description = Description;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "article{" + "id=" + id + ", nom=" + nom + ", Description=" + Description + ", image=" + image + '}';
    }
    
    
}
