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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
                
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/pidev/views/Accueil.fxml"));
                    Parent root = loader.load();
                    UpdateuserController controller=loader.getController();
                    controller.receiveData( pdao.displayByemail(email.getText()));
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Second Window");
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
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
