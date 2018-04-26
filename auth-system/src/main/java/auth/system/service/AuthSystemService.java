/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auth.system.service;

import datastore.manager.RepositoryManager;
import helma.xmlrpc.XmlRpcClient;
import helma.xmlrpc.XmlRpcException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.UUID;
import java.util.Vector;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author juan
 */
public class AuthSystemService {

    private final RepositoryManager manager;
    private XmlRpcClient xmlClient;

    public AuthSystemService(RepositoryManager manager) {
        this.manager = manager;
    }

    /**
     * this method is used by providers for store in the db an appID and an
     * related URL.
     *
     * @param appID
     * @param URL
     * @return boolean
     */
    public boolean registry(int appID, String URL) {
        return manager.storeAppID(appID, URL);
    }

    /**
     * this method is used for both provider and client that want to know if an
     * token is still valid. the query string is a JSON object that has an appID
     * and an token valued key.
     *
     * @param query
     * @return String
     */
    public String isTokenValid(String query) {
        //parsing the query string to JSONObject
        JSONObject queryObject = new JSONObject(query);
        //getting the value of token key
        String token = queryObject.getString("token");
        //calling the manager method to verify if the token is in the db
        boolean reply = manager.isValid(token);
        //mounting the response JSON object that will be returned to the
        //remote caller
        JSONObject replyObject = new JSONObject();
        //for redis repo
        replyObject.put("appID", queryObject.getInt("appID"));
        //for sql repo
        //replyObject.put("appID", queryObject.getString("appID"));
        replyObject.put("is_valid", reply);
        return replyObject.toString();
    }

    /**
     * this method is used by the provider that want to expire immediately an
     * given token, the query string is a JSON object that has an appID and an
     * token valued key.
     *
     * @param query
     * @return boolean
     */
    public boolean expireNow(String query) {
        //parsing the query string to JSONObject.
        JSONObject queryObject = new JSONObject(query);
        //getting the value of token key.
        String token = queryObject.getString("token");
        //return true if the token has been removed
        //or false if the token doesn't exist or not
        //could be removed.
        return manager.removeToken(token);
    }

    /**
     * this method is used by the client that wants to authenticate in the
     * system, the query string parameter is a JSON object that has an appID, an
     * email, and a password valued key. That info are be used by the system to
     * ask if the client passed email and password exist in the provider
     * database, through the provider service; that service address will be
     * founded using the appID value, that has a related URL stored in the
     * system database, if the appID don't have any URL stored in the database,
     * or the client login isn't found in provider, a JSON object with error
     * info will be returned.
     *
     * @param query
     * @return String
     */
    public String auth(String query) {
        JSONObject queryObject;

        //necessary info
        int appID;
        String email;
        String senha;
        String URL;

        //try count for exceptions when calling the provider to check a login is
        //allows 3 attemp, when count reach 3, a JSON object with error info is
        //returned
        int attempts = 0;

        try {
            //parsing the query string to JSONObject.            
            queryObject = new JSONObject(query);
            //getting the necessary info to do the auth functionality.
            appID = queryObject.getInt("appID");
            //searching in the database a related URL for the given appID. 
            URL = manager.getURL(appID);
            email = queryObject.getString("email");
            senha = queryObject.getString("senha");
        } catch (JSONException ex) {
            throw new RuntimeException("an error occurred trying to parse a string into a json object: " + ex.getMessage());
        }

        try {
            //if the URL string return isn't empty then create
            //a RPC client for this address.
            if (!URL.equals("")) {
                xmlClient = new XmlRpcClient(URL);
            } else {
                System.out.println("the URL was not founded, returning an error message");
                //if the URL is empty create a JSON object with the error info 
                //and send back to the remote caller.
                JSONObject replyObject = new JSONObject();
                replyObject.put("appID", appID);
                replyObject.put("success", false);
                replyObject.put("error_code", 564);
                replyObject.put("error_msg", "não foi possível encontrar a URL associada ao appID fornecido.");
                return replyObject.toString();
            }
        } catch (MalformedURLException ex) {
            throw new RuntimeException("an error occurred trying to create a xmlRpcClient: " + ex.getMessage());
        }

        //if nothing goes wrong create a parameters vector with the clien login 
        //info that will be passed for the remote procedure.
        Vector params = new Vector();
        params.add(email);
        params.add(senha);

        //call the processCall method, thats care about call the remote procedure
        //and deal with the exceptions.
        return processCall(params, attempts, email, appID);
    }

    /**
     * this method is used by the auth method to handle the procedure call and
     * the exceptions, is responsible to try 3 times in case of server
     * exceptions, even with the 3 attempts, the server don't work, a error JSON
     * object will be returned.
     *
     * @param params
     * @param attempts
     * @param appID
     * @return String
     * @throws JSONException
     */
    private String processCall(Vector params, int attempts, String email, int appID) throws JSONException {
        try {
            //calling the remote provider procedure to check if the client exists,
            //if exists returns true, else, false.
            boolean reply = (Boolean) xmlClient.execute("Provider.checkLogin", params);
            //creating the response object that will be return to the client
            JSONObject replyObject = new JSONObject();
            //depending on the returned response of the provider, a different
            //object will be returned.
            if (reply) {
                //if true, creates a random token and get the time of his creation
                //that will be used for check if the token have to expire.
                long createdIn = System.currentTimeMillis();
                String token = UUID.randomUUID().toString();
                //set the response object info
                replyObject.put("appID", appID);
                replyObject.put("token", token);
                replyObject.put("success", reply);
                //store the token in the system database 
                manager.storeToken(token, appID, email, createdIn);
                params.clear();
                params.add(token);
                params.add(Long.toString(createdIn));
                //store the token in the provider database 
                xmlClient.execute("Provider.storeToken", params);

                return replyObject.toString();
            } else {
                //if false, return a JSON object with the error info
                replyObject.put("appID", appID);
                replyObject.put("success", reply);
                replyObject.put("error_code", 563);
                replyObject.put("error_msg", "não foi possível confirmar seu login.");

                return replyObject.toString();
            }
        } catch (IOException | XmlRpcException ex) {
            //if attempts count reach 3, then return a error object
            if (attempts == 3) {
                JSONObject replyObject = new JSONObject();
                replyObject.put("appID", appID);
                replyObject.put("success", false);
                replyObject.put("error_code", 99999);
                replyObject.put("error_msg", "Não foi possível realizar autenticação devido a falha no servidor.");
                return replyObject.toString();
            }
            //increment attempts count
            attempts++;
            return processCall(params, attempts, email, appID);
        }
    }
}
