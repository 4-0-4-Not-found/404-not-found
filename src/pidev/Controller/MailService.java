/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.Controller;

/**
 *
 * @author momen
 */
import java.sql.PreparedStatement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailService {
    public static void sendMail(String recepient,String code) throws Exception{
        System.out.println("preparing send");
        Properties props=new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable","true");
        props.put("mail.smtp.host","smtp.gmail.com");
        props.put("mail.smtp.port","587");
        
        String myAccount="testesprit74@gmail.com";
        String password="testesprit74123@" ; 
        
        Session session=Session.getInstance(props, new Authenticator() {
             @Override
              protected PasswordAuthentication getPasswordAuthentication() {
                  
                 return new PasswordAuthentication(myAccount, password);
              }
            });
        
            Message message =prepareMessage(session,myAccount,recepient,code);
          
            System.out.println("message sent with success");
       
                   
                   
          Transport.send(message);
         }

        private static Message prepareMessage(Session session, String myAccount,String recepient,String code){
        try {
            Message message = new MimeMessage(session);
           
            message.setFrom(new InternetAddress(myAccount));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setText(code); 
            message.setSubject("confermation email from yours truly");
            return message;
            
        } catch (Exception ex) {
            Logger.getLogger(MailService.class.getName()).log(Level.SEVERE, null, ex);
        }
            return null;
         }
            
        
        
}
        
        
