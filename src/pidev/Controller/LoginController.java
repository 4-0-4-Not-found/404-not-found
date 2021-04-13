/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import pidev.dao.UserService;

/**
 * FXML Controller class
 *
 * @author momen
 */
public class LoginController implements Initializable {
@FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    private Button login;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        login.setOnMouseClicked((event) -> {
            UserService pdao = UserService.getInstance();
            if(pdao.login(email.getText(), password.getText())){
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("success");
              alert.setHeaderText(null);
              alert.setContentText("user login with success!");
              alert.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Failed");
              alert.setHeaderText(null);
              alert.setContentText("user login failed!");
              alert.show();
            }
        });
       
    }    
    
}
