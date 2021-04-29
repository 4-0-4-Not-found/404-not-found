/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pidev.dao.UserService;

/**
 * FXML Controller class
 *
 * @author momen
 */
public class ValidationController implements Initializable {
@FXML
    private Label L_mail;

    @FXML
    private Label L_code;

    @FXML
    private TextField Email;

    @FXML
    private TextField code;

    @FXML
    private Button validate;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       validate.setOnMouseClicked((event) -> {
            UserService pdao = UserService.getInstance();
            if(pdao.validation(Email.getText(), code.getText())){
                try {
                    Parent page1 = FXMLLoader.load(getClass().getResource("/pidev/views/login.fxml"));
                    Scene scene = new Scene(page1);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(ValidationController.class.getName()).log(Level.SEVERE, null, ex);
                }
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
