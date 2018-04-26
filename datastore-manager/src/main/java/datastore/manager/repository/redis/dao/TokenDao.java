/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastore.manager.repository.redis.dao;

import datastore.manager.repository.redis.dao.infra.ConFactory;
import java.util.Set;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

/**
 *
 * @author juan
 */
public class TokenDao {
    private final Jedis conn;
    
    public TokenDao() {
        conn = ConFactory.getConnection();
    }
    
    /**
     * 
     * @param token
     * @param json
     * @return String
     */
    public String storeToken(String token, String json) {
        return conn.set(token, json);
    }
    
    /**
     * 
     * @param token
     * @return long 
     */
    public long removeToken(String token) {
        return conn.del(token);
    }
    
    /**
     * 
     * @param token
     * @return long
     */
    public long getTokenTime(String token) {
        String json = conn.get(token);
        if(json == null)
            return 0;
        JSONObject object = new JSONObject(json);
        return object.getLong("createdIn");
    }
    
    /**
     * 
     * @return Set 
     */
    public Set<String> getTokens() {
        return conn.keys("*[A-z]*[0-9]*");
    }
    
    /**
     * 
     * @param user_email
     * @return String
     */
    public String recoverUserToken(String user_email) {
        Set<String> keys = conn.keys("*[A-z]*[0-9]*");
        for (String key : keys) {
            String json = conn.get(key);
            if(json.contains(user_email)){
                return key;
            }
        }
        return null;
    }
}
