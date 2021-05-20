/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.user;

import com.codename1.components.ImageViewer;
import com.codename1.ui.Button;
import static com.codename1.ui.Component.CENTER;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.myapp.Services.ServicesUsers;
import com.mycompany.myapp.entities.user;
import java.util.ArrayList;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.gui.SideMenuBaseForm;

/**
 *
 * @author momen
 */
public class UserShowOne extends SideMenuBaseForm{
    Form current;    
    public UserShowOne(Form previous,user u,user us,Resources res) {
         
           super( BoxLayout.y());
        ///////////////////////////////////////////////////////////////////////////////////        
        //////////////////////////////////////////////////////////////////////////////////        
        Button btnUpdateuser = new Button("edit user");
        
        btnUpdateuser.addActionListener(e -> new UserUpdate(current,us,u,res).show());
        Button btnDeleteuser= new Button("delete user");
        btnDeleteuser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ServicesUsers.getInstance().deleteUser(u.getId());
                new UserShow(us, current, res).showBack();
            }
        });
        setTitle("List tasks");
        for (user usz : getUsers(u.getId())) {
            System.out.println(usz.getId());
            this.add(createElement(u));
            this.add(btnUpdateuser);
             this.add(btnDeleteuser);
             
        }
     
        Toolbar tb = getToolbar();
        tb.setTitleCentered(false);
        Image profilePic = res.getImage("bleu.png");        
        Image tintedImage = Image.createImage(profilePic.getWidth(), profilePic.getHeight());
        Graphics g = tintedImage.getGraphics();
        g.drawImage(profilePic, 0, 0);
        g.drawImage(res.getImage("gradient-overlay.png"), 0, 0, profilePic.getWidth(), profilePic.getHeight());
        
        tb.getUnselectedStyle().setBgImage(tintedImage);
        
        Button menuButton = new Button("");
        menuButton.setUIID("Title");
        FontImage.setMaterialIcon(menuButton, FontImage.MATERIAL_MENU);
        menuButton.addActionListener(e -> getToolbar().openSideMenu());

        Button settingsButton = new Button("");
        settingsButton.setUIID("Title");
        FontImage.setMaterialIcon(settingsButton, FontImage.MATERIAL_SETTINGS);
        
        Label space = new Label("", "TitlePictureSpace");
        space.setShowEvenIfBlank(true);
        Container titleComponent = 
                BorderLayout.north(
                    BorderLayout.west(menuButton).add(BorderLayout.EAST, settingsButton)
                ).
                add(BorderLayout.CENTER, space).
                add(BorderLayout.SOUTH, 
                        FlowLayout.encloseIn(
                                new Label( "WelcomeBlue")
//                                new Label("list", "WelcomeWhite")
                        ));
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);
        
        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);
        add(separator);

          ImageViewer img = new ImageViewer(res.getImage("project.png").scaled(500, 500));
               setupSideMenu(u,res);

    }
     public ArrayList<user> getUsers(Integer id){
         ArrayList<user> users=ServicesUsers.getInstance().getOneUser(id);
        try {
           
           users=ServicesUsers.getInstance().getOneUser(id);

        } catch (Exception ex) {
            //System.err.println();
            ex.printStackTrace();
        }
      return users;
     }
    private Container createElement(user u) {
        Container element = new Container(BoxLayout.y());
        Container topContainer = new Container(BoxLayout.y());
        Container midContainer = new Container();
        Container bottomContainer = new Container(new FlowLayout(CENTER));

        Label lnom = new Label("nom: "+u.getNom());
        Label lprenom = new Label("Prenom :"+u.getPrenom());
        Label lemail = new Label("Email :"+u.getEmail());
         Label ltelephone = new Label("telephone :"+String.valueOf(u.getTelephone()));
        topContainer.addAll(lnom, lprenom,lemail,ltelephone);

        
        
        
       
      
        
       
        
        element.addAll(topContainer);
        
        Button b = new Button();
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
            }
        });
        
        element.setLeadComponent(b);
        return element;
        
    }
       
    @Override
    protected void showOtherForm(user u, Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
