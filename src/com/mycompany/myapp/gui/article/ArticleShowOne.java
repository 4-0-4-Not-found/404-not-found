/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.article;

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
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.Services.ServicesArticles;
import com.mycompany.myapp.Services.ServicesUsers;
import com.mycompany.myapp.entities.article;
import com.mycompany.myapp.entities.user;
import com.mycompany.myapp.gui.SideMenuBaseForm;
import com.mycompany.myapp.gui.user.UserShow;
import com.mycompany.myapp.gui.user.UserUpdate;
import java.util.ArrayList;

/**
 *
 * @author momen
 */
public class ArticleShowOne  extends SideMenuBaseForm {
    Form current;    
    public ArticleShowOne(Form previous,article a,user us,Resources res) {
         
           super( BoxLayout.y());
        ///////////////////////////////////////////////////////////////////////////////////        
        //////////////////////////////////////////////////////////////////////////////////        
        Button btnUpdateuser = new Button("edit user");
        btnUpdateuser.addActionListener(e -> new ArticleUpdate(current,a,us,res).show());
        Button btnDeleteuser= new Button("delete user");
        btnDeleteuser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                ServicesArticles.getInstance().deleteArticle(a.getId());
                new UserShow(us, current, res).showBack();
            }
        });
        setTitle("List tasks");
        for (article usz : getUsers(a.getId())) {
            System.out.println(usz.getId());
            this.add(createElement(a));
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
               setupSideMenu(us,res);

    }
     public ArrayList<article> getUsers(Integer id){
         ArrayList<article> users=ServicesArticles.getInstance().getOneArticle(id);
        try {
           
           users=ServicesArticles.getInstance().getOneArticle(id);

        } catch (Exception ex) {
            //System.err.println();
            ex.printStackTrace();
        }
      return users;
     }
    private Container createElement(article u) {
        Container element = new Container(BoxLayout.y());
        Container topContainer = new Container(BoxLayout.y());
        Container midContainer = new Container();
        Container bottomContainer = new Container(new FlowLayout(CENTER));

        Label lnom = new Label("nom: "+u.getNom());
        Label ldescription = new Label("Prenom :"+u.getDescription());
       
         
        topContainer.addAll(lnom, ldescription);

        
        
        
       
      
        
       
        
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
