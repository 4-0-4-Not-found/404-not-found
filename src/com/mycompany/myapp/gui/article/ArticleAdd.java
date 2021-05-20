/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.article;

import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.mycompany.myapp.Services.ServicesArticles;
import com.mycompany.myapp.Services.ServicesUsers;
import com.mycompany.myapp.entities.article;
import com.mycompany.myapp.entities.user;

/**
 *
 * @author momen
 */
public class ArticleAdd extends Form{
    public ArticleAdd(Form previous,user u) {
        setTitle("Add a new user");
        setLayout(BoxLayout.y());
        
        TextField tnom = new TextField("","username");
        TextField tdescription = new TextField("","email");
       
        Button btnValider = new Button("Add task");
        
        btnValider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                if ((tnom.getText().length()==0)||(tdescription.getText().length()==0))
                    Dialog.show("Alert", "Please fill all the fields", new Command("OK"));
                else
                {
                    try {
                        article a = new article(tnom.getText(),tdescription.getText(),"dsfdsfsdfsd");
                        if( ServicesArticles.getInstance().addTask(u,a))
                            Dialog.show("Success","Connection accepted",new Command("OK"));
                        else
                            Dialog.show("ERROR", "Server error", new Command("OK"));
                    } catch (NumberFormatException e) {
                        Dialog.show("ERROR", "Status must be a number", new Command("OK"));
                    }
                    
                }
                
                
            }
        });
        
        addAll(tnom,tdescription,btnValider);
        getToolbar().addMaterialCommandToLeftBar("", FontImage.MATERIAL_ARROW_BACK
                , e-> previous.showBack()); // Revenir vers l'interface précédente
                
    }
}
