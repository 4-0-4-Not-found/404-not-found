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
import com.mycompany.myapp.Services.ServicesUsers;
import com.mycompany.myapp.entities.user;

/**
 *
 * @author momen
 */
public class UserAdd extends Form{
    
   public UserAdd(Form previous) {
        setTitle("Add a new user");
        setLayout(BoxLayout.y());
        
        TextField tusername = new TextField("","username");
        TextField temail = new TextField("","email");
        TextField tnom = new TextField("","nom");
        TextField tprenom = new TextField("","prenom");
        TextField ttelephone = new TextField("","telephone");
        TextField tpassword = new TextField("","password");

        Button btnValider = new Button("Add task");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tusername.getText().length()==0)||(temail.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        user u = new user(Integer.parseInt(ttelephone.getText()),tusername.getText(),tnom.getText(), tprenom.getText(),temail.getText(), tpassword.getText(),"dsfdsfsdfsd");
                        if( ServicesUsers.getInstance().addTask(u))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
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
