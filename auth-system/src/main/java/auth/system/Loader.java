/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.system;

import auth.system.service.AuthSystemService;
import auth.system.worker.ExpireWorker;
import datastore.manager.RepositoryManager;
import helma.xmlrpc.WebServer;
import java.io.IOException;
import org.json.JSONException;

/**
 *
 * @author juan
 */
public class Loader {
    public static void main(String[] args) throws JSONException {
        //getting a repository manager of datastore manager system
        RepositoryManager manager = new RepositoryManager();
        //creating a thread that initialize the webserver
        Thread t = new Thread(() -> {
            try {
                WebServer ws = new WebServer(9010);
                ws.addHandler("AuthSystem", new AuthSystemService(manager));
                ws.start();
            } catch (IOException ex) {
                throw new RuntimeException("an error occurred trying to initialize a xmlrpc-webserver: " + ex.getMessage());
            }
        });
        //initializing the webserver
        t.start();
        //initializing the worker 
        Thread w = new ExpireWorker("expireThread", manager);
        w.start();
    }
}
