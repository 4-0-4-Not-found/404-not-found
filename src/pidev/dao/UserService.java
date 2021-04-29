/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.dao;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import java.time.Duration;
import java.time.Instant;

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
import pidev.Controller.MailService;
import pidev.Controller.randomStringFunction;
import pidev.utils.DataBase;
import pidev.entity.user;
/**
 *
 * @author momen
 */
public class UserService implements IService<user> {
    Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id); 
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
        String r=randomStringFunction.activationS();
        String req="INSERT INTO `user` (`email`, `roles`, `password`, `username`, `nom`, `prenom`, `tel`, `activation_token`, `validation`, `image`) VALUES ('"+o.getEmail()+"', '[\\\"ROLE_USER\\\"]', '"+argon2.hash(4, 65536, 1,o.getPassword()) +"', '"+o.getUsername()+"', '"+o.getNom()+"', '"+o.getPrenom()+"', '"+o.getTelephone()+"', '"+r+"', '0', '');";
        try {
            st.executeUpdate(req);
            MailService.sendMail(o.getEmail(),r);

        } catch (Exception ex) {
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
        return list;    
    }

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
        return false;
    }
    public boolean login(String email,String password){
        String test;
        String qery="select * from user where email = '"+email+"' AND validation ='"+1+"'";

        try {
               
            rs=st.executeQuery(qery);
            
            if (rs.next()) {
                test=rs.getString("password");
                
                if(argon2.verify(test, password)){
                    return true; 
                }
               
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; 
    }
   public List<user> displayAllList() {
       String req="select * from user";
        List<user> list=new ArrayList<>();
        
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
                p.setRole(rs.getString("validation"));

            }
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
         }
   public boolean validation(String email,String code){
        String qry = "UPDATE user SET validation = '1' WHERE email = '"+email+"' AND  activation_token = '"+code+"'";
        try {        
            if (st.executeUpdate(qry) > 0) {
               return true;  
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false; 
    }
     public user displayByemail(String mail) {
         String req="select * from user where email ="+mail;
           user p=new user();
        try {
            rs=st.executeQuery(req);
           // while(rs.next()){
      
            rs.next();
                p.setId(rs.getInt(1));
                p.setEmail(rs.getString("email"));
                p.setPrenom(rs.getString("prenom"));
                p.setRole(rs.getString("roles"));
                p.setNom(rs.getString("nom"));  
                p.setTelephone(rs.getInt("tel"));
                p.setUsername(rs.getString("username"));
                p.setValid(rs.getInt("validation"));
            //}  
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
    return p;    }
}  
