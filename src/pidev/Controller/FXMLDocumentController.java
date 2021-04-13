/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import pidev.dao.UserService;
import pidev.entity.user;

/**
 *
 * @author momen
 */
public class FXMLDocumentController implements Initializable {
    
   
    @FXML
    private TableView<user> Table_user;
    
    @FXML
    private TableColumn<user, Integer> id_col;
    
    @FXML
    private TableColumn<user, String> nom_col;

    @FXML
    private TableColumn<user, String> prenom_col;

    @FXML
    private TableColumn<user, String> email_col;

    @FXML
    private TableColumn<user, Integer> tel_col;
    @FXML
    private TableColumn<user, String> un_col;
    
     
    
    @FXML
    private Button delete;
    @FXML
    private Button add;
    @FXML
    private Button update;
    
    private DataList listdata = new DataList();

    private int var;
    UserService pdao = UserService.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
     
        Table_user.setItems(listdata.getUsers());
        id_col.setCellValueFactory(new PropertyValueFactory<>("id"));
        nom_col.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom_col.setCellValueFactory(new PropertyValueFactory<>("Prenom"));
        tel_col.setCellValueFactory(new PropertyValueFactory<>("telephone")); 
        email_col.setCellValueFactory(new PropertyValueFactory<>("Email")); 
        un_col.setCellValueFactory(new PropertyValueFactory<>("username")); 

        //!!!!!!!!!!!!!!!!!!!!!!!!!geting the client id!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        Table_user.setOnMouseClicked((event) -> {
            var=listdata.getUsers().get(Table_user.getSelectionModel().getSelectedIndex()).getId();
            pdao.displayById(var);
        });
        
        //!!!!!!!!!!!!!!!!!redirecting the the adduser interface!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        add.setOnAction(event -> {
          try {
                Parent page1 = FXMLLoader.load(getClass().getResource("/pidev/views/AdduserFXML.fxml"));
                Scene scene = new Scene(page1);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //!!!!!!!!!!!!!!!!!redirecting the the update interface!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        update.setOnAction(event -> {
          try {
               
              
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/pidev/views/updateuser.fxml"));
                Parent root = loader.load();
                UpdateuserController controller=loader.getController();
                controller.receiveData(pdao.displayById(var));
                System.out.println(pdao.displayById(var).getNom());
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Second Window");
                stage.show();
            } catch (IOException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        //!!!!!!!!!!!!!!!!!!!!!deleting the client from the database!!!!!!!!!!!!!!!!!!!!!!!!!!!
        delete.setOnMouseClicked((event) -> {
              pdao.delete(pdao.displayById(var));
              Table_user.setItems(listdata.getUsers());
        });
            }    
           
}
