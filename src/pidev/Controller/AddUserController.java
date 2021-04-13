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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import pidev.dao.UserService;
import pidev.entity.user;

/**
 * FXML Controller class
 *
 * @author momen
 */
public class AddUserController implements Initializable {

    @FXML
    private AnchorPane AnchorPane;
    @FXML
    private Button inscription;
@FXML
    private TextField username;

    @FXML
    private TextField nom;

    @FXML
    private TextField prenom;

    @FXML
    private TextField telephone;

    @FXML
    private TextField email;

    @FXML
    private TextField password;

    @FXML
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       inscription.setOnAction(event -> {
            user p = new user(Integer.parseInt(telephone.getText()),username.getText(),nom.getText(), prenom.getText(),email.getText(),password.getText());
            UserService pdao = UserService.getInstance();
            pdao.insert(p);      
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setHeaderText(null);
            alert.setContentText("Personne insérée avec succés!");
            alert.show();
            nom.setText("");
            prenom.setText("");
        });
    }    
    
}
