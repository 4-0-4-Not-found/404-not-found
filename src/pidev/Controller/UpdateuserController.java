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
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pidev.dao.UserService;
import pidev.entity.user;

/**
 * FXML Controller class
 *
 * @author momen
 */
public class UpdateuserController implements Initializable {
    @FXML
    private TextField username;

    @FXML
    private TextField password;

    @FXML
    private TextField email;

    @FXML
    private TextField telephone;

    @FXML
    private TextField prenom;

    @FXML
    private TextField nom;
     @FXML
    private Button update;
      @FXML
    private CheckBox activation;
     public user p ;
    /**
     * Initializes the controller class.
     */
      public void receiveData(user u) {
          username.setText(u.getUsername());
          nom.setText(u.getNom());
          prenom.setText(u.getPrenom());
          telephone.setText(String.valueOf(u.getTelephone()));
          email.setText(u.getEmail());
          System.out.println(u.getValid());
          if(u.getValid()==1){
              activation.setSelected(true);
          }
          p=u;     
         
        }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
          update.setOnAction((event) -> {
              p.setNom(nom.getText());
              p.setPrenom(prenom.getText());
              p.setTelephone(Integer.valueOf(telephone.getText()));
              p.setEmail(email.getText());
              if(activation.isSelected()){
                  p.setValid(1);              
              }
              UserService pdao = UserService.getInstance();
              pdao.update(p);
              Alert alert = new Alert(Alert.AlertType.INFORMATION);
              alert.setTitle("Information Dialog");
              alert.setHeaderText(null);
              alert.setContentText("Personne insérée avec succés!");
              alert.show();
          });
    }    
    
}
