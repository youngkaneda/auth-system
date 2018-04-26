/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import helma.xmlrpc.XmlRpcClient;
import helma.xmlrpc.XmlRpcException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author juan
 */
public class Loader {

    public static void main(String[] args) throws InterruptedException {
        try {
            XmlRpcClient client = new XmlRpcClient("http://auth:9010/RPC2");
//            XmlRpcClient client = new XmlRpcClient("http://localhost:9010/RPC2");
            //creating the parameters to call the registry procedure
            Vector params = new Vector();
            //mounting the query json object
            JSONObject object = new JSONObject();
            object.put("appID", 123);
            object.put("email", "juanpablolvl99@gmail.com");
            object.put("senha", "91071919");
            //adding the query object string to the pamareters
            params.add(object.toString());
            //calling the remote procedure, requesting to auth
            String reply = (String) client.execute("AuthSystem.auth", params);
            //mounting the json object with response string
            JSONObject query = new JSONObject(reply);
            System.out.println(query.toString());
            params.clear();
            //passing a query object string to parameters 
            params.add(query.toString());
            //calling the remote procedure that verify if a given token is valid
            String isValid = (String) client.execute("AuthSystem.isTokenValid", params);
            //true
            System.out.println(isValid);
            //waiting the authsystem expire the token
            Thread.sleep(30000);
            params.clear();
            params.add(query.toString());
            //calling the remote procedure that verify if a given token is valid
            isValid = (String) client.execute("AuthSystem.isTokenValid", params);
            //false
            System.out.println(isValid);
        } catch (MalformedURLException ex) {
            throw new RuntimeException("an error occurred trying to initialize a xmlrpc-client: " + ex.getMessage());
        } catch (XmlRpcException | IOException ex) {
            throw new RuntimeException("an error occurred trying to call a remote method: " + ex.getMessage());
        } catch (JSONException ex) {
            throw new RuntimeException("an error occurred trying to create a json object: " + ex.getMessage());
        }
    }
}
