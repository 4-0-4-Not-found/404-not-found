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
import com.mycompany.myapp.entities.article;
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
public class ServicesArticles {

    JSONObject json = new JSONObject();
    public ArrayList<article> users;
    
    public static ServicesArticles instance = null;
    public boolean resultOK;
    private ConnectionRequest req;

    private ServicesArticles() {
        req = new ConnectionRequest();
    }

    public static ServicesArticles getInstance() {
        if (instance == null) {
            instance = new ServicesArticles();
        }
        return instance;
    }

    public boolean addTask(user u,article a) {
        String url = DataBase.BASE_URL + "/users/api/articles/add/"+u.getId();
        ConnectionRequest post = new ConnectionRequest() {
            @Override
            protected void buildRequestBody(OutputStream os) throws IOException {
                os.write(json.toString().getBytes("UTF-8"));
            }
        };
        post.setUrl(url);// Insertion de l'URL de notre demande de connexion
        post.setPost(true);
        json.put("nom", a.getNom());
        json.put("description", a.getDescription());
        json.put("image", a.getImage());
        
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

    public ArrayList<article> parseTasks(String jsonText) {
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
                article a = new article();
                float id = Float.parseFloat(obj.get("id").toString());
                a.setId((int) id);
                a.setNom(obj.get("nom").toString());
                a.setDescription(obj.get("description").toString());
               
                //Ajouter la tâche extraite de la réponse Json à la liste
                users.add(a);
                
            }

        } catch (IOException ex) {

        }
        /*
            A ce niveau on a pu récupérer une liste des tâches à partir
        de la base de données à travers un service web
        
         */
        return users;
    }

    public ArrayList<article> getAllArticles() {
        String url = DataBase.BASE_URL + "/users/api/articles";
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
     public ArrayList<article> getOneArticle(Integer id) {
        String url = DataBase.BASE_URL + "/users/api/article/show/"+id;
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
    public boolean deleteArticle(Integer id) {
        String url = DataBase.BASE_URL + "/users/api/article/delete/"+id;
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
     public boolean updateArticle(Integer id,user u,article a) {
        String url = DataBase.BASE_URL + "/users/api/article/update/"+id;
        ConnectionRequest post = new ConnectionRequest() {
            @Override
            protected void buildRequestBody(OutputStream os) throws IOException {
                os.write(json.toString().getBytes("UTF-8"));
            }
        };
        post.setUrl(url);// Insertion de l'URL de notre demande de connexion
        post.setPost(true);
        json.put("nom", a.getNom());
        json.put("description", a.getDescription());
       
        
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
   
}
