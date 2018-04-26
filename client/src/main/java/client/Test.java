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
import org.json.JSONObject;

/**
 *
 * @author juan
 */
public class Test {

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
            long init = System.currentTimeMillis();
            String reply;
            // 50req/617ms
            //100req/965ms
            //200req/1834ms
            //200req/1133ms 1067ms 864ms 738ms # redis
            for (int i = 0; i < 200; i++) {
                reply = (String) client.execute("AuthSystem.auth", params);
            }
            long end = System.currentTimeMillis();
            System.out.println(end - init + " milliseconds");
        } catch (MalformedURLException ex) {
            throw new RuntimeException("an error occurred trying to initialize a xmlrpc-client: " + ex.getMessage());
        } catch (XmlRpcException | IOException ex) {
            throw new RuntimeException("an error occurred trying to call a remote method: " + ex.getMessage());
        }
    }
}
