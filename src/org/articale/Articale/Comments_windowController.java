/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.articale.Articale;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Alpha
 */
public class Comments_windowController implements Initializable {

    @FXML
    private Button commentbtn;
    @FXML
    private TextField textfield;
    @FXML
    private Label label1;
       
   

    @FXML
    private void actionbtn(ActionEvent event) {
        
            label1.setText(textfield.getText());
      
            
    
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        
    }

    private void show() {
        
    }

    @FXML
    private void textfieldd(ActionEvent event) {
    }
    
   
    

    
    
}
