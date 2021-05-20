/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myapp.Services;

import com.codename1.io.CharArrayReader;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.JSONParser;
import com.codename1.io.NetworkEvent;
import com.codename1.io.NetworkManager;
import com.codename1.ui.events.ActionListener;
import com.mycompany.myapp.entities.user;
import com.mycompany.myapp.utils.DataBase;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/**
 *
 * @author momen
 */
public class ServicesUsers {

    JSONObject json = new JSONObject();
    public ArrayList<user> users;
    user use;
    public static ServicesUsers instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServicesUsers() {
        req = new ConnectionRequest();
    }

    public static ServicesUsers getInstance() {
        if (instance == null) {
            instance = new ServicesUsers();
        }
        return instance;
    }

    public boolean addTask(user u) {
        String url = DataBase.BASE_URL + "/users/api/add";
        ConnectionRequest post = new ConnectionRequest() {
            @Override
            protected void buildRequestBody(OutputStream os) throws IOException {
                os.write(json.toString().getBytes("UTF-8"));
            }
        };
        post.setUrl(url);// Insertion de l'URL de notre demande de connexion
        post.setPost(true);
        json.put("email", u.getEmail());
        json.put("password", u.getPassword());
        json.put("username", u.getUsername());
        json.put("nom", u.getNom());
        json.put("tel", u.getTelephone());
        json.put("prenom", u.getPrenom());
        json.put("image", u.getImage());
        post.setContentType("APPLICATION/JSON");

        post.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                resultOK = post.getResponseCode() == 200; //Code HTTP 200 OK
                post.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(post);
        return resultOK;
    }

    public ArrayList<user> parseTasks(String jsonText) {
        try {
            users = new ArrayList<>();
            JSONParser j = new JSONParser();// Instanciation d'un objet JSONParser permettant le parsing du résultat json

            /*
                On doit convertir notre réponse texte en CharArray à fin de
            permettre au JSONParser de la lire et la manipuler d'ou vient 
            l'utilité de new CharArrayReader(json.toCharArray())
            
            La méthode parse json retourne une MAP<String,Object> ou String est 
            la clé principale de notre résultat.
            Dans notre cas la clé principale n'est pas définie cela ne veux pas
            dire qu'elle est manquante mais plutôt gardée à la valeur par defaut
            qui est root.
            En fait c'est la clé de l'objet qui englobe la totalité des objets 
                    c'est la clé définissant le tableau de tâches.
             */
            Map<String, Object> tasksListJson = j.parseJSON(new CharArrayReader(jsonText.toCharArray()));

            /* Ici on récupère l'objet contenant notre liste dans une liste 
            d'objets json List<MAP<String,Object>> ou chaque Map est une tâche.               
            
            Le format Json impose que l'objet soit définit sous forme
            de clé valeur avec la valeur elle même peut être un objet Json.
            Pour cela on utilise la structure Map comme elle est la structure la
            plus adéquate en Java pour stocker des couples Key/Value.
            
            Pour le cas d'un tableau (Json Array) contenant plusieurs objets
            sa valeur est une liste d'objets Json, donc une liste de Map
             */
            List<Map<String, Object>> list = (List<Map<String, Object>>) tasksListJson.get("root");

            //Parcourir la liste des tâches Json
            for (Map<String, Object> obj : list) {
                //Création des tâches et récupération de leurs données
                user u = new user();
                float id = Float.parseFloat(obj.get("id").toString());
                u.setId((int) id);
                u.setEmail(obj.get("email").toString());
                u.setNom(obj.get("nom").toString());
                u.setPrenom(obj.get("prenom").toString());
                u.setRole(obj.get("roles").toString());
                float tel = Float.parseFloat(obj.get("tel").toString());
                u.setTelephone((int) tel);
                //Ajouter la tâche extraite de la réponse Json à la liste
                users.add(u);
                
            }

        } catch (IOException ex) {

        }
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
         */
        return users;
    }

    public ArrayList<user> getAllTasks() {
        String url = DataBase.BASE_URL + "/users/api";
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
     public ArrayList<user> getOneUser(Integer id) {
        String url = DataBase.BASE_URL + "/users/api/show/"+id;
        req.setUrl(url);
        req.setPost(false);
        req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                users = parseTasks(new String(req.getResponseData()));
                req.removeResponseListener(this);
            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return users;
    }
    public boolean deleteUser(Integer id) {
        String url = DataBase.BASE_URL + "/users/api/delete/"+id;
        req.setUrl(url);
        req.setPost(false);
         req.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {

                resultOK = req.getResponseCode() == 200; //Code HTTP 200 OK
                req.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(req);
        return resultOK;
    } 
     public boolean updateUser(Integer id,user u) {
        String url = DataBase.BASE_URL + "/users/api/update/"+id;
        ConnectionRequest post = new ConnectionRequest() {
            @Override
            protected void buildRequestBody(OutputStream os) throws IOException {
                os.write(json.toString().getBytes("UTF-8"));
            }
        };
        post.setUrl(url);// Insertion de l'URL de notre demande de connexion
        post.setPost(true);
        json.put("email", u.getEmail());
        json.put("password", u.getPassword());
        json.put("username", u.getUsername());
        json.put("nom", u.getNom());
        json.put("tel", u.getTelephone());
        json.put("prenom", u.getPrenom());
        json.put("image", u.getImage());
        post.setContentType("APPLICATION/JSON");

        post.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                
                resultOK = post.getResponseCode() == 200; //Code HTTP 200 OK
                post.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(post);
        return resultOK;
    } 
    public ArrayList<user> Login(String email) {
        String url = DataBase.BASE_URL + "/users/api/login";
        ConnectionRequest post = new ConnectionRequest() {
            @Override
            protected void buildRequestBody(OutputStream os) throws IOException {
                os.write(json.toString().getBytes("UTF-8"));
            }
        };
        post.setUrl(url);// Insertion de l'URL de notre demande de connexion
        post.setPost(true);
        json.put("email", email);
       
       
        post.setContentType("APPLICATION/JSON");

        post.addResponseListener(new ActionListener<NetworkEvent>() {
            @Override
            public void actionPerformed(NetworkEvent evt) {
                resultOK = post.getResponseCode() == 200; //Code HTTP 200 OK
                users= parseTasks(new String(post.getResponseData()));
                post.removeResponseListener(this); //Supprimer cet actionListener
                /* une fois que nous avons terminé de l'utiliser.
                La ConnectionRequest req est unique pour tous les appels de 
                n'importe quelle méthode du Service task, donc si on ne supprime
                pas l'ActionListener il sera enregistré et donc éxécuté même si 
                la réponse reçue correspond à une autre URL(get par exemple)*/
               

            }
        });
        NetworkManager.getInstance().addToQueueAndWait(post);
        return users;
    }  
}
