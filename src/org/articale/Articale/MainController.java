/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.articale.Articale;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import static javafx.collections.FXCollections.observableList;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Alpha
 */
public class MainController implements Initializable {
    
  
    @FXML
    private TextField tfid;
    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfimg;
    @FXML
    private TextField tfdesc;
    @FXML
    private TableView<artic> tvbox;
    @FXML
    private TableColumn<artic, Integer> colid;
    @FXML
    private TableColumn<artic, String> colnom;
    @FXML
    private TableColumn<artic, Integer> colimg;
    @FXML
    private TableColumn<artic, String> coldesc;
    @FXML
    private Button btninsert;
    @FXML
    private Button btnupdate;
    @FXML
    private Button btndelete;
    @FXML
    private Label label;
    @FXML
    private TextField filterfield;
    @FXML
    private Button commentsbtn;
    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private TextField textfield1;
    @FXML
    private Button comments;
    @FXML
    
   
       

    
    private void handleButtonAction(ActionEvent event) {
       
        if (event.getSource() == btninsert){
           insertRecord();
       }else if(event.getSource() == btnupdate){
         updateRecord();
    }else if(event.getSource() == btndelete){
        delete();
    }
         
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        showartic();
       
    }    
    public Connection getConnection(){
        Connection conn;
        try{
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/articales", "root", "");
            return conn;
        }catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
            return null;
        }
        
    }
    public ObservableList<artic> getarticList(){
        ObservableList<artic> articList = FXCollections.observableArrayList();
        Connection conn = getConnection();
        String query = "SELECT * FROM ARTIC" ;
        Statement st;
        ResultSet rs;
        
        try{
            st = conn.createStatement();
            rs = st.executeQuery(query);
            artic artic;
            while(rs.next()){
                artic = new artic(rs.getInt("id"), rs.getString("nom"), rs.getString("description"));
                articList.add(artic);
            
            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return articList;
        
    }
    public void showartic(){
        ObservableList<artic> dataList = getarticList();
       
        colid.setCellValueFactory(new PropertyValueFactory<artic, Integer>("id"));
        colnom.setCellValueFactory(new PropertyValueFactory<artic, String>("nom"));
        coldesc.setCellValueFactory(new PropertyValueFactory<artic, String>("description"));
     
        tvbox.setItems(dataList);
         ObservableList<artic> datalist = getarticList();
         FilteredList<artic> filteredData = new FilteredList<>(datalist, b->true);
         filterfield.setOnKeyReleased(b ->{
        filterfield.textProperty().addListener((obserValue, oldValue, newValue) -> {
            filteredData.setPredicate(artic -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                
                if (artic.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if (String.valueOf(artic.getDescription()).indexOf(lowerCaseFilter) != -1)
                    return true ;
                else 
                    return false;
            });
          });
         });
        SortedList<artic> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tvbox.comparatorProperty());
        tvbox.setItems(sortedData);
       }
    private void insertRecord(){
        String query ="INSERT INTO ARTIC VALUES (" + tfid.getText() + ",'" + tfnom.getText() + "','" + tfdesc.getText()+ "'," + tfimg.getText() + ")";
        executeQuery(query);
        showartic();
    }
    private void updateRecord(){
        String query = "UPDATE artic SET  nom = '" + tfnom.getText() + "', image = '" + tfimg.getText() +
            "', description = '" +  tfdesc.getText() + "' WHERE id = " + tfid.getText() + "";
        executeQuery(query);
        showartic();
    }
    private void delete(){
        String query ="DELETE FROM artic WHERE id =" + tfid.getText() + "";
        executeQuery(query);
        showartic();
        
    }

    private void executeQuery(String query) {
       Connection conn = getConnection();
       Statement st;
       try{
           st = conn.createStatement();
           st.executeUpdate(query);
       }catch(Exception ex){
           ex.printStackTrace();
       }
    }

    @FXML
     private void handleButtonAction(MouseEvent event) {
         
        artic artic = tvbox.getSelectionModel().getSelectedItem();
        tfid.setText("" + artic.getId());
        tfnom.setText(artic.getNom());
        tfdesc.setText(artic.getDescription());
        
    }

    @FXML
    private void handleButtonActionns(ActionEvent event ) {
        try{
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("comments_window.fxml"));
        Parent root1 = (Parent) fxmlLoader.load( );
          Stage stage = new Stage();
          stage.initStyle(StageStyle.DECORATED);
            stage.setScene(new Scene(root1));  
                stage.show();                
        }catch (IOException e){
            System.out.println("can't load new window");
        }
    }

    @FXML
    private void actioncomtbtn(ActionEvent event) {
        label2.setText(textfield1.getText());
        label1.setText(tfnom.getText());
          String query ="INSERT INTO ARTIC VALUES (" + textfield1.getText() + ")";
        executeQuery(query);
    }

 
    
   
}
    
