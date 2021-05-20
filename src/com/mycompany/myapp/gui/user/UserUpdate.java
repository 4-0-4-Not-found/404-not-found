/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.user;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.Services.ServicesUsers;
import com.mycompany.myapp.entities.user;
import java.util.ArrayList;

/**
 *
 * @author momen
 */
public class UserUpdate  extends Form{
     user u;
     public UserUpdate(Form previous,user u,user us,Resources res) {
         
        setTitle("update a user");
        setLayout(BoxLayout.y());
         ArrayList<user> users=ServicesUsers.getInstance().getOneUser(us.getId());
        System.out.println(us.getId());
        TextField tusername = new TextField(users.get(0).getUsername(),"username");
        TextField temail = new TextField(users.get(0).getEmail(),"email");
        TextField tnom = new TextField(users.get(0).getNom(),"nom");
        TextField tprenom = new TextField(users.get(0).getPrenom(),"prenom");
        TextField ttelephone = new TextField(String.valueOf(users.get(0).getTelephone()) ,"telephone");
        TextField tpassword = new TextField(users.get(0).getPrenom(),"password"); 
        Button btnValider = new Button("edit task");
     
           
        
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
              
                if ((tusername.getText().length()==0)||(temail.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        user use = new user(Integer.parseInt(ttelephone.getText()),tusername.getText(), temail.getText(), tnom.getText(), tprenom.getText(), tpassword.getText(),"dsfdsfsdfsd");
                        if( ServicesUsers.getInstance().updateUser(us.getId(),use))
                             new UserShowOne(previous, u, us, res).showBack();
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tusername,temail,tnom,tprenom,ttelephone,tpassword,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
}
