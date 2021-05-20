/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.gui.user;

import com.codename1.components.SpanLabel;
import com.codename1.db.Cursor;
import com.codename1.db.Database;
import com.codename1.db.Row;
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
import java.io.IOException;
import java.util.ArrayList;
import com.codename1.ui.util.Resources;
import com.mycompany.myapp.gui.SideMenuBaseForm;

/**
 *
 * @author momen
 */
public class UserShow extends SideMenuBaseForm{
    Form current;
    
    public UserShow(user u,Form previous,Resources res) {
       
        super(BoxLayout.y());
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
        Container titleComponent
                = BorderLayout.north(
                        BorderLayout.west(menuButton).add(BorderLayout.EAST, settingsButton)
                ).
                add(BorderLayout.CENTER, space).
                add(BorderLayout.SOUTH,
                        FlowLayout.encloseIn(
                                new Label("  Project  ", "WelcomeBlue"),
                                new Label("list", "WelcomeWhite")
                        ));
        titleComponent.setUIID("BottomPaddingContainer");
        tb.setTitleComponent(titleComponent);

        Label separator = new Label("", "BlueSeparatorLine");
        separator.setShowEvenIfBlank(true);
        add(separator);

            Container B = new Container();

            Button add = new Button("Create New Project");
            add.setUIID("LoginButton");
            add.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {
                    new UserAdd(current).show();
                }
            });
            B.add(add);
            add(B);
        
        for (user us : ServicesUsers.getInstance().getAllTasks()) {
            Container InfoContainer = new Container(BoxLayout.y());
            Label l = new Label("Project " + us.getId());
            Label date = new Label(us.getNom());
            Label time = new Label(us.getEmail());

            InfoContainer.addAll(l, date, time);

            Container Container = new Container(BoxLayout.x());

            Container.add(res.getImage("project.png").scaled(150, 150));
            Container.add(InfoContainer);

            add(Container);

            date.addPointerPressedListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent evt) {

                    new UserShowOne(current ,us,u, res).show();

                }
            });
        }

        
            setupSideMenu(u,res); 
        
    }
     

    @Override
    protected void showOtherForm(user u, Resources res) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
