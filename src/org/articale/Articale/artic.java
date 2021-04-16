/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.articale.Articale;

/**
 *
 * @author Alpha
 */
public class artic {
    private int id;
    private String description;
    private String nom;
    private String comments;


    public artic(int id, String description, String nom) {
        this.id = id;
        this.description = description;
        this.nom = nom;
        this.comments = comments;
      
    }

    public int getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getNom() {
        return nom;
    }
      public String getcomments() {
        return comments;
    }

    void show(String comments_windowfxml) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
}
