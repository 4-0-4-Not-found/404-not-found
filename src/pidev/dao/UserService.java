/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.dao;

import pidev.dao.IService;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pidev.utils.DataBase;
import pidev.entity.user;

/**
 *
 * @author momen
 */
public class UserService implements IService<user> {
   
    private static UserService instance;
    private Statement st;
    private ResultSet rs;

    public UserService(){
        DataBase cs=DataBase.getInstance();
        try {
            st=cs.getConnection().createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     public static UserService getInstance(){
        if(instance==null) 
            instance=new UserService();
        return instance;
    }

    @Override
    public void insert(user o) {
        String req="INSERT INTO `user` (`email`, `roles`, `password`, `username`, `nom`, `prenom`, `tel`, `activation_token`, `validation`, `image`) VALUES ('"+o.getEmail()+"', '[\\\"ROLE_USER\\\"]', '"+o.getPassword()+"', '"+o.getUsername()+"', '"+o.getNom()+"', '"+o.getPrenom()+"', '"+o.getTelephone()+"', '"+o.getActiv()+"', '0', '');";
        try {
            st.executeUpdate(req);
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void delete(user o) {
        String req="delete from user where id="+o.getId();
        user p=displayById(o.getId());
        
          if(p!=null)
              try {
           
            st.executeUpdate(req);
             
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }else System.out.println("n'existe pas");    }

    @Override
    public ObservableList<user> displayAll() {
        String req="select * from user";
        ObservableList<user> list=FXCollections.observableArrayList();   
        try {
            rs=st.executeQuery(req);
            while(rs.next()){
                user p=new user();
                p.setId(rs.getInt(1));
                p.setEmail(rs.getString("email"));
                p.setPrenom(rs.getString("prenom"));
                p.setNom(rs.getString("nom"));  
                p.setTelephone(rs.getInt("tel"));
                p.setUsername(rs.getString("username"));
                p.setValid(rs.getInt("validation"));
                list.add(p);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;    }

    @Override
    public user displayById(int id) {
         String req="select * from user where id ="+id;
           user p=new user();
        try {
            rs=st.executeQuery(req);
           // while(rs.next()){
      
            rs.next();
                p.setId(rs.getInt(1));
                p.setEmail(rs.getString("email"));
                p.setPrenom(rs.getString("prenom"));
                p.setNom(rs.getString("nom"));  
                p.setTelephone(rs.getInt("tel"));
                p.setUsername(rs.getString("username"));
                p.setValid(rs.getInt("validation"));
            //}  
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    return p;    }

    @Override
    public boolean update(user os) {
        String qry = "UPDATE user SET nom = '"+os.getNom()+"', prenom = '"+os.getPrenom()+"', email = '"+os.getEmail()+"', tel = '"+os.getTelephone()+"', username = '"+os.getUsername()+"', validation = '"+os.getValid()+"' WHERE id = "+os.getId();
        
        try {
            if (st.executeUpdate(qry) > 0) {
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;    }
    public boolean login(String email,String password){
        String qery="select * from user where email = '"+email+"' AND password ='"+password+"' AND validation ='"+1+"'";
        try {
            rs=st.executeQuery(qery);
           
            if (rs.next()) {
                return true;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; 
    }
}
