/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package provider;

import helma.xmlrpc.WebServer;
import helma.xmlrpc.XmlRpcClient;
import helma.xmlrpc.XmlRpcException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;
import provider.repository.redis.Repository;
import provider.repository.redis.RepositorySingleton;
import provider.service.ProviderService;

/**
 *
 * @author juan
 */
public class Loader {

    public static void main(String[] args) throws InterruptedException {
        //storing a user by default
        Repository repo = RepositorySingleton.getInstance();
        repo.newUser("juanpablolvl99@gmail.com", "91071919");

        try {
            WebServer ws = new WebServer(9009);
            ws.addHandler("Provider", new ProviderService());
            ws.start();
        } catch (IOException ex) {
            throw new RuntimeException("an error occurred trying to initialize a xmlrpc-webserver: " + ex.getMessage());
        }
        
        try {
            XmlRpcClient client = new XmlRpcClient("http://auth:9010/RPC2");
//            XmlRpcClient client = new XmlRpcClient("http://localhost:9010/RPC2");
            //creating the parameters to call the registry procedure
            Vector params = new Vector();
            params.add(123);
            params.add("http://provider:9009/RPC2");
            //registering in the authsystem
            boolean reply = (Boolean) client.execute("AuthSystem.registry", params);
//            System.out.println("the provider was registered? " + reply);
//            //waiting the client executes
//            Thread.sleep(6000);
//            //checking if a token is valid
//            params.clear();
//            //getting the token stored by the user
//            String token = repo.getTokens().get(0);
//            //mounting the query json object
//            JSONObject object = new JSONObject();
//            object.put("appID", 123);
//            object.put("token", token);
//            params.add(object.toString());
//            //calling the remote procedure that will return a json object
//            String objectSt = (String) client.execute("AuthSystem.isTokenValid", params);
//            System.out.println("the given token is still valid?");
//            System.out.println(objectSt);
//            //expiring a token with a provider order
//            reply = (Boolean) client.execute("AuthSystem.expireNow", params);
//            System.out.println("the given token was removed of the db? " + reply);
//            repo.removeToken(token);
        } catch (MalformedURLException ex) {
            throw new RuntimeException("an error occurred trying to initialize a xmlrpc-client: " + ex.getMessage());
        } catch (XmlRpcException | IOException ex) {
            throw new RuntimeException("an error occurred trying to call a remote method: " + ex.getMessage());
        }
        //do another operations relationed with the provider vendor
        //...
    }
}
