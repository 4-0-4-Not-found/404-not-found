/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Controller;

import pidev.entity.user;
import pidev.dao.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author momen
 */
public class DataList {
     private ObservableList<user> users=FXCollections.observableArrayList();

    public DataList() {
        
        UserService pdao=UserService.getInstance();
        users= pdao.displayAll();
        System.out.println(users);
    }
    
    public ObservableList<user> getUsers(){
        return users;
    }
}
