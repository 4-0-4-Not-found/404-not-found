/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Controller;

import java.util.Random;

/**
 *
 * @author momen
 */
public class randomStringFunction {
    public static String activationS(){
        String s = "";
        String chaine="ABCDEFGHIJKLMNOPQRSITUVWYXZ0123456789";
        for (int i=0;i<20;i++)
    {        
            int r=(int)(Math.random() * 37);
            s+=Character.toString(chaine.charAt(r));
    }
    return s;
    } 
    
}
