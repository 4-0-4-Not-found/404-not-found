/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import pidev.dao.UserService;
import pidev.entity.user;

/**
 * FXML Controller class
 *
 * @author momen
 */
public class PiestatController implements Initializable {
   @FXML
    private PieChart piechart;
   ObservableList<Data> list=FXCollections.
            observableArrayList();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         UserService pdao=UserService.getInstance();
        List<user> client=pdao.displayAllList();
        for(user u:client) {
            list.addAll(
                new Data(u.getNom(), 12.0)             
        );
        }
        piechart.setAnimated(true);
        piechart.setData(list);
    }    
    
}
