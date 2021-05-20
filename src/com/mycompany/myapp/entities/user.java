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
public class user {
    int id,telephone,valid;
    String username,nom,prenom,email,activ,image,password,role;

    public user() {
    }

    public user(int telephone, String username, String nom, String prenom, String email, String password,String image) {
        this.telephone = telephone;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.image = image;
    }

    public user(int id, int telephone, String username, String nom, String prenom, String email, String activ, int valid, String image, String password,String role) {
        this.id = id;
        this.telephone = telephone;
        this.username = username;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.activ = activ;
        this.valid = valid;
        this.image = image;
        this.password = password;
        this.role = role;
    }

    
    
  
    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getTelephone() {
        return telephone;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getActiv() {
        return activ;
    }

    public int getValid() {
        return valid;
    }

    public String getImage() {
        return image;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }
    
    
    public void setId(int id) {
        this.id = id;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActiv(String activ) {
        this.activ = activ;
    }

    public void setValid(int valid) {
        this.valid = valid;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "user{" + "id=" + id + ", telephone=" + telephone + ", valid=" + valid + ", username=" + username + ", nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", activ=" + activ + ", image=" + image + ", password=" + password + ", role=" + role + '}';
    }

   
    
    
}
