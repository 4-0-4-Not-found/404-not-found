/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;

/**
 *
 * @author momen
 */
public class DataBase {
    private static DataBase instance;
    private Connection cnx;
    String url = "jdbc:mysql://localhost:3306/extrema11";

    private DataBase() {
        System.out.println("Instantiation de CNX");
        try {
            cnx = DriverManager.getConnection(url, "root", "");
            System.out.println("Connexion établie");
        } catch (SQLException ex) {
            System.err.println("Connexion impossible");
            System.err.println(ex);
        }
    }

    public static DataBase getInstance() {
        if (instance == null) {
            instance = new DataBase();
        }
        return instance;
    }
    
    public Connection getConnection(){
        return cnx;
    }
     
}
